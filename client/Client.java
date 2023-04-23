package client;

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

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client extends JFrame
{
	//Constants

	public static final int WINDOW_HEIGHT = 700;
	public static final int WINDOW_WIDTH = 1300;
	
	public static final int GAME_PANEL_HEIGHT = 700;
	public static final int GAME_PANEL_WIDTH = 1000;
	
	//Server statuses
	public static final int WAITING_PLAYERS = 1;
	public static final int GAME_RUNNING = 2;
	public static final int GAME_OVER = 3;
	
	public static JPanel rightPanel;
	public static GamePanel gamePanel;
	public static StateSelectionPanel stateSelectionPanel;
	public static StatusBar statusBar;
	
	public static Graphics gameGraphics;
	
	public static WarRoomServerInterface server;
	public static String host = "localhost";
	public static int port = 12345;
	
	public static int playerID;
	
	
	public Client()
	{

		//Gui Setup
		super("War Room - 412");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		//When Minimized
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		//When Maximized (Default)
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.getContentPane().setBackground(Color.DARK_GRAY);
		this.setVisible(true);


		//this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		//this.getContentPane().setLayout(null);
		//Create rightPanel
		createRightPanel();
		
		//Create gamePanel
		createGamePanel();
		
		//Create status bar
		statusBar = new StatusBar();
		
		//Add connection window
		//this.add(new ConnectionWindow());
		
		//add panels 
		this.add(gamePanel, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.LINE_END);
		this.add(statusBar, BorderLayout.PAGE_START);
		

		this.setVisible(true);

	}
	
	//Register with server
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
		catch(Exception e) {

		}
	}
	
	private void createRightPanel()
	{
		
		rightPanel = new JPanel();
		stateSelectionPanel = new StateSelectionPanel();


		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		//rightPanel.setLayout(null);
		rightPanel.setBackground(Color.BLACK);

		int width = (int)(Math.round(getWidth() * 0.2));
		rightPanel.setSize((WINDOW_HEIGHT - GAME_PANEL_WIDTH), GAME_PANEL_HEIGHT);


		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
		rightPanel.setBorder(border);
		
		rightPanel.add(stateSelectionPanel);
		//rightPanel = new StateSelectionPanel();
		rightPanel.setVisible(true);
	}
	
	private void createGamePanel()
	{
		gamePanel = new GamePanel();
		gamePanel.setSize(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
		gamePanel.setBackground(Color.BLACK);

		gamePanel.setLayout(null);

		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
		gamePanel.setBorder(border);

		gamePanel.setVisible(true);
	}
}
