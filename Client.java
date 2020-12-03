import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame {

	
	private JFrame frame;
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	
	private JButton connectButton;
	private String connectButtonLabel;
	
	public static int connected;
	
	

	
	//constructor
	public Client(String host)
	{
		
		super("Project #4");
		
		
		connectButton = new JButton();
		connectButton.setText("Connect");

		connected=0;
		

		frame = new JFrame();
		
		serverIP = host;
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event)
					{
						sendMessage(event.getActionCommand());
						userText.setText("");
					}
				});
		
		frame.add(userText,BorderLayout.NORTH);
		chatWindow = new JTextArea();
		frame.add(new JScrollPane(chatWindow),BorderLayout.CENTER);
		
		frame.add(connectButton,BorderLayout.SOUTH);
		
		frame.setSize(300,150);
		frame.setVisible(true);
		
		connectButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed (ActionEvent event)
					{
						if (connected == 1)
						{
							connected =0;
							connectButton.setText("Connect");
							closeAll();
						}
						
						else if (connected == 0)
						{
							connected = 2;
						}
						
					}
				}
				);
	}
	
	
	//connect to server
	public void runApplication()
	{
		try {
			//frame.setVisible(false);
			connectToServer();

			if (connected == 1)
			{

			setupStreams();
			whileChatting();

			}
			else
			{
				connectButton.setText("Connect");
				connected =0;
				displayMessage("\n No server exists!");
				
			}
			
		}
		catch(EOFException eofException)
		{

			displayMessage("\n Client ended connection");
		}
		catch(IOException ioException)
		{

			System.out.print("DISCONNECTED\n");

			if(connected == 0)
			{
				return;
			}
			ioException.printStackTrace();
		}
		
			
		}
		
	
	
	//connect to server
	private void connectToServer() throws IOException
	{

		
		displayMessage("Attempting connection...");
		
		try {

		connection = new Socket(InetAddress.getByName(serverIP),6789);
		

		displayMessage("Connected to: "+ connection.getInetAddress().getHostName());


		
		connectButton.setText("Disconnect");
		

		
		}
		
		catch(Exception e)
		{
			System.out.printf("No server to connect to\n");
			connected = 0;
		}
		


		
		System.out.printf("CONNECTED\n");
		connected = 1;


	}
	
	//set up streams
	private void setupStreams() throws IOException
	{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		displayMessage("\n Streams are now setup!");
	}
	
	private void whileChatting() throws IOException
	{
		

		ableToType(true);
		do {
			try {
				message = (String) input.readObject();
				displayMessage("\n"+message);
			}
			catch(ClassNotFoundException classNotFoundException){
				
				displayMessage("\n ERROR: Invalid String!");
			}
			
		}
		while(!message.contentEquals("SERVER - END"));
	}
	
	private void closeAll()
	{

		displayMessage("\nClosing connections...");
		ableToType(false);
		
		try {
				output.close();
				input.close();
				connection.close();
				

			}
		
		catch(IOException ioException)
		{
			ioException.printStackTrace();
		}
	}
	
	
	//send messaged to server
	private void sendMessage(String message)
	{
		try {
			output.writeObject("CLIENT - "+message);
			output.flush();
			displayMessage("\nCLIENT - "+message);
		}
		catch(IOException ioException)
		{
			chatWindow.append("\nERROR: Unable to send message!");
		}
	}
	
	//update chat window
	private void displayMessage(final String message)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatWindow.append(message);
			}
		}
	);		
		
  }
	
	// able to type
	
	private void ableToType(final boolean editable)
	{
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run()
					{
						userText.setEditable(editable);
					}
				}
			);
	}

	public static void main(String[] args) throws InterruptedException {
		Client app = new Client("127.0.0.1");
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			while(true)
			{
				TimeUnit.SECONDS.sleep(1);
				
				if(connected == 2)
				{
					app.runApplication();
				}
			}
		
		
	}
}


