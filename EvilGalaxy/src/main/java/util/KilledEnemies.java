package util;

import enemy.Alien;
import enemy.Dragon;
import game_engine.UpdateObjects;

public class KilledEnemies {


	public static String[] getEnemiesByTypeAndCount(String enemyName) {
		if (enemyName.equalsIgnoreCase(Alien.class.getName())) {
			final Integer alienKilledd = UpdateObjects.alienKilled;
			return new String[] { enemyName, alienKilledd.toString() };
		}
		if (enemyName.equalsIgnoreCase(Dragon.class.getName())) {
			final Integer dragonKilledd = UpdateObjects.dragonKilled;
			return new String[] { enemyName, dragonKilledd.toString() };
		}
		return new String[] { "" };
	}
}
