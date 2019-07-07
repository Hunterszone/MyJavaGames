package entities;

import java.util.ArrayList;
import java.util.List;

import game_engine.Images;
import game_engine.SpritePattern;
import items.CanonBall;
import items.FireBall;
import sound_engine.PlayWave1st;

public class EvilHead extends SpritePattern {

	private static final long serialVersionUID = 1L;
	public static EvilHead evilHead;
	private String imageName;
	private double speedX;
	private double speedY;
	private List<FireBall> fireballs;
	private List<CanonBall> canons;

	public EvilHead(int x, int y) {
		super(x, y);
		initHead();
		initAmmo();
	}

	public String initHead() {
		imageName = Images.EVILHEAD.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
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
			initHead();
		}

		y -= 1;
		if (y == 0) {
			x += 1.2;
			initHead();
		}

		if (x > 800) {

			speedX -= 1.2;
			speedY += 1.2;
			initHead();
		}

		if (y > 500) {
			speedY -= 1;
			initHead();
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
			initHead();
		}

		y -= 1;
		if (y == 0) {
			x += 1.2;
			initHead();
		}

		if (x > 800) {
			speedX -= 1.2;
			speedY += 1.2;
			initHead();

		}

		if (y > 500) {
			speedY -= 1;
			initHead();
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
			initHead();
		}

		y -= 1;
		if (y == 0) {
			x += 1.2;
			initHead();
		}

		if (x > 800) {
			speedX -= 1.2;
			speedY += 1.2;
			initHead();
		}

		if (y > 500) {
			speedY -= 1;
			initHead();
		}

	}

	public List<FireBall> getEvilMissiles() {
		return fireballs;
	}

	public List<CanonBall> getCanons() {
		return canons;
	}

	public List<FireBall> throwFireballs() {
		fireballs.add(new FireBall(x + width, y + height / 2));
		new PlayWave1st("sounds/boing2.wav").start();
		return fireballs;
	}

	public List<CanonBall> throwCanons() {
		canons.add(new CanonBall(x + width, y + height / 2));
		new PlayWave1st("sounds/boing.wav").start();
		return canons;
	}

	public void strikeHead() {
		loadImage("images/strikehead.png");
		getImageDimensions();
	}

}