package entities;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class Lives extends GameObject {

	private Font font;
	public static int lives = 3;

	public Lives(int x, int y, Font font) {
		super(x, y);
		this.font = font;
	}

	@Override
	public void draw(Graphics g) {
		if (g != null) {
			g.setFont(font);
			String numOfLives = String.format("%01d", lives);
			g.drawString("Lives: " + numOfLives, x, y);
		}
	}

	public int decrementLives(Integer live) {
		if (live != null) {
			lives--;
			live = new Integer(lives);
		}
		return live;
	}
}
