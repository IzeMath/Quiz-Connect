package quizcon.endpoint;

import org.quizcon.QuestionParameters;

public class QCM_StartGame extends QCMessage {

	private final QuestionParameters qp;

	public QCM_StartGame(final QuestionParameters qp) {
		this.qp = qp;
	}

	public QuestionParameters getQp() {
		return qp;
	}

}
