package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.CanonBall;

public class CanonBallTest {

private CanonBall canonBall;
	
	@Before
	public void setUp() {
		canonBall = new CanonBall(TestUtils.DUMMY_POS_X, TestUtils.DUMMY_POS_Y);
	}
	
	@Test
	public void testCanonBallImg() {
		assertNotNull(canonBall.getImage());
	}
	
	@Test
	public void testCanonBallPositionLeft() {
		canonBall.moveCanonLeft();
		assertNotEquals("CanonBall is not X-moving left!", TestUtils.DUMMY_POS_X, canonBall.getX());
	}
	
	@Test
	public void testCanonBallPositionRight() {
		canonBall.moveCanonRight();
		assertNotEquals("CanonBall is not X-moving left!", TestUtils.DUMMY_POS_X, canonBall.getX());
	}
	
	@After
	public void tearDown() {
		canonBall = null;
	}
	
}
