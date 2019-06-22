package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Dragon;
import game_engine.InitObjects;

public class DragonTests {

	private Dragon dragon;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		dragon = new Dragon(x, y);
	}

	@Test
	public void testDragonUnit() {
		assertNotNull(dragon.loadImage(dragon.initBoss()));
		assertFalse(dragon.initBoss().equals(""));
	}

	@Test
	public void testListOfDragons() {
		assertFalse(InitObjects.initDragons().isEmpty());
	}

	@Test
	public void testDragonsBorders() {
		assertFalse(dragon.getX() < 0);
	}

	@After
	public void tearDown() throws Exception {
		dragon = null;
	}
}