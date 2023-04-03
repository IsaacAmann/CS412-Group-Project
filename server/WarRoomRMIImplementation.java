import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WarRoomRMIImplementation extends UnicastRemoteObject implements WarRoomServerInterface
{
	//Constants
	//Server status
	public static final int WAITING_PLAYERS = 1;
	public static final int GAME_RUNNING = 2;
	public static final int GAME_OVER = 3;
	
	
	public static ArrayList<String> chatMessages = new ArrayList<String>();
	//Using a hashmap over arraylist so we can easily lookup players by playerID
	public static HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	public int serverStatus;
	
	//Date formatter for chat messages
	private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:m:s");
	public WarRoomRMIImplementation() throws RemoteException
	{
		serverStatus = WAITING_PLAYERS;
	}
	
	//Interface implementations (playerID should be last argument of each one for consistency)
	public void sendChatMessage(String message, int playerID) throws RemoteException
	{
		String newMessage = "<" + players.get(playerID).name + " " + java.time.LocalTime.now().format(timeFormat) + "> " + message;
		chatMessages.add(newMessage);
	}
	
	//Returns an array of new chats for the client. totalChats should be the total chats that the client already has
	//The client should append this array to their chats on their end to be displayed
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
	
	public void postTurn(GameStateUpdate update, int playerID) throws RemoteException
	{
		
	}
	
	public GameStateUpdate getGameState() throws RemoteException
	{
		
		return null;
	}
	
	public void testPrint(String message) throws RemoteException
	{	
		System.out.println(message);
	}
	
	//Returns the playerID if the player registered sucessfuly, returns current server status otherwise
	public int registerPlayer(String playerName) throws RemoteException
	{
		int output = this.serverStatus;
		//allow players to join if in the waiting for players state
		if(serverStatus == WAITING_PLAYERS)
		{
			Player newPlayer = new Player(playerName);
			players.put(newPlayer.playerID, newPlayer);
			output = newPlayer.playerID;	
		}
		return output;
	}
	
	
}

