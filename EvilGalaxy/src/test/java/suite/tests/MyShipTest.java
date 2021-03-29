package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.event.KeyEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.PlayerShip;
import enums.Images;
import sound_engine.PlayWave1st;

public class MyShipTest {

	private PlayerShip playerOne;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		playerOne = new PlayerShip(x, y);
	}

	@Test
	public void testMyShipUnit() {
		assertNotNull(playerOne.loadImage(Images.MYSHIPINIT.getImg()));
		assertFalse(Images.MYSHIPINIT.getImg().isEmpty());
	}

	@Test
	public void testMyShipDamaged() {
		assertNotNull(Images.MYSHIPDAMAGED.getImg());
	}

	@Test
	public void testMyShipUpsideDown() {
		assertNotNull(Images.MYSHIPLIFEBAR.getImg());
	}

	@Test
	public void testMyShipFired() {
		assertNotNull(Images.MYSHIPONFIRE.getImg());
	}

	@Test
	public void testMyShipEscapeForbidden() {
		assertNotNull(Images.MYSHIPESCAPE.getImg());
	}

	@Test
	public void testMyShipGodMode() {
		assertNotNull(Images.MYSHIPGOD.getImg());
	}

	@Test
	public void testListsOfAmmos() {
		assertFalse(playerOne.loadMissiles().isEmpty());
		assertFalse(playerOne.loadRockets().isEmpty());
	}

	@Test
	public void testGunLockedSound(KeyEvent e) {
		PlayWave1st sound = new PlayWave1st(playerOne.gunLocked(e));
		assertTrue(sound.doesFileExists());
	}

	@Test
	public void testMyShipBorders() {
		assertFalse(playerOne.getX() < 0 || playerOne.getX() > 900);
		assertFalse(playerOne.getY() < 0 || playerOne.getY() > 700);
	}

	@After
	public void tearDown() throws Exception {
		playerOne = null;
	}
}