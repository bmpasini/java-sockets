package sockets;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket client = new Socket("127.0.0.1", 12345);
		System.out.println("Client successfully connected to server!");

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
