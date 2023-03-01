package suite.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import util.KilledEnemies;

public class KilledEnemiesTest {
	
	@Test
	public void testKilledAliens() {
		String[] killedAliens = KilledEnemies.getEnemiesByTypeAndCount("enemy.Alien");
		
		assertEquals(2, killedAliens.length);
	}
	
	@Test
	public void testKilledDragons() {
		String[] killedDragons = KilledEnemies.getEnemiesByTypeAndCount("enemy.Dragon");
		
		assertEquals(2, killedDragons.length);
	}
}
