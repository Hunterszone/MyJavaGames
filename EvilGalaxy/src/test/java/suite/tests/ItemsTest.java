package suite.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import enums.Images;

public class ItemsTest {

	@Test
	public void testBunkerBulletUnit() {
		assertNotNull(Images.BULLET_INIT.getImg());
	}
	
	@Test
	public void testPlasmaBallUnit() {
		assertNotNull(Images.PLASMABALL_INIT.getImg());
	}
	
	@Test
	public void testCanonBallUnit() {
		assertNotNull(Images.CANON_INIT.getImg());
	}
	
	@Test
	public void testCrosshairUnit() {
		assertNotNull(Images.CROSSHAIR.getImg());
	}
	
	@Test
	public void testGoldUnit() {
		assertNotNull(Images.GOLD_INIT.getImg());
	}
	
	@Test
	public void testHealthUnit() {
		assertNotNull(Images.HEALTH_INIT.getImg());
	}
	
	@Test
	public void testSaveSignUnit() {
		assertNotNull(Images.SAVESIGN_INIT.getImg());
	}
	
	@Test
	public void testMissileUnit() {
		assertNotNull(Images.MISSILE_INIT.getImg());
	}
	
	@Test
	public void testRocketUnit() {
		assertNotNull(Images.ROCKET_INIT.getImg());
	}
}