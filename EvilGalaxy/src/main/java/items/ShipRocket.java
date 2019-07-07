package items;

import game_engine.Images;
import game_engine.SpritePattern;

public class ShipRocket extends SpritePattern {

	private static final long serialVersionUID = 1L;
	private final int BOARD_WIDTH = 390;
	private final int ROCKET_SPEED = 7;
	private final double ROCKET_SPEED2 = 2.5;
	private String imageName;

	public ShipRocket(int x, int y) {
		super(x, y);
		initRocket();
	}

	public String initRocket() {
		imageName = Images.ROCKETINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public void move() {

		x += ROCKET_SPEED;
		y += ROCKET_SPEED2;

		if (x > BOARD_WIDTH + 450)
			vis = false;
	}
}