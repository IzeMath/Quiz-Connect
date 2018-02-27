package quizcon.endpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.user.Utilisateur;

import questionServices.QuestionsServiceRemote;
import roomServices.RoomServicesRemote;
import userService.UserServicesRemote;

/**
 * @author IzeMath
 *
 */
@Stateless
@ServerEndpoint(value = "/Quizcon", encoders = QCMessageEncoder.class, decoders = QCMessageDecoder.class)
public class QuizEndPoint {
	private final Logger log = Logger.getLogger(getClass().getName());
	private static final Set<GameRoom> GameRooms = Collections.synchronizedSet(new HashSet<GameRoom>());

	@EJB
	RoomServicesRemote rsr;

	@EJB
	QuestionsServiceRemote qsr;

	@EJB
	UserServicesRemote usr;

	private GameRoom get(final int roomID) {
		GameRoom tmp = new GameRoom(roomID);
		if (GameRooms.contains(tmp)) {
			for (final GameRoom gr : GameRooms) {
				if (gr.equals(tmp)) {
					tmp = gr;
					break;
				}
			}
			return tmp;
		} else {
			return null;
		}
	}

	@OnOpen
	public void open(final Session session) {
		log.info("OnOpen");
	}

	@OnClose
	public void close(final Session session) throws IOException {
		log.info("OnClose");
		String type = "";
		int roomId = 0;
		GameRoom gr = null;
		try {
			type = (String) session.getUserProperties().get("type");
			roomId = (int) session.getUserProperties().get("roomId");
			gr = get(roomId);
		} catch (final Exception e) {
			// Session pas ini
			session.close();
			return;
		}

		if (type.equals("moniteur")) {
			final boolean master = (boolean) session.getUserProperties().get("master");
			gr.removeMonitor(session);

			// La session master a quitté
			if (master) {
				log.info("Change Master");
				// S'il n'y a pas d'autre master suppression de la salle
				if (!gr.setANewMaster()) {
					log.info("salon supprimé");
					gr.interrupt();
					gr.logoutAllPlayers();
					GameRooms.remove(gr);

					try {
						rsr.deleteRoom(roomId);
					} catch (final Exception e) {
					}
				}
				session.close();
			}
		}
		if (type.equals("mobile")) {
			gr.removePlayer(session);
			if (session.getUserProperties().get("user") != null) {
				final Utilisateur user = (Utilisateur) session.getUserProperties().get("user");
				usr.updateStat(user);
			}

			session.close();
		}

	}

	/**
	 * @param s
	 * @return true si la session peut démarer une partie master = true, type =
	 *         moniteur, roomId init
	 */
	private boolean canStartGame(final Session s) {
		try {
			final int roomId = (int) s.getUserProperties().get("roomId");
			final boolean isMaster = (boolean) s.getUserProperties().get("master");
			final String type = (String) s.getUserProperties().get("type");
			if (roomId != 0 && isMaster && type.equals("moniteur")) {
				return true;
			} else {
				return false;
			}
		} catch (final Exception e) {
			return false;
		}
	}

	private boolean canGiveResponse(final Session s) {
		try {
			final int roomId = (int) s.getUserProperties().get("roomId");
			final String type = (String) s.getUserProperties().get("type");
			final GameRoom tmp = get(roomId);

			if (tmp != null && type.equals("mobile")) {
				return true;
			} else {
				return false;
			}
		} catch (final Exception e) {
			return false;
		}
	}

