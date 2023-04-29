package client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//Connection window class that allows the client to enter the server connection info and a username
//Extends the JInternalFrame so it behaves like a JFrame contained within the main application JFrame
public class ConnectionWindow extends JInternalFrame
{
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 300;

	private JTextField serverAddress;
	private JTextField serverPort;
	private JTextField playerName;
	private JTextField empty;

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
		this.setBackground(Color.BLACK);
		this.setBorder(new LineBorder(Color.LIGHT_GRAY));

		//Text input fields
		serverAddress = new JTextField();
		serverPort = new JTextField();
		playerName = new JTextField();
		empty = new JTextField();

		//Center
		serverAddress.setHorizontalAlignment( JLabel.CENTER );
		serverPort.setHorizontalAlignment( JLabel.CENTER );
		playerName.setHorizontalAlignment( JLabel.CENTER );

		//Change Colors to White
		serverAddress.setForeground(Color.white);
		serverPort.setForeground(Color.white);
		playerName.setForeground(Color.white);

		//Set Field Background to Black
		serverAddress.setBackground(Color.DARK_GRAY);
		serverPort.setBackground(Color.DARK_GRAY);
		playerName.setBackground(Color.DARK_GRAY);

		serverAddress.setBorder(new LineBorder(Color.BLACK));
		serverPort.setBorder(new LineBorder(Color.BLACK));
		playerName.setBorder(new LineBorder(Color.BLACK));

		serverAddress.setFont(new Font("Monospaced", Font.BOLD, 20));
		serverPort.setFont(new Font("Monospaced", Font.BOLD, 20));
		playerName.setFont(new Font("Monospaced", Font.BOLD, 20));



		//Labels for the text input fields
		//
		serverAddressLabel = new JLabel("-- Server Address --");
		serverPortLabel = new JLabel("-- Port --");
		playerNameLabel = new JLabel("-- Name --");

		//Center
		serverAddressLabel.setHorizontalAlignment( JLabel.CENTER );
		serverPortLabel.setHorizontalAlignment( JLabel.CENTER );
		playerNameLabel.setHorizontalAlignment( JLabel.CENTER );

		//Change Colors to White
		serverAddressLabel.setForeground(Color.white);
		serverPortLabel.setForeground(Color.white);
		playerNameLabel.setForeground(Color.white);

		serverAddressLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
		serverPortLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
		playerNameLabel.setFont(new Font("Monospaced", Font.BOLD, 20));

		empty.setBackground(Color.BLACK);
		empty.setBorder(new LineBorder(Color.BLACK));

		//connection button that triggers the Connect ActionListener
		connectButton = new JButton("Connect");
		connectButton.addActionListener(new Connect());

		connectButton.setHorizontalAlignment( JLabel.CENTER );
		connectButton.setForeground(Color.white);
		connectButton.setBackground(Color.BLACK);
		connectButton.setFont(new Font("Monospaced", Font.BOLD, 20));
		connectButton.setBorder(new LineBorder(Color.LIGHT_GRAY));

		//The GridLayout adds elements from left to right, row by row
		//Elements must be added in this order to display correctly
		this.add(serverAddressLabel);
		this.add(serverAddress);

		this.add(serverPortLabel);
		this.add(serverPort);

		this.add(playerNameLabel);
		this.add(playerName);

		this.add(empty);
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
			//Set the player name in the status bar
			Client.statusBar.playerNameLabel.setText("Player Name: " + playerName.getText());
			//Close the JInternalFrame
			getConnectionWindow().setVisible(false);
		}
	}
}