package quizcon.endpoint;

import java.util.UUID;

public class QCM_PlayerConnected extends QCMessage {

	private final UUID userId;
	private final int roomId;
	private final String passw;
	
	public QCM_PlayerConnected(final UUID userId, final int roomId, final String passw) {
		this.userId = userId;
		this.roomId = roomId;
		this.passw = passw;
	}

	public UUID getUserId() {
		return userId;
	}

	public int getRoomId() {
		return roomId;
	}

	public String getPassw() {
		return passw;
	}

	

}
