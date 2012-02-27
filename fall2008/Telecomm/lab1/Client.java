/*******************************************************************************
*
*   Client.java
*
*   This program illustrates the basic concepts of writing a network client
* using Java Sockets class (in java.net).
*    In short, the program tries to make a connection to a server on port 9100,
* of localhost, and if successful, sets up I/O objects for interacting with the
* server, and a local user, and proceeds to:
*			prompt the user for input,
*			send the input to the server,
* 			wait for a reply from the server,
*			print the reply, and
*			repeat the process, until the user enter quit
*
*******************************************************************************/

import java.io.*;
import java.net.*;

public class ModifiedClient{

	public static void main(String[] args) {

		int    port = 9100;
		String host = "localhost";

		try {
			Socket theSocket = new Socket(host,port);
			System.out.println("socket = " + theSocket);

	      try {
				BufferedReader inFromServer = new BufferedReader(
						new InputStreamReader(theSocket.getInputStream()));

				PrintWriter outToServer = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(theSocket.getOutputStream())), true);

				BufferedReader inFromUser = new BufferedReader(
						new InputStreamReader(System.in));

				String messageToSend;
				String messageReceived;

				// start of code written for lab
				
        messageReceived = inFromServer.readLine();
        System.out.println(messageReceived);  // Read prompt from server(user id)
        messageToSend = inFromUser.readLine();  //enter user id 
        outToServer.println(messageToSend);
        
        messageReceived = inFromServer.readLine();
        System.out.println(messageReceived);  // Read prompt from server (passwd)
        messageToSend = inFromUser.readLine();  //enter passwd
        outToServer.println(messageToSend);
				
        messageReceived = inFromServer.readLine();
        System.out.println(messageReceived);  // Read welcome message

				// end of code written for lab
				
			}
			catch(IOException e) {
				System.err.println("The following exception: " + e
										+ ", occurred during communication.");
			}
			finally {
				System.out.println("closing . . .");
				theSocket.close();
			}
		}
		catch(UnknownHostException e) {
			System.err.println("The host: " + host  + ", is not a valid IP host.");
		}
		catch(ConnectException e) {
			System.err.println("The host: " + host  + ", does not run a server on port: " + port + ".");
		}
		catch(IOException e) {
			System.err.println("The following exception: " + e + ", occurred while opening or closing the connection.");
		}

	} // main

} // Client

