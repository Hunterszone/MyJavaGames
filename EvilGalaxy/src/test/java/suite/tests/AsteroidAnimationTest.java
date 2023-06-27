package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import animation.AsteroidAnimation;
import game_engine.InitObjects;

public class AsteroidAnimationTest {

	private AsteroidAnimation asteroidAnimation;
	
	@Before
	public void setUp() throws Exception {
		asteroidAnimation = new AsteroidAnimation(TestUtils.POS_X, TestUtils.POS_Y);
	}

	@Test
	public void testListOfAsteroidAnimationExists() {
		assertNotNull(AsteroidAnimation.ASTEROID_ANIMATION);
	}
	
	@Test
	public void testListOfAsteroidAnimationIsNotEmpty() {
		InitObjects.initAnimations();
		assertFalse(AsteroidAnimation.ASTEROID_ANIMATION.isEmpty());
	}

	@Test
	public void testAsteroidAnimationPosition() {
		asteroidAnimation.cycle();
		assertNotEquals("AsteroidAnimation is not X-cycling!", TestUtils.POS_X, asteroidAnimation.getX());
		assertNotEquals("AsteroidAnimation is not Y-cycling!", TestUtils.POS_Y, asteroidAnimation.getY());
	}	
}