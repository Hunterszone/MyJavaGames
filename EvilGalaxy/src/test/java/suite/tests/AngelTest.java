package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.Angel;

public class AngelTest {

private Angel angel;
	
	@Before
	public void setUp() {
		angel = new Angel(TestUtils.POS_X, TestUtils.POS_Y);
	}
	
	@Test
	public void testAngelImg() {
		assertNotNull(angel.getImage());
	}
	
	@Test
	public void testAngelPosition() {
		angel.move();
		assertNotEquals("Angel is not Y-moving!", TestUtils.POS_Y, angel.getY());
	}
	
	@After
	public void tearDown() {
		angel = null;
	}
	
}
