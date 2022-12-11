package util;

import enemy.Alien;
import enemy.Dragon;
import game_engine.UpdateObjects;

public class KilledEnemies {
	public static String[] getEnemiesByTypeAndCount(String enemyName) {
		
		if (Alien.class.getName().equalsIgnoreCase(enemyName)) {
			final Integer alienKilledd = UpdateObjects.alienKilled;
			return new String[] { enemyName, alienKilledd.toString() };
		}
		if (Dragon.class.getName().equalsIgnoreCase(enemyName)) {
			final Integer dragonKilledd = UpdateObjects.dragonKilled;
			return new String[] { enemyName, dragonKilledd.toString() };
		}
		return new String[] { "" };
	}
}
