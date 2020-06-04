package items;

import game_engine.Images;
import game_engine.SpritePattern;

public class BunkerBullet extends SpritePattern {

	// Constants
	private static final double BULLET_SPEED_X = 8;
	private static final double BULLET_SPEED_Y = 4;

	private static final long serialVersionUID = 1L;
	private String imageName;

	public BunkerBullet(int x, int y) {
		super(x, y);
		initBullet();
	}

	public String initBullet() {
		imageName = Images.BULLETINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public void moveLeft() {
		x -= BULLET_SPEED_X + 1;
	}

	public void moveRight() {
		x += BULLET_SPEED_X + 1;
	}

	public void moveDiagLeft() {
		y -= BULLET_SPEED_Y;
		x -= BULLET_SPEED_X;
	}

	public void moveDiagRight() {
		y -= BULLET_SPEED_Y;
		x += BULLET_SPEED_X;
	}

	public void moveDown() {
		y += BULLET_SPEED_Y + 1;
	}
}