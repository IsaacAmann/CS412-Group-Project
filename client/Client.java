import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.*;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;


public class Client extends JFrame
{
	//Constants
	public static final int WINDOW_WIDTH = 1300;
	public static final int WINDOW_HEIGHT = 700;
	
	public static final int GAME_PANEL_HEIGHT = WINDOW_HEIGHT;
	public static final int GAME_PANEL_WIDTH = 1000;
	
	//Server statuses
	public static final int WAITING_PLAYERS = 1;
	public static final int GAME_RUNNING = 2;
	public static final int GAME_OVER = 3;
	
	public static JPanel rightPanel;
	public static GamePanel gamePanel;
	public static StateSelectionPanel stateSelectionPanel;

	public static JTextField chatField;
	public static JTextArea chatArea;
	public static int chatNum = 0;
	
	public static Graphics gameGraphics;
	
	public static WarRoomServerInterface server;
	public static String host = "localhost";
	public static int port = 12345;
	
	public static int playerID;
	
	
	public Client()
	{
		super("War Room Client");

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		//this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		//this.getContentPane().setLayout(null);
		//Create rightPanel
		createRightPanel();
		
		//Create gamePanel
		createGamePanel();
		
		//Add connection window
		this.add(new ConnectionWindow());
		
		//add panels 
		this.add(gamePanel, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.LINE_END);
		
		
		//Set graphics object for drawing game screen
		gameGraphics = gamePanel.getGraphics();
		//gameGraphics.draw(new Rectangle2D.Float(40,40,20,20));
		
		//this.pack();
		this.setVisible(true);

		//chatStuff();
	}

	public static void chatStuff()
	{
		try {
			while (true)
			{
				String[] messages = Client.server.getChats(chatNum);
				chatArea.append("\n" + messages[0]);
				chatNum++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//register with server
	//Register with server and wait for game to start
	public static void registerWithServer(String playerName)
	{
		try
		{
			playerID = Client.server.registerPlayer(playerName);
		}
		catch(RemoteException exception)
		{
			exception.printStackTrace();
		}
	}
	
	//Attempt to connect to the server 
	public static void initializeRMI(String address, int port)
	{
		try
		{
			Registry registry = LocateRegistry.getRegistry(address,port);
			server = (WarRoomServerInterface) registry.lookup("WarRoomRMIImplementation");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			server.testPrint("Hey");
		}
		catch(Exception e)
		{
			
		}
	
	}
	
	private void createRightPanel()
	{
		
		rightPanel = new JPanel();
		stateSelectionPanel = new StateSelectionPanel();

		chatField = new JTextField("Enter Message Here");
		chatField.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					try {
						Client.server.sendChatMessage(event.getActionCommand(), playerID);
						chatField.setText("");
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		);				

		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setPreferredSize(new Dimension(100, 525));
		
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		//rightPanel.setLayout(null);
		rightPanel.setBackground(Color.RED);
		//rightPanel.setSize(RIGHT_PANEL_WIDTH, RIGHT_PANEL_HEIGHT);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
		rightPanel.setBorder(border);
		
		rightPanel.add(stateSelectionPanel);
		rightPanel.add(chatArea);
		rightPanel.add(chatField);
		
		//rightPanel.setVisible(true);
		
		//rightPanel = new StateSelectionPanel();
		rightPanel.setVisible(true);

		
	}
	
	private void createGamePanel()
	{
		gamePanel = new GamePanel();
		gamePanel.setSize(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
		
		gamePanel.setLayout(null);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
		gamePanel.setBorder(border);
		
		gamePanel.setVisible(true);
	}
	
}
