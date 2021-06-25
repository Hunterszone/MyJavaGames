package icons;

import java.awt.Image;

import enums.Images;
import game_engine.SpritePattern;

public class AlienIcon extends SpritePattern {

	public static AlienIcon alienIcon;
	private static final long serialVersionUID = 1L;
	
	public AlienIcon(int x, int y) {
		super(x, y);
		drawIcon();
	}

	public Image drawIcon() {
		String imageName = Images.ALIEN_ICON.getImg();
		Image img = loadImage(imageName);
		getImageDimensions();
		return img;
	}
}