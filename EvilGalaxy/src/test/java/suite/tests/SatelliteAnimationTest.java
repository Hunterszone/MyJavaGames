package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import animation.SatelliteAnimation;

public class SatelliteAnimationTest {

	private SatelliteAnimation satelliteAnimation;
	
	@Before
	public void setUp() throws Exception {
		satelliteAnimation = new SatelliteAnimation(TestUtils.POS_X, TestUtils.POS_Y);
	}

	@Test
	public void testSatelliteAnimationExists() {
		assertNotNull(SatelliteAnimation.SATELLITE);
	}
	
	@Test
	public void testSatelliteAnimationPosition() {
		satelliteAnimation.cycle();
		assertNotEquals("SatelliteAnimation is not X-cycling!", TestUtils.POS_X, satelliteAnimation.getX());
		assertNotEquals("SatelliteAnimation is not Y-cycling!", TestUtils.POS_Y, satelliteAnimation.getY());
	}	
}