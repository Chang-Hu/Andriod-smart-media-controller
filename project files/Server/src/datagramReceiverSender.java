import java.net.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.*;
import java.awt.event.*;

class datagramReceiverSender{

	//bind the server socket with a port number using "sobs -s" command
	static int OpenServer(){
		int a = 0;
		int localPortNum;
		System.out.println("Please input a Port Number: (no smaller than 1024 and smaller than 65536)");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		localPortNum = Integer.parseInt(input.substring(0,input.length()));
		if (localPortNum<1024 || localPortNum>65536 ){
			System.out.println("Port Number "+localPortNum+" Wrong!");
			a= OpenServer();
		}
		else{
			a = localPortNum;
		}


		return a;
	}

	public static void main(String[] args){
		try{
			int s =OpenServer();
			System.out.println(s+" OPENS SUCCESSFULLY!");
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
				switch (login[0]) {
				case "1":
					robot.keyPress(KeyEvent.VK_UP);
					robot.keyRelease(KeyEvent.VK_UP);
					break;
				case "2":
					robot.keyPress(KeyEvent.VK_SPACE);
					robot.keyRelease(KeyEvent.VK_SPACE);
					break;
				case "3":
					robot.keyPress(KeyEvent.VK_DOWN);
					robot.keyRelease(KeyEvent.VK_DOWN);
					break;
				case "4":
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_UP);
					robot.keyRelease(KeyEvent.VK_UP);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					break;
				case "5":
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_DOWN);
					robot.keyRelease(KeyEvent.VK_DOWN);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					break;
				case "6":
					robot.keyPress(KeyEvent.VK_LEFT);
					robot.keyRelease(KeyEvent.VK_LEFT);
					break;
				case "7":
					robot.keyPress(KeyEvent.VK_RIGHT);
					robot.keyRelease(KeyEvent.VK_RIGHT);
					break;
				}
			}
		}
		catch(Exception e){e.printStackTrace( );}
	}
}