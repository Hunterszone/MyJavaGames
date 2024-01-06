package util;

public class KilledByChecker {

	public static void checkKilledByAlien() {
		Flags.killedByBunker = false;
		Flags.killedByDragon = false;
		Flags.killedByEvilHead = false;
		Flags.killedByAlien = true;
	}
	
	public static void checkKilledByEvilHead() {
		Flags.killedByBunker = false;
		Flags.killedByAlien = false;
		Flags.killedByDragon = false;
		Flags.killedByEvilHead = true;
	}
	
	public static void checkKilledByBunker() {
		Flags.killedByAlien = false;
		Flags.killedByDragon = false;
		Flags.killedByEvilHead = false;
		Flags.killedByBunker = true;
	}
	
	public static void checkKilledByDragon() {
		Flags.killedByBunker = false;
		Flags.killedByEvilHead = false;
		Flags.killedByAlien = false;
		Flags.killedByDragon = true;
	}
}
