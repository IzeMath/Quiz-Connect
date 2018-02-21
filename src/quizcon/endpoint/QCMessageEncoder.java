package quizcon.endpoint;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.json.JSONArray;
import org.json.JSONObject;

public class QCMessageEncoder implements Encoder.Text<QCMessage> {
	@Override
	public void init(final EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public String encode(final QCMessage qcMess) throws EncodeException {
		final JSONObject jsonObj = new JSONObject();

		if (qcMess.getType().equals("question")) {
			jsonObj.put("type", qcMess.getType());
			jsonObj.put("question", qcMess.getQuestion());
			final JSONArray jsar = new JSONArray(qcMess.getReponses());
			jsonObj.put("reponses", jsar);
			jsonObj.put("explication", qcMess.getExplication());
			jsonObj.put("url", qcMess.getUrl().toString());
		}
		if (qcMess.getType().equals("score")) {
			jsonObj.put("type", qcMess.getType());
			jsonObj.put("scores", qcMess.getScore());
		}
		if (qcMess.getType().equals("inform")) {
			jsonObj.put("type", qcMess.getType());
			jsonObj.put("logInv", qcMess.isConnectInv());
			jsonObj.put("logUser", qcMess.isConnectUser());
			jsonObj.put("inRoom", qcMess.isConnectRoom());
			jsonObj.put("master", qcMess.isMaster());
		}

		return jsonObj.toString();
	}
}
