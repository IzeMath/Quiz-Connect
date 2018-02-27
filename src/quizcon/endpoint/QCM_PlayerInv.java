package quizcon.endpoint;

public class QCM_PlayerInv extends QCMessage {

	private final String name;
	private final int roomId;
	private final String passw;

	public QCM_PlayerInv(final String name, final int roomId, final String passw) {
		this.name = name;
		this.roomId = roomId;
		this.passw = passw;
	}

	public String getName() {
		return name;
	}

	public int getRoomId() {
		return roomId;
	}

	public String getPassw() {
		return passw;
	}

}
