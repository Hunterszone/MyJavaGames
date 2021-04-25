package suite.tests;

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