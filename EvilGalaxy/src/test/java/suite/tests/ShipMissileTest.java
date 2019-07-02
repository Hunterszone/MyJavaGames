package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.ShipMissile;

public class ShipMissileTest {

	private ShipMissile shipMissile;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		shipMissile = new ShipMissile(x, y);
	}

	@Test(timeout = 200)
	public void testShipMissileUnit() {
		assertNotNull(shipMissile.loadImage(shipMissile.initMissile()));
		assertNotEquals("", shipMissile.initMissile());
	}

	@After
	public void tearDown() throws Exception {
		shipMissile = null;
	}
}