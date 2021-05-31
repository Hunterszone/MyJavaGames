package items;

import entities.PlayerShip;
import enums.Images;
import game_engine.SpritePattern;

public class ShipMissile extends SpritePattern {

	private static final long serialVersionUID = 1L;
	private final double MISSILE_SPEED = 50;
	private String imageName;

	public ShipMissile(int x, int y) {
		super(x, y);
		initMissile();
	}

	public void initMissile() {
		imageName = Images.MISSILEINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
	}

	public void moveMissile() {

		x += MISSILE_SPEED;

		if (x > PlayerShip.playerOne.getX() + 550)
			vis = false;
	}
}