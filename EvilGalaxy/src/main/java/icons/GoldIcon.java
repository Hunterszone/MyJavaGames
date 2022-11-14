package icons;

import java.awt.Image;

import enums.Sprite;
import game_engine.SpritePattern;

public class GoldIcon extends SpritePattern {

	public static GoldIcon goldIcon;
	private static final long serialVersionUID = 1L;
	
	public GoldIcon(int x, int y) {
		super(x, y);
		drawIcon();
	}

	public Image drawIcon() {
		String imageName = Sprite.GOLD_ICON.getImg();
		Image img = loadImage(imageName);
		getImageDimensions();
		return img;
	}
}