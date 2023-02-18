package enemy;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import enums.Sprite;
import enums.SoundEffect;
import game_engine.SpritePattern;
import game_engine.UpdateObjects;
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

	public EvilHead(int x, int y) {
		super(x, y);
		drawHead();
		initAmmo();
	}

	public void drawHead() {
		imageName = Sprite.EVILHEAD.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	public void strikeHead() {
		imageName = Sprite.STRIKEHEAD.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	private void initAmmo() {
		PlasmaBall.plasmaBalls = new ArrayList<>();
		CanonBall.canonBalls = new ArrayList<>();
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
			loadCanon();
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
			loadCanon();
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
		return PlasmaBall.plasmaBalls;
	}

	public List<CanonBall> getCanons() {
		return CanonBall.canonBalls;
	}

	public void loadPlasmaBall() {
		PlasmaBall.plasmaBalls.add(new PlasmaBall(x + width, y + height / 2));
		if (UpdateObjects.getLifeBunker() >= 50) new PlayWave1st(SoundEffect.BOING_2.getSound()).start();
	}

	public void loadCanon() {
		CanonBall.canonBalls.add(new CanonBall(x + width, y + height / 2));
		if (UpdateObjects.getLifeBunker() >= 50) new PlayWave1st(SoundEffect.BOING.getSound()).start();
	}

	public void renderEvilHead(Graphics g) {
		g.drawImage(ImageColorizer.dye(Constants.LOAD_ASSETS.evilHead, CanvasMenu.color2.getColor()),
				Math.round(this.getX()), Math.round(this.getY()), null);
	}
}