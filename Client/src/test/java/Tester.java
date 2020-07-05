
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Tester {

	private static final int SERVER_PORT_ID = Server.SERVER_PORT_ID;
	private static String request = null;

	@Test
	void testCase() {
		Thread tester = new Thread(() -> {
			Tester.receive();
		});
		tester.start();

		Thread client = new Thread(() -> {
			Client.start();
		});
		client.start();
		try {
			client.join();
			tester.join();
		} catch (InterruptedException e) {
			Assertions.fail(e);
		}
		Assertions.assertEquals(request, "Frage");

	}

	static private void receive() {

		DatagramSocket testerPort;
		try {
			testerPort = new DatagramSocket(SERVER_PORT_ID);
			final int BUFFER_SIZE = 1024;
			DatagramPacket responseDatagramm = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
			final int WAIT_TIME_MILLI_SEC = 3000;
			try {
				testerPort.setSoTimeout(WAIT_TIME_MILLI_SEC);
			} catch (SocketException e1) {
				e1.printStackTrace();
			}
			boolean timeout = false;
			testerPort.receive(responseDatagramm);
			if (!timeout) {
				byte[] responsePDU = responseDatagramm.getData();
				request = new String(responsePDU).trim();
			} else {
				request = null;
			}
			testerPort.close();
		} catch (SocketException e1) {
			request = null;
		} catch (IOException e1) {
			request = null;
		}
	}
}
