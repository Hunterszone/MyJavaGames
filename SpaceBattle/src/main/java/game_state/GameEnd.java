package game_state;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

import entities.GameObject;
import entities.Points;

public class GameEnd extends GameObject {

	private int height;
	private int width;
	private int textWidth;
	private int textHeight;
	private Color transparent;
	private Font fontGameOver;
	private boolean isGameOver;
	private static final String GAME_OVER = "Game Over";

	public GameEnd(int height, int width, Font fontGameOver) {
		this.height = height;
		this.width = width;
		this.fontGameOver = fontGameOver;
		transparent = new Color(Color.black);
		transparent.a = 0.5f;
		if (fontGameOver != null) {
			textWidth = fontGameOver.getWidth(GAME_OVER);
			textHeight = fontGameOver.getHeight(GAME_OVER);
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(transparent);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		g.setFont(fontGameOver);
		g.drawString(GAME_OVER + "\nPoints: " + Points.points, (width / 2) - (textWidth / 2),
				(height / 2) - textHeight);
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public boolean isGameOver() {
		return isGameOver;
	}
}
