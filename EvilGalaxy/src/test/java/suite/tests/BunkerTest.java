package suite.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Bunker;
import enums.Images;

public class BunkerTest {

	private Bunker bunker;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		bunker = new Bunker(x, y);
	}

	@Test
	public void testBunkerUnit() {
		String drawBunker = bunker.drawBunker();
		assertNotNull("Image file not found!", drawBunker);
		assertEquals(Images.BUNKER.getImg(), drawBunker);
	}

	@Test
	public void testBunkerUnitHit() {
		String drawBunkerHit = bunker.drawBunkerHit();
		assertNotNull("Image file not found!", drawBunkerHit);
		assertEquals(Images.BUNKERHIT.getImg(), drawBunkerHit);
	}

	@Test
	public void testListsOfBullets() {
		assertFalse(bunker.loadBullet().isEmpty());
		assertFalse(bunker.loadBullet2().isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		bunker = null;
	}
}