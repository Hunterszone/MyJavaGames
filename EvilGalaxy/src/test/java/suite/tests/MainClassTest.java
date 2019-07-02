package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import frames.Main;

public class MainClassTest {

	private Main main;

	@Before
	public void setUp() {
		main = new Main();
	}

	@Test(timeout = 200)
	public void testFrameVisibility() {
		assertFalse(main.isVisible());
	}

	@Test(timeout = 200)
	public void testGameTitle() {
		assertTrue(main.getTitle().equals("EvilGalaxy"));
	}

	@Test(timeout = 200)
	public void testFrameSize() {
		assertFalse(main.isResizable());
	}

	@Test(timeout = 200)
	public void testFrameDecoration() {
		assertTrue(main.isUndecorated());
	}

	@After
	public void tearDown() {
		main = null;
	}
}
