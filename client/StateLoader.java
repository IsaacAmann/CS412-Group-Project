import java.net.URL;
import java.lang.ClassLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StateLoader
{
	
	public static State[] loadStates()
	{
		ExecutorService threadPool = Executors.newCachedThreadPool();
		State[] states = new State[GamePanel.NUMBER_STATES];
		
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
		
		states[0].adjacentStates.add(1);
		
		states[1].adjacentStates.add(0);
		states[1].adjacentStates.add(2);
		states[1].adjacentStates.add(4);
		states[1].setUnitOffset(-20, 0);
		
		states[2].setUnitOffset(0, -50);
		states[2].adjacentStates.add(1);
		states[2].adjacentStates.add(3);
		states[2].adjacentStates.add(4);
		
		states[3].adjacentStates.add(2);
		states[3].adjacentStates.add(4);
		states[3].adjacentStates.add(13);
		states[3].adjacentStates.add(14);		
		
		states[4].setUnitOffset(60, 0);
		states[4].adjacentStates.add(1);
		states[4].adjacentStates.add(2);
		states[4].adjacentStates.add(3);
		states[4].adjacentStates.add(5);
		states[4].adjacentStates.add(13);
		
		states[5].adjacentStates.add(4);
		states[5].adjacentStates.add(13);
		states[5].adjacentStates.add(6);
		states[5].adjacentStates.add(12);
		states[5].adjacentStates.add(11);
				
		states[6].adjacentStates.add(5);
		states[6].adjacentStates.add(7);
		states[6].adjacentStates.add(11);
		states[6].adjacentStates.add(8);
				
		states[7].setUnitOffset(0, 30);
		states[7].adjacentStates.add(6);
		states[7].adjacentStates.add(8);
		
		states[8].setUnitOffset(0, 30);
		states[8].adjacentStates.add(7);
		states[8].adjacentStates.add(9);
		states[8].adjacentStates.add(10);
		states[8].adjacentStates.add(11);
		
		states[9].adjacentStates.add(8);	
		states[9].adjacentStates.add(10);	
		states[9].setUnitOffset(40, 0);

		states[10].adjacentStates.add(9);
		states[10].adjacentStates.add(8);
		states[10].adjacentStates.add(19);
		states[10].adjacentStates.add(11);
		states[10].setUnitOffset(-20, -20);
		
		states[11].adjacentStates.add(10);
		states[11].adjacentStates.add(19);
		states[11].adjacentStates.add(8);
		states[11].adjacentStates.add(6);	
		states[11].adjacentStates.add(12);
		states[11].adjacentStates.add(5);
		states[11].adjacentStates.add(22);
		states[11].adjacentStates.add(21);	
				
		states[12].adjacentStates.add(11);
		states[12].adjacentStates.add(13);
		states[12].adjacentStates.add(5);
		states[12].adjacentStates.add(14);
		states[12].adjacentStates.add(15);
		states[12].adjacentStates.add(22);
		states[12].adjacentStates.add(18);
		
		states[13].setUnitOffset(-20, 20);
		states[13].adjacentStates.add(3);
		states[13].adjacentStates.add(4);
		states[13].adjacentStates.add(14);
		states[13].adjacentStates.add(12);
		states[13].adjacentStates.add(5);		
		
		states[14].setUnitOffset(0, 30);
		states[14].adjacentStates.add(15);
		states[14].adjacentStates.add(13);
		states[14].adjacentStates.add(12);
		states[14].adjacentStates.add(3);
		
		states[15].setUnitOffset(0, -40);
		states[15].adjacentStates.add(16);
		states[15].adjacentStates.add(18);
		states[15].adjacentStates.add(12);
		states[15].adjacentStates.add(14);
		
		states[16].setUnitOffset(20, 30);
		states[16].adjacentStates.add(17);
		states[16].adjacentStates.add(15);
		states[16].adjacentStates.add(14);
		
		states[17].adjacentStates.add(16);
		states[17].adjacentStates.add(14);
		states[17].adjacentStates.add(24);
		states[17].adjacentStates.add(18);
		
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
		
		states[20].setUnitOffset(-30, 0);
		states[20].adjacentStates.add(19);
		states[20].adjacentStates.add(21);
		states[20].adjacentStates.add(30);
		
		states[21].setUnitOffset(-30, 40);
		states[21].adjacentStates.add(20);
		states[21].adjacentStates.add(29);
		states[21].adjacentStates.add(30);
		states[21].adjacentStates.add(11);
		states[21].adjacentStates.add(19);
		
		states[22].setUnitOffset(-20, 20);
		states[22].adjacentStates.add(29);
		states[22].adjacentStates.add(21);
		states[22].adjacentStates.add(28);
		states[22].adjacentStates.add(18);
		states[22].adjacentStates.add(23);
		states[22].adjacentStates.add(11);
		states[22].adjacentStates.add(12);
		
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
		
		states[25].setUnitOffset(0, -30);
		states[25].adjacentStates.add(26);
		states[25].adjacentStates.add(34);
		states[25].adjacentStates.add(24);

		states[26].setUnitOffset(0, -30);
		states[26].adjacentStates.add(25);
		states[26].adjacentStates.add(27);
		states[26].adjacentStates.add(33);
		states[26].adjacentStates.add(24);
		states[26].adjacentStates.add(23);
		
		states[27].adjacentStates.add(26);
		states[27].adjacentStates.add(28);	
		states[27].adjacentStates.add(33);
		states[27].adjacentStates.add(32);	
		states[27].adjacentStates.add(22);
		states[27].adjacentStates.add(23);
				
		states[28].setUnitOffset(0, -30);
		states[28].adjacentStates.add(27);
		states[28].adjacentStates.add(29);
		states[28].adjacentStates.add(32);
		states[28].adjacentStates.add(22);
		
		states[29].adjacentStates.add(30);
		states[29].adjacentStates.add(31);
		states[29].adjacentStates.add(28);	
		states[29].adjacentStates.add(32);
		states[29].adjacentStates.add(22);
		states[29].adjacentStates.add(21);	
				
		states[30].adjacentStates.add(29);
		states[30].adjacentStates.add(31);
		states[30].adjacentStates.add(21);
		states[30].adjacentStates.add(20);

		states[31].adjacentStates.add(30);
		states[31].adjacentStates.add(32);
		states[31].adjacentStates.add(29);	
		states[31].adjacentStates.add(37);
		states[31].adjacentStates.add(36);	

		states[32].adjacentStates.add(31);
		states[32].adjacentStates.add(28);
		states[32].adjacentStates.add(27);
		states[32].adjacentStates.add(33);
		states[32].adjacentStates.add(36);	
		states[32].adjacentStates.add(37);
		states[32].adjacentStates.add(29);	

		states[33].adjacentStates.add(32);
		states[33].adjacentStates.add(34);
		states[33].adjacentStates.add(35);
		states[33].adjacentStates.add(36);
		states[33].adjacentStates.add(26);		
		states[33].adjacentStates.add(27);

		states[34].setUnitOffset(0, 30);
		states[34].adjacentStates.add(33);
		states[34].adjacentStates.add(35);
		states[34].adjacentStates.add(25);
		states[34].adjacentStates.add(26);

		states[35].setUnitOffset(-10, 0);
		states[35].adjacentStates.add(40);
		states[35].adjacentStates.add(41);
		states[35].adjacentStates.add(38);
		states[35].adjacentStates.add(36);
		states[35].adjacentStates.add(34);
		states[35].adjacentStates.add(38);
		states[35].adjacentStates.add(33);

		states[36].adjacentStates.add(37);
		states[36].adjacentStates.add(31);
		states[36].adjacentStates.add(32);	
		states[36].adjacentStates.add(35);
		states[36].adjacentStates.add(38);
		states[36].adjacentStates.add(33);	

		states[37].adjacentStates.add(38);
		states[37].adjacentStates.add(36);
		states[37].adjacentStates.add(31);
		states[37].adjacentStates.add(38);
		states[37].adjacentStates.add(39);
		
		states[38].setUnitOffset(0, 30);
		states[38].adjacentStates.add(40);
		states[38].adjacentStates.add(36);
		states[38].adjacentStates.add(35);
		states[38].adjacentStates.add(39);
		states[38].adjacentStates.add(37);

		states[39].setUnitOffset(-20, 0);
		states[39].adjacentStates.add(40);
		states[39].adjacentStates.add(37);
		states[39].adjacentStates.add(38);

		states[40].adjacentStates.add(41);
		states[40].adjacentStates.add(39);
		states[40].adjacentStates.add(38);
		states[40].adjacentStates.add(35);		

		states[41].adjacentStates.add(40);
		states[41].adjacentStates.add(35);
		
		return states;
	}
	
	//Old single threaded state loading
	/*
	public static State[] loadStates()
	{
		State[] states = new State[GamePanel.NUMBER_STATES];
		
		states[0] = new State("maine",
				"assets/maine.png", 0);
		states[0].adjacentStates.add(1);
		
		states[1] = new State("newEngland",
				"assets/newEngland.png", 1);
		states[1].adjacentStates.add(0);
		states[1].adjacentStates.add(2);
		states[1].adjacentStates.add(4);
		states[1].setUnitOffset(-20, 0);
		
		states[2] = new State("newYork",
				"assets/newYork.png", 2);
		states[2].setUnitOffset(0, -50);
		states[2].adjacentStates.add(1);
		states[2].adjacentStates.add(3);
		states[2].adjacentStates.add(4);
		
		states[3] = new State("pennsylvania",
				"assets/pennsylvania.png", 3);
		states[3].adjacentStates.add(2);
		states[3].adjacentStates.add(4);
		states[3].adjacentStates.add(13);
		states[3].adjacentStates.add(14);		
		
		states[4] = new State("midAtlantic",
				"assets/midAtlantic.png", 4);
		states[4].setUnitOffset(60, 0);
		states[4].adjacentStates.add(1);
		states[4].adjacentStates.add(2);
		states[4].adjacentStates.add(3);
		states[4].adjacentStates.add(5);
		states[4].adjacentStates.add(13);
		
		states[5] = new State("virginia",
				"assets/virginia.png", 5);
		states[5].adjacentStates.add(4);
		states[5].adjacentStates.add(13);
		states[5].adjacentStates.add(6);
		states[5].adjacentStates.add(12);
		states[5].adjacentStates.add(11);
				
		states[6] = new State("northCarolina",
				"assets/northCarolina.png", 6);
		states[6].adjacentStates.add(5);
		states[6].adjacentStates.add(7);
		states[6].adjacentStates.add(11);
		states[6].adjacentStates.add(8);
				
		states[7] = new State("southCarolina",
				"assets/southCarolina.png", 7);
		states[7].setUnitOffset(0, 30);
		states[7].adjacentStates.add(6);
		states[7].adjacentStates.add(8);
		
		states[8] = new State("georgia",
				"assets/georgia.png", 8);
		states[8].setUnitOffset(0, 30);
		states[8].adjacentStates.add(7);
		states[8].adjacentStates.add(9);
		states[8].adjacentStates.add(10);
		states[8].adjacentStates.add(11);
		
		states[9] = new State("florida",
				"assets/florida.png", 9);
		states[9].adjacentStates.add(8);	
		states[9].adjacentStates.add(10);	
		states[9].setUnitOffset(40, 0);
		
		states[10] = new State("alabama",
				"assets/alabama.png",10);
		states[10].adjacentStates.add(9);
		states[10].adjacentStates.add(8);
		states[10].adjacentStates.add(19);
		states[10].adjacentStates.add(11);
		states[10].setUnitOffset(-20, -20);
		
		states[11] = new State("tennessee",
				"assets/tennessee.png",11);
		states[11].adjacentStates.add(10);
		states[11].adjacentStates.add(19);
		states[11].adjacentStates.add(8);
		states[11].adjacentStates.add(6);	
		states[11].adjacentStates.add(12);
		states[11].adjacentStates.add(5);
		states[11].adjacentStates.add(22);
		states[11].adjacentStates.add(21);	
				
		states[12] = new State("kentucky",
				"assets/kentucky.png",12);
		states[12].adjacentStates.add(11);
		states[12].adjacentStates.add(13);
		states[12].adjacentStates.add(5);
		states[12].adjacentStates.add(14);
		states[12].adjacentStates.add(15);
		states[12].adjacentStates.add(22);
		states[12].adjacentStates.add(18);
		
		states[13] = new State("westVirginia",
				"assets/westVirginia.png",13);
		states[13].setUnitOffset(-20, 20);
		states[13].adjacentStates.add(3);
		states[13].adjacentStates.add(4);
		states[13].adjacentStates.add(14);
		states[13].adjacentStates.add(12);
		states[13].adjacentStates.add(5);		
		
		states[14] = new State("ohio",
				"assets/ohio.png",14);
		states[14].setUnitOffset(0, 30);
		states[14].adjacentStates.add(15);
		states[14].adjacentStates.add(13);
		states[14].adjacentStates.add(12);
		states[14].adjacentStates.add(3);
		
		states[15] = new State("indiana",
				"assets/indiana.png",15);
		states[15].setUnitOffset(0, -40);
		states[15].adjacentStates.add(16);
		states[15].adjacentStates.add(18);
		states[15].adjacentStates.add(12);
		states[15].adjacentStates.add(14);
		
		states[16] = new State("michigan",
				"assets/michigan.png",16);
		states[16].setUnitOffset(20, 30);
		states[16].adjacentStates.add(17);
		states[16].adjacentStates.add(15);
		states[16].adjacentStates.add(14);
		
		states[17] = new State("wisconsin",
				"assets/wisconsin.png",17);
		states[17].adjacentStates.add(16);
		states[17].adjacentStates.add(14);
		states[17].adjacentStates.add(24);
		states[17].adjacentStates.add(18);
		
		states[18] = new State("illinois",
				"assets/illinois.png",18);
		states[18].adjacentStates.add(17);
		states[18].adjacentStates.add(12);
		states[18].adjacentStates.add(22);
		states[18].adjacentStates.add(12);
		states[18].adjacentStates.add(15);
		states[18].adjacentStates.add(23);
		
		states[19] = new State("mississippi",
				"assets/mississippi.png",19);
		states[19].setUnitOffset(0, -50);
		states[19].adjacentStates.add(20);
		states[19].adjacentStates.add(21);
		states[19].adjacentStates.add(11);
		states[19].adjacentStates.add(10);
		
		states[20] = new State("louisiana",
				"assets/louisiana.png",20);
		states[20].setUnitOffset(-30, 0);
		states[20].adjacentStates.add(19);
		states[20].adjacentStates.add(21);
		states[20].adjacentStates.add(30);
		
		states[21] = new State("arkansas",
				"assets/arkansas.png",21);
		states[21].setUnitOffset(-30, 40);
		states[21].adjacentStates.add(20);
		states[21].adjacentStates.add(29);
		states[21].adjacentStates.add(30);
		states[21].adjacentStates.add(11);
		states[21].adjacentStates.add(19);
		
		states[22] = new State("missouri",
				"assets/missouri.png",22);
		states[22].setUnitOffset(-20, 20);
		states[22].adjacentStates.add(29);
		states[22].adjacentStates.add(21);
		states[22].adjacentStates.add(28);
		states[22].adjacentStates.add(18);
		states[22].adjacentStates.add(23);
		states[22].adjacentStates.add(11);
		states[22].adjacentStates.add(12);
		
		states[23] = new State("iowa",
				"assets/iowa.png",23);
		states[23].adjacentStates.add(24);
		states[23].adjacentStates.add(17);
		states[23].adjacentStates.add(26);
		states[23].adjacentStates.add(27);
		states[23].adjacentStates.add(18);
		states[23].adjacentStates.add(22);		
				
		states[24] = new State("minnesota",
				"assets/minnesota.png",24);
		states[24].setUnitOffset(-20, 70);
		states[24].adjacentStates.add(25);
		states[24].adjacentStates.add(26);
		states[24].adjacentStates.add(23);
		states[24].adjacentStates.add(17);
		
		states[25] = new State("northDakota",
				"assets/northDakota.png",25);
		states[25].setUnitOffset(0, -30);
		states[25].adjacentStates.add(26);
		states[25].adjacentStates.add(34);
		states[25].adjacentStates.add(24);
		
		states[26] = new State("southDakota",
				"assets/southDakota.png",26);
		states[26].setUnitOffset(0, -30);
		states[26].adjacentStates.add(25);
		states[26].adjacentStates.add(27);
		states[26].adjacentStates.add(33);
		states[26].adjacentStates.add(24);
		states[26].adjacentStates.add(23);
		
		states[27] = new State("nebraska",
				"assets/nebraska.png",27);
		states[27].adjacentStates.add(26);
		states[27].adjacentStates.add(28);	
		states[27].adjacentStates.add(33);
		states[27].adjacentStates.add(32);	
		states[27].adjacentStates.add(22);
		states[27].adjacentStates.add(23);
				
		states[28] = new State("kansas",
				"assets/kansas.png",28);
		states[28].setUnitOffset(0, -30);
		states[28].adjacentStates.add(27);
		states[28].adjacentStates.add(29);
		states[28].adjacentStates.add(32);
		states[28].adjacentStates.add(22);
		
		states[29] = new State("oklahmoa",
				"assets/oklahmoa.png",29);
		states[29].adjacentStates.add(30);
		states[29].adjacentStates.add(31);
		states[29].adjacentStates.add(28);	
		states[29].adjacentStates.add(32);
		states[29].adjacentStates.add(22);
		states[29].adjacentStates.add(21);	
				
		states[30] = new State("texas",
				"assets/texas.png",30);
		states[30].adjacentStates.add(29);
		states[30].adjacentStates.add(31);
		states[30].adjacentStates.add(21);
		states[30].adjacentStates.add(20);
		
		states[31] = new State("newMexico",
				"assets/newMexico.png",31);
		states[31].adjacentStates.add(30);
		states[31].adjacentStates.add(32);
		states[31].adjacentStates.add(29);	
		states[31].adjacentStates.add(37);
		states[31].adjacentStates.add(36);	
				
		states[32] = new State("colorado",
				"assets/colorado.png",32);
		states[32].adjacentStates.add(31);
		states[32].adjacentStates.add(28);
		states[32].adjacentStates.add(27);
		states[32].adjacentStates.add(33);
		states[32].adjacentStates.add(36);	
		states[32].adjacentStates.add(37);
		states[32].adjacentStates.add(29);	
				
		states[33] = new State("wyoming",
				"assets/wyoming.png",33);
		states[33].adjacentStates.add(32);
		states[33].adjacentStates.add(34);
		states[33].adjacentStates.add(35);
		states[33].adjacentStates.add(36);
		states[33].adjacentStates.add(26);		
		states[33].adjacentStates.add(27);
				
		states[34] = new State("montana",
				"assets/montana.png",34);
		states[34].setUnitOffset(0, 30);
		states[34].adjacentStates.add(33);
		states[34].adjacentStates.add(35);
		states[34].adjacentStates.add(25);
		states[34].adjacentStates.add(26);
		
		states[35] = new State("idaho",
				"assets/idaho.png",35);
		states[35].setUnitOffset(-10, 0);
		states[35].adjacentStates.add(40);
		states[35].adjacentStates.add(41);
		states[35].adjacentStates.add(38);
		states[35].adjacentStates.add(36);
		states[35].adjacentStates.add(34);
		states[35].adjacentStates.add(38);
		states[35].adjacentStates.add(33);
		
		states[36] = new State("utah",
				"assets/utah.png",36);
		states[36].adjacentStates.add(37);
		states[36].adjacentStates.add(31);
		states[36].adjacentStates.add(32);	
		states[36].adjacentStates.add(35);
		states[36].adjacentStates.add(38);
		states[36].adjacentStates.add(33);	
				
		states[37] = new State("arizona",
				"assets/arizona.png",37);
		states[37].adjacentStates.add(38);
		states[37].adjacentStates.add(36);
		states[37].adjacentStates.add(31);
		states[37].adjacentStates.add(38);
		states[37].adjacentStates.add(39);
		
		states[38] = new State("nevada",
				"assets/nevada.png",38);
		states[38].setUnitOffset(0, 30);
		states[38].adjacentStates.add(40);
		states[38].adjacentStates.add(36);
		states[38].adjacentStates.add(35);
		states[38].adjacentStates.add(39);
		states[38].adjacentStates.add(37);
		
		states[39] = new State("california",
				"assets/california.png",39);
		states[39].setUnitOffset(-20, 0);
		states[39].adjacentStates.add(40);
		states[39].adjacentStates.add(37);
		states[39].adjacentStates.add(38);
		
		states[40] = new State("oregon",
				"assets/oregon.png",40);
		states[40].adjacentStates.add(41);
		states[40].adjacentStates.add(39);
		states[40].adjacentStates.add(38);
		states[40].adjacentStates.add(35);		
		
		states[41] = new State("washington",
				"assets/washington.png",41);
		states[41].adjacentStates.add(40);
		states[41].adjacentStates.add(35);
		
		return states;
	}
	*/
}
