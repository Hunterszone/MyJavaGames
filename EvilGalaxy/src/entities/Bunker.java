package entities;

import java.util.ArrayList;

import game_engine.SpritePattern;
import items.BunkerBullet;

public class Bunker extends SpritePattern {

	private static final long serialVersionUID = 1L;
	public static ArrayList<BunkerBullet> bullets;
	public static ArrayList<BunkerBullet> bullets2;

	public static Bunker bunkerObj;

	public Bunker(int x, int y) {
		super(x, y);

		initBunker();
		initBullets();
	}

	private void initBullets() {

		bullets = new ArrayList<>();
		bullets2 = new ArrayList<>();

	}

	public void loadBullet() {
		bullets.add(new BunkerBullet(x + width - 40, y - 50 + height / 2));
	}

	public void loadBullet2() {
		bullets2.add(new BunkerBullet(x + width - 40, y - 50 + height / 2));
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getBullets() {
		return bullets;
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getBullets2() {
		return bullets2;
	}

	public void initBunker() {

		loadImage("images/bunker.png");
		getImageDimensions();

	}

	public void initBunkerHit() {

		loadImage("images/bunker_hit.png");
		getImageDimensions();

	}

}