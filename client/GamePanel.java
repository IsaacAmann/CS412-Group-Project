import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.border.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

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

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;


public class GamePanel extends JPanel
{
	//Constants
	public static final int NUMBER_STATES = 42;
	//Amount of time the game waits to check the gamestate again, should be a large amount of time so the server
	//is not overwhelmed
	public static final int frameWaitTime = 250;
	//Server states
	public static final int WAITING_PLAYERS = 1;
	public static final int GAME_RUNNING = 2;
	public static final int GAME_OVER = 3;
	
	//Checksum / hash for checking copy of game state
	
	
	
	public static State[] states = new State[NUMBER_STATES];
	private static BufferedImage mapImage;
	private GamePanelMouseListener mouseListener;
	
	//Game logic stuff
	//Clients copy of the game state, should be replaced with the server's version after requesting it 
	public static GameState gameState;
	public static GameStateUpdate currentGameStateUpdate;
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
			//mapImage = ImageIO.read(new File("assets/sea.png"));
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			mapImage = ImageIO.read(loader.getResource("assets/sea.png"));
		}
		catch(IOException e)
		{
			System.out.println("Could not load image");
		}
		//Add mouse listener to game panel. Mouse events only triggered by mouse input inside game panel
		//Other inputs from the sidebar should be handled separately
		mouseListener = new GamePanelMouseListener();
		this.addMouseListener(mouseListener);
		selectedState = null;
		selectedState2 = null;
		//Load states
		states = StateLoader.loadStates();
		
		//Start game loop
		gameTimer = new Timer(frameWaitTime, new GameLoop());
	}
	
	//Start game timer, should be called after connecting to the server
	public void startGame()
	{
		//get initial server status
		try
		{
			currentServerState = Client.server.getServerState(Client.playerID);
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
				currentServerState = Client.server.getServerState(Client.playerID);
				
				System.out.println("Server state: " + currentServerState + " ID: " + Client.playerID);
			}
			catch(RemoteException exception)
			{
				exception.printStackTrace();
			}
			//Tasks to run while waiting for players
			if(currentServerState == WAITING_PLAYERS)
			{
				System.out.println("Waiting for players");
			}
			//Request GameStateUpdate from server if the game has started
			else if(currentServerState == GAME_RUNNING)
			{
				//Needs to check if the player is in the middle of their turn, will overwrite their altered game state
				//without this check
				if(gameState == null || gameState.currentPlayerID != Client.playerID)
				{
				
					try
					{
						//Request hash of game state
						short serverHash = Client.server.getGameStateHash(Client.playerID);
						if(gameState == null || serverHash != gameState.hash)
						{
							gameState = Client.server.getGameState(Client.playerID);
							System.out.println(gameState);
						}
						else
						{
							System.out.println(serverHash + " " + gameState.hash);
						}
						//Set color of status bar
						System.out.println(gameState.playerColors);
						System.out.println(gameState.playerColors.get(Client.playerID));
						Client.statusBar.setBackground(new Color(gameState.playerColors.get(Client.playerID)));
						Client.statusBar.setStatusMessage("Other players turn");
					}
					catch(RemoteException exception)
					{
						exception.printStackTrace();
					}
				}
				//Process players turn
				if(gameState != null)
					System.out.println(gameState.currentPlayerID + " "+ Client.playerID);
				if(gameState != null && gameState.currentPlayerID == Client.playerID)
				{
					System.out.println("Your turn");
					//Catch the case that the last player quits in the middle of the current player's turn
					//also prevents the player being kicked for connection timeout during their turn
					try
					{
						int serverState = Client.server.getServerState(Client.playerID);
					}
					catch(RemoteException exception)
					{
						exception.printStackTrace();
					}
					//Turn not started yet
					if(currentGameStateUpdate == null)
					{
						System.out.println("Generated GameStateUpdate for turn");
						Client.statusBar.setStatusMessage("Your turn");
						currentGameStateUpdate = new GameStateUpdate();
						currentGameStateUpdate.playerID = Client.playerID;
					}
					else
					{
						
					}
				}
				//Update States with information from GameState received from server
				if(gameState != null)
				{
					for(int i = 0;i < states.length; i++)
					{
						GameState.StateData currentStateData = gameState.states.get(i);
						states[i].units = currentStateData.numberUnits;
						states[i].changeColor(currentStateData.color);
						states[i].ownerPlayerID = currentStateData.ownerPlayerID;
						
					}
				}
			}
			//Handle end game stuff and clean up
			else if(currentServerState == GAME_OVER)
			{
				
				//Request Winner info and display it
				try
				{
					PostGameInfo postGameInfo = Client.server.getPostGameInfo();
					JOptionPane.showMessageDialog(null, "Game over, Winner: " + postGameInfo.winnerName);
				}
				catch(RemoteException exception)
				{
					exception.printStackTrace();
				}
				Client.server = null;
				Client.connectionWindow.setVisible(true);
				Client.statusBar.getRequestStartButton().setVisible(true);
				gameState = null;
				gameTimer.stop();
			}
			repaint();
		}
	}

	//Go through each state image and see if the color is anything other than transparent
	//If it is, this indicates that state was clicked on
	public static void checkStateMouse(int mouseX, int mouseY)
	{
		int currentRGB;
		System.out.println("Mouse click at: "+ mouseX + ", " + mouseY);
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
					System.out.println("SELECTED" + selectedState);
					//Client.stateSelectionPanel.getSelectedStateLabel().setText("Selected State: " + states[i].name);
					Client.stateSelectionPanel.setSelectedState(states[i]);
				}
				//Select second state to receive unit movement
				else if(selectedState != null && states[i] != selectedState && selectedState.isAdjacent(states[i]))
				{
					selectedState2 = states[i];
					//Client.stateSelectionPanel.getSelectedStateLabel2().setText("Target State: " + states[i].name);
					Client.stateSelectionPanel.setSelectedState2(states[i]);
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
		//Rectangle2D rectangle = new Rectangle2D.Float(40,40,20,20);
		//g2D.draw(rectangle);
	}
	
}
