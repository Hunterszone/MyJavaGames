package suite.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.newdawn.slick.Font;

import game_state.GameEasy;

public class GameEasyTest {

	private Font font;
	private int x, y;

	@Test
	public void testGameEasy() {
		GameEasy gameEasy = new GameEasy(x, y, font);
		assertNotNull(gameEasy.isGameEasy());
	}

}
