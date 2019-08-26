package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sokoban.Nut;

public class NutTest {

	private Nut nut;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		nut = new Nut(x, y);
	}

	@Test
	public void testNut() {
		assertNotNull(nut.getImage());
		assertFalse("Nut img name is empty", nut.img.isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		nut = null;
	}
}