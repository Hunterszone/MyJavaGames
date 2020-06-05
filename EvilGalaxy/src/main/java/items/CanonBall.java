package items;

import game_engine.Images;
import game_engine.SpritePattern;

public class CanonBall extends SpritePattern {

	private static final long serialVersionUID = 1L;
	private final int CANON_SPEED = 8;
	private String imageName;

	public CanonBall(int x, int y) {
		super(x, y);
		initCanon();
	}

	public String initCanon() {
		imageName = Images.CANONINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public void moveCanonLeft() {
		x -= CANON_SPEED;
	}
}