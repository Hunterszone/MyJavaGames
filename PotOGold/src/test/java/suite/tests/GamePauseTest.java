package suite.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.newdawn.slick.Font;

import states.GamePause;

public class GamePauseTest {

	private Font font;
	private int x, y;

	@Test
	public void testGamePause() {
		GamePause gamePause = new GamePause(x, y, font);
		assertNotNull(gamePause.isGamePaused());
	}

}
