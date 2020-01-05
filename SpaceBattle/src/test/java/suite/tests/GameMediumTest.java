package suite.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.newdawn.slick.Font;

import game_state.GameMedium;

public class GameMediumTest {

	private Font font;
	private int x, y;

	@Test
	public void testGameMedium() {
		GameMedium gameMedium = new GameMedium(x, y, font);
		assertNotNull(gameMedium.isGameMedium());
	}

}
