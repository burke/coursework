import java.net.*;
import java.io.*;

public class RequestHandler {

	private DatagramPacket client;
	private DatagramSocket servSocket;
	private InetAddress    clientIP;
	private String         clientRequest; 
	private int            clientPort;

	public RequestHandler(DatagramPacket client, DatagramSocket socket) throws IOException {
		servSocket     = socket;
		clientPort     = client.getPort();
		clientIP       = client.getAddress();
		clientRequest  = new String(client.getData());

		System.out.println("*** Serving request for "+clientIP);
		serveClient();
		System.out.println("*** Finished request for "+clientIP);

	}
	
	private void serveClient() {

		String fileName = getFileName();

		if(fileName == null) {
			sendString("Error: Malformed request.");
			System.out.println("Ignored malformed request from "+clientIP+".");
			return; // BAIL OUT. Hey, I'm no compsci major. I get to do this.
		}

		File theFile = new File(fileName);

		if( !theFile.exists() ) { //NOTE: Check if file does NOT exist.
			sendString("Error: File does not exist.");
			System.out.println("Ignored request for non-existant file from "+clientIP+".");
			return;
		}

		boolean[] chunks = new boolean[(int)(theFile.length()/1022)];

		getChunks(chunks);

		if( chunks == null ) {
			sendNegotiationPacket(theFile);
			sendFile(theFile,chunks);
		} else {
			sendFile(theFile,chunks);
		}
	}

	private void getChunks(boolean[] chunks) {
		String[] words = new String[8];

		words = clientRequest.split("\\s+");
		
		if( words[0].equals("GETCHUNKS") ) {
			String temp = words[1];
			String[] temp2 = temp.split(",");
			for(int i=0; i<temp2.length; ++i) {
				chunks[Integer.parseInt(temp2[i])] = true;
			}
		} else {
			chunks = null;
		}
		
	}

	private void sendString(String send) {
		byte[] sendData = new byte[1024];
		sendData = send.getBytes();
		try {
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, clientPort);
			servSocket.send(sendPacket);
		} catch( IOException e ) {
			System.out.println("IO Error: " + e);
		}
	}

	private void sendOrderedPacket(byte[] bytes, int numBytes, int seqNum) {

		//We've kept the first two bytes blank, so we can use them to store the 
		//position in the sequence.
		byte highByte = (byte)(seqNum/256);
		byte lowByte  = (byte)(seqNum%256);
		bytes[0] = highByte;
		bytes[1] = lowByte;
		
		try {
			DatagramPacket sendPacket = new DatagramPacket(bytes, numBytes, clientIP, clientPort);
			servSocket.send(sendPacket);
		} catch( IOException e ) {
			System.out.println("IO Error: " + e);
		}
	}

	private String getFileName() {
		String filePath = null;

		String[] words = new String[8];

		System.out.println(clientRequest);
		

		words = clientRequest.split("\\s+");
		
		if( words[0].equals("GET") ) {
			filePath = words[1];
		} else if( words[0].equals("GETCHUNKS") ) {
			filePath = words[2];
		} else {
			filePath = null; //Error. Wrong verb.
		}

		return filePath.trim();

	} 
		
	public String sendNegotiationPacket(File theFile) {

		String response = "OK "+theFile.length()+" BYTES";
		sendString(response);

		return response;

	}


	public void sendFile(File theFile, boolean[] chunks) {

		final int BUFFER_SIZE = 1024;

		int totalBytes = 0;
		try {
			System.out.println("* Send File for " + clientIP + " Begins");

			FileInputStream fis = new FileInputStream(theFile);

			try {
				int bytesRead  = 0;

				byte[] buff = new byte[BUFFER_SIZE];

				for(int i=0; true; ++i) {
					bytesRead = fis.read(buff,2,BUFFER_SIZE-2);
					if(bytesRead == -1) {
						break;
					}
					totalBytes += bytesRead;
					if( chunks == null || chunks[i] == true ) {
						sendOrderedPacket(buff,bytesRead+2,i);
						try {
							Thread.sleep(1);
						} catch(Exception e){}
					}
				}

			} catch(IOException e) {
				System.err.println("An I/O error occurred: " + e);
			} finally {
				fis.close();
			}
		} catch(IOException e) {
			System.err.println("An error occurred opening or closing the input stream for " 
				+ theFile.getName() + ": " + e);
		} finally {
			System.out.println("Total Bytes Sent: " + totalBytes);
			System.out.println("* Send File for " + clientIP + " Ends");
		}

	} 

}
