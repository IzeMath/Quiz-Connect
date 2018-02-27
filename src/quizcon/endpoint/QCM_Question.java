package quizcon.endpoint;

import org.quizcon.Question;

public class QCM_Question extends QCMessage {

	private String type;
	private final Question question;
	
	public QCM_Question(final Question question) {
		this.question = question;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public Question getQuestion() {
		return question;
	}
	
	


}
