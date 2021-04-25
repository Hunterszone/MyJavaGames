package icons;

import java.awt.Image;

import enums.Images;
import game_engine.SpritePattern;

public class DragonIcon extends SpritePattern {

	public static DragonIcon dragonIcon;
	private static final long serialVersionUID = 1L;
	
	public DragonIcon(int x, int y) {
		super(x, y);
		drawIcon();
	}

	public Image drawIcon() {
		String imageName = Images.DRAGON_ICON.getImg();
		Image img = loadImage(imageName);
		getImageDimensions();
		return img;
	}
}