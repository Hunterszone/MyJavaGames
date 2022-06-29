package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.PlayerShip;
import items.ShipRocket;

public class ShipRocketTest {

private ShipRocket shipRocket;
private int x, y;
	
	@Before
	public void setUp() {
		shipRocket = new ShipRocket(TestUtils.DUMMY_POS_X, TestUtils.DUMMY_POS_Y);
	}
	
	@Test
	public void testShipRocketImg() {
		assertNotNull(shipRocket.getImage());
	}
	
	@Test
	public void testShipRocketPosition() {
		PlayerShip.playerShip = new PlayerShip(x, y);

		shipRocket.moveRocket();
		
		assertNotEquals("ShipMissile is not X-moving left!", TestUtils.DUMMY_POS_X, shipRocket.getX());
		assertNotEquals("ShipMissile is not Y-moving left!", TestUtils.DUMMY_POS_Y, shipRocket.getY());
	}
	
	@After
	public void tearDown() {
		shipRocket = null;
	}
	
}
