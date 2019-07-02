package entities;

import java.awt.event.KeyEvent;

import game_engine.SpritePattern;

public class Crosshair extends SpritePattern {

	private static final long serialVersionUID = 1L;
	public static Crosshair crosshair;
	public String imageName;
	double speedX;
	double speedY;

	public Crosshair(int x, int y) {
		super(x, y);

		initCrosshair();
	}

	public String initCrosshair() {
		imageName = "images/pointer.png";
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

		if (y < -15) {
			y = -15;
		} else if (y > 685) {
			y = 685;
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
			initCrosshair();
		}

		if (key == KeyEvent.VK_RIGHT) {
			speedX = 7.5;
			initCrosshair();
		}

		if (key == KeyEvent.VK_UP) {
			speedY = -7.5;
			initCrosshair();
		}

		if (key == KeyEvent.VK_DOWN) {
			speedY = 7.5;
			initCrosshair();
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			speedX = 0;
			initCrosshair();
		}

		if (key == KeyEvent.VK_RIGHT) {
			speedX = 0;
			initCrosshair();
		}

		if (key == KeyEvent.VK_UP) {
			speedY = 0;
			initCrosshair();
		}

		if (key == KeyEvent.VK_DOWN) {
			speedY = 0;
			initCrosshair();
		}

	}
}