package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Crosshair;

public class CrosshairTests {

	private Crosshair crosshair;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		crosshair = new Crosshair(x, y);
	}

	@Test
	public void testCrosshair() {
		assertNotNull(crosshair.loadImage(crosshair.initCrosshair()));
		assertFalse(crosshair.initCrosshair().equals(""));
	}

	@After
	public void tearDown() throws Exception {
		crosshair = null;
	}
}
