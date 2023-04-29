
import java.io.Serializable;
import java.util.ArrayList;

public class GameStateUpdate implements Serializable
{
	public int playerID;
	//Should include the moves made by a player during their turn client side, should be 
	public ArrayList<UnitMove> moveList;
	
	public GameStateUpdate()
	{
		moveList = new ArrayList<UnitMove>();
	}
	
	public void addMove(int units, int sourceStateID, int destinationStateID, int senderPlayerID)
	{
		moveList.add(new UnitMove(units, sourceStateID, destinationStateID, senderPlayerID));
	}
	
	public class UnitMove implements Serializable
	{
		public int sourceStateID;
		public int destinationStateID;
		public int senderPlayerID;
		int units;
		
		public UnitMove(int units, int sourceStateID, int destinationStateID, int senderPlayerID)
		{
			this.sourceStateID = sourceStateID;
			this.destinationStateID = destinationStateID;
			this.senderPlayerID = senderPlayerID;
			this.units = units;
		}
	}
}
