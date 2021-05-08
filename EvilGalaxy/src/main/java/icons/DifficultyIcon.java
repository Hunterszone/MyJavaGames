package icons;

import java.awt.Image;

import enums.Images;
import game_engine.SpritePattern;

public class DifficultyIcon extends SpritePattern {

	public static DifficultyIcon difficultyIcon;
	private static final long serialVersionUID = 1L;
	
	public DifficultyIcon(int x, int y) {
		super(x, y);
		drawIcon();
	}

	public Image drawIcon() {
		String imageName = Images.DIFF_ICON.getImg();
		Image img = loadImage(imageName);
		getImageDimensions();
		return img;
	}
}