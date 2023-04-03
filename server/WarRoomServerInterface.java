import java.rmi.*;

public interface WarRoomServerInterface extends Remote
{
	public void sendChatMessage(String message, int playerID) throws RemoteException;
	
	//Return an array of Strings of last chat message, totalChats should indicate how many it already has stopping duplicates
	public String[] getChats(int totalChats) throws RemoteException;
	
	//Allows clients to request that the gamestate be updated
	public void postTurn(GameStateUpdate update, int playerID) throws RemoteException;
	
	//Return the current game state, should be called by the client every couple seconds (unless it is already their turn
	public GameStateUpdate getGameState() throws RemoteException;
	
	public void testPrint(String message) throws RemoteException;
	
	//Should be called by clients wanting to join the game, should return an integer indicating whether
	//they were added or indicate why they were not added
	//Should also have a return value to indicate that the game started
	public int registerPlayer(String playerName) throws RemoteException;
}
