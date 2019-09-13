package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.CanonBall;

public class CanonBallTest {

	private CanonBall canonBall;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		canonBall = new CanonBall(x, y);
	}

	@Test
	public void testCanonBallUnit() {
		assertNotNull(canonBall.loadImage(canonBall.initCanon()));
		assertFalse(canonBall.initCanon().isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		canonBall = null;
	}
}