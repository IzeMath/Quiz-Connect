package servlet.quizcon;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import roomServices.RoomServicesRemote;

/**
 * Servlet implementation class gameServlet
 */
@WebServlet("/CreationRoom")
public class CreationRoom extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	RoomServicesRemote Rsr;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreationRoom() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String nameRoom = request.getParameter("nameRoom");
		final String password = request.getParameter("password");
		
		if(password != null)
		{
			
		}
		else
		{
		
		}
		
		request.getRequestDispatcher("/Room.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
