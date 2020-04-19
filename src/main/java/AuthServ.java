

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class authServ
 */
public class AuthServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public authServ() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	boolean authorization(String incomingUsername, String incomingPassword) {
    	String username;  
    	String password;
    	
    	InputStream usersStream = getClass()
    			.getClassLoader().getResourceAsStream("usernames.txt");
    	InputStream passStream = getClass()
    			.getClassLoader().getResourceAsStream("passes.txt");
    	
			Scanner usersScan = new Scanner(usersStream);
			Scanner passScan = new Scanner(passStream);
			
			while (usersScan.hasNextLine() &&  passScan.hasNextLine()) {
				username = usersScan.nextLine();
				password = passScan.nextLine();
				if (username.equals(incomingUsername) && password.equals(incomingPassword)) {
					usersScan.close();
					passScan.close();
					return true;
				}
			}
			
			usersScan.close();
			passScan.close();
			
		return false;
    } 

}
