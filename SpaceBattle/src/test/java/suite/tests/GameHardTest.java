package suite.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.newdawn.slick.Font;

import game_state.GameHard;

public class GameHardTest {

	private Font font;
	private int x, y;

	@Test
	public void testGameHard() {
		GameHard gameHard = new GameHard(x, y, font);
		assertNotNull(gameHard.isGameHard());
	}

}