	@OnMessage
	public void onMessage(final Session session, final QCMessage message) throws IOException, EncodeException {

		// Mobile
		if (message instanceof QCM_PlayerConnected) {
			final QCM_PlayerConnected messPlayerConnected = (QCM_PlayerConnected) message;

			log.info("DEMANDE : mobile connected");
			final Utilisateur user = usr.getById(messPlayerConnected.getUserId());

			if (user != null && rsr.canAccess(messPlayerConnected.getRoomId(), messPlayerConnected.getPassw())) {
				final GameRoom gameR = get(messPlayerConnected.getRoomId());
				if (gameR != null) {
					// Attribut session
					session.getUserProperties().put("type", "mobile");
					session.getUserProperties().put("user", user);
					session.getUserProperties().put("name", user.getUsername());
					session.getUserProperties().put("score", 0);
					session.getUserProperties().put("reponse", "");
					session.getUserProperties().put("roomId", messPlayerConnected.getRoomId());

					gameR.addPlayer(session);
				} else {
					log.warning("ERREUR Le salon: " + messPlayerConnected.getRoomId() + " n'existe pas en WS");
				}
			} else {
				log.warning("ERREUR La session mobile n'a pas pu se connecter au salon: "
						+ messPlayerConnected.getRoomId());
			}

		}
		// Mobile
		if (message instanceof QCM_PlayerInv) {
			final QCM_PlayerInv messPlayerInv = (QCM_PlayerInv) message;

			log.info("DEMANDE : mobile invit");

			if (rsr.canAccess(messPlayerInv.getRoomId(), messPlayerInv.getPassw())) {
				final GameRoom gameR = get(messPlayerInv.getRoomId());
				if (gameR != null) {

					// Attribut session
					session.getUserProperties().put("type", "mobile");
					session.getUserProperties().put("name", messPlayerInv.getName());
					session.getUserProperties().put("score", 0);
					session.getUserProperties().put("reponse", "");
					session.getUserProperties().put("roomId", messPlayerInv.getRoomId());

					gameR.addPlayer(session);
				} else {
					log.warning("ERREUR Le salon: " + messPlayerInv.getRoomId() + " n'existe pas en WS");
				}

			} else {
				log.warning("ERREUR La session mobile n'a pas pu se connecter au salon: " + messPlayerInv.getRoomId());
			}
		}

		// Moniteur
		if (message instanceof QCM_MonitorCreate) {
			final QCM_MonitorCreate messMonitorCreate = (QCM_MonitorCreate) message;

			log.info("DEMANDE : monitor create");

			// Creation Room
			final int newRoomId = rsr.createRoom(messMonitorCreate.getRoomName(), messMonitorCreate.getRoomPassw());
			final GameRoom gameR = new GameRoom(newRoomId);
			gameR.addMonitor(session);
			GameRooms.add(gameR);

			// Attribut session
			session.getUserProperties().put("type", "moniteur");
			session.getUserProperties().put("master", true);
			session.getUserProperties().put("roomId", newRoomId);

			log.info("Le salon " + newRoomId + " a été créé");
		}
		// Moniteur
		if (message instanceof QCM_MonitorJoin) {
			final QCM_MonitorJoin messMonitorJoin = (QCM_MonitorJoin) message;

			log.info("DEMANDE : monitor join");

			if (rsr.canAccess(messMonitorJoin.getRoomId(), messMonitorJoin.getRoomPassw())) {
				final GameRoom gameR = get(messMonitorJoin.getRoomId());

				if (gameR != null) {
					gameR.addMonitor(session);

					// Attribut session
					session.getUserProperties().put("type", "moniteur");
					session.getUserProperties().put("master", false);
					session.getUserProperties().put("roomId", messMonitorJoin.getRoomId());
				} else {
					log.warning("ERREUR Le salon: " + messMonitorJoin.getRoomId() + " n'existe pas en WS");
				}

			} else {
				log.warning(
						"ERREUR La session moniteur n'a pas pu se connecter au salon: " + messMonitorJoin.getRoomId());
			}

		}
		// Moniteur
		if (message instanceof QCM_StartGame) {
			final QCM_StartGame messStartGame = (QCM_StartGame) message;

			log.info("DEMANDE : start game");
			// Controle session
			if (canStartGame(session)) {

				final int roomId = (int) session.getUserProperties().get("roomId");
				final GameRoom gameR = get(roomId);

				// Controle room
				if (gameR != null) {

					gameR.setArq(qsr.getQuestionsWithParameters(messStartGame.getQp()));
					gameR.start();

				} else {
					log.warning("ERREUR Le salon: " + roomId + " n'existe pas en WS");
				}
			} else {
				log.warning("ERREUR La session n'est pas correctement initialisée");
			}

		}

		// Mobile
		if (message instanceof QCM_PlayerReponse) {
			final QCM_PlayerReponse messPlayerReponse = (QCM_PlayerReponse) message;

			log.info("DEMANDE : player reponse");
			if (canGiveResponse(session)) {
				session.getUserProperties().put("reponse", messPlayerReponse.getReponse());
			} else {
				log.warning("ERREUR La session n'est pas correctement initialisée");
			}
		}

	}

}
