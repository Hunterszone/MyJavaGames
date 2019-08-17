package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;

import game_engine.InitObjects;
import items.Gold;

public class GoldTest {

	private Gold goldBar;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		goldBar = new Gold(x, y);
	}

	public void testGoldUnit() {
		assertNotNull(goldBar.loadImage(goldBar.initGifts()));
		assertNotEquals("", goldBar.initGifts());
	}

	public void testListOfGoldBars() {
		assertFalse(InitObjects.initGold().isEmpty());
	}

	public void testGoldBorders() {
		assertFalse(goldBar.getY() < 0);
	}

	@After
	public void tearDown() throws Exception {
		goldBar = null;
	}
}