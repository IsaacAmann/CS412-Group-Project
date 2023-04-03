import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.border.*;

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
	public static final int WINDOW_WIDTH = 1300;
	public static final int WINDOW_HEIGHT = 700;
	
	public static final int GAME_PANEL_HEIGHT = WINDOW_HEIGHT;
	public static final int GAME_PANEL_WIDTH = 1000;
	
	public static JPanel rightPanel;
	public static GamePanel gamePanel;
	
	public static Graphics gameGraphics;
	
	public static WarRoomServerInterface server;
	public static String host = "localhost";
	public static int port = 12345;
	
	public Client()
	{
		super("War Room Client");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		//Create rightPanel
		createRightPanel();
		
		//Create gamePanel
		createGamePanel();
		
		//add panels 
		this.add(gamePanel);
		this.add(rightPanel);
		
		//Set graphics object for drawing game screen
		gameGraphics = gamePanel.getGraphics();
		//gameGraphics.draw(new Rectangle2D.Float(40,40,20,20));
		
		initializeRMI();
		
		this.setVisible(true);	
	}
	
	private void initializeRMI()
	{
		try
		{
		Registry registry = LocateRegistry.getRegistry(host,port);
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
		
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		rightPanel.setBackground(Color.RED);
		//rightPanel.setSize(RIGHT_PANEL_WIDTH, RIGHT_PANEL_HEIGHT);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
		rightPanel.setBorder(border);
		
		rightPanel.setVisible(true);
	}
	
	private void createGamePanel()
	{
		//Possibly use swing elements as click detectors overlayed on map
		//use element.setBounds(x,y,width,height) to set position
		gamePanel = new GamePanel();
		gamePanel.setSize(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
		
		gamePanel.setLayout(null);
		//gamePanel.setBackground(Color.GREEN);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
		gamePanel.setBorder(border);
		
		gamePanel.setVisible(true);
	}
}
