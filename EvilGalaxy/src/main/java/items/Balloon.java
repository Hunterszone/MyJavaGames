package items;

import enums.Images;
import game_engine.SpritePattern;
import util.Moveable;

public class Balloon extends SpritePattern implements Moveable {

	private static final long serialVersionUID = 1L;
	private String imageName;

	public Balloon(int x, int y) {
		super(x, y);

		initBalloons();
	}

	public void initBalloons() {
		imageName = Images.TEN_POINTS.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	@Override
	public void move() {
		y -= 0.01;
	}
}