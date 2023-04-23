package client;

import javax.swing.JFrame;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Container;
import java.beans.PropertyVetoException;
//Connection window class that allows the client to enter the server connection info and a username
//Extends the JInternalFrame so it behaves like a JFrame contained within the main application JFrame
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
	static ConnectionWindow connectionWindow;
	
	public ConnectionWindow()
	{
		super("Connect to server");
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		this.setLayout(new GridLayout(4,2));
		
		//Text input fields
		serverAddress = new JTextField();
		serverPort = new JTextField();
		playerName = new JTextField();
		
		//Labels for the text input fields
		serverAddressLabel = new JLabel("Server Address");
		serverPortLabel = new JLabel("Port");
		playerNameLabel = new JLabel("Name");
		
		//connection button that triggers the Connect ActionListener
		connectButton = new JButton("Connect");
		connectButton.addActionListener(new Connect());
		
		//The GridLayout adds elements from left to right, row by row
		//Elements must be added in this order to display correctly
		this.add(serverAddressLabel);
		this.add(serverAddress);
		
		this.add(serverPortLabel);
		this.add(serverPort);
		
		this.add(playerNameLabel);
		this.add(playerName);
		
		this.add(connectButton);
		
		this.setVisible(true);
		connectionWindow = this;
	}
	//Return a reference to the current ConnectionWindow object 
	private static ConnectionWindow getConnectionWindow()
	{
		return connectionWindow;
	}	
	
	//Action listener for the connect button
	private class Connect implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Client.initializeRMI(serverAddress.getText(), Integer.parseInt(serverPort.getText()));
			Client.registerWithServer(playerName.getText());
			
			//Begin game loop in gamePanel
			Client.gamePanel.startGame();
			//Close the JInternalFrame
			getConnectionWindow().setVisible(false);
		}
	}
}
