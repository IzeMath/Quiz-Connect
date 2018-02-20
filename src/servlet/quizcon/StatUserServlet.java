package servlet.quizcon;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.user.Utilisateur;

import userService.UserServicesRemote;

/**
 * Servlet implementation class StatUserServlet
 */
@WebServlet("/StatUserServlet")
public class StatUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	UserServicesRemote usr;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StatUserServlet() {
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
		//final Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
		final Utilisateur user =usr.login("az@gt.fr", "azerty");
		final JSONObject jo = new JSONObject(usr.getStats(user));
		response.setContentType("application/json");
		response.getWriter().append(jo.toString());
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
