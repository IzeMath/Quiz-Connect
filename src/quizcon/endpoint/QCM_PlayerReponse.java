package quizcon.endpoint;

public class QCM_PlayerReponse extends QCMessage {

	private final String reponse;

	public QCM_PlayerReponse(final String reponse) {
		this.reponse = reponse;
	}

	public String getReponse() {
		return reponse;
	}

	

}
