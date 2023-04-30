
public class LoaderThread implements Runnable
{
	private State[] states;
	private String stateName;
	private String imagePath;
	private int stateID;
	
	public LoaderThread(State[] states, String stateName, String imagePath, int stateID)
	{
		this.states = states;
		this.stateName = stateName;
		this.imagePath = imagePath;
		this.stateID = stateID;
	}
	
	public void run()
	{
		//Call State constructor and place result in the correct index of the states array
		State newState = new State(stateName, imagePath, stateID);
		//Synchronization not needed for array access, no thread ever accesses the same index
		states[stateID] = newState;
	}
}	
