package quizcon.endpoint;

import java.util.Hashtable;

public class QCM_Score extends QCMessage {

	private final Hashtable<String, Integer> tabScore;

	public QCM_Score(final Hashtable<String, Integer> tabScore) {
		this.tabScore = tabScore;
	}

	public Hashtable<String, Integer> getTabScore() {
		return tabScore;
	}
	

}
