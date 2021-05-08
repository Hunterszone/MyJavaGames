package menu_engine;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageColorizer {
	public static Image dye(Image image, Color color) {
		final int w = image.getWidth(null);
		final int h = image.getHeight(null);
		final BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = dyed.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.setComposite(AlphaComposite.SrcAtop);
		g.setColor(color);
		g.fillRect(0, 0, w, h);
		g.dispose();
		return dyed;
	}
}
