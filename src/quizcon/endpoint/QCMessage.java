package quizcon.endpoint;

import java.util.Hashtable;

import org.quizcon.QuestionParameters;

public class QCMessage {
	// Donne une indication sur le message
	private String type;

	// Utilisé pour initialiser type : init pour endpoint
	private QuestionParameters qp;

	// Donne une question type : question pour tout les clients
	private String question;
	private String[] reponses;
	private String explication;

	// Donne une reponse type : reponse pour endpoint
	private String reponse;

	// Donne le score type : score pour tout les client
	private Hashtable<String, Integer> score;

	// Donne le login et le mdp pour connexion type : logUser pour endpoint
	private String userName;
	private String password;

	// Donne le nom de l'invité pour connexion type : logInv pour endpoint
	private String invitName;
	private boolean moniteurType;

	// Donne l'id room type : joinRoom pour endpoint
	private int roomId;

	// Pour créer une room type : createRoom pour endpoint
	private String roomName;
	private String roomPassword;

	// true ou false pour status utilisateur type : inform pour client
	private boolean connectInv = false;
	private boolean connectUser = false;
	private boolean connectRoom = false;
	private boolean isMaster = false;
	
	
	
	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public QuestionParameters getQp() {
		return qp;
	}

	public void setQp(final QuestionParameters qp) {
		this.qp = qp;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(final String question) {
		this.question = question;
	}

	public String[] getReponses() {
		return reponses;
	}

	public void setReponses(final String[] reponses) {
		this.reponses = reponses;
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(final String reponse) {
		this.reponse = reponse;
	}

	public String getExplication() {
		return explication;
	}

	public void setExplication(final String explication) {
		this.explication = explication;
	}

	public Hashtable<String, Integer> getScore() {
		return score;
	}

	public void setScore(final Hashtable<String, Integer> score) {
		this.score = score;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getInvitName() {
		return invitName;
	}

	public void setInvitName(final String invitName) {
		this.invitName = invitName;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(final int roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(final String roomName) {
		this.roomName = roomName;
	}

	public String getRoomPassword() {
		return roomPassword;
	}

	public void setRoomPassword(final String roomPassword) {
		this.roomPassword = roomPassword;
	}

	public boolean isConnectInv() {
		return connectInv;
	}

	public void setConnectInv(final boolean connectInv) {
		this.connectInv = connectInv;
	}

	public boolean isConnectUser() {
		return connectUser;
	}

	public void setConnectUser(final boolean connectUser) {
		this.connectUser = connectUser;
	}

	public boolean isConnectRoom() {
		return connectRoom;
	}

	public void setConnectRoom(final boolean connectRoom) {
		this.connectRoom = connectRoom;
	}

	public boolean isMaster() {
		return isMaster;
	}

	public void setMaster(final boolean isMaster) {
		this.isMaster = isMaster;
	}

	public boolean isMoniteurType() {
		return moniteurType;
	}

	public void setMoniteurType(final boolean moniteurType) {
		this.moniteurType = moniteurType;
	}
	
	

}
