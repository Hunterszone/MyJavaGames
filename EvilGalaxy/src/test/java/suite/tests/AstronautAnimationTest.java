package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import animation.AstronautAnimation;

public class AstronautAnimationTest {

	private AstronautAnimation astronautAnimation;
	
	@Before
	public void setUp() throws Exception {
		astronautAnimation = new AstronautAnimation(TestUtils.POS_X, TestUtils.POS_Y);
	}

	@Test
	public void testAstronautAnimationExists() {
		assertNotNull(AstronautAnimation.ASTRONAUT);
	}
	
	@Test
	public void testAstronautAnimationPosition() {
		astronautAnimation.cycle();
		assertNotEquals("AstronautAnimation is not X-cycling!", TestUtils.POS_X, astronautAnimation.getX());
		assertNotEquals("AstronautAnimation is not Y-cycling!", TestUtils.POS_Y, astronautAnimation.getY());
	}	
}