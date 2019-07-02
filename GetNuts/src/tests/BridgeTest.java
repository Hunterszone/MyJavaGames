package tests;

import static org.junit.Assert.assertNotEquals;
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
		assertNotEquals("", bridge.img);
	}

	@After
	public void tearDown() throws Exception {
		bridge = null;
	}
}
