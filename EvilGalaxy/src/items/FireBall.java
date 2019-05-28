package items;

import game_engine.SpritePattern;

public class FireBall extends SpritePattern {

	private static final long serialVersionUID = 1L;
	private final int EVILGUN_SPEED = 9;
	private final double EVILGUN_SPEED_2 = 2.5;

	public FireBall(int x, int y) {
		super(x, y);

		initEvilGun();
	}

	private void initEvilGun() {

		loadImage("images/fireball.png");
		getImageDimensions();
	}

	public void evilShot() {

		x -= EVILGUN_SPEED;

	}

	public void evilShotDiagUp() {

		x -= EVILGUN_SPEED;
		y -= EVILGUN_SPEED_2;

	}

	public void evilShotDiagDown() {

		x -= EVILGUN_SPEED;
		y += EVILGUN_SPEED_2;

	}

}