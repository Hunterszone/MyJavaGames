package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import entities.TheEndAnimationUp;
import game_engine.InitObjects;

public class TheEndAnimationUpTest {

	private TheEndAnimationUp theEndAnimationUp;
	
	@Before
	public void setUp() throws Exception {
		theEndAnimationUp = new TheEndAnimationUp(TestUtils.POS_X, TestUtils.POS_Y);
	}

	@Test
	public void testTheEndAnimationUpImg() {
		assertNotNull(theEndAnimationUp.getImage());
	}
	
	@Test
	public void testListOfEndAnimationsUpExists() {
		assertNotNull(TheEndAnimationUp.theEndAnimationUpList);
	}
	
	@Test
	public void testListOfEndAnimationsUpIsNotEmpty() {
		InitObjects.initAnimations();
		assertFalse(TheEndAnimationUp.theEndAnimationUpList.isEmpty());
	}

	@Test
	public void testEndAnimationUpPosition() {
		theEndAnimationUp.cycle();
		assertNotEquals("TheEndAnimationUp is not Y-cycling", TestUtils.POS_Y, theEndAnimationUp.getY());
	}	
}