package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public interface Drawable {

	public void draw(Graphics g);
	
	public static void setFontStyle(Graphics g) {
		final Font small = new Font(Constants.FONT_PAPYRUS, Font.BOLD, 22);
		g.setColor(Color.ORANGE);
		g.setFont(small);
	}
}
