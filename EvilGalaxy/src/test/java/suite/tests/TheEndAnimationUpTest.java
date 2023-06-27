package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import animation.TheEndAnimationUp;
import game_engine.InitObjects;

public class TheEndAnimationUpTest {

	private TheEndAnimationUp theEndAnimationUp;
	
	@Before
	public void setUp() throws Exception {
		theEndAnimationUp = new TheEndAnimationUp(TestUtils.POS_X, TestUtils.POS_Y);
	}

	@Test
	public void testListOfEndAnimationsUpExists() {
		assertNotNull(TheEndAnimationUp.END_ANIMATION_UP);
	}
	
	@Test
	public void testListOfEndAnimationsUpIsNotEmpty() {
		InitObjects.initAnimations();
		assertFalse(TheEndAnimationUp.END_ANIMATION_UP.isEmpty());
	}

	@Test
	public void testEndAnimationUpPosition() {
		theEndAnimationUp.cycle();
		assertNotEquals("TheEndAnimationUp is not Y-cycling", TestUtils.POS_Y, theEndAnimationUp.getY());
	}	
}