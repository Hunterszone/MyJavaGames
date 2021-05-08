package icons;

import java.awt.Image;

import enums.Images;
import game_engine.SpritePattern;

public class GoldIcon extends SpritePattern {

	public static GoldIcon goldIcon;
	private static final long serialVersionUID = 1L;
	
	public GoldIcon(int x, int y) {
		super(x, y);
		drawIcon();
	}

	public Image drawIcon() {
		String imageName = Images.GOLD_ICON.getImg();
		Image img = loadImage(imageName);
		getImageDimensions();
		return img;
	}
}