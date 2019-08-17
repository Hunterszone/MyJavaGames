package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;

import entities.Crosshair;

public class CrosshairTest {

	private Crosshair crosshair;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		crosshair = new Crosshair(x, y);
	}

	public void testCrosshair() {
		assertNotNull(crosshair.loadImage(crosshair.initCrosshair()));
		assertNotEquals("", crosshair.initCrosshair());
	}

	@After
	public void tearDown() throws Exception {
		crosshair = null;
	}
}
