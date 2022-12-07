package items;

import java.util.List;

import enemy.PlayerShip;
import enums.Sprite;
import game_engine.SpritePattern;

public class ShipRocket extends SpritePattern {

	public static List<ShipRocket> rockets;

	private static final long serialVersionUID = 1L;
	private final double ROCKET_SPEED_X = 7;
	private final double ROCKET_SPEED_Y = 2.5;
	private String imageName;

	public ShipRocket(int x, int y) {
		super(x, y);
		initRocket();
	}

	public void initRocket() {
		imageName = Sprite.ROCKET_INIT.getImg();
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