package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.HealthPack;

public class HealthPackTest {

private HealthPack healthPack;
	
	@Before
	public void setUp() {
		healthPack = new HealthPack(TestUtils.HEALTH_PACK_X, TestUtils.HEALTH_PACK_Y);
	}
	
	@Test
	public void testHealthPackImg() {
		assertNotNull(healthPack.getImage());
	}
	
	@Test
	public void testHealthPackPosition() {
		healthPack.move();
		assertNotEquals("HealthPack is not Y-moving left!", TestUtils.HEALTH_PACK_Y, healthPack.getY());
	}
	
	@After
	public void tearDown() {
		healthPack = null;
	}
	
}
