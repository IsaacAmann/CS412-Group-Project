import java.util.HashMap;


public class GameState
{
	//ID of the player who's turn it is
	public int currentPlayerID;
	
	public HashMap<Integer, StateData> states;
	private HashMap<Integer, Integer> playerColors;
	
	public GameState(HashMap<Integer, Integer> playerColors)
	{
		states = new HashMap<Integer, StateData>();
		this.playerColors = playerColors;
	}
	
	public void mergeGameStateUpdate(GameStateUpdate update)
	{
		//Process unit moves
		for(int i = 0; i < update.moveList.size(); i++)
		{
			GameStateUpdate.UnitMove currentMove = update.moveList.get(i);
			StateData sourceState = states.get(currentMove.sourceStateID);
			StateData destinationState = states.get(currentMove.destinationStateID);
			//Unit movement between players own states
			if(sourceState.ownerPlayerID == destinationState.ownerPlayerID)
			{
				sourceState.numberUnits -= currentMove.units;
				destinationState.numberUnits += currentMove.units;
			}
			//Unit movement to neutral state, add units to state and then change the owner to sender
			else if(destinationState.ownerPlayerID == -1)
			{
				sourceState.numberUnits -= currentMove.units;
				destinationState.numberUnits += currentMove.units;
				destinationState.ownerPlayerID = currentMove.senderPlayerID;
				destinationState.color = playerColors.get(currentMove.senderPlayerID);
			}
			//Unit movement to enemy state, subtract the units sent from the enemy states units then flip the state
			//if the units fall below 0 
			else
			{
				//Sender outnumbers the destination, change ownership
				if(currentMove.units > destinationState.numberUnits)
				{
					sourceState.numberUnits -= currentMove.units;
					destinationState.numberUnits = currentMove.units - destinationState.numberUnits;
					destinationState.ownerPlayerID = currentMove.senderPlayerID;
				}
				//Destination state has enough to defend, subtract from the total units
				else 
				{
					sourceState.numberUnits -= currentMove.units;
					destinationState.numberUnits -= currentMove.units;
				}
			}	
		}
	}
	
	//Class to hold the information for territories.
	//Creating a separate class from the State class in the client since the extra methods
	//And drawing information is not needed server side and it would be wasteful to send it over the network
	public class StateData
	{
		public int ownerPlayerID;
		public int stateID;
		//Number of units present in the territory
		public int numberUnits;
		public int color;
	}
}
