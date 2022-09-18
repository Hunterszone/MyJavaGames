package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.Gold;

public class GoldTest {

private Gold goldBar;
	
	@Before
	public void setUp() {
		goldBar = new Gold(TestUtils.POS_X, TestUtils.POS_Y);
	}
	
	@Test
	public void testGoldBarImg() {
		assertNotNull(goldBar.getImage());
	}
	
	@Test
	public void testGoldBarPosition() {
		goldBar.move();
		assertNotEquals("GoldBar is not Y-moving!", TestUtils.POS_Y, goldBar.getY());
	}
	
	@After
	public void tearDown() {
		goldBar = null;
	}
	
}
