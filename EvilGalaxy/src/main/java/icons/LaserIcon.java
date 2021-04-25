package icons;

import java.awt.Image;

import enums.Images;
import game_engine.SpritePattern;

public class LaserIcon extends SpritePattern {

	public static LaserIcon laserIcon;
	private static final long serialVersionUID = 1L;
	
	public LaserIcon(int x, int y) {
		super(x, y);
		drawIcon();
	}

	public Image drawIcon() {
		String imageName = Images.LASER_ICON.getImg();
		Image img = loadImage(imageName);
		getImageDimensions();
		return img;
	}
}