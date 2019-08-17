package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;

import items.FireBall;

public class FireBallTest {

	private FireBall fireBall;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		fireBall = new FireBall(x, y);
	}

	public void testFireBallUnit() {
		assertNotNull(fireBall.loadImage(fireBall.initEvilGun()));
		assertNotEquals("", fireBall.initEvilGun());
	}

	@After
	public void tearDown() throws Exception {
		fireBall = null;
	}
}