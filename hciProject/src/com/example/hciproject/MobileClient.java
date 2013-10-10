package com.example.hciproject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class MobileClient {
	private String serverHostname = null;
	private int serverPort = 0;
	private DatagramSocket clientSocket;

	public void setClient(String S1, int S) throws SocketException {
		serverHostname =  S1;
		serverPort = S;
		clientSocket = new DatagramSocket();
	}
	
	public void printInfo() {
		System.out.println(serverHostname);
		System.out.println(serverPort);
	}

	public void sendMessages(String strData) throws IOException {
		byte[] buffer = strData.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, 
				InetAddress.getByName(serverHostname), serverPort);
		clientSocket.send(sendPacket);
		System.out.println("Send to receiver: " + strData);
	}

}
