package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import game_engine.InitObjects;
import items.HealthPack;

public class HealthPackTests {

	private HealthPack healthPack;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		healthPack = new HealthPack(x, y);
	}

	@Test
	public void testGoldUnit() {
		assertNotNull(healthPack.loadImage(healthPack.initHealth()));
		assertFalse(healthPack.initHealth().equals(""));
	}

	@Test
	public void testListOfGoldBars() {
		assertFalse(InitObjects.initGold().isEmpty());
	}

	@Test
	public void testGoldBorders() {
		assertFalse(healthPack.getY() > 1200);
	}

	@After
	public void tearDown() throws Exception {
		healthPack = null;
	}
}