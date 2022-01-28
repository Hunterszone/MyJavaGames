package suite.tests;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import entities.Alien;
import game_engine.InitObjects;

public class AlienTest {

	private Alien alien;
	private int x, y;

	@Test
	public void testListOfAliens() {
		assertFalse(InitObjects.initAliens().isEmpty());
	}

	@Test
	public void testAliensBorders() {
		alien = new Alien(x, y);
		assertFalse(alien.getX() < 0);
		assertFalse(alien.getY() > 1200);
	}

}