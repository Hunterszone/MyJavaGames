package entities;

import java.util.ArrayList;

import game_engine.SpritePattern;
import items.CanonBall;
import items.FireBall;
import sound_engine.PlayWave1st;

public class EvilHead extends SpritePattern {

	private static final long serialVersionUID = 1L;
	public static EvilHead evilHead;
	private double speedX;
	private double speedY;
	private ArrayList<FireBall> fireballs;
	private ArrayList<CanonBall> canons;

	public EvilHead(int x, int y) {
		super(x, y);

		initEnemy();
		initAmmo();
	}

	private void initEnemy() {

		loadImage("images/evilhead.png");
		getImageDimensions();
	}

	private void initAmmo() {

		fireballs = new ArrayList<>();
		canons = new ArrayList<>();

	}

	// Simulated Artificial Intelligence on EASY
	public void AIOnEasy() {

		x += speedX;
		y += speedY;

		if (x < 1) {
			x = 1;

		}

		if (y < 1) {
			y = 1;
		}

		x -= 1;

		if (x < 500) {
			speedX += 1.2;
			initEnemy();

		}

		y -= 1;
		if (y == 0) {
			x += 1.2;
			initEnemy();

		}

		if (x > 800) {

			speedX -= 1.2;
			speedY += 1.2;
			initEnemy();

		}

		if (y > 500) {
			speedY -= 1;
			initEnemy();
		}

	}

	// Simulated Artificial Intelligence on MEDIUM
	public void AIOnMedium() {

		x += speedX;
		y += speedY;

		if (x < 1) {
			x = 1;

		}

		if (y < 1) {
			y = 1;
		}

		x -= 1;
		if (y == 250 || y == 350 || y == 450) {
			throwCanons();
			strikeHead();
		}

		if (x < 500) {
			speedX += 1.2;
			initEnemy();

		}

		y -= 1;
		if (y == 0) {
			x += 1.2;
			initEnemy();

		}

		if (x > 800) {

			speedX -= 1.2;
			speedY += 1.2;
			initEnemy();

		}

		if (y > 500) {
			speedY -= 1;
			initEnemy();
		}

	}

	// Simulated Artificial Intelligence on HARD
	public void AIOnHard() {

		x += speedX;
		y += speedY;

		if (x < 1) {
			x = 1;

		}

		if (y < 1) {
			y = 1;
		}

		x -= 1;
		if (y == 250 || y == 350 || y == 450 || y == 550 || y == 650) {
			throwCanons();
			strikeHead();
		}

		if (x < 500) {
			speedX += 1.2;
			initEnemy();

		}

		y -= 1;
		if (y == 0) {
			x += 1.2;
			initEnemy();

		}

		if (x > 800) {

			speedX -= 1.2;
			speedY += 1.2;
			initEnemy();

		}

		if (y > 500) {
			speedY -= 1;
			initEnemy();
		}

	}

	@SuppressWarnings("rawtypes")
	public ArrayList getEvilMissiles() {
		return fireballs;
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getCanons() {
		return canons;
	}

	public void throwFireballs() {
		fireballs.add(new FireBall(x + width, y + height / 2));
		new PlayWave1st("sounds/boing2.wav").start();
	}

	public void throwCanons() {
		canons.add(new CanonBall(x + width, y + height / 2));
		new PlayWave1st("sounds/boing.wav").start();
	}

	public void strikeHead() {

		loadImage("images/strikehead.png");
		getImageDimensions();
	}

}