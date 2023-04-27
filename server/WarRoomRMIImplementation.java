import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.lang.Thread;
import java.lang.InterruptedException;

import java.awt.Color;

import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;
import java.time.*;

public class WarRoomRMIImplementation extends UnicastRemoteObject implements WarRoomServerInterface
{
	//Constants
	//Server status
	public static final int WAITING_PLAYERS = 1;
	public static final int GAME_RUNNING = 2;
	public static final int GAME_OVER = 3;
	
	public static final int STARTING_UNITS = 20;
	public static final int MAX_PLAYERS = 8;
	public static final int RESTART_DELAY = 5000;
	
	public static final int SERVER_REFRESH_RATE = 1000;
	//Set the amount of time the server waits before kicking a player from the game 
	public static final Duration PLAYER_TIMEOUT_LIMIT = Duration.ofSeconds(4);
	//Picked some states in an order that promotes spacing between starting locations
	public static final int[] STARTING_LOCATIONS=
	{
		8, //Alabama
		39, //California
		25, //North Dakota
		30, //Texas
		6, // North Carolina
		32, //Colorado
		36, //Utah
		9 //Florida
	};
	
	//Random generator for the player colors
	private Random colorGenerator;
	
	//Random generator for game state version
	private static Random gameStateRandom = new Random();
	
	public static ArrayList<String> chatMessages = new ArrayList<String>();
	
	//Using a hashmap over arraylist so we can easily lookup players by playerID
	public static HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	//HashMap to store player colors to be passed to the client, the key should be by playerID
	public static HashMap<Integer, Integer> playerColors = new HashMap<Integer, Integer>();
	//Maintain an ordered list of playerIDs, the HashMap provides method to return a Set of keys, list may not be ordered though
	public static ArrayList<Integer> playerIDs = new ArrayList<Integer>();
	
	public int serverStatus;
	
	//Date formatter for chat messages
	private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:m:s");
	
	private static int playersReady = 0;
	
	public static int currentPlayerID;
	public static int currentPlayerIndex;
	
	//Timer for serverLoop
	private Timer serverTimer;
	
	//Timer for server restart
	Timer serverRestartTimer;
	
	//Server clock
	private Clock serverClock;
	
	//Server's copy of the game state
	public static GameState currentGameState;
	
	//Winner information (Requested on client when GAME_OVER server state is detected)
	public PostGameInfo postGameInfo;
	
	public WarRoomRMIImplementation() throws RemoteException
	{
		serverStatus = WAITING_PLAYERS;
		colorGenerator = new Random();
		serverTimer = new Timer(SERVER_REFRESH_RATE, new ServerLoop());
		serverClock = Clock.systemDefaultZone();
		serverTimer.start();
	}
	
	//Change the server status and allow the first player to submit a turn
	public void startGame()
	{
		System.out.println("Starting game...");
		//get the first player's ID to allow them to submit a turn
		currentPlayerID = playerIDs.get(0);
		currentPlayerIndex = 0;
		//Generate GameState
		currentGameState = new GameState(playerColors);
		currentGameState.currentPlayerID = currentPlayerID;
		//Assign starting territories
		for(int i = 0; i < playerIDs.size(); i++)
		{
			currentGameState.states.get(STARTING_LOCATIONS[i]).ownerPlayerID = playerIDs.get(i);
			currentGameState.states.get(STARTING_LOCATIONS[i]).numberUnits = STARTING_UNITS;
			currentGameState.states.get(STARTING_LOCATIONS[i]).color = playerColors.get(playerIDs.get(i));
		}
		currentGameState.updateHash((short) gameStateRandom.nextInt());
		//change server status and allow turns to be submitted
		serverStatus = GAME_RUNNING;
		
	}
	
	private void updateLastSeen(int playerID)
	{
		players.get(playerID).lastSeen = serverClock.instant();
	}
	
	//Interface implementations (playerID should be last argument of each one for consistency)
	public void sendChatMessage(String message, int playerID) throws RemoteException
	{
		String newMessage = "<" + players.get(playerID).name + " " + java.time.LocalTime.now().format(timeFormat) + "> " + message;
		chatMessages.add(newMessage);
		updateLastSeen(playerID);
	}
	
	//Returns an array of new chats for the client. totalChats should be the total chats that the client already has
	//The client should append this array to their chats on their end to be displayed
	//Needs to be tested once the chatbox is working client side
	public String[] getChats(int totalChats) throws RemoteException
	{
		//The return array only includes the chats the client has not seen yet
		//int i = chatMessages.size() - totalChats - 1;
		String[] newChats = new String[chatMessages.size() - totalChats];
		for(int i = 0; i < newChats.length; i++)
		{
			newChats[i] = chatMessages.get(i+(chatMessages.size() - 1));
		}
		return null;
	}
	
	//Allow client to post their turn to the server, should update gamestate and set currentPlayerID to the next player
	public void postTurn(GameStateUpdate update, int playerID) throws RemoteException
	{
		if(playerID == currentGameState.currentPlayerID)
		{
			currentGameState.mergeGameStateUpdate(update);
			currentGameState.updateHash((short) gameStateRandom.nextInt());
			//Check if game has cycled back to first player
			System.out.println("Updating game state");
			System.out.println((currentGameState.currentPlayerID + 1 ) % playerIDs.size());
			if((currentGameState.currentPlayerID + 1) % playerIDs.size() == 0)
			{
				//Increment unit counts on held states
				for(int i = 0; i < currentGameState.states.size(); i++)
				{
					GameState.StateData currentState = currentGameState.states.get(i);
					if(currentState.ownerPlayerID != -1)
					{
						currentState.numberUnits += STARTING_UNITS;
					}
				}
			}
			//Move on to next player
			currentGameState.currentPlayerID = playerIDs.get((currentGameState.currentPlayerID + 1) % playerIDs.size());
			System.out.println("CurrentPlayeR: " + currentGameState.currentPlayerID);
			
			currentGameState.updateHash((short) gameStateRandom.nextInt());
			
			//Check for win conditions and update server status 
			if(checkForWinner() == true)
			{
				serverStatus = GAME_OVER;
				playersReady = 0;
				serverRestartTimer = new Timer(RESTART_DELAY, new ServerRestart());
				serverRestartTimer.setRepeats(false);
				serverRestartTimer.start();
			}
			updateLastSeen(playerID);
		}
	}
	
