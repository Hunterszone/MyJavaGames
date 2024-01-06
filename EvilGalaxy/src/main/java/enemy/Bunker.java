package enemy;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import enums.SoundEffect;
import enums.Sprite;
import game_engine.SpritePattern;
import items.BunkerBullet;
import items.Explosion;
import sound_engine.PlayWave1st;
import util.Intersectable;
import util.KilledByChecker;
import util.LivesAndCounts;

public class Bunker extends SpritePattern implements Intersectable {

	public static Bunker bunkerObj;
	private static final long serialVersionUID = 1L;
	
	public Bunker(int x, int y) {
		super(x, y);

		drawBunker();
		initBullets();
	}

	private void initBullets() {
		BunkerBullet.bulletsLeft = new ArrayList<>();
		BunkerBullet.bulletsRight = new ArrayList<>();
	}

	public List<BunkerBullet> loadBulletsLeft() {
		BunkerBullet.bulletsLeft.add(new BunkerBullet(x + width + 40, y - 50 + height / 2));
		return BunkerBullet.bulletsLeft;
	}

	public List<BunkerBullet> loadBulletsRight() {
		BunkerBullet.bulletsRight.add(new BunkerBullet(x - width - 40, y - 50 + height / 2));
		return BunkerBullet.bulletsRight;
	}

	public List<BunkerBullet> getBulletsLeft() {
		return BunkerBullet.bulletsLeft;
	}

	public List<BunkerBullet> getBulletsRight() {
		return BunkerBullet.bulletsRight;
	}

	public void drawBunker() {
		String imageName = Sprite.BUNKER.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	public void drawBunkerHit() {
		String imageName = Sprite.BUNKER_HIT.getImg();
		assert imageName != null; 
		loadImage(imageName);
		getImageDimensions();
	}

	@Override
	public void intersects(Shape entity) {
		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && LivesAndCounts.getLifeBunker() < 50) {
			if (entity.intersects(this.getBounds())) {
//			FrameUtils.vibrate(MouseInputHandler.main);
				Explosion.explosions.add(new Explosion(((Rectangle) entity).getX(), ((Rectangle) entity).getY(), 30, 30));
				new PlayWave1st(SoundEffect.SCREAM.getSound()).start();
				new PlayWave1st(SoundEffect.EXPLOSION.getSound()).start();
				LivesAndCounts.setLifePlayerShip(0);
				KilledByChecker.checkKilledByBunker();
			}
		}
	}
}