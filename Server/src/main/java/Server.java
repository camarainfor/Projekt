import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
/**
 * ProjektUDP
 * @author jkrug/acamara
 * @version 20/04/20
 */
public class Server {
	
	public static final int SERVER_PORT_ID = 10001;
	public static void start() {

		try {
			DatagramSocket serverPort = new DatagramSocket(SERVER_PORT_ID);
			final int BUFFER_SIZE = 1024;
			DatagramPacket requestDatagram = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
			serverPort.receive(requestDatagram);
			byte[] requestPDU = requestDatagram.getData();
			String request = new String(requestPDU).trim();
			InetAddress originatingAddress = requestDatagram.getAddress();
			int originatingPortId = requestDatagram.getPort();
			System.out.println(
					"Server: received request: " + request + " from " + originatingAddress + ":" + originatingPortId);
			serverPort.close();
		} catch (SocketException e ) {
			e.printStackTrace();
		} catch (UnknownHostException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server.start();
	}
}
