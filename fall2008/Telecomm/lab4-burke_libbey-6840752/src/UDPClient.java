import java.io.*;
import java.net.*;

class UDPClient {
	public static final int PORT = 9876;
	
	public static void main(String args[]) throws Exception {
	
		int index;
	
		BufferedReader inFromUser   = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];

		// Pull the target host from the command line.
		InetAddress ipAddress = null;		
		if(args.length > 0) {
			ipAddress = InetAddress.getByName(args[0]);
		} else {
			System.err.println("FATAL: You must specify a hast to connect to.");
			System.exit(1);
		}

		// Get the request string from input and send it off.
		String request = inFromUser.readLine();
		sendData = request.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, PORT);
		clientSocket.send(sendPacket);
		
		// Get the header packet.
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);	
		clientSocket.receive(receivePacket);
		receiveData = receivePacket.getData();
		
		String[] words = new String[8];
		words = (new String(receiveData)).split("\\s+");
		
	 	int numBytes = Integer.parseInt(words[1]);

		boolean[] packets = new boolean[numBytes];
		
		File download_file = new File("downloaded_file");
		download_file.createNewFile();
		
		RandomAccessFile download = new RandomAccessFile(download_file,"rw");
		download.setLength(numBytes);

		clientSocket.setSoTimeout(1000);
		
		// Get the server's response.
		do {
			try {
				while( true ) {
					receivePacket = new DatagramPacket(receiveData, receiveData.length);	
					clientSocket.receive(receivePacket);
					receiveData = receivePacket.getData();
			
					// Convert the first two bytes back into the integer index of this packet.
					index = 256*(receiveData[0] & 0xff) + (receiveData[1] & 0xff);
					packets[index] = true;
					download.seek(index*1022);
			
					for( int i=0; i<1021; ++i ) {
			  		download.writeByte(receiveData[i+2]);
					}
				}
			} catch( Exception e ) {}
		} while( moreToDo(packets) );
	}
	
	private static boolean moreToDo(boolean[] packets) {
		for( int i=0; i<packets.length; ++i) {
			if(packets[i] == false) return true;
		}
		return false;
	}
}
