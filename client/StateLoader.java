
import java.net.URL;
import java.lang.ClassLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.io.File;

public class StateLoader
{
	
	public static State[] loadStates()
	{
		//Creating a cached thread pool to load states and calculate center points for the unit counter label
		ExecutorService threadPool = Executors.newCachedThreadPool();
		State[] states = new State[GamePanel.NUMBER_STATES];


		//Isaac must revert this code.

		
		//Adding LoaderThreads to the thread pool
		threadPool.execute(new LoaderThread(states, "maine", "assets/maine.png", 0));
		threadPool.execute(new LoaderThread(states, "newEngland", "assets/newEngland.png", 1));
		threadPool.execute(new LoaderThread(states, "newYork", "assets/newYork.png", 2));
		threadPool.execute(new LoaderThread(states, "pennsylvania", "assets/pennsylvania.png", 3));
		threadPool.execute(new LoaderThread(states, "midAtlantic", "assets/midAtlantic.png", 4));
		threadPool.execute(new LoaderThread(states, "virginia", "assets/virginia.png", 5));
		threadPool.execute(new LoaderThread(states, "northCarolina", "assets/northCarolina.png", 6));
		threadPool.execute(new LoaderThread(states, "southCarolina", "assets/southCarolina.png", 7));
		threadPool.execute(new LoaderThread(states, "georgia", "assets/georgia.png", 8));
		threadPool.execute(new LoaderThread(states, "florida", "assets/florida.png", 9));
		threadPool.execute(new LoaderThread(states, "alabama", "assets/alabama.png", 10));
		threadPool.execute(new LoaderThread(states, "tennessee", "assets/tennessee.png", 11));
		threadPool.execute(new LoaderThread(states, "kentucky", "assets/kentucky.png", 12));
		threadPool.execute(new LoaderThread(states, "westVirginia", "assets/westVirginia.png", 13));
		threadPool.execute(new LoaderThread(states, "ohio", "assets/ohio.png", 14));
		threadPool.execute(new LoaderThread(states, "indiana", "assets/indiana.png", 15));
		threadPool.execute(new LoaderThread(states, "michigan", "assets/michigan.png", 16));
		threadPool.execute(new LoaderThread(states, "wisconsin", "assets/wisconsin.png", 17));
		threadPool.execute(new LoaderThread(states, "illinois", "assets/illinois.png", 18));
		threadPool.execute(new LoaderThread(states, "mississippi", "assets/mississippi.png", 19));
		threadPool.execute(new LoaderThread(states, "louisiana", "assets/louisiana.png", 20));
		threadPool.execute(new LoaderThread(states, "arkansas", "assets/arkansas.png", 21));
		threadPool.execute(new LoaderThread(states, "missouri", "assets/missouri.png", 22));
		threadPool.execute(new LoaderThread(states, "iowa", "assets/iowa.png", 23));
		threadPool.execute(new LoaderThread(states, "minnesota", "assets/minnesota.png", 24));
		threadPool.execute(new LoaderThread(states, "northDakota", "assets/northDakota.png", 25));
		threadPool.execute(new LoaderThread(states, "southDakota", "assets/southDakota.png", 26));
		threadPool.execute(new LoaderThread(states, "nebraska", "assets/nebraska.png", 27));
		threadPool.execute(new LoaderThread(states, "kansas", "assets/kansas.png", 28));
		threadPool.execute(new LoaderThread(states, "oklahoma", "assets/oklahmoa.png", 29));
		threadPool.execute(new LoaderThread(states, "texas", "assets/texas.png", 30));
		threadPool.execute(new LoaderThread(states, "newMexico", "assets/newMexico.png", 31));
		threadPool.execute(new LoaderThread(states, "colorado", "assets/colorado.png", 32));
		threadPool.execute(new LoaderThread(states, "wyoming", "assets/wyoming.png", 33));
		threadPool.execute(new LoaderThread(states, "montana", "assets/montana.png", 34));
		threadPool.execute(new LoaderThread(states, "idaho", "assets/idaho.png", 35));
		threadPool.execute(new LoaderThread(states, "utah", "assets/utah.png", 36));
		threadPool.execute(new LoaderThread(states, "arizona", "assets/arizona.png", 37));
		threadPool.execute(new LoaderThread(states, "nevada", "assets/nevada.png", 38));
		threadPool.execute(new LoaderThread(states, "california", "assets/california.png", 39));
		threadPool.execute(new LoaderThread(states, "oregon", "assets/oregon.png", 40));
		threadPool.execute(new LoaderThread(states, "washington", "assets/washington.png", 41));
	
		//Start shutting down thread pool		
		threadPool.shutdown();		
		//Block until all threads have finished and thread pool is shutdown
		try
		{
			threadPool.awaitTermination(10, TimeUnit.SECONDS);
		}
		catch(InterruptedException exception)
		{
			exception.printStackTrace();
		}
		
		//Setting unit label offsets and setting adjacent states
		states[0].setUnitOffset(-10, -6);
		states[0].adjacentStates.add(1);
		
		
		states[1].setUnitOffset(-14, 12);
		states[1].adjacentStates.add(0);
		states[1].adjacentStates.add(2);
		states[1].adjacentStates.add(4);
		
		
		states[2].setUnitOffset(6, -36);
		states[2].adjacentStates.add(1);
		states[2].adjacentStates.add(3);
		states[2].adjacentStates.add(4);
		
		states[3].setUnitOffset(-7, 8);
		states[3].adjacentStates.add(2);
		states[3].adjacentStates.add(4);
		states[3].adjacentStates.add(13);
		states[3].adjacentStates.add(14);		
		
		states[4].setUnitOffset(14, 14);
		states[4].adjacentStates.add(1);
		states[4].adjacentStates.add(2);
		states[4].adjacentStates.add(3);
		states[4].adjacentStates.add(5);
		states[4].adjacentStates.add(13);
		
		states[5].setUnitOffset(12, -12);
		states[5].adjacentStates.add(4);
		states[5].adjacentStates.add(13);
		states[5].adjacentStates.add(6);
		states[5].adjacentStates.add(12);
		states[5].adjacentStates.add(11);
				
		states[6].setUnitOffset(14, 0);
		states[6].adjacentStates.add(5);
		states[6].adjacentStates.add(7);
		states[6].adjacentStates.add(11);
		states[6].adjacentStates.add(8);
				
		states[7].setUnitOffset(0, 18);
		states[7].adjacentStates.add(6);
		states[7].adjacentStates.add(8);
		
		states[8].setUnitOffset(-8, 18);
		states[8].adjacentStates.add(7);
		states[8].adjacentStates.add(9);
		states[8].adjacentStates.add(10);
		states[8].adjacentStates.add(11);
		
		states[9].setUnitOffset(38, 0);
		states[9].adjacentStates.add(8);	
		states[9].adjacentStates.add(10);	
		
		states[10].setUnitOffset(-10, -24);
		states[10].adjacentStates.add(9);
		states[10].adjacentStates.add(8);
		states[10].adjacentStates.add(19);
		states[10].adjacentStates.add(11);
		
		states[11].setUnitOffset(-10, 6);
		states[11].adjacentStates.add(10);
		states[11].adjacentStates.add(19);
		states[11].adjacentStates.add(8);
		states[11].adjacentStates.add(6);	
		states[11].adjacentStates.add(12);
		states[11].adjacentStates.add(5);
		states[11].adjacentStates.add(22);
		states[11].adjacentStates.add(21);	
				
		states[12].setUnitOffset(8, -10);
		states[12].adjacentStates.add(11);
		states[12].adjacentStates.add(13);
		states[12].adjacentStates.add(5);
		states[12].adjacentStates.add(14);
		states[12].adjacentStates.add(15);
		states[12].adjacentStates.add(22);
		states[12].adjacentStates.add(18);
		
		states[13].setUnitOffset(-20, 24);
		states[13].adjacentStates.add(3);
		states[13].adjacentStates.add(4);
		states[13].adjacentStates.add(14);
		states[13].adjacentStates.add(12);
		states[13].adjacentStates.add(5);		
		
		states[14].setUnitOffset(-4, 26);
		states[14].adjacentStates.add(15);
		states[14].adjacentStates.add(13);
		states[14].adjacentStates.add(12);
		states[14].adjacentStates.add(3);
		
		states[15].setUnitOffset(-4, -36);
		states[15].adjacentStates.add(16);
		states[15].adjacentStates.add(18);
		states[15].adjacentStates.add(12);
		states[15].adjacentStates.add(14);
		
		states[16].setUnitOffset(24, 30);
		states[16].adjacentStates.add(17);
		states[16].adjacentStates.add(15);
		states[16].adjacentStates.add(14);
		
		states[17].setUnitOffset(0, 30);
		states[17].adjacentStates.add(16);
		states[17].adjacentStates.add(14);
		states[17].adjacentStates.add(24);
		states[17].adjacentStates.add(18);
		
		states[18].setUnitOffset(0, -10);
		states[18].adjacentStates.add(17);
		states[18].adjacentStates.add(12);
		states[18].adjacentStates.add(22);
		states[18].adjacentStates.add(12);
		states[18].adjacentStates.add(15);
		states[18].adjacentStates.add(23);

		states[19].setUnitOffset(0, -50);
		states[19].adjacentStates.add(20);
		states[19].adjacentStates.add(21);
		states[19].adjacentStates.add(11);
		states[19].adjacentStates.add(10);
		
		states[20].setUnitOffset(-26, 0);
		states[20].adjacentStates.add(19);
		states[20].adjacentStates.add(21);
		states[20].adjacentStates.add(30);
		
		states[21].setUnitOffset(-10, 40);
		states[21].adjacentStates.add(20);
		states[21].adjacentStates.add(29);
		states[21].adjacentStates.add(30);
		states[21].adjacentStates.add(11);
		states[21].adjacentStates.add(19);
		states[21].adjacentStates.add(22);
		
		states[22].setUnitOffset(-10, 12);
		states[22].adjacentStates.add(29);
		states[22].adjacentStates.add(21);
		states[22].adjacentStates.add(28);
		states[22].adjacentStates.add(18);
		states[22].adjacentStates.add(23);
		states[22].adjacentStates.add(11);
		states[22].adjacentStates.add(12);
		
		states[23].setUnitOffset(-6, 10);
		states[23].adjacentStates.add(24);
		states[23].adjacentStates.add(17);
		states[23].adjacentStates.add(26);
		states[23].adjacentStates.add(27);
		states[23].adjacentStates.add(18);
		states[23].adjacentStates.add(22);		
				
		states[24].setUnitOffset(-20, 70);
		states[24].adjacentStates.add(25);
		states[24].adjacentStates.add(26);
		states[24].adjacentStates.add(23);
		states[24].adjacentStates.add(17);
		
		states[25].setUnitOffset(0, -26);
		states[25].adjacentStates.add(26);
		states[25].adjacentStates.add(34);
		states[25].adjacentStates.add(24);

		states[26].setUnitOffset(0, -26);
		states[26].adjacentStates.add(25);
		states[26].adjacentStates.add(27);
		states[26].adjacentStates.add(33);
		states[26].adjacentStates.add(24);
		states[26].adjacentStates.add(23);
		
		states[27].setUnitOffset(0, -14);
		states[27].adjacentStates.add(26);
		states[27].adjacentStates.add(28);	
		states[27].adjacentStates.add(33);
		states[27].adjacentStates.add(32);	
		states[27].adjacentStates.add(22);
		states[27].adjacentStates.add(23);
				
		states[28].setUnitOffset(0, -28);
		states[28].adjacentStates.add(27);
		states[28].adjacentStates.add(29);
		states[28].adjacentStates.add(32);
		states[28].adjacentStates.add(22);
		
		states[29].setUnitOffset(22, 0);
		states[29].adjacentStates.add(30);
		states[29].adjacentStates.add(31);
		states[29].adjacentStates.add(28);	
		states[29].adjacentStates.add(32);
		states[29].adjacentStates.add(22);
		states[29].adjacentStates.add(21);	
				
		states[30].setUnitOffset(15, 15);
		states[30].adjacentStates.add(29);
		states[30].adjacentStates.add(31);
		states[30].adjacentStates.add(21);
		states[30].adjacentStates.add(20);

		states[31].setUnitOffset(-4, 0);
		states[31].adjacentStates.add(30);
		states[31].adjacentStates.add(32);
		states[31].adjacentStates.add(29);	
		states[31].adjacentStates.add(37);
		states[31].adjacentStates.add(36);	

		states[32].setUnitOffset(-4, 2);
		states[32].adjacentStates.add(31);
		states[32].adjacentStates.add(28);
		states[32].adjacentStates.add(27);
		states[32].adjacentStates.add(33);
		states[32].adjacentStates.add(36);	
		states[32].adjacentStates.add(37);
		states[32].adjacentStates.add(29);	

		states[33].setUnitOffset(-4, 6);
		states[33].adjacentStates.add(32);
		states[33].adjacentStates.add(34);
		states[33].adjacentStates.add(35);
		states[33].adjacentStates.add(36);
		states[33].adjacentStates.add(26);		
		states[33].adjacentStates.add(27);

		states[34].setUnitOffset(0, 40);
		states[34].adjacentStates.add(33);
		states[34].adjacentStates.add(35);
		states[34].adjacentStates.add(25);
		states[34].adjacentStates.add(26);

		states[35].setUnitOffset(-6, -4);
		states[35].adjacentStates.add(40);
		states[35].adjacentStates.add(41);
		states[35].adjacentStates.add(38);
		states[35].adjacentStates.add(36);
		states[35].adjacentStates.add(34);
		states[35].adjacentStates.add(38);
		states[35].adjacentStates.add(33);

		states[36].setUnitOffset(-4, 0);
		states[36].adjacentStates.add(37);
		states[36].adjacentStates.add(31);
		states[36].adjacentStates.add(32);	
		states[36].adjacentStates.add(35);
		states[36].adjacentStates.add(38);
		states[36].adjacentStates.add(33);	

		states[37].setUnitOffset(0, 10);
		states[37].adjacentStates.add(38);
		states[37].adjacentStates.add(36);
		states[37].adjacentStates.add(31);
		states[37].adjacentStates.add(38);
		states[37].adjacentStates.add(39);
		
		states[38].setUnitOffset(-4, 30);
		states[38].adjacentStates.add(40);
		states[38].adjacentStates.add(36);
		states[38].adjacentStates.add(35);
		states[38].adjacentStates.add(39);
		states[38].adjacentStates.add(37);

		states[39].setUnitOffset(-26, 0);
		states[39].adjacentStates.add(40);
		states[39].adjacentStates.add(37);
		states[39].adjacentStates.add(38);

		states[40].setUnitOffset(0, 9);
		states[40].adjacentStates.add(41);
		states[40].adjacentStates.add(39);
		states[40].adjacentStates.add(38);
		states[40].adjacentStates.add(35);		

		states[41].setUnitOffset(0, 14);
		states[41].adjacentStates.add(40);
		states[41].adjacentStates.add(35);
		
		return states;
	}
}
