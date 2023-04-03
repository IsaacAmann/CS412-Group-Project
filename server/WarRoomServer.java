import java.rmi.registry.*;
import java.rmi.*;
import java.util.*;

public class WarRoomServer
{
	public static final int PORT = 12345;
	
	public static void main(String[] args)
	{
		try
		{
			WarRoomServerInterface RMIServer = new WarRoomRMIImplementation();
			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.rebind("WarRoomRMIImplementation", RMIServer);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
