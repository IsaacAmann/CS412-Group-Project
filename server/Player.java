
import java.time.Instant;

public class Player
{
	public String name;
	public int playerID;
	public static int nextID = 100;
	public int playerColor;
	public Instant lastSeen;
	
	public Player(String name, int playerColor)
	{
		this.name = name;
		this.playerColor = playerColor;
		this.playerID = nextID++;
	}	
}
