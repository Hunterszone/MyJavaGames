package util;

import java.awt.Font;
import java.awt.Graphics;

@FunctionalInterface
public interface Drawable {

	void draw(Graphics g);
	
	static void setFontStyle(Graphics g) {
		final Font small = new Font(Constants.FONT_PAPYRUS, Font.BOLD, Constants.FONT_SIZE);
		g.setColor(Constants.FONT_COLOR);
		g.setFont(small);
	}
}
