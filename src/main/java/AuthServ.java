import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
  
@WebServlet("/insurancyCalc")
public class SimpleServlet extends HttpServlet {
    static HttpSession session;
	private static final long serialVersionUID = 1L;
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		session = request.getSession();
		session.setMaxInactiveInterval(600);
		boolean authSuccess = false;
		String username;
	    String password;
		username = request.getParameter("username");
	    password = request.getParameter("password");
	    session.setAttribute("username", username);
        authSuccess = authorization(username, password);
        if (authSuccess == true) {
        	response.sendRedirect("MainForm.html");
        } else {  
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            try {
                writer.println("<h1> Данные не совпадают! </h1>");
                writer.println("<h2> Пожалуйста, повторите попытку </h2>");
                writer.println("<form action=\"AuthForm.html\" method=\"POST\">");
                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                writer.println("&nbsp;&nbsp;&nbsp;");
                writer.println("<input type=\"submit\" value=\"Ладно\" />");
                writer.println("</form>");
            } finally {
                writer.close();  
            } 
        }        
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
