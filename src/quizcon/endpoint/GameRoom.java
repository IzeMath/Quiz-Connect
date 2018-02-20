package quizcon.endpoint;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.websocket.Session;

import org.quizcon.Question;
import org.quizcon.QuestionParameters;

public class GameRoom {
	private int id;
	private String name;
	private final ArrayList<Session> ars = new ArrayList<>();

	private List<Question> arq;
	private Question doing = null;
	private QuestionParameters qp;

	public GameRoom(final int id) {
		this.id = id;
	}

	public boolean addSession(final Session s) {
		return ars.add(s);
	}

	public boolean removeSession(final Session s) {
		return ars.remove(s);
	}

	public boolean isEnd() {
		return arq.isEmpty();
	}

	public Question nextQuestion() {
		if (!arq.isEmpty()) {
			final Question q = arq.get(0);
			doing = q;
			arq.remove(0);
			return q;
		} else {
			return null;
		}
	}

	public String getLaReponse() {
		return doing.getLaReponse().getLibelle();
	}

	public Hashtable<String, Integer> getScoreList() {
		final Hashtable<String, Integer> tabScore = new Hashtable<>();
		for (final Session s : ars) {
			if(s.getUserProperties().get("type").equals("mobile")) {
				final String name = (String) s.getUserProperties().get("name");
				final int score = (int) s.getUserProperties().get("score");
				tabScore.put(name, score);
			}
		}

		return tabScore;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public ArrayList<Session> getArs() {
		return ars;
	}

	public List<Question> getArq() {
		return arq;
	}

	public void setArq(final List<Question> arq) {
		this.arq = arq;
	}

	public QuestionParameters getQp() {
		return qp;
	}

	public void setQp(final QuestionParameters qp) {
		this.qp = qp;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		if (id != other.id)
			return false;
		return true;
	}

}
