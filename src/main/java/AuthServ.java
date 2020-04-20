
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
  /*
   * Класс служит для авторизации
   */
@WebServlet("/insurancyCalc")
public class AuthServ extends HttpServlet {
    static HttpSession session; //переменная для сохранения данных сессии
	private static final long serialVersionUID = 1L;
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		session = request.getSession(); //присвоенние текущей сессии
		session.setMaxInactiveInterval(600); //удаление данных после 10 минут неактивности пользователя
		boolean authSuccess = false;
		
		/*
		 * работа с входящими паролем и юзернэймом
		 */
		String username;  
	    String password; 
		username = request.getParameter("username"); 
	    password = request.getParameter("password");
	    
	    session.setAttribute("username", username); //задать атрибут для сессии
	    
        authSuccess = authorization(username, password); //авторизация
        if (authSuccess == true) {
        	response.sendRedirect("MainForm.html"); //перенаправление на основную страницу
        } else {   //в противном случае создание страницы-ошибки
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
	/*
	 * метод сравнивает входящие пароль и имя с таковыми,
	 * записанными в файлах, хранящихся в папке с ресурсами
	 * успех — возвращает true
	 */
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