package quizcon.endpoint;

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
		final QCMessage qcMess = new QCMessage();
		final JSONObject jsonObj = new JSONObject(textMessage);
		qcMess.setType(jsonObj.getString("type"));

		if (qcMess.getType().equals("init")) {
			final QuestionParameters qp = new QuestionParameters();

			final JSONArray arjsd = jsonObj.getJSONArray("difficulties");
			final JSONArray arjst = jsonObj.getJSONArray("themes");
			final JSONArray arjsl = jsonObj.getJSONArray("langs");

			qp.setNbQuestion(jsonObj.getInt("nbQ"));

			for (int i = 0; i < arjsd.length(); i++) {
				qp.addDifficulty(arjsd.getString(i));
			}

			for (int i = 0; i < arjst.length(); i++) {
				qp.addTheme(arjst.getString(i));
			}

			for (int i = 0; i < arjsl.length(); i++) {
				qp.addLang(arjsl.getString(i));
			}

			qcMess.setQp(qp);
		}

		if (qcMess.getType().equals("reponse")) {
			qcMess.setReponse(jsonObj.getString("reponse"));
		}

		if (qcMess.getType().equals("logInv")) {
			qcMess.setInvitName(jsonObj.getString("invitName"));

		}
		if (qcMess.getType().equals("logUser")) {
			qcMess.setUserName(jsonObj.getString("userName"));
			qcMess.setPassword(jsonObj.getString("password"));
		}
		if (qcMess.getType().equals("joinRoom")) {
			qcMess.setRoomId(jsonObj.getInt("roomId"));
			String rpass = jsonObj.getString("password").trim();
			if (rpass.equals("")) {
				rpass = null;
			}
			qcMess.setRoomPassword(rpass);
		}
		if (qcMess.getType().equals("createRoom")) {
			qcMess.setRoomName(jsonObj.getString("roomName"));
			String rpass = jsonObj.getString("password").trim();
			if (rpass.equals("")) {
				rpass = null;
			}
			qcMess.setRoomPassword(rpass);
		}

		return qcMess;
	}

	@Override
	public boolean willDecode(final String s) {
		return true;
	}
}
