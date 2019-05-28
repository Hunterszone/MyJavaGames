package items;

import java.util.List;

import game_engine.SpritePattern;

public class Gold extends SpritePattern {

	private static final long serialVersionUID = 1L;
	public static List<Gold> goldstack;
	private final int INITIAL_Y = 1200;

	public Gold(int x, int y) {
		super(x, y);

		initGifts();
	}

	private void initGifts() {

		loadImage("images/gold.png");
		getImageDimensions();
	}

	public void move() {

		if (y < 0) {
			y = INITIAL_Y;
		}

		y -= 1;

	}
}