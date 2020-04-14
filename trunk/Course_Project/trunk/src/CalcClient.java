import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CalcClient {

	static String line;
	public static void main(String[] args) {
	    int serverPort = 5000;
	    BufferedReader fromServer; {
	    	try (Socket socket = new Socket("127.0.0.1", serverPort)) {
	    		try (PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true)) {
	    				toServer.println("Hello from TEST");
	    				fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    				line = fromServer.readLine();
	    		}
	            	fromServer.close();
	        	} catch (IOException e) {
	        		e.printStackTrace();
	        	}
	    }
	    System.out.print(line);
	}
}

