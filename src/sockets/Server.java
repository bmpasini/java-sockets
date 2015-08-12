package sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	public static void main(String[] args) throws IOException {

		ServerSocket server = new ServerSocket(12345);
		System.out.println("Port 12345 is open.");

		Socket client = server.accept();
		System.out.println("Connection established with client: " + client.getInetAddress().getHostAddress());

		Scanner sc = new Scanner(client.getInputStream());
		while (sc.hasNextLine()) {
			System.out.println(sc.nextLine());
		}

		sc.close();
		server.close();
	}
}
