package items;

import game_engine.SpritePattern;

public class ShipRocket extends SpritePattern {

	private static final long serialVersionUID = 1L;
	private final int BOARD_WIDTH = 390;
	private final int ROCKET_SPEED = 7;
	private final double ROCKET_SPEED2 = 2.5;

	public ShipRocket(int x, int y) {
		super(x, y);

		initRocket();
	}

	private void initRocket() {

		loadImage("images/rocket.png");
		getImageDimensions();
	}

	public void move() {

		x += ROCKET_SPEED;
		y += ROCKET_SPEED2;

		if (x > BOARD_WIDTH + 450)
			vis = false;
	}
}