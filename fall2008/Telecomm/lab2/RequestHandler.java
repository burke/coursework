/*******************************************************************************
 *
 *   RequestHandler.java
 *
 *   This class implements a simple request handler class to service requests to
 * the web server.
 *
 *******************************************************************************/

import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.String;

public class RequestHandler extends Thread {


/* Data */

// Public Constants
		 public final static int HTTP_OK = 200;
		 public final static int HTTP_NO_CONTENT = 204;
		 public final static int HTTP_NOT_MODIFIED = 304;
		 public final static int HTTP_BAD_REQUEST = 400;
		 public final static int HTTP_NOT_FOUND = 404;

		 public final static String DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z";


//Private Variables
		 private Socket         client;
		 private String         hostName;
		 private PrintStream    outToClient;
		 private BufferedReader inFromClient; 

		 private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		 private Date             lastModified;
		 private Date             ifModifiedSince;
		 
		 private static Hashtable statusTable;



/* Constructors */

		 public RequestHandler(Socket incoming)throws IOException {
					client = incoming;
					hostName = client.getInetAddress().toString();
					inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
					outToClient  = new PrintStream(client.getOutputStream());
					createStatusTable();
					setDaemon(true);
					start();
		 }



/* Public Methods */

//   The run() method provides the basic functionality for the thread, and is 
// initiated by the call to start() in the constructor.
		 public void run() {
					try {
							 System.out.println("Request Handler for " + hostName + " Begins");

							 String fileName = getRequestHeader();

							 try {
										File theFile = new File(fileName);
				
										if (theFile.exists()) {
												 lastModified = new Date(theFile.lastModified());

												 if(!lastModified.after(ifModifiedSince)) {
															String response = getResponseHeader(HTTP_NOT_MODIFIED,fileName);
															outToClient.println(response);
															outToClient.flush();
												 } else {
															String response = getResponseHeader(HTTP_OK,fileName);
															outToClient.println(response);
															outToClient.flush();
	
//   After sending the header, send the requested file to the client.
															sendFile(theFile);
												 }

										} else {
												 String response = getResponseHeader(HTTP_NOT_FOUND,"");
												 outToClient.println(response);
												 outToClient.flush();
										}
							 }
							 catch(NullPointerException e) {
										System.err.println("No file name / path specified.");
										String response = getResponseHeader(HTTP_NOT_FOUND,"");
										outToClient.println(response);
										outToClient.flush();
							 }
							 finally {
//   Close the I/O handles and end the connection.
										inFromClient.close();
										outToClient.close();
										client.close();
							 }
					}
					catch(IOException e) {
							 System.err.println("An error occurred closing a connection: " + e);
					}
					finally {
							 System.out.println("Request Handler for " + hostName + " Ends");
					}

		 } // run()



//   The getRequestHeader() method is used to get and parse the header of the 
// client request.
		 private String getRequestHeader() {
					String filePath = null;

					try {
							 System.out.println("**** Client " + hostName + " Request Begins ****");
							 String line = inFromClient.readLine();         
							 System.out.println(line);      

							 StringTokenizer st = new StringTokenizer(line);
//   Skip the method: GET, POST, HEAD, and just use the file path, but first 
// checks if the default web page (index.html) is being requested. Finally, 
// the leading seperator (/) is removed.
							 st.nextToken();
							 filePath = st.nextToken();
							 if(filePath.endsWith("/")) {
										filePath += "index.html";
							 }
							 filePath = filePath.substring(1);

//   Read in and print out any additional input from the client included with
// the request header. 
							 while(true) {
										line = inFromClient.readLine();
										if(line == null || line.trim().equals("")) {
												 break;
										}
										if(line.startsWith("If-Modified-Since: ")) {
												 try {
															ifModifiedSince = dateFormat.parse(line.substring(19));
												 } catch(ParseException e) {
															System.err.println("Couldn't parse date: " + e);
												 }
										}
										System.out.println(line);
							 }

							 System.out.println("**** Client " + hostName + " Request Ends ****");

					}
					catch(IOException e) {
							 System.err.println("An I/O error occurred: " + e);
					}

					return filePath;

		 } // getRequestHeader()



//   The getResponseHeader() method is used to generate the response header to 
// be returned to the client (in addition to any data that might be returned).
		 public String getResponseHeader(int statusCode, String fileName) {

					StringBuffer buffer = new StringBuffer();

					System.out.println("**** Response Header for " + hostName + " Begins ****");

                
					String code = String.valueOf(statusCode);
					buffer.append("HTTP/1.0 " + code + " " + statusTable.get(code) + "\n");
					buffer.append("Server: ECE370" + "\n");  

					switch(statusCode) {
					case HTTP_OK:
							 buffer.append("Last-Modified: " + dateFormat.format(lastModified)+ "\n");
							 if (fileName.endsWith("gif")) {
										buffer.append("Content-Type: image/gif \n");
							 } else if (fileName.endsWith("png")) {
										buffer.append("Content-Type: image/png \n");
							 } else if (fileName.endsWith("html") || fileName.endsWith("htm")) {
										buffer.append("Content-Type: text/html; charset=utf-8 \n");
							 } else if (fileName.endsWith("jpg")) {
										buffer.append("Content-Type: image/jpg \n");
							 } else buffer.append("Content-Type: text/plain \n");

																								 
							 break;
					case HTTP_NOT_MODIFIED:
							 break;
					case HTTP_BAD_REQUEST:
					case HTTP_NOT_FOUND:
							 buffer.append("\n"); // to end the header part
							 buffer.append("<h2>Server: ECE370</h2>" + "\n");
							 buffer.append("<h2>Error: " + code + " " + statusTable.get(code) + "</h2>" + "\n");
							 break;
					}

					String response = buffer.toString();
					System.out.println(response);
					System.out.println("**** Response Header for " + hostName + " Ends ****");

					return response;

		 } // getResponseHeader()



//   The method sendFile() is used to send the requested file (if available).
		 public void sendFile(File theFile) {

					final int BUFFER_SIZE = 128;

					int totalBytes = 0;
					try {
							 System.out.println("Send File for " + hostName + " Begins");

							 FileInputStream fis = new FileInputStream(theFile);

							 try {
										int bytesRead  = 0;

										byte[] buff = new byte[BUFFER_SIZE];

										while (true) {
												 bytesRead = fis.read(buff,0,BUFFER_SIZE);
												 if(bytesRead == -1) {
															break;
												 }
												 totalBytes += bytesRead;
												 System.out.print(bytesRead + " " );
												 outToClient.write(buff,0,bytesRead);
										}

							 }
							 catch(IOException e) {
										System.err.println("An I/O error occurred: " + e);
							 }
							 finally {
										fis.close();
							 }
					}
					catch(IOException e) {
							 System.err.println("An error occurred openning or closing the input stream for " 
																	+ theFile.getName() + ": " + e);
					}
					finally {
							 System.out.println("total Bytes = " + totalBytes);
							 System.out.println("Send File for " + hostName + " Ends");
					}

		 } // sendFile()



//   The createStatusTable() method is simply used to dreate a hashtable of 
// response codes and more meaningful response messages. This is static, because
// it only needs to be created once for any number threads that may use it.
		 public static void createStatusTable(){
					statusTable = new Hashtable();

					statusTable.put(String.valueOf(HTTP_OK), "OK");
					statusTable.put(String.valueOf(HTTP_NO_CONTENT), "No Content");
					statusTable.put(String.valueOf(HTTP_NOT_MODIFIED), "Not Modified");
					statusTable.put(String.valueOf(HTTP_BAD_REQUEST), "Bad Request");
					statusTable.put(String.valueOf(HTTP_NOT_FOUND), "Not Found");
 
		 } // createStatusTable()



} // RequestHandler
