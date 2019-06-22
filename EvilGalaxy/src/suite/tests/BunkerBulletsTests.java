package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.BunkerBullet;

public class BunkerBulletsTests {

	private BunkerBullet bunkerBullet;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		bunkerBullet = new BunkerBullet(x, y);
	}

	@Test
	public void testBunkerBulletUnit() {
		assertNotNull(bunkerBullet.loadImage(bunkerBullet.initBullet()));
		assertFalse(bunkerBullet.initBullet().equals(""));
	}

	@After
	public void tearDown() throws Exception {
		bunkerBullet = null;
	}
}