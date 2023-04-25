import java.net.URL;
import java.lang.ClassLoader;

public class StateLoader
{
	public static State[] loadStates()
	{
		State[] states = new State[GamePanel.NUMBER_STATES];
		
		states[0] = new State("maine",
				"assets/maine.png", 0);
		states[1] = new State("newEngland",
				"assets/newEngland.png", 1);
		states[1].setUnitOffset(-20, 0);
		states[2] = new State("newYork",
				"assets/newYork.png", 2);
		states[2].setUnitOffset(0, -50);
		states[3] = new State("pennsylvania",
				"assets/pennsylvania.png", 3);
		states[4] = new State("midAtlantic",
				"assets/midAtlantic.png", 4);
		states[4].setUnitOffset(60, 0);
		states[5] = new State("virginia",
				"assets/virginia.png", 5);
		states[6] = new State("northCarolina",
				"assets/northCarolina.png", 6);
		states[7] = new State("southCarolina",
				"assets/southCarolina.png", 7);
		states[7].setUnitOffset(0, 30);
		states[8] = new State("georgia",
				"assets/georgia.png", 8);
		states[8].setUnitOffset(0, 30);
		states[9] = new State("florida",
				"assets/florida.png", 9);
		states[9].setUnitOffset(40, 0);
		states[10] = new State("alabama",
				"assets/alabama.png",10);
		states[10].setUnitOffset(-20, -20);
		states[11] = new State("tennessee",
				"assets/tennessee.png",11);
		states[12] = new State("kentucky",
				"assets/kentucky.png",12);
		states[13] = new State("westVirginia",
				"assets/westVirginia.png",13);
		states[13].setUnitOffset(-20, 20);
		states[14] = new State("ohio",
				"assets/ohio.png",14);
		states[14].setUnitOffset(0, 30);
		states[15] = new State("indiana",
				"assets/indiana.png",15);
		states[15].setUnitOffset(0, -40);
		states[16] = new State("michigan",
				"assets/michigan.png",16);
		states[16].setUnitOffset(20, 30);
		states[17] = new State("wisconsin",
				"assets/wisconsin.png",17);
		states[18] = new State("illinois",
				"assets/illinois.png",18);
		states[19] = new State("mississippi",
				"assets/mississippi.png",19);
		states[19].setUnitOffset(0, -50);
		states[20] = new State("louisiana",
				"assets/louisiana.png",20);
		states[20].setUnitOffset(-30, 0);
		states[21] = new State("arkansas",
				"assets/arkansas.png",21);
		states[21].setUnitOffset(-30, 40);
		states[22] = new State("missouri",
				"assets/missouri.png",22);
		states[22].setUnitOffset(-20, 20);
		states[23] = new State("iowa",
				"assets/iowa.png",23);
		states[24] = new State("minnesota",
				"assets/minnesota.png",24);
		states[24].setUnitOffset(-20, 70);
		states[25] = new State("northDakota",
				"assets/northDakota.png",25);
		states[25].setUnitOffset(0, -30);
		states[26] = new State("southDakota",
				"assets/southDakota.png",26);
		states[26].setUnitOffset(0, -30);
		states[27] = new State("nebraska",
				"assets/nebraska.png",27);
		states[28] = new State("kansas",
				"assets/kansas.png",28);
		states[28].setUnitOffset(0, -30);
		states[29] = new State("oklahmoa",
				"assets/oklahmoa.png",29);
		states[30] = new State("texas",
				"assets/texas.png",30);
		states[31] = new State("newMexico",
				"assets/newMexico.png",31);
		states[32] = new State("colorado",
				"assets/colorado.png",32);
		states[33] = new State("wyoming",
				"assets/wyoming.png",33);
		states[34] = new State("montana",
				"assets/montana.png",34);
		states[34].setUnitOffset(0, 30);
		states[35] = new State("idaho",
				"assets/idaho.png",35);
		states[35].setUnitOffset(-10, 0);
		states[36] = new State("utah",
				"assets/utah.png",36);
		states[37] = new State("arizona",
				"assets/arizona.png",37);
		states[38] = new State("nevada",
				"assets/nevada.png",38);
		states[38].setUnitOffset(0, 30);
		states[39] = new State("california",
				"assets/california.png",39);
		states[39].setUnitOffset(-20, 0);
		states[40] = new State("oregon",
				"assets/oregon.png",40);
		states[41] = new State("washington",
				"assets/washington.png",41);
		
		return states;
	}
}
