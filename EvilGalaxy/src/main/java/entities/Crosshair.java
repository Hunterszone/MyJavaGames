package entities;

import java.awt.event.KeyEvent;

import game_engine.Images;
import game_engine.SpritePattern;

public class Crosshair extends SpritePattern {

	public static Crosshair crosshair;
	public String imageName;
	double speedX, speedY;
	private static final long serialVersionUID = 1L;

	public Crosshair(int x, int y) {
		super(x, y);

		drawCrosshair();
	}

	public String drawCrosshair() {
		imageName = Images.CROSSHAIR.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public void move() {
		x += speedX;
		y += speedY;

		if (x < 210) {
			x = 210;
		} else if (x > 1110) {
			x = 1110;
		}

		if (y < -27) {
			y = -27;
		} else if (y > 675) {
			y = 675;
		}
	}

	public void crosShaked() {

		x += speedX;
		y += speedY;

		if (x < 211) {
			x = 211;

		}

		if (y < 1) {
			y = 1;
		}

		x -= 1;

		if (x < 310) {
			speedX += 0.3;

		}

		y -= 1;
		if (y == 0) {
			x += 0.3;

		}

		if (x > 410) {

			speedX -= 0.3;
			speedY += 0.3;

		}

		if (y > 50) {
			speedY -= 0.3;
		}

	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			speedX = -7.5;
			drawCrosshair();
		}

		if (key == KeyEvent.VK_RIGHT) {
			speedX = 7.5;
			drawCrosshair();
		}

		if (key == KeyEvent.VK_UP) {
			speedY = -7.5;
			drawCrosshair();
		}

		if (key == KeyEvent.VK_DOWN) {
			speedY = 7.5;
			drawCrosshair();
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			speedX = 0;
			drawCrosshair();
		}

		if (key == KeyEvent.VK_RIGHT) {
			speedX = 0;
			drawCrosshair();
		}

		if (key == KeyEvent.VK_UP) {
			speedY = 0;
			drawCrosshair();
		}

		if (key == KeyEvent.VK_DOWN) {
			speedY = 0;
			drawCrosshair();
		}
	}
}