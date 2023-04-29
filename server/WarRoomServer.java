import java.rmi.registry.*;
import java.rmi.*;
import java.util.*;
import java.util.Properties;

public class WarRoomServer
{
	public static final int PORT = 12345;
	
	public static void main(String[] args)
	{
		if(args.length == 1)
		{
			Properties systemProperties = System.getProperties();
			systemProperties.put("java.rmi.server.hostname", args[0]);
			System.setProperties(systemProperties);
			System.out.println("Registering on " + args[0]);
		}
		else
		{
			System.out.println("Registering on localhost");
		}
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
