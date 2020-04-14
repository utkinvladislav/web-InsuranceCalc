import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CalcServer {

	static int serverPort = 5000;
	static ServerSocket serverSocket;
	
	private static void infoTransfer() {
		while (true) {
			try {
				serverSocket = new ServerSocket(serverPort);
				Socket server = serverSocket.accept();
				BufferedReader fromClient;	
				fromClient = new BufferedReader(new InputStreamReader(server.getInputStream()));
				String line = fromClient.readLine();
				PrintWriter toClient = new PrintWriter(server.getOutputStream(), true);
				toClient.println("Thank you for connecting to " + server.getLocalSocketAddress() + " THIS IS A TEST");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/*
	private static double insCalculation () {
		double 
			age1, age2, age3, age4, region, sport, lawyer, specialCase,
			foresight, baggage, summMod, ageMod1, ageMod2, ageMod3,
			ageMod4, summ, promo, result;
		int 
			extra1, extra2, extra3, extra4, extra5, k1, k2, k3, k4, days, multi;
		
		age1 = (region + sport * extra1 + lawyer * extra2 + specialCase * extra3 +
				foresight * extra4 + baggage * extra5) * summMod * ageMod1 * days * k1;
		age2 = (region + sport * extra1 + lawyer * extra2 + specialCase * extra3 +
				foresight * extra4 + baggage * extra5) * summMod * ageMod2 * days * k2;
		age3 = (region + sport * extra1 + lawyer * extra2 + specialCase * extra3 +
				foresight * extra4 + baggage * extra5) * summMod * ageMod3 * days * k2;
		age4 = (region + sport * extra1 + lawyer * extra2 + specialCase * extra3 +
				foresight * extra4 + baggage * extra5) * summMod * ageMod4 * days * k3;
		summ = age1 + age2 + age3 + age4;
		result = summ * multi - summ * promo;
		return result;
	}	
	*/
}