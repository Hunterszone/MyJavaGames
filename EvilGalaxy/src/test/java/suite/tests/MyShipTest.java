package suite.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Component;
import java.awt.event.KeyEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import entities.PlayerShip;
import enums.Images;
import sound_engine.PlayWave1st;

@RunWith(Theories.class)
public class MyShipTest {

	private PlayerShip playerOne;
	private int x, y;
	@DataPoints
	public static KeyEvent e = new KeyEvent(new Component() {}, 0, 0, 0, 0, (char) 0);

	@Before
	public void setUp() throws Exception {
		playerOne = new PlayerShip(x, y);
	}

	@Test
	public void testMyShipUnit() {
		String drawShip = playerOne.drawShip();
		assertNotNull("Image file not found!", drawShip);
		assertEquals(Images.MYSHIPINIT.getImg(), drawShip);
	}

	@Test
	public void testMyShipDamaged() {
		assertNotNull(Images.MYSHIPDAMAGED.getImg());
	}

	@Test
	public void testMyShipUpsideDown() {
		String upsideDown = playerOne.upsideDown();
		assertNotNull("Image file not found!", upsideDown);
		assertEquals(Images.MYSHIPLIFEBAR.getImg(), upsideDown);
	}

	@Test
	public void testMyShipFired() {
		String shipOnFire = playerOne.shipOnFire();
		assertNotNull("Image file not found!", shipOnFire);
		assertEquals(Images.MYSHIPONFIRE.getImg(), shipOnFire);
	}

	@Test
	public void testMyShipEscapeForbidden() {
		assertNotNull(Images.MYSHIPESCAPE.getImg());
	}

	@Test
	public void testMyShipGodMode() {
		String godMode = playerOne.godMode();
		assertNotNull("Image file not found!", godMode);
		assertEquals(Images.MYSHIPGOD.getImg(), godMode);
	}

	@Test
	public void testListsOfAmmos() {
		assertFalse(playerOne.loadMissiles().isEmpty());
		assertFalse(playerOne.loadRockets().isEmpty());
	}

	@Theory
	public void testGunLockedSound() {
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