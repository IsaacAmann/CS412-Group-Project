import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.lang.Thread;
import java.lang.InterruptedException;

import java.awt.Color;

public class WarRoomRMIImplementation extends UnicastRemoteObject implements WarRoomServerInterface
{
	//Constants
	//Server status
	public static final int WAITING_PLAYERS = 1;
	public static final int GAME_RUNNING = 2;
	public static final int GAME_OVER = 3;
	
	public static final int STARTING_UNITS = 20;
	public static final int MAX_PLAYERS = 2;
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
	
	//Server's copy of the game state
	public static GameState currentGameState;
	
	public WarRoomRMIImplementation() throws RemoteException
	{
		serverStatus = WAITING_PLAYERS;
		colorGenerator = new Random();
		
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
		//change server status and allow turns to be submitted
		serverStatus = GAME_RUNNING;
	}
	
	//Interface implementations (playerID should be last argument of each one for consistency)
	public void sendChatMessage(String message, int playerID) throws RemoteException
	{
		String newMessage = "<" + players.get(playerID).name + " " + java.time.LocalTime.now().format(timeFormat) + "> " + message;
		chatMessages.add(newMessage);
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
			//Move on to next player
			
			currentGameState.currentPlayerID = playerIDs.get((currentGameState.currentPlayerID + 1) % playerIDs.size());
			System.out.println("CurrentPlayeR: " + currentGameState.currentPlayerID);
		}
	}
	
	//Should include updated map information if the player has submitted a turn
	//The client should keep note of the current player locally, if it is different, they should merge the GameStateUpdate
	//With their copy of the gameState
	public GameState getGameState() throws RemoteException
	{
		System.out.println("Sent gameState, player turn: " + currentGameState.currentPlayerID);
		return currentGameState;
	}
	
	//RMI method for testing the server connection
	public void testPrint(String message) throws RemoteException
	{	
		System.out.println(message);
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
			playersReady++;
			if(playersReady == players.size())
			{
				serverStatus = GAME_RUNNING;
			}
		}
	}
	
	public int getServerState()
	{
		return this.serverStatus;
	}
	
}

