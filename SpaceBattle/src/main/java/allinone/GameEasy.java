package allinone;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class GameEasy extends GameObject {

	private int height;
	private int width;
	private int textWidth;
	private int textHeight;
	private Color transparent;
	private Font fontGameEasy;
	private boolean isGameEasy;
	private static final String GAME_EASY = "Easy";

	public GameEasy(int height, int width, Font fontGameEasy) {
		this.height = height;
		this.width = width;
		this.fontGameEasy = fontGameEasy;
		if (fontGameEasy != null) {
			textWidth = fontGameEasy.getWidth(GAME_EASY);
			textHeight = fontGameEasy.getHeight(GAME_EASY);
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(transparent);
		g.setFont(fontGameEasy);
		g.drawString(GAME_EASY, (width / 2) - (textWidth / 2), (height / 2) - textHeight - 290);
	}

	public void setGameEasy(boolean isGameEasy) {
		this.isGameEasy = isGameEasy;
	}

	public boolean isGameEasy() {
		return isGameEasy;
	}
}
