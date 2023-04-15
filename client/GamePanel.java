import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.border.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;

import java.awt.Image;
import java.awt.image.*;
import javax.imageio.ImageIO;

import java.lang.Thread;
import java.lang.InterruptedException;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class GamePanel extends JPanel
{
	//Constants
	public static final int NUMBER_STATES = 1;
	//Amount of time the game waits to check the gamestate again, should be a large amount of time so the server
	//is not overwhelmed
	public static final int frameWaitTime = 3000;
	//Server states
	public static final int WAITING_PLAYERS = 1;
	public static final int GAME_RUNNING = 2;
	public static final int GAME_OVER = 3;
	
	public static State[] states = new State[NUMBER_STATES];
	private static BufferedImage mapImage;
	private GamePanelMouseListener mouseListener;
	
	//Game logic stuff
	//Clients copy of the game state, should be replaced with the server's version after requesting it 
	public GameState gameState;
	private GameStateUpdate currentGameStateUpdate;
	public int currentServerState;
	
	private Timer gameTimer;
	
	public static State selectedState;
	public static State selectedState2;
	
	public GamePanel()
	{
		super();
		//Load Images
		try
		{
			mapImage = ImageIO.read(new File("assets/map.png"));
		}
		catch(IOException e)
		{
			System.out.println("Could not load image");
		}
		//Add mouse listener to game panel. Mouse events only triggered by mouse input inside game panel
		//Other inputs from the sidebar should be handled separately
		mouseListener = new GamePanelMouseListener();
		this.addMouseListener(mouseListener);
		//Load states
		loadStates();
		
		//Start game loop
		gameTimer = new Timer(frameWaitTime, new GameLoop());
	}
	
	//Start game timer, should be called after connecting to the server
	public void startGame()
	{
		//get initial server status
		try
		{
			currentServerState = Client.server.getServerState();
		}
		catch(RemoteException exception)
		{
			exception.printStackTrace();
		}
		
		gameTimer.start();
	}
	//Game update function, also calls repaint to draw the screen
	private class GameLoop implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				currentServerState = Client.server.getServerState();
				System.out.println("Server state: " + currentServerState);
			}
			catch(RemoteException exception)
			{
				exception.printStackTrace();
			}
			//Request GameStateUpdate from server if the game has started
			if(currentServerState == GAME_RUNNING)
			{
				try
				{
					gameState = Client.server.getGameState();
				}
				catch(RemoteException exception)
				{
					exception.printStackTrace();
				}
				//Process players turn
				if(gameState.currentPlayerID == Client.playerID)
				{
					//Turn not started yet
					if(currentGameStateUpdate == null)
					{
						currentGameStateUpdate = new GameStateUpdate();
						currentGameStateUpdate.playerID = Client.playerID;
					}
					else
					{
						
					}
				}
			}
			repaint();
		}
	}
	//Creating state objects
	private void loadStates()
	{
		states[0] = new State("Test State", "assets/testState.png", 0);
	}
	//Go through each state image and see if the color is anything other than transparent
	//If it is, this indicates that state was clicked on
	public static void checkStateMouse(int mouseX, int mouseY)
	{
		int currentRGB;
		for(int i = 0; i < states.length; i++)
		{
			currentRGB = states[i].image.getRGB(mouseX, mouseY);
			//System.out.println("RGB " + i + " " + currentRGB);
			//RGB of 0 indicates the pixel is transparent
			if(currentRGB != 0)
			{
				states[i].click();
				//allow player to select friendly state
				if(selectedState == null && states[i].ownerPlayerID == Client.playerID)
				{
					selectedState = states[i];
					Client.stateSelectionPanel.getSelectedStateLabel().setText("Selected State: " + states[i].name);
				}
				//Select second state to receive unit movement
				else if(selectedState2 == null)
				{
					selectedState2 = states[i];
					Client.stateSelectionPanel.getSelectedStateLabel2().setText("Target State: " + states[i].name);
				}
			}
		}
	}
	
	public void paint(Graphics g)
	{
		//Have to call this first, resets the panel size otherwise
		super.paint(g);
		Graphics2D g2D = (Graphics2D)g;
		
		g2D.drawImage(mapImage,0,0,this);
		
		//draw all states
		for(int i = 0; i < states.length; i++)
		{
			states[i].draw(g2D, this);
		}
		Rectangle2D rectangle = new Rectangle2D.Float(40,40,20,20);
		g2D.draw(rectangle);
	}
	
}
