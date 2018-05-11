package ControllerManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ConnectionManager implements Runnable {
	
	int listen_port;
	byte[] buffer;
	DatagramSocket server;
	DatagramPacket packet;
	String msg;
	int clientCount=0; 
	String clientID="";
	String content="";
	DatagramPacket response;
	
	BoardManager boardManager;
	KeyboardRobot robot;
	MainController controller;
	public ConnectionManager(int port) {
		this.listen_port = port;
		msg = "";		
		boardManager = BoardManager.getBoardManager();
		robot = new KeyboardRobot();
	}
	
	public ConnectionManager(int port, MainController mainController) {
		this.listen_port = port;
		msg = "";		
		boardManager = BoardManager.getBoardManager();
		robot = new KeyboardRobot();
		controller = mainController;
	}

	@Override
	public void run() {
		this.begin();
		this.listen();
	}
	private void begin() {
		buffer = new byte[65507];
		try {
			packet = new DatagramPacket(buffer, buffer.length);
			server = new DatagramSocket(listen_port); // Set Server Port
			System.out.println("伺服器啟動於 : " + InetAddress.getLocalHost().getHostAddress() + ":" + server.getLocalPort());
			
			controller.showWifiInfo("伺服器啟動於 : \n" + InetAddress.getLocalHost().getHostAddress() + ":" + server.getLocalPort());		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void listen() {
		try {
			
			while (true) {
				server.receive(packet);
				//redirect to handler
				msg = new String(packet.getData(), 0, packet.getLength());

//				System.out.println("received data from "+ msg);
//				controller.showSysInfo("received data from "+ msg);
				clientID=msg.split(":")[0];
				content = msg.split(":")[1];
				
				if(!boardManager.contain(clientID)) {
					boardManager.add(clientID);
//					controller.showBoardInfo(clientID);
				}
				//TODO tell controller which btn of borad is clicked
				//msg = 0,1,2,3
				if(content.length()<2) {
					robot.click(BoardManager.getKeyNo(clientID, Integer.valueOf(content)));
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
