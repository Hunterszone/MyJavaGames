package suite.tests;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import entities.Alien;
import game_engine.InitObjects;

public class AlienTest {

	private Alien alien;
	
	@Before
	public void setUp() throws Exception {
		alien = new Alien(450, 450);
	}

	public void testListOfAliens() {
		assertFalse(InitObjects.initAliens().isEmpty());
	}

	@Test
	public void testAlienMove_standard() {
		alien.move();
		assertFalse(alien.getX() < 0 || alien.getX() > 1200);
		assertFalse(alien.getY() < 0 || alien.getY() > 700);
	}
	
	@Test
	public void testAlienMove_faster() {
		alien.moveFaster();
		assertFalse(alien.getX() < 0 || alien.getX() > 1200);
		assertFalse(alien.getY() < 0 || alien.getY() > 700);
	}

}