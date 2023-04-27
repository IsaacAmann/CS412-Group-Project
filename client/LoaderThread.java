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
			State newState = new State(stateName, imagePath, stateID);
			synchronized(this)
			{
				states[stateID] = newState;
			}
		}
	}	
