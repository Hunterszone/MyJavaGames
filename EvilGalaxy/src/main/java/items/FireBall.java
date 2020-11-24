package items;

import enums.Images;
import game_engine.SpritePattern;

public class FireBall extends SpritePattern {

	private static final long serialVersionUID = 1L;
	private final int EVILGUN_SPEED_X = 9;
	private final double EVILGUN_SPEED_Y = 2.5;
	private String imageName;

	public FireBall(int x, int y) {
		super(x, y);

		initEvilGun();
	}

	public String initEvilGun() {
		imageName = Images.FIREBALLINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public void evilShot() {

		x -= EVILGUN_SPEED_X;

	}

	public void evilShotDiagUp() {

		x -= EVILGUN_SPEED_X;
		y -= EVILGUN_SPEED_Y;

	}

	public void evilShotDiagDown() {

		x -= EVILGUN_SPEED_X;
		y += EVILGUN_SPEED_Y;

	}

}