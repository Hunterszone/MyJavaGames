package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Alien;
import game_engine.InitObjects;

public class AlienTest {

	private Alien alien;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		alien = new Alien(x, y);
	}

	@Test
	public void testAlienUnit() {
		assertNotNull("Image file not found!", alien.drawAlien());
		assertNotEquals("Image name is empty!", "", alien.drawAlien());
	}

	@Test
	public void testListOfAliens() {
		assertFalse(InitObjects.initAliens().isEmpty());
	}

	@Test
	public void testAliensBorders() {
		assertFalse(alien.getX() < 0);
		assertFalse(alien.getY() > 1200);
	}

	@After
	public void tearDown() throws Exception {
		alien = null;
	}
}