package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Dragon;
import game_engine.InitObjects;

public class DragonTest {

	private Dragon dragon;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		dragon = new Dragon(x, y);
	}

	@Test(timeout = 200)
	public void testDragonUnit() {
		assertNotNull(dragon.loadImage(dragon.initDragon()));
		assertNotEquals("", dragon.initDragon());
	}

	@Test(timeout = 200)
	public void testListOfDragons() {
		assertFalse(InitObjects.initDragons().isEmpty());
	}

	@Test(timeout = 200)
	public void testDragonsBorders() {
		assertFalse(dragon.getX() < 0);
	}

	@After
	public void tearDown() throws Exception {
		dragon = null;
	}
}