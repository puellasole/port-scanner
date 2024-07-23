import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PortScanner {
	
	private final static int THREADS = 100;
	private final static int TIMEOUT = 100;
	private final static int MIN_PORT_NUMBER = 0;
	private final static int MAX_PORT_NUMBER = 65335;

	public static void main(String[] args) {
		scan("en.wikipedia.org");
	}

	private static void scan(String host) {
		ExecutorService executorService = Executors.newFixedThreadPool(THREADS);
		
		for(int i = MIN_PORT_NUMBER; i<=MAX_PORT_NUMBER; i++) {
			final int port = i;
			executorService.execute(() -> {
				var inetSocketAddress = new InetSocketAddress(host, port);
				try (var socket = new Socket()) {
					socket.connect(inetSocketAddress, TIMEOUT);
					System.out.printf("Host: %s, port %d is open\n", host, port);
				} catch (IOException ignored) {}
			});
		}
		executorService.shutdown();
		try {
			executorService.awaitTermination(5, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println("Finish!");
	}
}
