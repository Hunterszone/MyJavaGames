package utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageDrawer {
	public static Image dye(Image image) {
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = dyed.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.setComposite(AlphaComposite.SrcAtop);
		g.fillRect(0, 0, w, h);
		g.dispose();
		return dyed;
	}
}
