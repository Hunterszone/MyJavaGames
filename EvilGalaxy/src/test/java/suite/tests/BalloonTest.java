package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.Balloon;

public class BalloonTest {

private Balloon balloon;
	
	@Before
	public void setUp() {
		balloon = new Balloon(TestUtils.DUMMY_POS_X, TestUtils.DUMMY_POS_Y);
	}
	
	@Test
	public void testAngelImg() {
		assertNotNull(balloon.getImage());
	}
	
	@Test
	public void testAngelPosition() {
		balloon.move();
		assertNotEquals("Angel is not Y-moving!", TestUtils.DUMMY_POS_Y, balloon.getY());
	}
	
	@After
	public void tearDown() {
		balloon = null;
	}
	
}
