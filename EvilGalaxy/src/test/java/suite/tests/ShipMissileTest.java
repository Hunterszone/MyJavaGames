package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.ShipMissile;

public class ShipMissileTest {

private ShipMissile shipMissile;
	
	@Before
	public void setUp() {
		shipMissile = new ShipMissile(TestUtils.SHIP_MISSILE_X, TestUtils.SHIP_MISSILE_Y);
	}
	
	@Test
	public void testShipMissileImg() {
		assertNotNull(shipMissile.getImage());
	}
	
	@Test
	public void testShipMissilePosition() {
		shipMissile.moveMissile();
		assertNotEquals("ShipMissile is not X-moving left!", TestUtils.SHIP_MISSILE_X, shipMissile.getX());
	}
	
	@After
	public void tearDown() {
		shipMissile = null;
	}
	
}
