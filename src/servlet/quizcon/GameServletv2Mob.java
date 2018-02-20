package servlet.quizcon;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quizcon.Room;

import roomServices.RoomServicesRemote;

/**
 * Servlet implementation class gameServlet
 */
@WebServlet("/GameServlet2Mob")
public class GameServletv2Mob extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	RoomServicesRemote Rsr;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameServletv2Mob() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		final List<Room> lroom = Rsr.getListRoom();

		request.getSession().setAttribute("server", lroom);
		request.getRequestDispatcher("/index_mob.jsp").forward(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
