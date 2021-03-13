package entities;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import enums.Images;
import game_engine.SpritePattern;
import items.CanonBall;
import items.PlasmaBall;
import menu_engine.CanvasMenu;
import menu_engine.ImageColorizer;
import sound_engine.PlayWave1st;
import util.Constants;

public class EvilHead extends SpritePattern {

	public static EvilHead evilHead;
	private static final long serialVersionUID = 1L;
	private String imageName;
	private double speedX, speedY;
	private List<PlasmaBall> plasmaBalls;
	private List<CanonBall> canons;

	public EvilHead(int x, int y) {
		super(x, y);
		drawHead();
		initAmmo();
	}

	public Image drawHead() {
		imageName = Images.EVILHEAD.getImg();
		loadImage(imageName);
		getImageDimensions();
		return loadImage(imageName);
	}
	
	public Image strikeHead() {
		imageName = Images.STRIKEHEAD.getImg();
		loadImage(imageName);
		getImageDimensions();
		return loadImage(imageName);
	}

	private void initAmmo() {
		plasmaBalls = new ArrayList<>();
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
			drawHead();
		}

		y -= 1;
		if (y == 0) {
			x += 1.2;
			drawHead();
		}

		if (x > 800) {

			speedX -= 1.2;
			speedY += 1.2;
			drawHead();
		}

		if (y > 500) {
			speedY -= 1;
			drawHead();
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
			drawHead();
		}

		y -= 1;
		if (y == 0) {
			x += 1.2;
			drawHead();
		}

		if (x > 800) {
			speedX -= 1.2;
			speedY += 1.2;
			drawHead();

		}

		if (y > 500) {
			speedY -= 1;
			drawHead();
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
			drawHead();
		}

		y -= 1;
		if (y == 0) {
			x += 1.2;
			drawHead();
		}

		if (x > 800) {
			speedX -= 1.2;
			speedY += 1.2;
			drawHead();
		}

		if (y > 500) {
			speedY -= 1;
			drawHead();
		}

	}

	public List<PlasmaBall> getEvilPlasmaBalls() {
		return plasmaBalls;
	}

	public List<CanonBall> getCanons() {
		return canons;
	}

	public List<PlasmaBall> throwPlasmaBalls() {
		plasmaBalls.add(new PlasmaBall(x + width, y + height / 2));
		new PlayWave1st("sounds/boing2.wav").start();
		return plasmaBalls;
	}

	public List<CanonBall> throwCanons() {
		canons.add(new CanonBall(x + width, y + height / 2));
		new PlayWave1st("sounds/boing.wav").start();
		return canons;
	}
	
	public void renderEvilHead(Graphics g) {
        g.drawImage(ImageColorizer.dye(Constants.LOAD_ASSETS.evilHead, CanvasMenu.color2.getColor()), Math.round(this.getX()), Math.round(this.getY()), null);
    }
}