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

import org.quizcon.Question;
import org.quizcon.QuestionParameters;
import org.user.Utilisateur;

import questionServices.QuestionsServiceRemote;
import roomServices.RoomServicesRemote;
import userService.UserServicesRemote;

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

	private boolean createAndJoinRoom(final Session session, final int roomId, final String password) {
		GameRoom gr = get(roomId);
		log.info("id : " + roomId);
		boolean isMaster = false;
		if (rsr.canAccess(roomId, password)) {// en bdd
			if (gr == null) {// Creation endpoint
				gr = new GameRoom(roomId);
				GameRooms.add(gr);
				isMaster = true;
			}
			gr.addSession(session);// rejoindre
			rsr.addPlayer(roomId);
			log.info("ok");
			session.getUserProperties().put("roomId", roomId);
			session.getUserProperties().put("score", 0);
			session.getUserProperties().put("inRoom", true);
			session.getUserProperties().put("master", isMaster);
			
			return true;
		} else {
			log.info("cant access");
			return false;
		}
	}

	@OnOpen
	public void open(final Session session) {
		log.info("OnOpen");
		session.getUserProperties().put("type", "moniteur");
		session.getUserProperties().put("roomId", 0);

		session.getUserProperties().put("logInv", false);
		session.getUserProperties().put("logUser", false);
		session.getUserProperties().put("inRoom", false);
		session.getUserProperties().put("master", false);
	}

	@OnClose
	public void close(final Session session) {
		log.info("OnClose");
		try {
			final int id = (int) session.getUserProperties().get("roomId");
			GameRoom gr = null;
			if (id != 0) {
				gr = get(id);
				gr.removeSession(session);
				log.info("OnClose1");
			}
			if (gr != null) {
				GameRooms.remove(gr);
				rsr.removePlayer(id);
			}
			session.close();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendSessionStatus(final Session session) throws IOException, EncodeException {
		final QCMessage qcmess = new QCMessage();
		qcmess.setType("inform");
		qcmess.setConnectInv((boolean) session.getUserProperties().get("logInv"));
		qcmess.setConnectUser((boolean) session.getUserProperties().get("logUser"));
		qcmess.setConnectRoom((boolean) session.getUserProperties().get("inRoom"));
		qcmess.setMaster((boolean) session.getUserProperties().get("master"));
		
		session.getBasicRemote().sendObject(qcmess);
	}

	@OnMessage
	public void onMessage(final Session session, final QCMessage message) throws IOException, EncodeException {
		final QCMessage qcmess = new QCMessage();

		final int id = (int) session.getUserProperties().get("roomId");
		GameRoom gr = null;
		if (id != 0) {
			gr = get(id);
		}

		switch (message.getType()) {
		case "logInv":
			log.info("ASK FOR logInv");
			session.getUserProperties().put("type", "mobile");//TODO a changer voir mess client
			session.getUserProperties().put("name", message.getInvitName());
			session.getUserProperties().put("logInv", true);

			sendSessionStatus(session);
			break;

		case "logUser":
			log.info("ASK FOR logUser");
			session.getUserProperties().put("type", "mobile");
			final Utilisateur user = usr.login(message.getUserName(), message.getPassword());
			if (user != null) {
				session.getUserProperties().put("user", user);
				session.getUserProperties().put("name", user.getUsername());
				session.getUserProperties().put("logUser", true);
			}
			sendSessionStatus(session);
			break;

		case "createRoom":
			log.info("ASK FOR createRoom");
			final int newRoomId = rsr.createRoom(message.getRoomName(), message.getRoomPassword());
			createAndJoinRoom(session, newRoomId, message.getRoomPassword());
			sendSessionStatus(session);
			break;

		case "joinRoom":
			log.info("ASK FOR joinRoom");
			createAndJoinRoom(session, message.getRoomId(), message.getRoomPassword());
			sendSessionStatus(session);
			break;

		case "init":
			log.info("ASK FOR INIT");
			log.info(message.getQp().toString());

			final QuestionParameters qp = message.getQp();
			gr.setQp(qp);// pas utile?

			gr.setArq(qsr.getQuestionsWithParameters(qp));

			break;

		case "question":
			log.info("ASK FOR QUESTION");
			final Question q = gr.nextQuestion();

			if (q != null) {
				qcmess.setType("question");
				qcmess.setQuestion(q.getLibelle());
				qcmess.setReponses(q.getListRepInString());
				qcmess.setExplication(q.getExplications());
			}
			for (final Session s : gr.getArs()) {// Faire distinction entre moniteur et mobile?
				s.getBasicRemote().sendObject(qcmess);
			}
			break;

		case "reponse":
			log.info("ASK FOR RESPONSE");
			if (gr.getLaReponse().equals(message.getReponse())) {// Si bonne reponse
				int score = (int) session.getUserProperties().get("score");
				score += 1;
				session.getUserProperties().put("score", score);
				log.info("GOOD JOB!");

				qcmess.setType("score");
				qcmess.setScore(gr.getScoreList());
				for (final Session s : gr.getArs()) {// Faire distinction entre moniteur et mobile
					s.getBasicRemote().sendObject(qcmess);
				}
			}
			break;
		}

	}

}
