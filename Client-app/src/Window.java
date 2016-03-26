import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class Window extends JFrame{
	
	Client client;
	String title;
	String username;
	int w, h;
	
	JButton loginB;
	JButton logoutB;
	JLabel UserLabel;
	JLabel statusLabel;
	JList<String> usersList;
	UsersModel listModel;
	JTextArea chatArea;
	JButton sendB;
	
	public Window(String title, int w, int h) {
		// TODO Auto-generated constructor stub
		
		this.title = title;
		this.username = "USER";
		this.w = w;
		this.h = h;
		JFrame frame = this;
		listModel = new UsersModel();
		listModel.users.add("aziz");
		
		this.setTitle(title);
		this.setSize(w, h);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(new FlowLayout());
		
		loginB = new JButton("Login");
		this.add(loginB);
		
		JPanel mainPanel = new JPanel(new GridLayout(3, 2, 50, 20));
		mainPanel.setVisible(false);
		
		logoutB = new JButton("Logout");
		sendB = new JButton("Send");
		UserLabel = new JLabel("");
		statusLabel = new JLabel("");
		usersList = new JList<String>(listModel);
		chatArea = new JTextArea(5, 10);
				
		mainPanel.add(logoutB);
		mainPanel.add(UserLabel);
		mainPanel.add(usersList);
		mainPanel.add(chatArea);
		mainPanel.add(sendB);
		mainPanel.add(statusLabel);
		
		this.add(mainPanel);
		
		sendB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						client.send("aziz", "memo", "hhhhhhhh");
					}
				}).start();
			}
		});
		
		logoutB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.setTitle(title);
				loginB.setVisible(true);
				mainPanel.setVisible(false);
				client.close();
			}
		});
		
		loginB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = (String) JOptionPane.showInputDialog(frame, "Enter Username:", "Username", JOptionPane.PLAIN_MESSAGE);
				if((name != null) && (name.length() > 0)){
					username = name;
				}else{
					username = "USER";
				}
				
				frame.setTitle("User-->"+username);
				UserLabel.setText(username);
				loginB.setVisible(false);
				mainPanel.setVisible(true);
				
				
				client = new Client(username);
				
				try {
					if(client.connect()){
						System.out.println("connected");
						statusLabel.setText("Connected");
					}else{
						System.out.println("logout");
						logoutB.doClick();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
		
		
		
	}
	
}
