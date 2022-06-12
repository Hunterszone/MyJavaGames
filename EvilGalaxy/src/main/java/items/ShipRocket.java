package items;

import entities.PlayerShip;
import enums.Images;
import game_engine.SpritePattern;

public class ShipRocket extends SpritePattern {

	private static final long serialVersionUID = 1L;
	private final double ROCKET_SPEED_X = 7;
	private final double ROCKET_SPEED_Y = 2.5;
	private String imageName;

	public ShipRocket(int x, int y) {
		super(x, y);
		initRocket();
	}

	public void initRocket() {
		imageName = Images.ROCKETINIT.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	public void moveRocket() {

		x += ROCKET_SPEED_X;
		y += ROCKET_SPEED_Y;

		if (x > PlayerShip.playerShip.getX() + 320)
			vis = false;
	}
}