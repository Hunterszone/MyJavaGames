package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;

import items.CanonBall;

public class CanonBallTest {

	private CanonBall canonBall;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		canonBall = new CanonBall(x, y);
	}

	public void testCanonBallUnit() {
		assertNotNull(canonBall.loadImage(canonBall.initCanon()));
		assertNotEquals("", canonBall.initCanon());
	}

	@After
	public void tearDown() throws Exception {
		canonBall = null;
	}
}