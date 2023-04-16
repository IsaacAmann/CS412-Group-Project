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
	public static final int NUMBER_STATES = 42;
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
			mapImage = ImageIO.read(new File("assets/sea.png"));
			
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
						gameState = Client.server.getGameState();
					}
					catch(RemoteException exception)
					{
						exception.printStackTrace();
					}
				}
				//Process players turn
				if(gameState != null && gameState.currentPlayerID == Client.playerID)
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
				
			}
			repaint();
		}
	}
	//Creating state objects
	private void loadStates()
	{
		states[0] = new State("maine",
				"assets/maine.png", 0);
		states[1] = new State("newEngland",
				"assets/newEngland.png", 1);
		states[2] = new State("newYork",
				"assets/newYork.png", 2);
		states[3] = new State("pennsylvania",
				"assets/pennsylvania.png", 3);
		states[4] = new State("midAtlantic",
				"assets/midAtlantic.png", 4);
		states[5] = new State("virginia",
				"assets/virginia.png", 5);
		states[6] = new State("northCarolina",
				"assets/northCarolina.png", 6);
		states[7] = new State("southCarolina",
				"assets/southCarolina.png", 7);
		states[8] = new State("georgia",
				"assets/georgia.png", 8);
		states[9] = new State("florida",
				"assets/florida.png", 9);
		states[10] = new State("alabama",
				"assets/alabama.png",10);
		states[11] = new State("tennessee",
				"assets/tennessee.png",11);
		states[12] = new State("kentucky",
				"assets/kentucky.png",12);
		states[13] = new State("westVirginia",
				"assets/westVirginia.png",13);
		states[14] = new State("ohio",
				"assets/ohio.png",14);
		states[15] = new State("indiana",
				"assets/indiana.png",15);
		states[16] = new State("michigan",
				"assets/michigan.png",16);
		states[17] = new State("wisconsin",
				"assets/wisconsin.png",17);
		states[18] = new State("illinois",
				"assets/illinois.png",18);
		states[19] = new State("mississippi",
				"assets/mississippi.png",19);
		states[20] = new State("louisiana",
				"assets/louisiana.png",20);
		states[21] = new State("arkansas",
				"assets/arkansas.png",21);
		states[22] = new State("missouri",
				"assets/missouri.png",22);
		states[23] = new State("iowa",
				"assets/iowa.png",23);
		states[24] = new State("minnesota",
				"assets/minnesota.png",24);
		states[25] = new State("northDakota",
				"assets/northDakota.png",25);
		states[26] = new State("southDakota",
				"assets/southDakota.png",26);
		states[27] = new State("nebraska",
				"assets/nebraska.png",27);
		states[28] = new State("kansas",
				"assets/kansas.png",28);
		states[29] = new State("oklahmoa",
				"assets/oklahmoa.png",29);
		states[30] = new State("texas",
				"assets/texas.png",30);
		states[31] = new State("newMexico",
				"assets/newMexico.png",31);
		states[32] = new State("colorado",
				"assets/colorado.png",32);
		states[33] = new State("wyoming",
				"assets/wyoming.png",33);
		states[34] = new State("montana",
				"assets/montana.png",34);
		states[35] = new State("idaho",
				"assets/idaho.png",35);
		states[36] = new State("utah",
				"assets/utah.png",36);
		states[37] = new State("arizona",
				"assets/arizona.png",37);
		states[38] = new State("nevada",
				"assets/nevada.png",38);
		states[39] = new State("california",
				"assets/california.png",39);
		states[40] = new State("oregon",
				"assets/oregon.png",40);
		states[41] = new State("washington",
				"assets/washington.png",41);
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
					//Client.stateSelectionPanel.getSelectedStateLabel().setText("Selected State: " + states[i].name);
					Client.stateSelectionPanel.setSelectedState(states[i]);
				}
				//Select second state to receive unit movement
				else if(selectedState2 == null && selectedState != null)
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
		Rectangle2D rectangle = new Rectangle2D.Float(40,40,20,20);
		g2D.draw(rectangle);
	}
	
}
