package server;

public class Player
{
	public String name;
	public int playerID;
	public static int nextID = 100;
	
	public Player(String name)
	{
		this.name = name;
		this.playerID = nextID++;
	}	
}
