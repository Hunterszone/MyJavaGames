package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.PlasmaBall;

public class PlasmaBallTest {

	private PlasmaBall plasmaBall;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		plasmaBall = new PlasmaBall(x, y);
	}

	@Test
	public void testPlasmaBallUnit() {
		assertNotNull(plasmaBall.loadImage(plasmaBall.initEvilGun()));
		assertFalse(plasmaBall.initEvilGun().isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		plasmaBall = null;
	}
}