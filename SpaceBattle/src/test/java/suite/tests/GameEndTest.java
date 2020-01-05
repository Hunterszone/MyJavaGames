package suite.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.newdawn.slick.Font;

import game_state.GameEnd;

public class GameEndTest {

	private Font font;
	private int x, y;

	@Test
	public void testGameEnd() {
		GameEnd gameEnd = new GameEnd(x, y, font);
		assertNotNull(gameEnd.isGameOver());
	}

}
