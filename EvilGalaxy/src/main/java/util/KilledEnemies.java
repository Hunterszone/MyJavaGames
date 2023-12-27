package util;

import enemy.Alien;
import enemy.Dragon;

public class KilledEnemies {
	public static String[] getEnemiesByTypeAndCount(String enemyName) {
		
		if (Alien.class.getName().equalsIgnoreCase(enemyName)) {
			final int alienKilled = LivesAndCounts.getAlienKilled();
			return new String[] { enemyName, String.valueOf(alienKilled) };
		}
		if (Dragon.class.getName().equalsIgnoreCase(enemyName)) {
			final int dragonKilled = LivesAndCounts.getDragonKilled();
			return new String[] { enemyName, String.valueOf(dragonKilled) };
		}
		return new String[] { "" };
	}
}
