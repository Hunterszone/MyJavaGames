package menu_engine;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageColorizer {
	public static Image dye(Image image, Color color) {
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = dyed.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.setComposite(AlphaComposite.SrcAtop);
		g.setColor(color);
		g.fillRect(0, 0, w, h);
		g.dispose();
		return dyed;
	}
}
