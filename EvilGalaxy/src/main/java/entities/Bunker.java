package entities;

import java.util.ArrayList;
import java.util.List;

import enums.Images;
import game_engine.SpritePattern;
import items.BunkerBullet;

public class Bunker extends SpritePattern {

	public static List<BunkerBullet> bulletsLeft, bulletsRight;
	public static Bunker bunkerObj;
	private static final long serialVersionUID = 1L;
	private String imageName;

	public Bunker(int x, int y) {
		super(x, y);

		drawBunker();
		initBullets();
	}

	private void initBullets() {
		bulletsLeft = new ArrayList<>();
		bulletsRight = new ArrayList<>();
	}

	public List<BunkerBullet> loadBulletsLeft() {
		bulletsLeft.add(new BunkerBullet(x + width + 40, y - 50 + height / 2));
		return bulletsLeft;
	}

	public List<BunkerBullet> loadBulletsRight() {
		bulletsRight.add(new BunkerBullet(x - width - 40, y - 50 + height / 2));
		return bulletsRight;
	}

	public List<BunkerBullet> getBulletsLeft() {
		return bulletsLeft;
	}

	public List<BunkerBullet> getBulletsRight() {
		return bulletsRight;
	}

	public void drawBunker() {
		imageName = Images.BUNKER.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	public void drawBunkerHit() {
		imageName = Images.BUNKERHIT.getImg();
		assert imageName != null; 
		loadImage(imageName);
		getImageDimensions();
	}
}