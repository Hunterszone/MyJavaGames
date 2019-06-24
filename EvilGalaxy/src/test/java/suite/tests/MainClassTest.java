package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import frames.Main;

public class MainClassTest {

//	@BeforeClass
//	public static void onceExecutedBeforeAll() {
//		System.out.println("Executing tests for class MainClassTest");
//	}

	private Main main;

	@Before
	public void setUp() {
		main = new Main();
	}

	@Test
	public void testFrameVisibility() {
		assertFalse(main.isVisible());
	}

	@Test
	public void testGameTitle() {
		assertTrue(main.getTitle().equals("EvilGalaxy"));
	}

	@Test
	public void testFrameSize() {
		assertFalse(main.isResizable());
	}

	@Test
	public void testFrameDecoration() {
		assertTrue(main.isUndecorated());
	}

	@After
	public void tearDown() {
		main = null;
	}
}
