package suite.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import enums.Images;

public class ItemsTest {

	@Test
	public void testBunkerBulletUnit() {
		assertNotNull(Images.BULLETINIT.getImg());
	}
	
	@Test
	public void testPlasmaBallUnit() {
		assertNotNull(Images.PLASMABALLINIT.getImg());
	}
	
	@Test
	public void testCanonBallUnit() {
		assertNotNull(Images.CANONINIT.getImg());
	}
	
	@Test
	public void testCrosshairUnit() {
		assertNotNull(Images.CROSSHAIR.getImg());
	}
	
	@Test
	public void testGoldUnit() {
		assertNotNull(Images.GOLDINIT.getImg());
	}
	
	@Test
	public void testHealthUnit() {
		assertNotNull(Images.HEALTHINIT.getImg());
	}
	
	@Test
	public void testSaveSignUnit() {
		assertNotNull(Images.SAVESIGNINIT.getImg());
	}
	
	@Test
	public void testMissileUnit() {
		assertNotNull(Images.MISSILEINIT.getImg());
	}
	
	@Test
	public void testRocketUnit() {
		assertNotNull(Images.ROCKETINIT.getImg());
	}
}