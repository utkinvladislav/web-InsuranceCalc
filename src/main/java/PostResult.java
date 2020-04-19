import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate; 
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/result")
public class PostResult extends HttpServlet {

	private static final long serialVersionUID = 1L;
 
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        try {
	        if (AuthServ.session == null || AuthServ.session.getAttribute("username") == null) {
	            try {
	                writer.println("<h1> ������� ����� ����� � �������! </h1>");
	                writer.println("<form action=\"AuthForm.html\" method=\"POST\">");
	                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	                writer.println("&nbsp;&nbsp;&nbsp;");
	                writer.println("<input type=\"submit\" value=\"�����\" />");
	                writer.println("</form>");
	            } finally {
	                writer.close();  
	            } 
	        }
        }
        catch (IllegalStateException e) {
            try {
                writer.println("<h1> ��� ����� ����������! </h1>");
                writer.println("<h2> ��������� ����������� </h2>");
                writer.println("<form action=\"AuthForm.html\" method=\"POST\">");
                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                writer.println("&nbsp;&nbsp;&nbsp;");
                writer.println("<input type=\"submit\" value=\"�����\" />");
                writer.println("</form>");
            } finally {
                writer.close();  
            } 
        }
		int daysResult = daysCalculation(request, response);
		if (daysResult <=1)
			 try {
	                writer.println("<h1> ������! </h1>");
	                writer.println("<h3> �� �� ����� ����� ������, ��� �� ���� ����� </h3>");
	                writer.println("<form action=\"MainForm.html\" method=\"POST\">");
	                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	                writer.println("&nbsp;&nbsp;&nbsp;");
	                writer.println("<input type=\"submit\" value=\"�����\" />");
	                writer.println("</form>");   
	            } finally {
	                writer.close();   
	            }
		BigDecimal res = insCalculation( request, response);
		if (res.compareTo(new BigDecimal(0)) == 0) {
			 try {
	                writer.println("<h1> ������! </h1>");
	                writer.println("<h3> �� ������� ������� ���������� ���������� </h3>");
	                writer.println("<form action=\"MainForm.html\" method=\"POST\">");
	                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	                writer.println("&nbsp;&nbsp;&nbsp;");
	                writer.println("<input type=\"submit\" value=\"�����\" />");
	                writer.println("</form>");   
	            } finally {
	                writer.close();   
	            }
		}
            try {
                writer.println("<h2> ���������� </h2>");
                writer.println("<strong> ��������� ������ � ������: </strong>" + res);
                writer.println("<br> </br>");
                writer.println("<strong> ���� �������� � ������: </strong>" + daysResult);
                writer.println("<form action=\"MainForm.html\" method=\"POST\">");
                writer.println("<br></br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                writer.println("<input type=\"submit\" value=\"���������\" />");
                writer.println("</form>");
            } finally {
                writer.close();   
            }
        }        

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
	                writer.println("<h1> ������! </h1>");
	                writer.println("<h3> �� ������ ������� ���� ������ � ����� ������ </h3>");
	                writer.println("<form action=\"MainForm.html\" method=\"POST\">");
	                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	                writer.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	                writer.println("&nbsp;&nbsp;&nbsp;");
	                writer.println("<input type=\"submit\" value=\"�����\" />");
	                writer.println("</form>");            
	            } finally {
	            	writer.close();
			}
			}
		    Period period = Period.between(startDate, endDate);
		    int daysResult = period.getDays();
		    return daysResult;
		}
	
	private static BigDecimal insCalculation (HttpServletRequest request, HttpServletResponse response) throws IOException {
		BigDecimal 
			age1, age2, age3, age4, region, sport, lawyer, specialCase,
			foresight, baggage, summMod, summ, promo, result, extra1,
			extra2, extra3, extra4, extra5, k1, k2, k3, k4, days, multi, usable;
		final BigDecimal 
		ageMod1 = new BigDecimal(1, MathContext.UNLIMITED),
		ageMod2 = new BigDecimal(2.16, MathContext.UNLIMITED),
		ageMod3 = new BigDecimal(2.54, MathContext.UNLIMITED),
		ageMod4 = new BigDecimal(5, MathContext.UNLIMITED);
			
		days = new BigDecimal(Double.toString(daysCalculation(request, response)));
		
		region = new BigDecimal(0, MathContext.UNLIMITED);
		lawyer = new BigDecimal(0, MathContext.UNLIMITED);
		specialCase = new BigDecimal(0, MathContext.UNLIMITED);
		sport = new BigDecimal(0, MathContext.UNLIMITED);
		foresight = new BigDecimal(0, MathContext.UNLIMITED);
		baggage = new BigDecimal(0, MathContext.UNLIMITED);

		String regionName = new String(request.getParameter("region"));
		switch (regionName) {
		case ("���������� ����"):
			region = new BigDecimal(44, MathContext.UNLIMITED);
			lawyer = new BigDecimal(21, MathContext.UNLIMITED);
			specialCase = new BigDecimal(18, MathContext.UNLIMITED);
			sport = new BigDecimal(51, MathContext.UNLIMITED);
			foresight = new BigDecimal(18, MathContext.UNLIMITED);
			baggage = new BigDecimal(35, MathContext.UNLIMITED);
			break;
		case ("������ � ���"):
			region = new BigDecimal(30, MathContext.UNLIMITED);
			lawyer = new BigDecimal(16, MathContext.UNLIMITED);
			specialCase = new BigDecimal(15, MathContext.UNLIMITED);
			sport = new BigDecimal(33, MathContext.UNLIMITED);
			foresight = new BigDecimal(10, MathContext.UNLIMITED);
			baggage = new BigDecimal(30, MathContext.UNLIMITED);
			break;
		case ("���"):
			region = new BigDecimal(70, MathContext.UNLIMITED);
			lawyer = new BigDecimal(39, MathContext.UNLIMITED);
			specialCase = new BigDecimal(40, MathContext.UNLIMITED);
			sport = new BigDecimal(50, MathContext.UNLIMITED);
			foresight = new BigDecimal(31, MathContext.UNLIMITED);
			baggage = new BigDecimal(40, MathContext.UNLIMITED);
			break;
		case ("��������� ���"):
			region = new BigDecimal(50, MathContext.UNLIMITED);
			lawyer = new BigDecimal(20, MathContext.UNLIMITED);
			specialCase = new BigDecimal(22, MathContext.UNLIMITED);
			sport = new BigDecimal(60, MathContext.UNLIMITED);
			foresight = new BigDecimal(24, MathContext.UNLIMITED);
			baggage = new BigDecimal(36, MathContext.UNLIMITED);
			break;
		}

		extra1 = new BigDecimal(0, MathContext.UNLIMITED);
		extra2 = new BigDecimal(0, MathContext.UNLIMITED);
		extra3 = new BigDecimal(0, MathContext.UNLIMITED);
		extra4 = new BigDecimal(0, MathContext.UNLIMITED);
		extra5 = new BigDecimal(0, MathContext.UNLIMITED);
		
		if (request.getParameter("extra1") != null ) 
			extra1 = new BigDecimal(1, MathContext.UNLIMITED);
		if (request.getParameter("extra2")!= null )
			extra2 = new BigDecimal(1, MathContext.UNLIMITED);
		if (request.getParameter("extra3")!= null )
			extra3 = new BigDecimal(1, MathContext.UNLIMITED);
		if (request.getParameter("extra4")!= null )
			extra4 = new BigDecimal(1, MathContext.UNLIMITED);
		if (request.getParameter("extra5")!= null )
			extra5 = new BigDecimal(1, MathContext.UNLIMITED);

		k1 = new BigDecimal(Double.parseDouble(request.getParameter("age1")), MathContext.UNLIMITED);
		k2 = new BigDecimal(Double.parseDouble(request.getParameter("age2")), MathContext.UNLIMITED);
		k3 = new BigDecimal(Double.parseDouble(request.getParameter("age3")), MathContext.UNLIMITED);
		k4 = new BigDecimal(Double.parseDouble(request.getParameter("age4")), MathContext.UNLIMITED);
		
		multi = new BigDecimal(1, MathContext.UNLIMITED);
		if (request.getParameter("multi")!= null )
			multi = new BigDecimal(2, MathContext.UNLIMITED);
		
		PostResult calc = new PostResult();
		promo = new BigDecimal(Double.toString(calc.promoCheck(request.getParameter("promo"))));	
		summMod = new BigDecimal(0, MathContext.UNLIMITED);
		switch (request.getParameter("summ")) {
		case ("$35000"):
			summMod = new BigDecimal(1, MathContext.UNLIMITED);
			break;
		case ("$60000"):
			summMod = new BigDecimal(1.909, MathContext.UNLIMITED);
			break;
		case ("$120000"):
			summMod = new BigDecimal(2.4546, MathContext.UNLIMITED);
			break;
		}
		
		usable = new BigDecimal(0, MathContext.UNLIMITED);
		usable = usable.add(region);
		usable = usable.add(sport.multiply(extra1));
		usable = usable.add(lawyer.multiply(extra2));
		usable = usable.add(specialCase.multiply(extra3));
		usable = usable.add(foresight.multiply(extra4));
		usable = usable.add(baggage.multiply(extra5));

		age1 = new BigDecimal(0);
		age1 = usable;
		age1 = age1.multiply(summMod.multiply(ageMod1.multiply(days.multiply(k1))));

		age2 = new BigDecimal(0);
		age2 = usable;
		age2 = age2.multiply(summMod.multiply(ageMod2.multiply(days.multiply(k2))));

		age3 = new BigDecimal(0);
		age3 = usable;
		age3 = age3.multiply(summMod.multiply(ageMod3.multiply(days.multiply(k3))));

		age4 = new BigDecimal(0);
		age4 = usable;
		age4 = age4.multiply(summMod.multiply(ageMod4.multiply(days.multiply(k4))));

		summ = new BigDecimal(0);
		summ = age1.add(age2.add(age3.add(age4)));

		result = new BigDecimal(0);
		result = summ;
		result = result.subtract(summ.multiply(promo));
		result = result.multiply(multi);
		result = result.setScale(2, RoundingMode.CEILING);

		return result;
	}	 

	private double promoCheck(String incomingPromo) {
    	String promo;
    	double promoVal;
    	InputStream promosStream = getClass()
    			.getClassLoader().getResourceAsStream("promos.txt");
    	InputStream promoValStream = getClass()
    			.getClassLoader().getResourceAsStream("promoVal.txt");
			Scanner promosScan = new Scanner(promosStream);
			Scanner promoValScan = new Scanner(promoValStream);
			while (promosScan.hasNextLine() &&  promoValScan.hasNextLine()) {
				promo = promosScan.nextLine();
				promoVal = Double.parseDouble(promoValScan.nextLine());
				if (promo.equals(incomingPromo)) {
					promosScan.close();
					promoValScan.close();
					return promoVal;
				}
			}
			promosScan.close();
			promoValScan.close();
		return 0;
    } 
}

