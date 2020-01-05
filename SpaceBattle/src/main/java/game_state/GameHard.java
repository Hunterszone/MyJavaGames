package game_state;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

import entities.GameObject;

public class GameHard extends GameObject {

	private int height;
	private int width;
	private int textWidth;
	private int textHeight;
	private Color transparent;
	private Font fontGameHard;
	private boolean isGameHard;
	private static final String GAME_HARD = "Hard";

	public GameHard(int height, int width, Font fontGameHard) {
		this.height = height;
		this.width = width;
		this.fontGameHard = fontGameHard;
		if (fontGameHard != null) {
			textWidth = fontGameHard.getWidth(GAME_HARD);
			textHeight = fontGameHard.getHeight(GAME_HARD);
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(transparent);
		g.setFont(fontGameHard);
		g.drawString(GAME_HARD, (width / 2) - (textWidth / 2), (height / 2) - textHeight - 290);
	}

	public void setGameHard(boolean isGameHard) {
		this.isGameHard = isGameHard;
	}

	public boolean isGameHard() {
		return isGameHard;
	}
}
