package servlet.quizcon;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import roomServices.RoomServicesRemote;

/**
 * Servlet implementation class CheckRoomServlet
 */
@WebServlet("/CheckRoomServlet")
public class CheckRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	RoomServicesRemote rsr;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckRoomServlet() {
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
		response.setContentType("application/json");
		final JSONObject jobject = new JSONObject();
		jobject.put("canChangePage", false);

		try {
			final int roomId = Integer.parseInt(request.getParameter("roomId"));
			final String password = request.getParameter("password");

			jobject.put("canChangePage", rsr.canAccess(roomId, password));
		} catch (final Exception e) {
			// TODO: handle exception
		}
		response.getWriter().append(jobject.toString());
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