	//Should include updated map information if the player has submitted a turn
	//The client should keep note of the current player locally, if it is different, they should merge the GameStateUpdate
	//With their copy of the gameState
	public GameState getGameState(int playerID) throws RemoteException
	{
		System.out.println("Sent gameState, player turn: " + currentGameState.currentPlayerID);
		updateLastSeen(playerID);
		return currentGameState;
	}
	
	public short getGameStateHash(int playerID) throws RemoteException
	{
		//System.out.println("Sent hash: " + currentGameState.hash);
		updateLastSeen(playerID);
		return currentGameState.hash;
	}
		
	//Returns the playerID if the player registered sucessfuly, returns current server status otherwise indicating the game is already runnning
	public int registerPlayer(String playerName) throws RemoteException
	{
		int output = this.serverStatus;
		//allow players to join if in the waiting for players state
		if(serverStatus == WAITING_PLAYERS)
		{
			Color color = new Color(colorGenerator.nextFloat(), colorGenerator.nextFloat(), colorGenerator.nextFloat());
			Player newPlayer = new Player(playerName, color.hashCode());
			players.put(newPlayer.playerID, newPlayer);
			playerColors.put(newPlayer.playerID, newPlayer.playerColor);
			output = newPlayer.playerID;
			playerIDs.add(output);	
			//start game loop if max players has been reached
			if(players.size() == MAX_PLAYERS)
			{
				startGame();
			}
			System.out.println("Player joined: " + playerName + " ID: " + newPlayer.playerID);
		}
		
		return output;
	}
	
	//Allow clients to signal the server to  start the game early instead of waiting for a full server
	public void requestGameStart(int playerID) throws RemoteException
	{
		if(serverStatus == WAITING_PLAYERS)
		{
			System.out.println("Player: " + playerID + " is ready for game start");
			if(++playersReady == players.size())
			{
				System.out.println("All players ready, starting early");
				startGame();
			}
		}
	}
	
	public PostGameInfo getPostGameInfo() throws RemoteException
	{
		return this.postGameInfo;
	}
	
	public int getServerState(int playerID)
	{
		updateLastSeen(playerID);
		return this.serverStatus;
	}
	
	public void kickPlayer(int playerID)
	{
		//Remove entry from player colors list
		playerColors.remove(playerID);
		currentGameState.playerColors.remove(playerID);
		//Set held states back to neutral 
		for(int i = 0; i < currentGameState.states.size(); i++)
		{
			GameState.StateData currentState = currentGameState.states.get(i);
			if(currentState.ownerPlayerID == playerID)
			{
				//reset back to neutral color
				currentState.color = Color.WHITE.hashCode();
				//set units back to 0
				currentState.numberUnits = 0;
			}
		}
		//Increment turn if player kicked during their turn
		if(currentGameState.currentPlayerID == playerID)
		{
			System.out.println("Incrementing turn");
			try
			{
				//Post blank turn with kicked player's ID
				postTurn(new GameStateUpdate(), playerID);
			}
			catch(RemoteException e)
			{
			
			}
		}
	}
	
	public boolean checkForWinner()
	{
		//Check if all held states are held by just 1 player
		int currentPlayerID= -1;
		boolean output = true;
		for(int i = 0; i < currentGameState.states.size(); i++)
		{
			GameState.StateData currentState = currentGameState.states.get(i);
			if(currentState.ownerPlayerID != -1)
			{
				if(currentPlayerID == -1)
				{
					currentPlayerID = currentState.ownerPlayerID;
				}
				else if(currentPlayerID != currentState.ownerPlayerID)
				{
					output = false;
					break;
				}
			}
		}
		//Create a PostGameInfo object if there is a winner
		if(output == true)
		{
			postGameInfo = new PostGameInfo(currentPlayerID, players.get(currentPlayerID).name);
		}
		return output;
	}
		
	private class ServerRestart implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//Clear game data
			chatMessages.clear();
			players.clear();
			playerColors.clear();
			playersReady = 0;
			//Reset server state to WAITING_PLAYERS
			serverStatus = WAITING_PLAYERS;
			System.out.println("Restarting...");
		}
	}	
		
	private class ServerLoop implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(serverStatus == GAME_RUNNING)
			{
				//System.out.println("server loop");
				//Check for players who's connection timed out
				Iterator<Integer> iterator = playerIDs.iterator();
				int currentPlayerID;
				while(iterator.hasNext())
				{
					currentPlayerID = iterator.next();
					if(players.get(currentPlayerID).lastSeen.isBefore(serverClock.instant().minusMillis(PLAYER_TIMEOUT_LIMIT.toMillis())))
					{
						kickPlayer(currentPlayerID);
						System.out.println(currentPlayerID + " has been kicked from the game (timeout)");
						//Remove player from players list
						iterator.remove();
					}
				}
			}
		}
	}
}

