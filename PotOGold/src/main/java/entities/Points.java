package entities;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class Points extends GameObject {

	private Font font;
	public static int points;

	public Points(int x, int y, Font font) {
		super(x, y);
		this.font = font;
	}

	@Override
	public void draw(Graphics g) {
		if (g != null) {
			g.setFont(font);
			String zeroDigits = String.format("%02d", points);
			g.drawString(zeroDigits, x, y);
		}
	}

	public int incrementPoints(Integer smth) {
		if (smth != null) {
			points++;
			smth = new Integer(points);
		}
		return smth;
	}
}
