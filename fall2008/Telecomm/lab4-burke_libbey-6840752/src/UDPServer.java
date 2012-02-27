import java.io.*;
import java.net.*;

class UDPServer {
	public static final int PORT = 9876;

	public static void main(String args[]) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(PORT);
		
		byte[] receiveData;
		
		while(true) {
			// Listen for a request packet.
			// Can be of the form "GET <filename>" or "GETCHUNKS <m>[,<n>][,<o>][,...] <filename>"
			receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			
			new RequestHandler(receivePacket, serverSocket);

		}
	}
}
