package entities;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import game_engine.SpritePattern;
import items.ShipMissile;
import items.ShipRocket;
import sound_engine.PlayWave1st;

public class MyShip extends SpritePattern {

	private static final long serialVersionUID = 1L;
	public static MyShip myShip;
	double speedX;
	double speedY;
	private List<ShipMissile> missiles;
	private List<ShipRocket> rockets;

	private String imageName, soundName;

	public MyShip(int x, int y) {
		super(x, y);

		initCraft();
		initAmmo();
	}

	public String initCraft() {

		imageName = "images/craft.png";
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public String shipOnFire() {

		imageName = "images/newship.png";
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public String upsideDown() {

		imageName = "images/alien.gif";
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public String godMode() {

		imageName = "images/alien2.gif";
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public String escapeForbidden() {

		imageName = "images/alien3.gif";
		loadImage(imageName);
		getImageDimensions();
		return imageName;

	}

	public String shipDamaged() {

		imageName = "images/hitcraft.gif";
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public void shipShaked() {

		x += speedX;
		y += speedY;

		if (x < 1) {
			x = 1;

		}

		if (y < 1) {
			y = 1;
		}

		x -= 1;

		if (x < 100) {
			speedX += 0.3;

		}

		y -= 1;
		if (y == 0) {
			x += 0.3;

		}

		if (x > 200) {

			speedX -= 0.3;
			speedY += 0.3;

		}

		if (y > 50) {
			speedY -= 0.3;
		}

	}

	public void move() {

		x += speedX;
		y += speedY;

		if (x < 1) {
			x = 1;
			escapeForbidden();
		} else if (x > 900) {
			x = 900;
			escapeForbidden();
		}

		if (y < 0) {
			y = 0;
			escapeForbidden();
		} else if (y > 700) {

			y = 700;
			escapeForbidden();
		}
	}

	private void initAmmo() {
		missiles = new ArrayList<>();
		rockets = new ArrayList<>();
	}

	public List<ShipMissile> getMissiles() {
		return missiles;
	}

	public List<ShipRocket> getRockets() {
		return rockets;
	}

	public List<ShipMissile> loadMissiles() {
		soundName = "sounds/laser.wav";
		missiles.add(new ShipMissile(x + width, y + height / 2));
		new PlayWave1st(soundName).start();
		return missiles;
	}

	public List<ShipRocket> loadRockets() {
		soundName = "sounds/rocket.wav";
		rockets.add(new ShipRocket(x + width, y + height / 2));
		new PlayWave1st(soundName).start();
		return rockets;
	}

	public String gunLocked() {
		soundName = "sounds/denied.wav";
		new PlayWave1st(soundName).start();
		return soundName;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			speedX = -7.5;
			shipOnFire();
		}

		if (key == KeyEvent.VK_RIGHT) {
			speedX = 7.5;
			shipOnFire();
		}

		if (key == KeyEvent.VK_UP) {
			speedY = -7.5;
			shipOnFire();
		}

		if (key == KeyEvent.VK_DOWN) {
			speedY = 7.5;
			shipOnFire();
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			speedX = 0;
			initCraft();
		}

		if (key == KeyEvent.VK_RIGHT) {
			speedX = 0;
			initCraft();
		}

		if (key == KeyEvent.VK_UP) {
			speedY = 0;
			initCraft();
		}

		if (key == KeyEvent.VK_DOWN) {
			speedY = 0;
			initCraft();
		}
	}
}