package items;

import java.util.List;

import enums.Images;
import game_engine.SpritePattern;
import util.Moveable;

public class HealthPack extends SpritePattern implements Moveable {

	public static List<HealthPack> healthpacks;
	private static final long serialVersionUID = 1L;
	private final int INITIAL_Y = 0;
	private String imageName;

	public HealthPack(int x, int y) {
		super(x, y);
		initHealth();
	}

	public void initHealth() {
		imageName = Images.HEALTH_INIT.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	@Override
	public void move() {

		if (y > 1200) {
			y = INITIAL_Y;
		}

		y += 5;

	}
}