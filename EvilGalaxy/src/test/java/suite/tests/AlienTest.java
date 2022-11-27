package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import entities.Alien;

public class AlienTest {

	private Alien alien;
	
	@Before
	public void setUp() throws Exception {
		alien = new Alien(TestUtils.POS_X, TestUtils.POS_Y);
	}

	@Test
	public void testAlienImg() {
		assertNotNull(alien.getImage());
	}
	
	@Test
	public void testListOfAliens() {
		assertNotNull(Alien.aliens);
		assertFalse(Alien.aliens.isEmpty());
	}

	@Test
	public void testAlienPositionOnEasyAndMedium() {
		alien.move();
		assertNotEquals("Alien is not X-moving on EASY and MEDIUM difficulty!", TestUtils.POS_X, alien.getX());
		assertNotEquals("Alien is not Y-moving on EASY and MEDIUM difficulty!", TestUtils.POS_Y, alien.getY());
	}
	
	@Test
	public void testAlienPositionOnHard() {
		alien.moveFaster();
		assertNotEquals("Alien is not X-moving on HARD difficulty!", TestUtils.POS_X, alien.getX());
		alien.move();
		assertNotEquals("Alien is not Y-moving on HARD difficulty!", TestUtils.POS_Y, alien.getY());
	}
	
}