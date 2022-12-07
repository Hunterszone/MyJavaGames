package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import enemy.Bunker;

public class BunkerTest {

	private Bunker bunker;
	private int x, y;
	
	@Before
	public void setUp() throws Exception {
		bunker = new Bunker(x, y);
	}

	@Test
	public void testListOfBulletsLeft() {
		assertNotNull(bunker.loadBulletsLeft());
		assertFalse(bunker.loadBulletsLeft().isEmpty());
	}
	
	@Test
	public void testListOfBulletsTwo() {
		assertNotNull(bunker.loadBulletsRight());
		assertFalse(bunker.loadBulletsRight().isEmpty());
	}

}