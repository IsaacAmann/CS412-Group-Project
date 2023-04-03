import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class WarRoomRMIImplementation extends UnicastRemoteObject implements WarRoomServerInterface
{
	
	public static ArrayList<String> chatMessages = new ArrayList<String>();
	
	public WarRoomRMIImplementation() throws RemoteException
	{
		
	}
	
	//Interface implementations
	public void sendChatMessage(String message) throws RemoteException
	{
		
	}
	
	public String[] getChats(int totalChats) throws RemoteException
	{
		return null;
	}
	
	public void postTurn(GameStateUpdate update) throws RemoteException
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
}

