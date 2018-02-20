package servlet.quizcon;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.quizcon.QuestionParameters;

import questionServices.QuestionsServiceRemote;

/**
 * Servlet implementation class InfoQuestServlet
 */
@WebServlet("/InfoQuestServlet")
public class InfoQuestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	QuestionsServiceRemote qsr;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InfoQuestServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final QuestionParameters qp = new QuestionParameters();
		
		final JSONArray arjsd = new JSONArray(request.getParameter("difficulties"));
		final JSONArray arjst = new JSONArray(request.getParameter("themes"));
		final JSONArray arjsl = new JSONArray(request.getParameter("langs"));
		
		
		for (int i = 0; i < arjsd.length(); i++) {
			qp.addDifficulty(arjsd.getString(i));
		}
		
		for (int i = 0; i < arjst.length(); i++) {
			qp.addTheme(arjst.getString(i));
		}
		
		for (int i = 0; i < arjsl.length(); i++) {
			qp.addLang(arjsl.getString(i));
		}
		
		response.setContentType("application/json");
		response.getWriter().append(qsr.getNbQuestionWithParameters(qp).toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
