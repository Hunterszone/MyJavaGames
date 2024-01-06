package items;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.List;
import java.util.Objects;

import enums.SoundEffect;
import enums.Sprite;
import game_engine.SpritePattern;
import sound_engine.PlayWave1st;
import util.Intersectable;
import util.KilledByChecker;
import util.LivesAndCounts;

public class BunkerBullet extends SpritePattern implements Intersectable {

	// Constants
	public static List<BunkerBullet> bulletsLeft, bulletsRight;
	
	private static final double BULLET_SPEED_X = 8;
	private static final double BULLET_SPEED_Y = 4;

	private static final long serialVersionUID = 1L;
	private String imageName;

	public BunkerBullet(int x, int y) {
		super(x, y);
		initBullet();
	}

	public void initBullet() {
		imageName = Objects.requireNonNull(Sprite.BULLET_INIT.getImg());
		loadImage(imageName);
		getImageDimensions();
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

	@Override
	public void intersects(Shape entity) {
		if (this.getBounds().intersects((Rectangle) entity)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			LivesAndCounts.decreasePlayerShipLife(1);
			this.setVisible(false);
			Explosion.explosions.add(new Explosion(((Rectangle) entity).getX(), ((Rectangle) entity).getY(), 30, 30));
			new PlayWave1st(SoundEffect.SCREAM.getSound()).start();
			new PlayWave1st(SoundEffect.EXPLOSION.getSound()).start();
//			PlayerShip.playerOne.upsideDown();
			KilledByChecker.checkKilledByBunker();
		}
	}
}