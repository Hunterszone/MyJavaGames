package enemy;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enums.SoundEffect;
import enums.Sprite;
import game_engine.SpritePattern;
import items.CanonBall;
import items.Explosion;
import sound_engine.PlayWave1st;
import util.Constants;
import util.Intersectable;
import util.KilledByChecker;
import util.LivesAndCounts;
import util.Moveable;

public class Alien extends SpritePattern implements Moveable, Intersectable {

	public static List<Alien> aliens;

	private static final double FASTER_SPEED_X = 0.1;
	private static final double SPEED_Y = 1.1;
	private static final double SPEED_X = 3.1;
	private static final long serialVersionUID = 1L;
	private static final int INITIAL_X = 1024;
	private static final int INITIAL_Y = 0;
	
	public Alien(int x, int y) {
		super(x, y);
		drawAlien();
	}

	public void drawAlien() {
		String imageName = Sprite.ALIEN.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	@Override
	public void move() {

		if (x < 0) {
			x = INITIAL_X;
		}

		if (y > Constants.B_HEIGHT) {
			y = INITIAL_Y;
		}

		x -= SPEED_X;
		y += SPEED_Y;
	}

	public void moveFaster() {

		if (x < 0) {
			x = INITIAL_X;
		}

		x -= FASTER_SPEED_X;
	}

	public void loadCanon() {
		CanonBall.canonBalls = new ArrayList<CanonBall>(Arrays.asList(new CanonBall(x + width, y + height / 2)));
		if (!Alien.aliens.isEmpty()) new PlayWave1st(SoundEffect.BOING.getSound()).start();
	}

	@Override
	public void intersects(Shape entity) {
		if (entity.intersects(this.getBounds())) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			this.setVisible(false);
			Explosion.explosions.add(new Explosion(((Rectangle) entity).getX(), ((Rectangle) entity).getY(), 30, 30));
			new PlayWave1st(SoundEffect.SCREAM.getSound()).start();
//			PlayerShip.playerOne.upsideDown();
			LivesAndCounts.decreasePlayerShipLife(1);
			LivesAndCounts.increaseAlienKilledCount(1);
			KilledByChecker.checkKilledByAlien();
		}
	}
}