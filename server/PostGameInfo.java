package server;

import java.io.Serializable;
import java.awt.Color;

//Contains post game info to be sent to the client to display who won the game
public class PostGameInfo implements Serializable
{
	public int winnerPlayerID;
	public String winnerName;
	
	public PostGameInfo(int winnerPlayerID, String winnerName)
	{
		this.winnerPlayerID = winnerPlayerID;
		this.winnerName = winnerName;
	}
}
