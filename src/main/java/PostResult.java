

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostResult extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static int daysCalculation (HttpServletRequest request, HttpServletResponse response) throws IOException {
		LocalDate startDate = LocalDate.MIN;
	    LocalDate endDate = LocalDate.MAX; 
	    	try {
			startDate = LocalDate.parse(request.getParameter("startDate"));
		    endDate = LocalDate.parse(request.getParameter("endDate"));
			} catch(DateTimeParseException e) {
	            response.setContentType("text/html");
	            response.setCharacterEncoding("UTF-8");
	            PrintWriter writer = response.getWriter();
	            try {
	                writer.println("<h1> Ошибка! </h1>");
	                writer.println("<h3> Вы должны выбрать даты начала и конца поезки </h3>");
	                writer.println("<form action=\"MainForm.html\" method=\"POST\">");
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
		    Period period = Period.between(startDate, endDate);
		    int daysResult = period.getDays();
		    return daysResult;
		}

}
