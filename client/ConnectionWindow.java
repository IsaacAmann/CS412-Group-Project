import javax.swing.JFrame;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConnectionWindow extends JInternalFrame
{
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 300;
	
	private JTextField serverAddress;
	private JTextField serverPort;
	private JTextField playerName;
	
	private JLabel serverAddressLabel;
	private JLabel serverPortLabel;
	private JLabel playerNameLabel;
	
	private JButton connectButton;
	
	public ConnectionWindow()
	{
		super("Connect to server");
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		this.setLayout(new GridLayout(4,2));
		
		serverAddress = new JTextField();
		serverPort = new JTextField();
		playerName = new JTextField();
		
		serverAddressLabel = new JLabel("Server Address");
		serverPortLabel = new JLabel("Port");
		playerNameLabel = new JLabel("Name");
		
		connectButton = new JButton("Connect");
		connectButton.addActionListener(new Connect());
		
		this.add(serverAddressLabel);
		this.add(serverAddress);
		
		this.add(serverPortLabel);
		this.add(serverPort);
		
		this.add(playerNameLabel);
		this.add(playerName);
		
		this.add(connectButton);
		
		this.setVisible(true);
	}
	
	//Action listener for the connect button
	private class Connect implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Client.initializeRMI(serverAddress.getText(), Integer.parseInt(serverPort.getText()));
			Client.registerWithServer(playerName.getText());
			
			//make it invisible, might be needed later to reconnect
			//this.setVisible(false);
		}
	}
}
