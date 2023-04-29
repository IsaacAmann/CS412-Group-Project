package client;

import java.util.HashMap;
import java.io.Serializable;
import java.awt.Color;
import java.util.ArrayList;

public class GameState implements Serializable
{
	private static final int NUMBER_STATES = 42;
	
	//ID of the player who's turn it is
	public int currentPlayerID;
	
	public HashMap<Integer, StateData> states;
	public HashMap<Integer, Integer> playerColors;
	
	//Using as a sort of checksum, allows the clients to update more frequently without
	//Sending the entire gamestate if it has not changed
	public short hash;
	
	public GameState(HashMap<Integer, Integer> playerColors)
	{
		states = new HashMap<Integer, StateData>();
		this.playerColors = playerColors;
		//Create StateData objects with base values
		for(int i = 0; i < NUMBER_STATES; i++)
		{
			//Create states with default values
			states.put(i, new StateData(-1, i, 0, Color.WHITE.hashCode())); 
		}
	}
	
	public void updateHash(short newHash)
	{
		//Using a short instead of the full integer hashcode to send less data over network
		//May need to increase to full integer, collision freezes the client
		hash = newHash;
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
			else if(destinationState.ownerPlayerID == -1 && currentMove.units > 0)
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
					destinationState.color = playerColors.get(currentMove.senderPlayerID);
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
	public class StateData implements Serializable
	{
		public StateData(int ownerPlayerID, int stateID, int numberUnits, int color)
		{
			this.ownerPlayerID = ownerPlayerID;
			this.stateID = stateID;
			this.numberUnits = numberUnits;
			this.color = color;
			adjacentStates = new ArrayList<Integer>();
		}
		
		public int ownerPlayerID;
		public int stateID;
		//Number of units present in the territory
		public int numberUnits;
		public int color;
		public ArrayList<Integer> adjacentStates;
	}
}
