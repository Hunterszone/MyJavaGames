package entities;

import java.util.ArrayList;
import java.util.List;

import enums.Images;
import game_engine.SpritePattern;
import items.BunkerBullet;

public class Bunker extends SpritePattern {

	public static List<BunkerBullet> bullets, bullets2;
	public static Bunker bunkerObj;
	private static final long serialVersionUID = 1L;
	private String imageName;

	public Bunker(int x, int y) {
		super(x, y);

		drawBunker();
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

	public List<BunkerBullet> getBulletsLeft() {
		return bullets;
	}

	public List<BunkerBullet> getBulletsRight() {
		return bullets2;
	}

	public String drawBunker() {
		imageName = Images.BUNKER.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public String drawBunkerHit() {
		imageName = Images.BUNKERHIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}
}