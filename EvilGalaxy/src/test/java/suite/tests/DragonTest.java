package suite.tests;

import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Dragon;
import game_engine.InitObjects;

public class DragonTest {

	private Dragon dragon;

	@Before
	public void setUp() throws Exception {
		dragon = new Dragon(450, 450);
	}

	@Test
	public void testListOfDragons() {
		assertFalse(InitObjects.initDragons().isEmpty());
	}

	@Test
	public void testDragonsBorders() {
		dragon.move();
		assertFalse(dragon.getX() < 0);
		assertFalse(dragon.getX() > 1200);
	}

	@After
	public void tearDown() throws Exception {
		dragon = null;
	}
}