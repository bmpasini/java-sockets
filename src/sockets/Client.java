package sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	
	private String host;
	private int port;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Client("127.0.0.1", 12345).run();
	}
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void run() throws UnknownHostException, IOException {
		Socket client = new Socket(host, port);
		System.out.println("Client successfully connected to server!");
		
		// create a new thread for server messages handling
		new Thread(new ReceivedMessagesHandler(client.getInputStream())).start();
		
		// read messages from keyboard and send to server
//		System.out.print("Send a message: ");
		Scanner sc = new Scanner(System.in);
		PrintStream output = new PrintStream(client.getOutputStream());
		while (sc.hasNextLine()) {
			output.println(sc.nextLine());
		}

		output.close();
		sc.close();
		client.close();
	}
}

class ReceivedMessagesHandler implements Runnable {
	
	private InputStream server;
	
	public ReceivedMessagesHandler(InputStream server) {
		this.server = server;
	}
	
	@Override
	public void run() {
		// receive server messages and print out to screen
		Scanner s = new Scanner(server);
		while (s.hasNextLine()) {
//			System.out.print("Received message: ");
			System.out.println(s.nextLine());
		}
		s.close();
	}
}
