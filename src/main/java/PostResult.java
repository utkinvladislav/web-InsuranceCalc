import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Сервлет, главная функция которого — производить расчёт
 * и выводить информацию
 */
@WebServlet("/result")
public class PostResult extends HttpServlet {

	private static final long serialVersionUID = 1L;
 
	//объявление необходимых переменных
	BigDecimal 
		age1, age2, age3, age4, region, sport, lawyer, specialCase,
		foresight, baggage, summMod, summ, promo, result, extra1,
		extra2, extra3, extra4, extra5, k1, k2, k3, k4, days, multi, usable;
	//Объявление и инициализация констант
	final BigDecimal 
	ageMod1 = new BigDecimal(1, MathContext.UNLIMITED),
	ageMod2 = new BigDecimal(2.16, MathContext.UNLIMITED),
	ageMod3 = new BigDecimal(2.54, MathContext.UNLIMITED),
	ageMod4 = new BigDecimal(5, MathContext.UNLIMITED);
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        /*
         * проверка успеха авторизации по атрибуту сессии
         */
        try {
	        if (AuthServ.session == null || AuthServ.session.getAttribute("username") == null) {
	            try { //формирование сообщение об ошибке
	                writer.println("<h1> Сначала нужно войти в систему! </h1>");
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
        catch (IllegalStateException e) { //перехват ошибки при истечении срока сессии
            try {
                writer.println("<h1> Ваш сеанс закончился! </h1>");
                writer.println("<h2> Повторите авторизацию </h2>");
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
        
        PostResult calc = new PostResult();
        calc.setAttribs(request, response);
 
		long daysResult = daysCalculation(request, response); //подсчёт разницы между датами
		if (daysResult < 1)
			 try { //формирование сообщения об ошибке
	                writer.println("<h1> Ошибка! </h1>");
	                writer.println("<h3> Мы не выдаём полис меньше, чем на одни сутки </h3>");
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
		BigDecimal res = calc.insCalculation(); //подсчёт стоимости
		if (res.compareTo(new BigDecimal(0)) == 0) { //стоимость может равняться нулю, только если не указано число страхуемых
			 try { //вывод сообщения об ошибке
	                writer.println("<h1> Ошибка! </h1>");
	                writer.println("<h3> Вы указали нулевое количество страхуемых </h3>");
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
            try { //вывод результатов
                writer.println("<h2> Результаты </h2>");
                writer.println("<strong> Стоимость полиса в рублях: </strong>" + res);
                writer.println("<br> </br>");
                writer.println("<strong> Срок действия в сутках: </strong>" + daysResult);
                writer.println("<form action=\"MainForm.html\" method=\"POST\">");
                writer.println("<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                writer.println("<input type=\"submit\" value=\"Вернуться\" />");
                writer.println("</form>");
            } finally {
                writer.close();   
            }
        }        
/*
 * метод подсчитывает разницу между днями с помощью инструментов LocalDate
 */
	private long daysCalculation (HttpServletRequest request, HttpServletResponse response) throws IOException {
		LocalDate startDate = LocalDate.now(); //инициализация переменных
	    LocalDate endDate = LocalDate.MAX; 
	    LocalDate temp;
	    	try {
	    		temp = LocalDate.parse(request.getParameter("startDate"));
	    		temp = temp.minusDays(1);
	    		if (startDate.isAfter(temp)) {
	    			PrintWriter writer = response.getWriter();
		            try {  //формирование сообщения
		                writer.println("<h1> Ошибка! </h1>");
		                writer.println("<h3> Мы не выдаём полис задним числом </h3>");
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
	    		temp = LocalDate.parse(request.getParameter("startDate"));
	    		startDate = startDate.plusDays(365);
	    		if (temp.isAfter(startDate)) {
	    			PrintWriter writer = response.getWriter();
		            try {  //формирование сообщения
		                writer.println("<h1> Ошибка! </h1>");
		                writer.println("<h3> Выдаваемый полис должен начать действие максимум через 365 дней </h3>");
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
			startDate = LocalDate.parse(request.getParameter("startDate")); //получение начальной даты
		    endDate = LocalDate.parse(request.getParameter("endDate")); //получение конечной даты
			} catch(DateTimeParseException e) { //ловля ошибки, возникающей при пустом календаре
	            response.setContentType("text/html");
	            response.setCharacterEncoding("UTF-8");
	            PrintWriter writer = response.getWriter();
	            try {  //формирование сообщения
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
	    	//создание объекта Period и получение его атрибута
		    long daysResult = ChronoUnit.DAYS.between(startDate, endDate);
		    if (daysResult > 365) {
		    	PrintWriter writer = response.getWriter();
	            try {  //формирование сообщения
	                writer.println("<h1> Ошибка! </h1>");
	                writer.println("<h3> Мы не выдаём полис больше, чем на 365 дней </h3>");
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
		    }else {
				
			}
		    return daysResult;
		}
	/*
	 * В данном методе инициализируются переменные, необходимые для расчёта
	 */
	void setAttribs(HttpServletRequest request, HttpServletResponse response) throws IOException {
		days = new BigDecimal(Double.toString(daysCalculation(request, response))); //получение числа дней
		
		//инициализация
		region = new BigDecimal(0, MathContext.UNLIMITED);
		lawyer = new BigDecimal(0, MathContext.UNLIMITED);
		specialCase = new BigDecimal(0, MathContext.UNLIMITED);
		sport = new BigDecimal(0, MathContext.UNLIMITED);
		foresight = new BigDecimal(0, MathContext.UNLIMITED);
		baggage = new BigDecimal(0, MathContext.UNLIMITED);
		/*
		 * Инициализация ряда переменных в зависимости от выбранного региона 
		 */
		String regionName = new String(request.getParameter("region"));
		switch (regionName) {
		case ("Шенгенская зона"):
			region = new BigDecimal(44, MathContext.UNLIMITED);
			lawyer = new BigDecimal(21, MathContext.UNLIMITED);
			specialCase = new BigDecimal(18, MathContext.UNLIMITED);
			sport = new BigDecimal(51, MathContext.UNLIMITED);
			foresight = new BigDecimal(18, MathContext.UNLIMITED);
			baggage = new BigDecimal(35, MathContext.UNLIMITED);
			break;
		case ("Россия и СНГ"):
			region = new BigDecimal(30, MathContext.UNLIMITED);
			lawyer = new BigDecimal(16, MathContext.UNLIMITED);
			specialCase = new BigDecimal(15, MathContext.UNLIMITED);
			sport = new BigDecimal(33, MathContext.UNLIMITED);
			foresight = new BigDecimal(10, MathContext.UNLIMITED);
			baggage = new BigDecimal(30, MathContext.UNLIMITED);
			break;
		case ("США"):
			region = new BigDecimal(70, MathContext.UNLIMITED);
			lawyer = new BigDecimal(39, MathContext.UNLIMITED);
			specialCase = new BigDecimal(40, MathContext.UNLIMITED);
			sport = new BigDecimal(50, MathContext.UNLIMITED);
			foresight = new BigDecimal(31, MathContext.UNLIMITED);
			baggage = new BigDecimal(40, MathContext.UNLIMITED);
			break;
		case ("Остальной мир"):
			region = new BigDecimal(50, MathContext.UNLIMITED);
			lawyer = new BigDecimal(20, MathContext.UNLIMITED);
			specialCase = new BigDecimal(22, MathContext.UNLIMITED);
			sport = new BigDecimal(60, MathContext.UNLIMITED);
			foresight = new BigDecimal(24, MathContext.UNLIMITED);
			baggage = new BigDecimal(36, MathContext.UNLIMITED);
			break;
		}
		//инициализация
		extra1 = new BigDecimal(0, MathContext.UNLIMITED);
		extra2 = new BigDecimal(0, MathContext.UNLIMITED);
		extra3 = new BigDecimal(0, MathContext.UNLIMITED);
		extra4 = new BigDecimal(0, MathContext.UNLIMITED);
		extra5 = new BigDecimal(0, MathContext.UNLIMITED);
		
		//присвоение единиц, если отмечен чекбокс
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
		
		//получение числа страхуемых каждого возраста
		k1 = new BigDecimal(Double.parseDouble(request.getParameter("age1")), MathContext.UNLIMITED);
		k2 = new BigDecimal(Double.parseDouble(request.getParameter("age2")), MathContext.UNLIMITED);
		k3 = new BigDecimal(Double.parseDouble(request.getParameter("age3")), MathContext.UNLIMITED);
		k4 = new BigDecimal(Double.parseDouble(request.getParameter("age4")), MathContext.UNLIMITED);
		
		//присвоение 2, если отмечен чекбокс, иначе — 1
		multi = new BigDecimal(1, MathContext.UNLIMITED);
		if (request.getParameter("multi")!= null )
			multi = new BigDecimal(2, MathContext.UNLIMITED);
		
		/*
		 * вызов метода проверки промокода
		 */
		PostResult calc = new PostResult();
		promo = new BigDecimal(Double.toString(calc.promoCheck(request.getParameter("promo"))));	
		
		/*
		 * выбор модификатора суммы в зависимости от выбранной пользователем опции
		 */
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
	}
	
	/*
	 * метод производит расчёт стоимости полиса.
	 * Используется класс BigDecimal для расчётов в виду их финансового характера
	 */
	BigDecimal insCalculation () throws IOException {
		
		//переменная использованная для уменьшения объёма кода
		usable = new BigDecimal(0, MathContext.UNLIMITED);
		usable = usable.add(region);
		usable = usable.add(sport.multiply(extra1));
		usable = usable.add(lawyer.multiply(extra2));
		usable = usable.add(specialCase.multiply(extra3));
		usable = usable.add(foresight.multiply(extra4));
		usable = usable.add(baggage.multiply(extra5));

		//подсчёт результатов по каждому возрасту
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

		//подсчёт суммы
		summ = new BigDecimal(0);
		summ = age1.add(age2.add(age3.add(age4)));
		
		//подсчёт результата
		result = new BigDecimal(0);
		result = summ;
		BigDecimal k = new BigDecimal(0);
		k = k.add(k1.add(k2.add(k3.add(k4))));
		promo = promo.add(k.multiply(new BigDecimal(0.01)));
		result = result.multiply(multi);
		result = result.subtract(summ.multiply(promo));
		result = result.setScale(2, RoundingMode.CEILING); //округление до копеек в большую сторону
		return result;
	}	 

	/*
	 * метод получает промокод, введённый пользователем 
	 * и сравнивает его с записанными в файле.
	 * Если совпадение найдено, возвращается соответствующее значение
	 */
	double promoCheck(String incomingPromo) {
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

