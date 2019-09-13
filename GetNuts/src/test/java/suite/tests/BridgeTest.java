package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sokoban.Bridge;

public class BridgeTest {

	private Bridge bridge;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		bridge = new Bridge(x, y);
	}

	@Test
	public void testBridge() {
		assertNotNull(bridge.getImage());
		assertFalse("Bridge img name is empty", bridge.img.isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		bridge = null;
	}
}