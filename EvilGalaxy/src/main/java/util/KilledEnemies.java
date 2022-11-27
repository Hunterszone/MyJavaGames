package util;

import entities.Alien;
import entities.Dragon;
import game_engine.Collisions;

public class KilledEnemies {


	public static String[] getEnemiesByTypeAndCount(String enemyName) {
		if (enemyName.equalsIgnoreCase(Alien.class.getName())) {
			final Integer alienKilledd = Collisions.alienKilled;
			return new String[] { enemyName, alienKilledd.toString() };
		}
		if (enemyName.equalsIgnoreCase(Dragon.class.getName())) {
			final Integer dragonKilledd = Collisions.dragonKilled;
			return new String[] { enemyName, dragonKilledd.toString() };
		}
		return new String[] { "" };
	}
}
