package com.server;
import java.net.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

class datagramReceiverSender{
	static JFrame window;
	//bind the server socket with a port number using "sobs -s" command
	public static int OpenServer(){
		int a = 0;
		int localPortNum;
		System.out.println("Please input a Port Number: (no smaller than 1024 and smaller than 65536)");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		localPortNum = Integer.parseInt(input.substring(0,input.length()));
		if (localPortNum < 1024 || localPortNum > 65536 ){
			System.out.println("Port Number "+localPortNum+" Wrong!");
			a = OpenServer();
		}
		else{
			a = localPortNum;
		}

		return a;
	}

	public static void enCode(int tmpPort) {  
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress().toString();
			ip = ip + ":" + tmpPort;
			String path = "code.png";
			BitMatrix bitMatrix;
			bitMatrix = new MultiFormatWriter().encode(new String(ip.getBytes("GBK"),"iso-8859-1"),  
					BarcodeFormat.QR_CODE, 300, 300);
			File file = new File(path);
			MatrixToImageWriter.writeToFile(bitMatrix, "png", file);
			String cmd = "display code.png" ;
			Runtime run = Runtime.getRuntime() ;
			Process pr = run.exec(cmd) ;
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}  

	public static void main(String[] args){

		try{
			int s = OpenServer();
			System.out.println(s + " OPENS SUCCESSFULLY!");
			enCode(s);
			DatagramSocket mySocket = new DatagramSocket(s);

			while(true){
				int MAX_LEN = 1200;
				byte[] recvBuffer = new byte[MAX_LEN];
				DatagramPacket packet = new DatagramPacket(recvBuffer, recvBuffer.length);
				mySocket.receive(packet);
				String message = new String(recvBuffer);
				//System.out.println(message);
				System.out.println(message);
				Robot robot = new Robot();
				String login[]=message.split(" ");
				int label = Integer.parseInt(login[0]);
				switch (label) {
				case 1:
					robot.keyPress(KeyEvent.VK_UP);
					robot.keyRelease(KeyEvent.VK_UP);
					break;
				case 2:
					robot.keyPress(KeyEvent.VK_SPACE);
					robot.keyRelease(KeyEvent.VK_SPACE);
					break;
				case 3:
					robot.keyPress(KeyEvent.VK_DOWN);
					robot.keyRelease(KeyEvent.VK_DOWN);
					break;
				case 4:
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_UP);
					robot.keyRelease(KeyEvent.VK_UP);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					break;
				case 5:
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_DOWN);
					robot.keyRelease(KeyEvent.VK_DOWN);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					break;
				case 6:
					robot.keyPress(KeyEvent.VK_LEFT);
					robot.keyRelease(KeyEvent.VK_LEFT);
					break;
				case 7:
					robot.keyPress(KeyEvent.VK_RIGHT);
					robot.keyRelease(KeyEvent.VK_RIGHT);
					break;
				}
			}
		}
		catch(Exception e){e.printStackTrace( );}
	}
}