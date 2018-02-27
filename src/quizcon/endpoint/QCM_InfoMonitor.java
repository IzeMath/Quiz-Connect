package quizcon.endpoint;

public class QCM_InfoMonitor extends QCMessage {

	private final String roomId;

	public QCM_InfoMonitor(final String rommId) {
		this.roomId = rommId;
	}

	public String getRoomId() {
		return roomId;
	}

}
