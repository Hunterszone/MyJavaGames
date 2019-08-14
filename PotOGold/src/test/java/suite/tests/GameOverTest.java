package suite.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.newdawn.slick.Font;

import potogold.GameOver;

public class GameOverTest {

	private Font font;
	private int x, y;

	@Test
	public void testGameOver() {
		GameOver gameOver = new GameOver(x, y, font);
		assertNotNull(gameOver.isGameOver());
	}

}
