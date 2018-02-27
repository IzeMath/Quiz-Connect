package quizcon.endpoint;

import java.util.ArrayList;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.json.JSONArray;
import org.json.JSONObject;
import org.quizcon.Reponse;

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

		if (qcMess instanceof QCM_Question) {
			final QCM_Question messQuestion = (QCM_Question) qcMess;
			jsonObj.put("type", messQuestion.getType());

			switch (messQuestion.getType()) {
			case "question":
				// Pour monitor
				jsonObj.put("question", messQuestion.getQuestion().getLibelle());
				jsonObj.put("url", messQuestion.getQuestion().getContenuURL());

				return jsonObj.toString();
			case "listRep":
				// Pour mobile
				final ArrayList<String> listRep = new ArrayList<>();
				for (final Reponse rep : messQuestion.getQuestion().getListRep()) {
					listRep.add(rep.getLibelle());
				}

				jsonObj.put("listRep", new JSONArray(listRep));

				return jsonObj.toString();
			case "explications":
				jsonObj.put("explications", messQuestion.getQuestion().getExplications());

				return jsonObj.toString();
			case "reponse":
				jsonObj.put("reponse", messQuestion.getQuestion().getLaReponse());

				return jsonObj.toString();
			}
		}

		if (qcMess instanceof QCM_Score) {
			final QCM_Score messScore = (QCM_Score) qcMess;
			jsonObj.put("type", "score");
			jsonObj.put("scores", messScore.getTabScore());

			return jsonObj.toString();

		}
		if (qcMess instanceof QCM_InfoMonitor) {
			final QCM_InfoMonitor messInfoMonitor = (QCM_InfoMonitor) qcMess;
			jsonObj.put("type", "infoMonitor");
			jsonObj.put("roomId", messInfoMonitor.getRoomId());

			return jsonObj.toString();
		}

		return jsonObj.toString();
	}
}
