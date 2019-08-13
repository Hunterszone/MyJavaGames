package potogold;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class Lives extends GameObject {

	private Font font;
	static int lives = 3;

	public Lives(int x, int y, Font font) {
		super(x, y);
		this.font = font;
	}

	@Override
	public void draw(Graphics g) {
		g.setFont(font);
		String numOfLives = String.format("%01d", lives);
		g.drawString("Lives: " + numOfLives, x, y);
	}

	public int decrementLives(Integer live) {
		if (live != null) {
			lives--;
			live = new Integer(lives);
		}
		return live;
	}
}
