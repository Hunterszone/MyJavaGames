package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import animation.TheEndAnimationDown;
import animation.TheEndAnimationUp;
import game_engine.InitObjects;

public class TheEndAnimationDownTest {

	private TheEndAnimationDown theEndAnimationDown;
	
	@Before
	public void setUp() throws Exception {
		theEndAnimationDown = new TheEndAnimationDown(TestUtils.POS_X, TestUtils.POS_Y);
	}

	@Test
	public void testTheEndAnimationUpImg() {
		assertNotNull(theEndAnimationDown.getImage());
	}
	
	@Test
	public void testListOfEndAnimationsDownExists() {
		assertNotNull(TheEndAnimationDown.END_ANIMATION_DOWN);
	}
	
	@Test
	public void testListOfEndAnimationsDownIsNotEmpty() {
		InitObjects.initAnimations();
		assertFalse(TheEndAnimationUp.END_ANIMATION_UP.isEmpty());
	}

	@Test
	public void testTheEndAnimationDownPosition() {
		theEndAnimationDown.cycle();
		assertNotEquals("TheEndAnimationDown is not Y-cycling", TestUtils.POS_Y, theEndAnimationDown.getY());
	}	
}