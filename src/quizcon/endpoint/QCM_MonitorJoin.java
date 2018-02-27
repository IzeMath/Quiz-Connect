package quizcon.endpoint;

public class QCM_MonitorJoin extends QCMessage {

	private int roomId = 0;
	private String roomPassw = "";

	public QCM_MonitorJoin(final int roomId, final String roomPassw) {
		this.roomId = roomId;
		this.roomPassw = roomPassw;
	}

	public int getRoomId() {
		return roomId;
	}

	public String getRoomPassw() {
		return roomPassw;
	}

}
