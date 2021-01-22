// MyShip.java
// 
// Creator: Konstantin
// 

package entities;

// import java libraries:
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

// import game packages:
import enums.Images;
import enums.SoundEffects;
import game_engine.SpritePattern;
import items.ShipMissile;
import items.ShipRocket;
import main.Main;
import menu_engine.CanvasMenu;
import menu_engine.ImageColorizer;
import sound_engine.PlayWave1st;
import util.Constants;

public class PlayerShip extends SpritePattern {

	public static PlayerShip playerOne;
	double speedX, speedY;
	private static final long serialVersionUID = 1L;
	private List<ShipMissile> missiles;
	private List<ShipRocket> rockets;

	private String imageName, soundName;

	public PlayerShip(int x, int y) {
		super(x, y);

		drawShip();
		initAmmo();
	}

	public Image drawShip() {

		imageName = Images.MYSHIPINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return loadImage(imageName);
	}

	public Image shipOnFire() {

		imageName = Images.MYSHIPONFIRE.getImg();
		loadImage(imageName);
		getImageDimensions();
		return loadImage(imageName);
	}

	public Image upsideDown() {

		imageName = Images.MYSHIPLIFEBAR.getImg();
		loadImage(imageName);
		getImageDimensions();
		return loadImage(imageName);
	}

	public Image godMode() {

		imageName = Images.MYSHIPGOD.getImg();
		loadImage(imageName);
		getImageDimensions();
		return loadImage(imageName);
	}

	public Image escapeForbidden() {

		imageName = Images.MYSHIPESCAPE.getImg();
		loadImage(imageName);
		getImageDimensions();
		return loadImage(imageName);

	}

	public Image shipDamaged() {

		imageName = Images.MYSHIPDAMAGED.getImg();
		loadImage(imageName);
		getImageDimensions();
		return loadImage(imageName);
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
		
		Main.dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		x += speedX;
		y += speedY;

		if (x < 1) {
			x = 1;
//			escapeForbidden();
		} 
		
		if (x > Main.dim.getWidth() - 390) {
			x = (int) Main.dim.getWidth() - 390;
		}

		if (y < 0) {
			y = 0;
//			escapeForbidden();
		} else if (y > Main.dim.getHeight() - 350) {
			y = (int) (Main.dim.getHeight() - 350);
//			escapeForbidden();
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
		soundName = SoundEffects.LASER.getSound();
		missiles.add(new ShipMissile(x + width, y + height / 2));
		new PlayWave1st(soundName).start();
		return missiles;
	}

	public List<ShipRocket> loadRockets() {
		soundName = SoundEffects.ROCKET.getSound();
		rockets.add(new ShipRocket(x + width, y + height / 2));
		new PlayWave1st(soundName).start();
		return rockets;
	}

	public String gunLocked() {
		soundName = SoundEffects.DENIED.getSound();
		new PlayWave1st(soundName).start();
		return soundName;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			speedX = -7.5;
//			shipOnFire();
		}

		if (key == KeyEvent.VK_RIGHT) {
			speedX = 7.5;
//			shipOnFire();
		}

		if (key == KeyEvent.VK_UP) {
			speedY = -7.5;
//			shipOnFire();
		}

		if (key == KeyEvent.VK_DOWN) {
			speedY = 7.5;
//			shipOnFire();
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
			speedX = 0;
			drawShip();
		}

		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
			speedY = 0;
			drawShip();
		}
	}
	
	public void renderShip(Graphics g) {
        g.drawImage(ImageColorizer.dye(Constants.LOAD_ASSETS.myShip, CanvasMenu.color.getColor()), Math.round(this.getX()), Math.round(this.getY()), null);
    }
}
