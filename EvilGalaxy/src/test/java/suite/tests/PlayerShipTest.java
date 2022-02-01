package suite.tests;

import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.PlayerShip;
import util.Constants;

public class PlayerShipTest {

	private PlayerShip playerOne;

	@Before
	public void setUp() throws Exception {
		playerOne = new PlayerShip(Constants.MYSHIP_X, Constants.MYSHIP_Y);
	}

	@Test
	public void testListsOfAmmos() {
		assertFalse(playerOne.loadMissiles().isEmpty());
		assertFalse(playerOne.loadRockets().isEmpty());
	}

	@Test
	public void testMyShipBorders() {
		playerOne.move();
		assertFalse("Player X out of bounds!", playerOne.getX() < 0 || playerOne.getX() > 1200);
		assertFalse("Player Y out of bounds!", playerOne.getY() < 0 || playerOne.getY() > 700);
	}

	@After
	public void tearDown() throws Exception {
		playerOne = null;
	}
}