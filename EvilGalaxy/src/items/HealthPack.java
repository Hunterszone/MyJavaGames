package items;

import java.util.List;

import game_engine.SpritePattern;

public class HealthPack extends SpritePattern {

	private static final long serialVersionUID = 1L;
	public static List<HealthPack> healthpack;
	private final int INITIAL_Y = 0;

	public HealthPack(int x, int y) {
		super(x, y);

		initHealth();
	}

	private void initHealth() {

		loadImage("images/health.png");
		getImageDimensions();
	}

	public void move() {

		if (y > 1200) {
			y = INITIAL_Y;
		}

		y += 5;

	}
}