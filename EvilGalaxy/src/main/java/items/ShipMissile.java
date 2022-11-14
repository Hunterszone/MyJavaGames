package items;

import java.util.List;

import entities.PlayerShip;
import enums.Sprite;
import game_engine.SpritePattern;

public class ShipMissile extends SpritePattern {
	
	public static List<ShipMissile> missiles;

	private static final long serialVersionUID = 1L;
	private final double MISSILE_SPEED = 50;
	private String imageName;

	public ShipMissile(int x, int y) {
		super(x, y);
		initMissile();
	}

	public void initMissile() {
		imageName = Sprite.MISSILE_INIT.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	public void moveMissile() {

		x += MISSILE_SPEED;

		if (x > PlayerShip.playerShip.getX() + 550)
			vis = false;
	}
}