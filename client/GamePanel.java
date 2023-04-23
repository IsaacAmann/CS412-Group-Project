package client;

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

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;


public class GamePanel extends JPanel
{
	//Constants
	public static final int NUMBER_STATES = 42;

	//Amount of time the game waits to check the game state again, should be a large amount of time so the server

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

		//Add mouse listener to game panel. Mouse events only triggered by mouse input inside game panel
		//Other inputs from the sidebar should be handled separately
		mouseListener = new GamePanelMouseListener();
		this.addMouseListener(mouseListener);
		selectedState = null;
		selectedState2 = null;
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
			currentServerState = Client.server.getServerState(Client.playerID);
		}
		catch(RemoteException exception)
		{
			exception.printStackTrace();
		}

		gameTimer.start();
	}

	private static void getAllFiles(File curDir) {

		File[] filesList = curDir.listFiles();
		for(File f : filesList){
			if(f.isDirectory())
				getAllFiles(f);
			if(f.isFile()){
				System.out.println(f.getName());
				System.out.println(f.getPath());
			}
		}
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
				
			}
			repaint();
		}
	}
	//Creating state objects
	private void loadStates()
	{

		//Hard Coding the states
		states[0] = new State("maine",
				"./src/client/assets/maine.png", 0);
		states[1] = new State("newEngland",
				"./src/client/assets/newEngland.png", 1);
		states[2] = new State("newYork",
				"./src/client/assets/newYork.png", 2);
		states[3] = new State("pennsylvania",
				"./src/client/assets/pennsylvania.png", 3);
		states[4] = new State("midAtlantic",
				"./src/client/assets/midAtlantic.png", 4);
		states[5] = new State("virginia",
				"./src/client/assets/virginia.png", 5);
		states[6] = new State("northCarolina",
				"./src/client/assets/northCarolina.png", 6);
		states[7] = new State("southCarolina",
				"./src/client/assets/southCarolina.png", 7);
		states[8] = new State("georgia",
				"./src/client/assets/georgia.png", 8);
		states[9] = new State("florida",
				"./src/client/assets/florida.png", 9);
		states[10] = new State("alabama",
				"./src/client/assets/alabama.png",10);
		states[11] = new State("tennessee",
				"./src/client/assets/tennessee.png",11);
		states[12] = new State("kentucky",
				"./src/client/assets/kentucky.png",12);
		states[13] = new State("westVirginia",
				"./src/client/assets/westVirginia.png",13);
		states[14] = new State("ohio",
				"./src/client/assets/ohio.png",14);
		states[15] = new State("indiana",
				"./src/client/assets/indiana.png",15);
		states[16] = new State("michigan",
				"./src/client/assets/michigan.png",16);
		states[17] = new State("wisconsin",
				"./src/client/assets/wisconsin.png",17);
		states[18] = new State("illinois",
				"./src/client/assets/illinois.png",18);
		states[19] = new State("mississippi",
				"./src/client/assets/mississippi.png",19);
		states[20] = new State("louisiana",
				"./src/client/assets/louisiana.png",20);
		states[21] = new State("arkansas",
				"./src/client/assets/arkansas.png",21);
		states[22] = new State("missouri",
				"./src/client/assets/missouri.png",22);
		states[23] = new State("iowa",
				"./src/client/assets/iowa.png",23);
		states[24] = new State("minnesota",
				"./src/client/assets/minnesota.png",24);
		states[25] = new State("northDakota",
				"./src/client/assets/northDakota.png",25);
		states[26] = new State("southDakota",
				"./src/client/assets/southDakota.png",26);
		states[27] = new State("nebraska",
				"./src/client/assets/nebraska.png",27);
		states[28] = new State("kansas",
				"./src/client/assets/kansas.png",28);
		states[29] = new State("oklahmoa",
				"./src/client/assets/oklahmoa.png",29);
		states[30] = new State("texas",
				"./src/client/assets/texas.png",30);
		states[31] = new State("newMexico",
				"./src/client/assets/newMexico.png",31);
		states[32] = new State("colorado",
				"./src/client/assets/colorado.png",32);
		states[33] = new State("wyoming",
				"./src/client/assets/wyoming.png",33);
		states[34] = new State("montana",
				"./src/client/assets/montana.png",34);
		states[35] = new State("idaho",
				"./src/client/assets/idaho.png",35);
		states[36] = new State("utah",
				"./src/client/assets/utah.png",36);
		states[37] = new State("arizona",
				"./src/client/assets/arizona.png",37);
		states[38] = new State("nevada",
				"./src/client/assets/nevada.png",38);
		states[39] = new State("california",
				"./src/client/assets/california.png",39);
		states[40] = new State("oregon",
				"./src/client/assets/oregon.png",40);
		states[41] = new State("washington",
				"./src/client/assets/washington.png",41);

	}
	//Go through each state image and see if the color is anything other than transparent
	//If it is, this indicates that state was clicked on
	public static void checkStateMouse(int mouseX, int mouseY)
	{
		int currentRGB;
		System.out.println("Mouse click at: "+ mouseX + ", " + mouseY);
		for(int i = 0; i < states.length; i++)
		{
			if (states[i] != null) {


			currentRGB = states[i].image.getRGB(mouseX, mouseY);
			//System.out.println("RGB " + i + " " + currentRGB);
			//RGB of 0 indicates the pixel is transparent
			if(currentRGB != 0) {
				states[i].click();
				//allow player to select friendly state
				if (selectedState == null && states[i].ownerPlayerID == Client.playerID) {
					selectedState = states[i];
					System.out.println("SELECTED" + selectedState);
					//Client.stateSelectionPanel.getSelectedStateLabel().setText("Selected State: " + states[i].name);
					Client.stateSelectionPanel.setSelectedState(states[i]);
				}
				//Select second state to receive unit movement
				else if(selectedState != null && states[i] != selectedState)
				{
					selectedState2 = states[i];
					//Client.stateSelectionPanel.getSelectedStateLabel2().setText("Target State: " + states[i].name);
					Client.stateSelectionPanel.setSelectedState2(states[i]);
				}
				break; //Allows for only one state to be selected.
			}
			}//End of Null Break
		}
	}
	
	public void paint(Graphics g)
	{
		//Have to call this first, resets the panel size otherwise
		super.paint(g);
		Graphics2D g2D = (Graphics2D)g;
		
		g2D.drawImage(mapImage,0,0,this);
		
		//draw all states
		for(int i = 0; i < states.length; i++) {
			if (states[i] != null) {
				states[i].draw(g2D, this);
			}
		}
	}
}
