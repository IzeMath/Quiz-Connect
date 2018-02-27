package quizcon.endpoint;

import java.util.UUID;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.json.JSONArray;
import org.json.JSONObject;
import org.quizcon.QuestionParameters;

public class QCMessageDecoder implements Decoder.Text<QCMessage> {
	@Override
	public void init(final EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public QCMessage decode(final String textMessage) throws DecodeException {
		final JSONObject jsonObj = new JSONObject(textMessage);

		switch (jsonObj.getString("type")) {
		case "monitorCreate":
			// Recup data
			final String roomName = jsonObj.getString("roomName");
			final String password = jsonObj.getString("password");

			// Set in mess
			final QCM_MonitorCreate messMonitorCreate = new QCM_MonitorCreate(roomName, password);

			return messMonitorCreate;

		case "monitorJoin":
			// Recup data
			final int roomId = jsonObj.getInt("roomId");
			final String password2 = jsonObj.getString("password");

			// Set in mess
			final QCM_MonitorJoin messMonitorJoin = new QCM_MonitorJoin(roomId, password2);

			return messMonitorJoin;

		case "startGame":
			final QuestionParameters qp = new QuestionParameters();

			// Recup data
			final JSONArray arjsd = jsonObj.getJSONArray("difficulties");
			final JSONArray arjst = jsonObj.getJSONArray("themes");
			final JSONArray arjsl = jsonObj.getJSONArray("langs");
			final int nbQuestion = jsonObj.getInt("nbQ");

			// set in mess
			qp.setNbQuestion(nbQuestion);

			for (int i = 0; i < arjsd.length(); i++) {
				qp.addDifficulty(arjsd.getString(i));
			}

			for (int i = 0; i < arjst.length(); i++) {
				qp.addTheme(arjst.getString(i));
			}

			for (int i = 0; i < arjsl.length(); i++) {
				qp.addLang(arjsl.getString(i));
			}

			final QCM_StartGame messStartGame = new QCM_StartGame(qp);

			return messStartGame;

		case "playerReponse":
			// Recup data
			final String reponse = jsonObj.getString("reponse");

			// Set in mess
			final QCM_PlayerReponse messPlayerReponse = new QCM_PlayerReponse(reponse);

			return messPlayerReponse;

		case "playerInv":
			// Recup data
			final String playerName = jsonObj.getString("playerName");
			final int roomId2 = jsonObj.getInt("roomId");
			final String password3 = jsonObj.getString("password");

			// Set in mess
			final QCM_PlayerInv messPlayerInv = new QCM_PlayerInv(playerName, roomId2, password3);

			return messPlayerInv;

		case "playerConnected":
			// Recup data
			final UUID userId = UUID.fromString(jsonObj.getString("userId"));
			final int roomId3 = jsonObj.getInt("roomId");
			final String password4 = jsonObj.getString("password");

			// Set in mess
			final QCM_PlayerConnected messPlayerConnected = new QCM_PlayerConnected(userId, roomId3, password4);

			return messPlayerConnected;

		}

		return null;

		/*
		 * final QCMessage qcMess = new QCMessage(); final JSONObject jsonObj = new
		 * JSONObject(textMessage); qcMess.setType(jsonObj.getString("type"));
		 * 
		 * if (qcMess.getType().equals("init")) { final QuestionParameters qp = new
		 * QuestionParameters();
		 * 
		 * final JSONArray arjsd = jsonObj.getJSONArray("difficulties"); final JSONArray
		 * arjst = jsonObj.getJSONArray("themes"); final JSONArray arjsl =
		 * jsonObj.getJSONArray("langs");
		 * 
		 * qp.setNbQuestion(jsonObj.getInt("nbQ"));
		 * 
		 * for (int i = 0; i < arjsd.length(); i++) {
		 * qp.addDifficulty(arjsd.getString(i)); }
		 * 
		 * for (int i = 0; i < arjst.length(); i++) { qp.addTheme(arjst.getString(i)); }
		 * 
		 * for (int i = 0; i < arjsl.length(); i++) { qp.addLang(arjsl.getString(i)); }
		 * 
		 * qcMess.setQp(qp); }
		 * 
		 * if (qcMess.getType().equals("reponse")) {
		 * qcMess.setReponse(jsonObj.getString("reponse")); }
		 * 
		 * if (qcMess.getType().equals("logInv")) {
		 * qcMess.setInvitName(jsonObj.getString("invitName"));
		 * qcMess.setMoniteurType(jsonObj.getBoolean("moniteurType"));
		 * 
		 * } if (qcMess.getType().equals("logUser")) {
		 * qcMess.setUserName(jsonObj.getString("userName"));
		 * qcMess.setPassword(jsonObj.getString("password")); } if
		 * (qcMess.getType().equals("joinRoom")) {
		 * qcMess.setRoomId(jsonObj.getInt("roomId")); String rpass =
		 * jsonObj.getString("password").trim(); if (rpass.equals("")) { rpass = null; }
		 * qcMess.setRoomPassword(rpass); } if (qcMess.getType().equals("createRoom")) {
		 * qcMess.setRoomName(jsonObj.getString("roomName")); String rpass =
		 * jsonObj.getString("password").trim(); if (rpass.equals("")) { rpass = null; }
		 * qcMess.setRoomPassword(rpass); }
		 */

	}

	@Override
	public boolean willDecode(final String s) {
		return true;
	}
}
