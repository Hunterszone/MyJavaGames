package util;

import enemy.Alien;
import enemy.Dragon;
import game_engine.UpdateObjects;

public class KilledEnemies {
	public static String[] getEnemiesByTypeAndCount(String enemyName) {
		
		if (Alien.class.getName().equalsIgnoreCase(enemyName)) {
			final int alienKilled = UpdateObjects.getAlienKilled();
			return new String[] { enemyName, String.valueOf(alienKilled) };
		}
		if (Dragon.class.getName().equalsIgnoreCase(enemyName)) {
			final int dragonKilled = UpdateObjects.getDragonKilled();
			return new String[] { enemyName, String.valueOf(dragonKilled) };
		}
		return new String[] { "" };
	}
}
