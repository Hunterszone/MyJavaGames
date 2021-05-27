package items;

import enums.Images;
import game_engine.SpritePattern;

public class PlasmaBall extends SpritePattern {

	private static final long serialVersionUID = 1L;
	private final int EVILGUN_SPEED_X = 9;
	private final double EVILGUN_SPEED_Y = 2.5;
	private String imageName;

	public PlasmaBall(int x, int y) {
		super(x, y);

		initEvilGun();
	}

	public void initEvilGun() {
		imageName = Images.PLASMABALLINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
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