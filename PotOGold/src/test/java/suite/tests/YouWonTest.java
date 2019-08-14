package suite.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.newdawn.slick.Font;

import potogold.YouWon;

public class YouWonTest {

	private Font font;
	private int x, y;

	@Test
	public void testYouWon() {
		YouWon youWon = new YouWon(x, y, font);
		assertNotNull(youWon.isGameWon());
	}
}
