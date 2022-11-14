package icons;

import java.awt.Image;

import enums.Sprite;
import game_engine.SpritePattern;

public class RocketIcon extends SpritePattern {

	public static RocketIcon rocketIcon;
	private static final long serialVersionUID = 1L;
	
	public RocketIcon(int x, int y) {
		super(x, y);
		drawIcon();
	}

	public Image drawIcon() {
		String imageName = Sprite.ROCKET_ICON.getImg();
		Image img = loadImage(imageName);
		getImageDimensions();
		return img;
	}
}