package items;

import java.util.List;

import game_engine.Images;
import game_engine.SpritePattern;

public class Gold extends SpritePattern {

	public static List<Gold> goldstack;
	private static final long serialVersionUID = 1L;
	private final int INITIAL_Y = 1200;
	private String imageName;

	public Gold(int x, int y) {
		super(x, y);

		initGifts();
	}

	public String initGifts() {
		imageName = Images.GOLDINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public void move() {

		if (y < 0) {
			y = INITIAL_Y;
		}

		y -= 1;

	}
}