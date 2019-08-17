package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;

import entities.MyShip;
import sound_engine.PlayWave1st;

public class MyShipTest {

	private MyShip myShip;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		myShip = new MyShip(x, y);
	}

	public void testMyShipUnit() {
		assertNotNull(myShip.loadImage(myShip.initShip()));
		assertNotEquals("", myShip.initShip());
	}

	public void testMyShipDamaged() {
		assertNotNull(myShip.loadImage(myShip.shipDamaged()));
	}

	public void testMyShipUpsideDown() {
		assertNotNull(myShip.loadImage(myShip.upsideDown()));
	}

	public void testMyShipFired() {
		assertNotNull(myShip.loadImage(myShip.shipOnFire()));
	}

	public void testMyShipEscapeForbidden() {
		assertNotNull(myShip.loadImage(myShip.escapeForbidden()));
	}

	public void testMyShipGodMode() {
		assertNotNull(myShip.loadImage(myShip.godMode()));
	}

	public void testListsOfAmmos() {
		assertFalse(myShip.loadMissiles().isEmpty());
		assertFalse(myShip.loadRockets().isEmpty());
	}

	public void testGunLockedSound() {
		PlayWave1st sound = new PlayWave1st(myShip.gunLocked());
		assertTrue(sound.doesFileExists());
	}

	public void testMyShipBorders() {
		assertFalse(myShip.getX() < 0 || myShip.getX() > 900);
		assertFalse(myShip.getY() < 0 || myShip.getY() > 700);
	}

	@After
	public void tearDown() throws Exception {
		myShip = null;
	}
}