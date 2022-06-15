package items;

import java.util.List;

import enums.Images;
import game_engine.SpritePattern;

public class PlasmaBall extends SpritePattern {

	public static List<PlasmaBall> plasmaBalls;
	
	private static final long serialVersionUID = 1L;
	private final int EVILGUN_SPEED_X = 9;
	private final double EVILGUN_SPEED_Y = 2.5;
	private String imageName;

	public PlasmaBall(int x, int y) {
		super(x, y);

		initEvilGun();
	}

	public void initEvilGun() {
		imageName = Images.PLASMABALL_INIT.getImg();
		assert imageName != null;
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