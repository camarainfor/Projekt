import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
	
	private static final String SERVER_IP_ADDRESS = "127.0.0.1";
	private static final int SERVER_PORT_ID = Server.SERVER_PORT_ID;

	public static void start() {

		try {
			
			DatagramSocket clientPort = new DatagramSocket();
			String request = "Frage";
			byte[] requestPDU = request.getBytes();
			InetAddress serverAddress = InetAddress.getByName(SERVER_IP_ADDRESS);
			DatagramPacket requestDatagram = new DatagramPacket(requestPDU, requestPDU.length, serverAddress, SERVER_PORT_ID);
			clientPort.send(requestDatagram); 
			System.out.println("Sending " + request + " to " + SERVER_IP_ADDRESS + "/" + SERVER_PORT_ID);
			clientPort.close();
		} catch (SocketException e ) {
			e.printStackTrace();
		} catch (UnknownHostException e){
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Client.start();
	}
}
