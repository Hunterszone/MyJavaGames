package items;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.List;
import java.util.Objects;

import enemy.Alien;
import enums.SoundEffect;
import enums.Sprite;
import game_engine.SpritePattern;
import sound_engine.PlayWave1st;
import util.Constants;
import util.Intersectable;
import util.KilledByChecker;
import util.LivesAndCounts;
import util.Moveable;

public class CanonBall extends SpritePattern implements Moveable, Intersectable {
	
	public static List<CanonBall> canonBalls;

	private static final long serialVersionUID = 1L;
	private final int CANON_SPEED = 8;
	private String imageName;

	public CanonBall(int x, int y) {
		super(x, y);
		initCanon();
	}

	private void initCanon() {
		imageName = Objects.requireNonNull(Sprite.CANON_INIT.getImg());
		loadImage(imageName);
		getImageDimensions();
	}

	@Override
	public void move() {
		x -= CANON_SPEED;
		if(x < Constants.B_WIDTH - 1280) {
			this.setVisible(false);
		}
	}
	
	@Override
	public void intersects(Shape entity) {
		if ((!Alien.aliens.isEmpty() || LivesAndCounts.getLifeBunker() >= 50) 
				&& this.getBounds().intersects((Rectangle) entity)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			LivesAndCounts.decreasePlayerShipLife(1);
			this.setVisible(false);
			Explosion.explosions.add(new Explosion(((Rectangle) entity).getX(), ((Rectangle) entity).getY(), 30, 30));
			new PlayWave1st(SoundEffect.BURNED.getSound()).start();
//			PlayerShip.playerOne.upsideDown();
			if (LivesAndCounts.getLifePlayerShip() == 0) {
				if (!Alien.aliens.isEmpty()) {
					KilledByChecker.checkKilledByAlien();
				} else {
					KilledByChecker.checkKilledByEvilHead();
				}
			}
		}
	}
}