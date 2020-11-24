package items;

import java.util.List;

import enums.Images;
import game_engine.SpritePattern;

public class HealthPack extends SpritePattern {

	public static List<HealthPack> healthpack;
	private static final long serialVersionUID = 1L;
	private final int INITIAL_Y = 0;
	private String imageName;

	public HealthPack(int x, int y) {
		super(x, y);
		initHealth();
	}

	public String initHealth() {
		imageName = Images.HEALTHINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public void move() {

		if (y > 1200) {
			y = INITIAL_Y;
		}

		y += 5;

	}
}