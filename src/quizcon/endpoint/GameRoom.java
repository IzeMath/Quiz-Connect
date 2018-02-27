package quizcon.endpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import org.quizcon.Question;
import org.user.Statistique;
import org.user.Utilisateur;

public class GameRoom extends Thread {
	private final int num;

	private final ArrayList<Session> arPlayers = new ArrayList<>();
	private final ArrayList<Session> arMonitors = new ArrayList<>();
	private List<Question> arq;

	public GameRoom(final int num) {
		this.num = num;
	}

	public void start() {
		try {
			for (final Question question : arq) {
				final QCM_Question messQuestion = new QCM_Question(question);

				// Envoyer la question
				messQuestion.setType("question");
				for (final Session monitor : arMonitors) {
					monitor.getBasicRemote().sendObject(messQuestion);
				}

				// Envoyer les choix de réponses
				messQuestion.setType("listRep");
				for (final Session player : arPlayers) {
					player.getBasicRemote().sendObject(messQuestion);
				}

				// Temps pour répondre
				Thread.sleep(12000);

				// Envoyer l'explications
				messQuestion.setType("explications");
				for (final Session monitor : arMonitors) {
					monitor.getBasicRemote().sendObject(messQuestion);
				}

				// Envoyer la bonne réponse
				messQuestion.setType("reponse");
				for (final Session player : arPlayers) {
					player.getBasicRemote().sendObject(messQuestion);
				}

				// Mise a jour des scores
				updateScore(question.getLaReponse().getLibelle(), question.getTheme());
				sendScoreList();

				// Temps entre 2 questions pour lire la bonne réponse
				Thread.sleep(5000);

			}

		} catch (final InterruptedException | IOException | EncodeException e) {
			e.printStackTrace();
		}
	}

	private void updateScore(final String reponse, final String theme) {
		boolean goodAnswer = false;
		for (final Session player : arPlayers) {
			goodAnswer = false;
			if (reponse.equals(player.getUserProperties().get("reponse"))) {
				int score = (int) player.getUserProperties().get("score");
				score++;
				player.getUserProperties().put("score", score);
				player.getUserProperties().put("reponse", "");
				goodAnswer = true;
			}
			if (player.getUserProperties().get("user") != null) {
				final Utilisateur user = (Utilisateur) player.getUserProperties().get("user");
				for (final Statistique stat : user.getlStat()) {
					if (stat.getTheme().equals(theme)) {
						stat.setNbQuestions(stat.getNbQuestions() + 1);
						if (goodAnswer) {
							stat.setNbReponse(stat.getNbReponse() + 1);
						}
					}
					break;
				}
			}
		}
	}

	public synchronized void addPlayer(final Session s) {
		arPlayers.add(s);
		sendScoreList();
	}

	public synchronized void removePlayer(final Session s) {
		arPlayers.remove(s);
		sendScoreList();
	}

	public synchronized void addMonitor(final Session s) throws IOException, EncodeException {
		arMonitors.add(s);
		final QCM_InfoMonitor messInfoMonitor = new QCM_InfoMonitor("" + num);
		s.getBasicRemote().sendObject(messInfoMonitor);
	}

	public synchronized void removeMonitor(final Session s) {
		arMonitors.remove(s);
	}

	public void sendScoreList() {
		try {
			final Hashtable<String, Integer> tabScore = new Hashtable<>();
			for (final Session player : arPlayers) {
				final String name = (String) player.getUserProperties().get("name");
				final int score = (int) player.getUserProperties().get("score");
				tabScore.put(name, score);
			}

			final QCM_Score messScore = new QCM_Score(tabScore);
			for (final Session monitor : arMonitors) {
				monitor.getBasicRemote().sendObject(messScore);
			}
		} catch (IOException | EncodeException e) {
			e.printStackTrace();
		}
	}

	public void setArq(final List<Question> arq) {
		this.arq = arq;
	}

	public boolean setANewMaster() {
		if (!arMonitors.isEmpty()) {
			arMonitors.get(0).getUserProperties().put("master", true);
			return true;
		} else {
			return false;
		}
	}

	public void logoutAllPlayers() throws IOException {
		for (final Session player : arPlayers) {
			player.close();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + num;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GameRoom other = (GameRoom) obj;
		if (num != other.num)
			return false;
		return true;
	}

}
