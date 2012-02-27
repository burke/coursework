/*******************************************************************************
 *
 *   Server.java
 *
 *   This program illustrates the basic concepts of writing a network server
 * using Java ServerSockets and Sockets class (in java.net).
 *    In short, the program tries to bind to a port on the local host using a
 * ServerSocket. Once bound, the server waits for a client to make a connection
 * to the server, via a Socket. I/O streams are then created for the connection
 * and then in a loop the server:
 *			waits for an input String from the client,
 *			if the String is "quit" then exits the loop,
 * 			prints the message to the local terminal, and
 *			sends a reply to the client of the original message in uppercase.
 *
 *******************************************************************************/

import java.io.*;
import java.net.*;

public class ModifiedServer{

		 public static void main(String[] args) {

					int port = 9100;

					try {
							 ServerSocket servSock = new ServerSocket(port);
							 System.out.println("Server listening on port: " + port);

							 try {
										Socket theSocket = servSock.accept();
										System.out.println("Connection accepted: " + theSocket);

										try {
												 BufferedReader inFromClient = new BufferedReader(
															new InputStreamReader(theSocket.getInputStream()));

												 PrintWriter outToClient = new PrintWriter(new BufferedWriter(
																																				new OutputStreamWriter(theSocket.getOutputStream())), true);

					
												 // start of code written for lab

												 outToClient.println("Username: ");
												 inFromClient.readLine();

												 outToClient.println("Password: ");
												 inFromClient.readLine();

												 outToClient.println("Welcome. You are about to be disconnected. Goodbye.");					

												 //end of code written for lab

					
										}
										catch(IOException e) {
												 System.err.println("The following exception: " + e
																						+ ", occurred during communication.");
										}
										finally {
												 System.out.println("Closing the connection to: " + theSocket);
												 theSocket.close();
										}
							 }
							 catch(IOException e) {
										System.err.println("The following exception: " + e
																			 + ", occurred while opening or closing the connection.");
							 }
							 finally {
										System.out.println("Closing the server: " + servSock);
										servSock.close();
							 }
					}
					catch(BindException e) {
							 System.err.println("Could not bind the server on port: " + port);
					}
					catch(IOException e) {
							 System.err.println("The following exception: " + e
																	+ ", occurred while opening or closing the ServerSocket.");
					}

		 } // main

} // Server
