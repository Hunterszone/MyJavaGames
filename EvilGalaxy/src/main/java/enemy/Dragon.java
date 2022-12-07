package enemy;

import java.util.List;

import enums.Sprite;
import game_engine.SpritePattern;
import util.Moveable;

public class Dragon extends SpritePattern implements Moveable {

	private static final long serialVersionUID = 1L;
	public static List<Dragon> dragons;
	private final int INITIAL_X = (int) Math.ceil(Math.random() * 6500);
	private String imageName;

	public Dragon(int x, int y) {
		super(x, y);

		drawDragon();
	}

	public void drawDragon() {
		imageName = Sprite.DRAGON.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	@Override
	public void move() {

		if (x < 0) {
			x = INITIAL_X;
		}

		x -= 2;
	}
}