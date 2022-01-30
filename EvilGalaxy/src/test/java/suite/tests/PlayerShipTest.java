package suite.tests;

import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.PlayerShip;

public class PlayerShipTest {

	private PlayerShip playerOne;

	@Before
	public void setUp() throws Exception {
		playerOne = new PlayerShip(450, 450);
	}

	@Test
	public void testListsOfAmmos() {
		assertFalse(playerOne.loadMissiles().isEmpty());
		assertFalse(playerOne.loadRockets().isEmpty());
	}

	@Test
	public void testMyShipBorders() {
		playerOne.move();
		assertFalse(playerOne.getX() < 0 || playerOne.getX() > 1200);
		assertFalse(playerOne.getY() < 0 || playerOne.getY() > 700);
	}

	@After
	public void tearDown() throws Exception {
		playerOne = null;
	}
}