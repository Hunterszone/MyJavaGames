package items;

import enums.Sprite;
import game_engine.SpritePattern;
import util.Moveable;

public class Angel extends SpritePattern implements Moveable {

	private static final long serialVersionUID = 1L;
	private String imageName;

	public Angel(int x, int y) {
		super(x, y);

		initAngels();
	}

	public void initAngels() {
		imageName = Sprite.ANGEL.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	@Override
	public void move() {
		y -= 0.01;
	}
}