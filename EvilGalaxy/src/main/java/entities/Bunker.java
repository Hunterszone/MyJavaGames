package entities;

import java.util.ArrayList;
import java.util.List;

import game_engine.Images;
import game_engine.SpritePattern;
import items.BunkerBullet;

public class Bunker extends SpritePattern {

	private static final long serialVersionUID = 1L;
	public static List<BunkerBullet> bullets;
	public static List<BunkerBullet> bullets2;
	public static Bunker bunkerObj;
	private String imageName;

	public Bunker(int x, int y) {
		super(x, y);

		initBunker();
		initBullets();
	}

	private void initBullets() {
		bullets = new ArrayList<>();
		bullets2 = new ArrayList<>();
	}

	public List<BunkerBullet> loadBullet() {
		bullets.add(new BunkerBullet(x + width + 40, y - 50 + height / 2));
		return bullets;
	}

	public List<BunkerBullet> loadBullet2() {
		bullets2.add(new BunkerBullet(x - width - 40, y - 50 + height / 2));
		return bullets2;
	}

	public List<BunkerBullet> getBullets() {
		return bullets;
	}

	public List<BunkerBullet> getBullets2() {
		return bullets2;
	}

	public String initBunker() {
		imageName = Images.BUNKER.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public String initBunkerHit() {
		imageName = Images.BUNKERHIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}
}