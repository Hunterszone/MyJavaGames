package items;

import java.util.List;

import enums.Images;
import game_engine.SpritePattern;
import util.Moveable;

public class Gold extends SpritePattern implements Moveable {

	public static List<Gold> goldstack;
	private static final long serialVersionUID = 1L;
	private final int INITIAL_Y = 1200;
	private String imageName;

	public Gold(int x, int y) {
		super(x, y);

		initGifts();
	}

	public void initGifts() {
		imageName = Images.GOLD_INIT.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	@Override
	public void move() {

		if (y < 0) {
			y = INITIAL_Y;
		}

		y -= 1;

	}
}