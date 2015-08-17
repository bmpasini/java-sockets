package sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

	private int port;
	private List<PrintStream> clients;
	private ServerSocket server;

	public static void main(String[] args) throws IOException {
		new Server(12345).run();
	}

	public Server(int port) {
		this.port = port;
		this.clients = new ArrayList<PrintStream>();
	}

	public void run() throws IOException {
		server = new ServerSocket(port) {
			protected void finalize() throws IOException {
				this.close();
			}
		};
		System.out.println("Port 12345 is now open.");

		while (true) {
			// accepts a new client
			Socket client = server.accept();
			System.out.println("Connection established with client: " + client.getInetAddress().getHostAddress());
			
			// add client message to list
			this.clients.add(new PrintStream(client.getOutputStream()));
			
			// create a new thread for client handling
			new Thread(new ClientHandler(this, client.getInputStream())).start();
		}
	}

	void broadcastMessages(String msg) {
		for (PrintStream client : this.clients) {
			client.println(msg);
		}
	}
}

class ClientHandler implements Runnable {

	private Server server;
	private InputStream client;

	public ClientHandler(Server server, InputStream client) {
		this.server = server;
		this.client = client;
	}

	@Override
	public void run() {
		String message;
		
		// when there is a new message, broadcast to all
		Scanner sc = new Scanner(this.client);
		while (sc.hasNextLine()) {
			message = sc.nextLine();
			server.broadcastMessages(message);
		}
		sc.close();
	}
}
