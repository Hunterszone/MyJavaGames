package items;

import game_engine.SpritePattern;

public class ShipMissile extends SpritePattern {

	private static final long serialVersionUID = 1L;
	private final int BOARD_WIDTH = 390;
	private final int MISSILE_SPEED = 50;

	public ShipMissile(int x, int y) {
		super(x, y);

		initMissile();
	}

	private void initMissile() {

		loadImage("images/missile.png");
		getImageDimensions();
	}

	public void move() {

		x += MISSILE_SPEED;

		if (x > BOARD_WIDTH + 350)
			vis = false;
	}
}