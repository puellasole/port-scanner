import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PortScanner {
	
	private final static int THREADS = 100;
	private final static int TIMEOUT = 100;
	private final static int MIN_PORT_NUMBER = 0;
	private final static int MAX_PORT_NUMBER = 65335;

	public static void main(String[] args) {
		scan("old-orel-city.narod.ru");

	}

	private static void scan(String host) {
		for(int port = MIN_PORT_NUMBER; port<=MAX_PORT_NUMBER; port++) {
			var inetSocketAddress = new InetSocketAddress(host, port);
			try (var socket = new Socket()) {
				socket.connect(inetSocketAddress, TIMEOUT);
				System.out.printf("Host: %s, port %d is open\n", host, port);
			} catch (IOException ignored) {}
		}
	}
}
