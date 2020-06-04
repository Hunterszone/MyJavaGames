package items;

import entities.MyShip;
import game_engine.Images;
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

	public String initRocket() {
		imageName = Images.ROCKETINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public void moveRocket() {

		x += ROCKET_SPEED_X;
		y += ROCKET_SPEED_Y;

		if (x > MyShip.myShip.getX() + 320)
			vis = false;
	}
}