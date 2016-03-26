import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Client {
	
	public String serverIp = "localhost";
	public String serverValidation = "ok";
	public int port;
	public String username;
	public String response;
	Socket socket;
	public DataOutputStream serverOut;
	public DataInputStream serverIn;
	
	
	public Client() {
		// TODO Auto-generated constructor stub
		this("192.168.1.4", 7777, "aziz");
	}

	public Client(String username) {
		// TODO Auto-generated constructor stub
		this("192.168.1.4", 7777, username);
	}
	
	public Client(String Ip, int port, String username) {
		// TODO Auto-generated constructor stub
		this.serverIp = Ip;
		this.port = port;
		this.username = username;		
	}
	
	public boolean connect() throws UnknownHostException, IOException{
		socket = new Socket(serverIp, port);
		
		serverOut = new DataOutputStream(socket.getOutputStream());
		serverIn = new DataInputStream(socket.getInputStream());
		
		serverOut.writeUTF(username);
		serverOut.flush();
		
		response = serverIn.readUTF();
		System.out.println(response);
		if(!response.equals(serverValidation)){
			return false;
		}
		
		return true;
	}
	
	
	public boolean close(){
		
		try {
			serverIn.close();
			serverOut.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean send(String sender, String receiver, String message){
		
		String msg = sender+","+receiver+","+message;
		
		try {
			serverOut.writeUTF(msg);
			
			response = serverIn.readUTF();
			
			System.out.println(response);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public String[] requestUsersList(){
		String[] users = null;
		
		try {
			serverOut.writeUTF("userslist");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return users;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame w = new Window("Mighty Chat", 500, 500);
		
//		String ip = "localhost";
//		String username;
//		String response;
//		DataOutputStream serverOut;
//		DataInputStream serverIn;
//		Scanner in = new Scanner(System.in);
//		
//		System.out.println("Enter your name to login.");
//		
//		username = in.next();
		
//		System.out.println("Enter IP Address of server.");
//		String temp = in.next();
//		if(!temp.equals("1")){
//			ip = temp;
//		}
//		ip = "192.168.1.4";
		
//		try {
//			Socket socket = new Socket(ip, 7777);
//			
//			serverOut = new DataOutputStream(socket.getOutputStream());
//			serverIn = new DataInputStream(socket.getInputStream());
//			
//			serverOut.writeUTF(username);
//			serverOut.flush();
//			
//			response = serverIn.readUTF();
//			
//			if(!response.equals("ok")){
//				
//			}
//			
//			Thread inputThread = new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					String inputStr;
//					String[] messages;
//					
//					while(true){
//						try {
//							inputStr = serverIn.readUTF();
//							messages = inputStr.split(",");
//							
//							System.out.println(messages[0]+": "+messages[1]);
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
////							System.out.println("Error: Message was not recieved");
//						}
//						
//						
//					}
//				}
//			});
//			inputThread.start();
//			
//			Thread outputThread = new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					String userinput;
//					String message;
//					while(true){
//						userinput = in.next();
//						if(userinput.equals("q")){
//							try {
//								serverOut.close();
//								serverIn.close();
//								socket.close();
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								System.out.println("Error client ended unexpectedly.");
//							}
//							break;
//						}else{
//							message = username + "," + userinput;
//							try {
//								serverOut.writeUTF(message);
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								System.out.println("Error message could not be sent.");
//							}
//						}
//					}
//				}
//			});
//			outputThread.start();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//			System.out.println("Server did not accept connection. Server is out of reach.");
//		}
	}

}
