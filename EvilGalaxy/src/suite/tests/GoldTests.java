package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import game_engine.InitObjects;
import items.Gold;

public class GoldTests {

	private Gold goldBar;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		goldBar = new Gold(x, y);
	}

	@Test
	public void testGoldUnit() {
		assertNotNull(goldBar.loadImage(goldBar.initGifts()));
		assertFalse(goldBar.initGifts().equals(""));
	}

	@Test
	public void testListOfGoldBars() {
		assertFalse(InitObjects.initGold().isEmpty());
	}

	@Test
	public void testGoldBorders() {
		assertFalse(goldBar.getY() < 0);
	}

	@After
	public void tearDown() throws Exception {
		goldBar = null;
	}
}