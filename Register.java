import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Register extends JFrame {
	
	
	private JFrame frame;
	private JLabel pLabel;
	private JLabel uLabel;
	
	private JButton unhideButton;
	private int unhideToggle;
	
	
	
	private JButton registerButton;
	
	private JButton loginButton;
	
	private JLabel label;
	
	private JTextField userName;

	private JPasswordField passWord; // JPasswordField
	
	
	public String user = "";
	public String pass = "";
	
	
	static int maxUsers = 260;
	public userDataBase initialize = new userDataBase("","");
	public  userDataBase [] userData =  new userDataBase[maxUsers];

	private static int loggedIn;

	
	
	public Register()
	{
		unhideToggle = 0;
		
		

		setLoggedIn(0);
		frame = new JFrame("Register");
		frame.setBounds(60,60,350,150);
		frame.setResizable(true);
		
		unhideButton = new JButton("H/U");		
		
		userName = new JTextField();
		passWord = new JPasswordField();
		
		uLabel = new JLabel("Username:");
		pLabel = new JLabel("Password:");
		
		
		label = new JLabel("");

		
		registerButton = new JButton("Register");
		loginButton = new JButton("Login");
		
		userName.setBounds(75, 0, 200, 30);
		passWord.setBounds(75, 40, 200, 30);
		
		uLabel.setBounds(5,1 , 80, 25);
		pLabel.setBounds(5,1 , 80, 110);
		
		registerButton.setBounds(80,80,80,30);
		
		loginButton.setBounds(190,80,80,30);
		
		unhideButton.setBounds(282, 41, 30, 30);


		
		frame.add(userName);
		frame.add(passWord);
		frame.add(uLabel);
		frame.add(pLabel);
		
		frame.add(registerButton);
		frame.add(loginButton);
		
		frame.add(unhideButton);
		
		frame.add(label);//
		

		frame.setVisible(true);

		
		registerButton.setVisible(true);
		loginButton.setVisible(true);

		
		userName.setEditable(true);
		userName.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event)
					{
						user = event.getActionCommand();
						userName.setText("");
					}
				});
		

		
		passWord.setEditable(true);
		passWord.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event)
					{
						pass = event.getActionCommand();
						passWord.setText("");
					}
				});
		
		registerButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						if (passWord.getText().equals("") || userName.getText().contentEquals(""))
						{
							JOptionPane.showMessageDialog(frame, "ERROR: User did not register a username and/or a password.\n");
						}
						
						else if (userName.getText().length()<5|| userName.getText().length()>10)
						{
							JOptionPane.showMessageDialog(frame, "ERROR: Username muse be greater than 4 characters and less than 11.\n");
						}
						
						else 
						{
							if (userDataBase.isEmpty(userName.getText(), userData))
							{
								userDataBase.insertData(userName.getText(),passWord.getText(),userData);///////
								userDataBase.printTable(userData);
	

							}
							
							else
							{
								JOptionPane.showMessageDialog(frame, "ERROR: Username Taken\n");

							}
						
						}
						
					}
				}
				
			);
		
		loginButton.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					
					
				 if (passWord.getText().contentEquals("") || userName.getText().contentEquals(""))
					{
						JOptionPane.showMessageDialog(frame, "ERROR: User did not enter a username and/or a password.\n");
					}
				 
				 else if(userDataBase.isEmpty(userName.getText(),userData))
					{
						JOptionPane.showMessageDialog(frame, "ERROR: INVALID LOGIN. NO SUCH USER EXISTS.\n");
					}
					
					else if ((userDataBase.isValidPassword(userName.getText(), userData)).equals(passWord.getText()))
					{
						
						setLoggedIn(1);
						user = userName.getText();
						frame.dispose();
						
						
					}
					else
					{
						JOptionPane.showMessageDialog(frame, "ERROR: Incorrect username and/or a password.\n");
					}
					
				}
				
			}
			
		);
		
		
		unhideButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed (ActionEvent event)
					{
						
						if (unhideToggle ==0)
						{
							unhideToggle = 1;
						passWord.setEchoChar((char)0);
						}
						
						else
						{
							unhideToggle = 0;
						passWord.setEchoChar('•');
						}
						
					}
				}
				
				
				);
		
		//setVisible(true);
		
	}



	public static int getLoggedIn() {
		
		
		return loggedIn;
	}



	public static void setLoggedIn(int loggedIn) {
		Register.loggedIn = loggedIn;
	}

	
	

}
