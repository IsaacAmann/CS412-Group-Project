import java.io.Serializable;
import java.awt.Color;

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
