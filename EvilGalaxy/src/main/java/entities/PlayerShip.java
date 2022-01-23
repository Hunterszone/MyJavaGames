// MyShip.java
// 
// Creator: Konstantin
// 

package entities;

// import java libraries:
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

// import game packages:
import enums.Images;
import enums.SoundEffects;
import game_engine.SpritePattern;
import items.ShipMissile;
import items.ShipRocket;
import menu_engine.CanvasMenu;
import menu_engine.ImageColorizer;
import sound_engine.PlayWave1st;
import util.Constants;
import util.LoadSounds;

public class PlayerShip extends SpritePattern {

	public static PlayerShip playerOne;
	public static double speedX, speedY;
	private static final long serialVersionUID = 1L;
	private List<ShipMissile> missiles;
	private List<ShipRocket> rockets;

	private String imageName, soundName;

	public PlayerShip(int x, int y) {
		super(x, y);

		drawShip();
		initAmmo();
	}

	public void drawShip() {
		imageName = Images.MYSHIPINIT.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	public void shipOnFire() {
		imageName = Images.MYSHIPONFIRE.getImg();
		Images.MYSHIPINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
	}

	public void upsideDown() {
		imageName = Images.MYSHIPLIFEBAR.getImg();
		Images.MYSHIPINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
	}

	public void godMode() {
		imageName = Images.MYSHIPGOD.getImg();
		Images.MYSHIPINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
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
//			escapeForbidden();
		} 
		
		if (x > Constants.DIMENSION.getWidth() - 340) {
			x = (int) Constants.DIMENSION.getWidth() - 340;
		}

		if (y < 0) {
			y = 0;
//			escapeForbidden();
		} else if (y > Constants.DIMENSION.getHeight() - 260) {
			y = (int) (Constants.DIMENSION.getHeight() - 260);
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
		assert soundName != null;
		missiles.add(new ShipMissile(x + width, y-15 + height / 2));
		new PlayWave1st(soundName).start();
		return missiles;
	}

	public List<ShipRocket> loadRockets() {
		soundName = SoundEffects.ROCKET.getSound();
		assert soundName != null;
		rockets.add(new ShipRocket(x + width, y + height / 2));
		new PlayWave1st(soundName).start();
		return rockets;
	}

	public void gunLocked(KeyEvent e) {
		assert SoundEffects.DENIED.getSound() != null;
		LoadSounds.DENIED.play();
		if(e.isConsumed()) {
			LoadSounds.DENIED.stop();	
		}
	}

	public void keyPressed(KeyEvent e) {

		final int key = e.getKeyCode();

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

		final int key = e.getKeyCode();

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
