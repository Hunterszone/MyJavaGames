package allinone;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class Points extends GameObject {

	private Font font;
	static int points;

	public Points(int x, int y, Font font) {
		super(x, y);
		this.font = font;
	}

	@Override
	public void draw(Graphics g) {
		g.setFont(font);
		String zeroDigits = String.format("%04d", points);
		g.drawString(zeroDigits, x, y);
	}

	public void incrementPoints() {
		points++;
	}
}
