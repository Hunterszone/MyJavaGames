package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.PlayerShip;
import game_engine.DrawScene;
import util.Constants;

public class PlayerShipTest {

	private PlayerShip playerShip;
	private Component component;

	@Before
	public void setUp() throws Exception {
		playerShip = new PlayerShip(Constants.MYSHIP_X, Constants.MYSHIP_Y);
		component = new DrawScene();
	}

	@Test
	public void testListsOfMissiles() {
		assertNotNull(playerShip.loadMissiles());
		assertFalse(playerShip.loadMissiles().isEmpty());
	}
	
	@Test
	public void testListsOfRockets() {
		assertNotNull(playerShip.loadRockets());
		assertFalse(playerShip.loadRockets().isEmpty());
	}
	
	@Test
	public void testMyShipPositionX() throws IOException {
		KeyEvent rightArrowKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, '\u2192');
		playerShip.keyPressed(rightArrowKey);
		
		playerShip.move();
		
		assertNotEquals("MyShip is not X-moving!", Constants.MYSHIP_X, playerShip.getX());
	}

	@Test
	public void testMyShipPositionY() throws IOException {
		KeyEvent downArrowKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, '\u2193');
		playerShip.keyPressed(downArrowKey);

		playerShip.move();
		
		assertNotEquals("MyShip is not Y-moving!", Constants.MYSHIP_Y, playerShip.getY());
	}

	@After
	public void tearDown() throws Exception {
		playerShip = null;
	}
}