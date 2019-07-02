package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Bunker;

public class BunkerTest {

	private Bunker bunker;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		bunker = new Bunker(x, y);
	}

	@Test(timeout = 200)
	public void testBunkerUnit() {
		assertNotNull(bunker.loadImage(bunker.initBunker()));
		assertNotEquals("", bunker.initBunker());
	}

	@Test(timeout = 200)
	public void testBunkerUnitHit() {
		assertNotNull(bunker.loadImage(bunker.initBunkerHit()));
		assertFalse(bunker.initBunkerHit().equals(""));
	}

	@Test(timeout = 200)
	public void testListsOfBullets() {
		assertFalse(bunker.loadBullet().isEmpty());
		assertFalse(bunker.loadBullet2().isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		bunker = null;
	}
}