package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.MyShip;
import game_engine.Images;
import sound_engine.PlayWave1st;

public class MyShipTest {

	private MyShip myShip;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		myShip = new MyShip(x, y);
	}

	@Test
	public void testMyShipUnit() {
		assertNotNull(myShip.loadImage(Images.MYSHIPINIT.getImg()));
		assertFalse(Images.MYSHIPINIT.getImg().isEmpty());
	}

	@Test
	public void testMyShipDamaged() {
		assertNotNull(Images.MYSHIPDAMAGED.getImg());
	}

	@Test
	public void testMyShipUpsideDown() {
		assertNotNull(Images.MYSHIPUPDOWN.getImg());
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
		assertFalse(myShip.loadMissiles().isEmpty());
		assertFalse(myShip.loadRockets().isEmpty());
	}

	@Test
	public void testGunLockedSound() {
		PlayWave1st sound = new PlayWave1st(myShip.gunLocked());
		assertTrue(sound.doesFileExists());
	}

	@Test
	public void testMyShipBorders() {
		assertFalse(myShip.getX() < 0 || myShip.getX() > 900);
		assertFalse(myShip.getY() < 0 || myShip.getY() > 700);
	}

	@After
	public void tearDown() throws Exception {
		myShip = null;
	}
}