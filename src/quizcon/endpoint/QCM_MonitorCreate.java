package quizcon.endpoint;

public class QCM_MonitorCreate extends QCMessage {

	private String roomName = "";
	private String roomPassw = "";

	public QCM_MonitorCreate(final String roomName, final String roomPassw) {
		this.roomName = roomName;
		this.roomPassw = roomPassw;
	}

	public String getRoomName() {
		return roomName;
	}

	public String getRoomPassw() {
		return roomPassw;
	}

}
