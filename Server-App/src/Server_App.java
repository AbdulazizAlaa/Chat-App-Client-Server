import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

public class Server_App {
	private static String serverCommand="";
	private static String[] messageSplited;
	private static ArrayList<Socket> socketArray=new ArrayList<Socket>();
	private static ArrayList<String> socketname=new ArrayList<String>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello to our chat system");
		
		
		
		try {
			
			final ServerSocket ss=new ServerSocket(7777);
			System.out.println("The Server is started");
		
//			Thread stopServer=new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					
//					
//					System.out.println("Enter stop to close the server");
//					Scanner sc=new Scanner(System.in);
//					serverCommand=sc.nextLine();
//					
//				}
//			});
//			
//			stopServer.run();
			
//			Thread ReadMessageFromClient=new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					String message;
//					String [] splitedMessage;
//					for(int i=0;i<socketname.size()-1;i=i+1%socketname.size()){
//						
//	
//						try {
//							DataInputStream dis;
//							dis = new DataInputStream(socketArray.get(i).getInputStream());
//							
//							DataOutputStream dos = new DataOutputStream(socketArray.get(i).getOutputStream());
//							BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
//							
//							message=dis.readUTF();
//							System.out.println(message);
//							dos.writeUTF("ok");
//							//sender ,reciever,message
//							splitedMessage=message.split(",");
//							int index=socketname.indexOf(splitedMessage[1]);
//							if(index!=-1){
//								DataOutputStream dout=new DataOutputStream(socketArray.get(index).getOutputStream());
//								dout.writeUTF(splitedMessage[0]+","+splitedMessage[2]);
//								dos.flush();
//							}
//							
//							
//							
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//	
//					}					
//				}
//			});
//			ReadMessageFromClient.start();
			
			while(!serverCommand.equals("stop")){
				
				
				String read="";

				Socket s=ss.accept();
				System.out.println("new Client added");
				
				DataInputStream dis;
				dis = new DataInputStream(s.getInputStream());

				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				
				read=dis.readUTF();
				System.out.println("Client name :"+read);
	
				final int i = socketArray.size();
				
				socketname.add(read);
				socketArray.add(s);
				dos.writeUTF("ok");
				dos.flush();
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							DataInputStream dis = new DataInputStream(socketArray.get(i).getInputStream());
							
							DataOutputStream dos = new DataOutputStream(socketArray.get(i).getOutputStream());
							
							String message = dis.readUTF();
							System.out.println(message);
							dos.writeUTF("ok");
							//sender ,reciever,message
							String [] splitedMessage = message.split(",");
							int index=socketname.indexOf(splitedMessage[1]);
							if(index!=-1){
								System.out.println("hello"+index);
								DataOutputStream dout=new DataOutputStream(socketArray.get(index).getOutputStream());
								dout.writeUTF(splitedMessage[0]+","+splitedMessage[2]);
								System.out.println("ok sent");
								dos.flush();
							}
							
							
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
						
			}
			
			
			ss.close();
			
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
