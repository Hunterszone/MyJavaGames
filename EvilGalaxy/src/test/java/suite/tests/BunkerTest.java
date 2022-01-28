package suite.tests;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import entities.Bunker;

public class BunkerTest {

	private Bunker bunker;
	private int x, y;

	@Test
	public void testListsOfBullets() {
		bunker = new Bunker(x, y);
		assertFalse(bunker.loadBullet().isEmpty());
		assertFalse(bunker.loadBullet2().isEmpty());
	}

}