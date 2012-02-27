/*******************************************************************************
*
*   WebServer.java
*
*   This class illustrates a basic web server. It uses the RequestHandler class
* to service each connection as a seperate thread.
*
*******************************************************************************/

//import RequestHandler;
import java.net.*;

public class WebServer{

	final static int DEFAULT_PORT = 9100;
	final static int TIME_OUT = 120000;										// 2 minutes

	static ServerSocket server;

	public static void main(String args[]) throws java.io.IOException{
  
		int port = DEFAULT_PORT;

		if(args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);
				if(port < 0 || port > 65535) {
					port = DEFAULT_PORT;
				}
			}
			catch(NumberFormatException e) {
				port = DEFAULT_PORT;
			}
		}

		try {
			server = new ServerSocket(port);
			System.out.println("Web server started at IP-address " + server.getInetAddress());
			System.out.println("Accepting Connections on port " + server.getLocalPort());

			while(true) {
				Socket newClient = server.accept();

				newClient.setSoTimeout(TIME_OUT);

				new Thread(new RequestHandler(newClient));
			}
		}
		catch(SocketException e) {
			System.err.println(e);
		}
		catch(Exception e){
			e.printStackTrace();     
		}
		finally{
			server.close();
		}

	} // main()
      
} // WebServer
