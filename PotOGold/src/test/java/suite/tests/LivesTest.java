package suite.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.newdawn.slick.Font;

import potogold.Lives;

public class LivesTest {

	private Font font;
	private int x, y;

	@Test
	public void testLives() {
		Lives lives = new Lives(x, y, font);
		assertNotNull(lives.decrementLives(new Integer(Lives.lives)));
	}
}
