package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Alien;
import game_engine.InitObjects;

public class AlienTests {

	private Alien alien;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		alien = new Alien(x, y);
	}

	@Test
	public void testAlienUnit_imageNotNull_imageNameNotEmpty() {
		assertNotNull(alien.loadImage(alien.initAlien()));
		assertFalse(alien.initAlien().equals(""));
	}

	@Test
	public void testListOfAliens_NotEmpty() {
		assertFalse(InitObjects.initAliens().isEmpty());
	}

	@Test
	public void testAliensBorders_imageInsideBorders() {
		assertFalse(alien.getX() < 0);
		assertFalse(alien.getY() > 1200);
	}

	@After
	public void tearDown() throws Exception {
		alien = null;
	}
}