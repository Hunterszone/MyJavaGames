package enemy;

import java.util.ArrayList;
import java.util.List;

import enums.Sprite;
import game_engine.SpritePattern;
import items.BunkerBullet;

public class Bunker extends SpritePattern {

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
}