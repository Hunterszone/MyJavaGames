// MyShip.java
// 
// Creator: Konstantin
// 

package enemy;

// import java libraries:
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

// import game packages:
import enums.Sprite;
import enums.SoundEffect;
import game_engine.SpritePattern;
import items.ShipMissile;
import items.ShipRocket;
import menu_engine.CanvasMenu;
import menu_engine.ImageColorizer;
import sound_engine.PlayWave1st;
import util.Constants;
import util.LoadSounds;
import util.Moveable;

public class PlayerShip extends SpritePattern implements Moveable {

	public static PlayerShip playerShip;
	public static double speedX, speedY;	
	private static final long serialVersionUID = 1L;

	public PlayerShip(int x, int y) {
		super(x, y);

		drawShip();
		initAmmo();
	}

	public void drawShip() {
		String imageName = Sprite.MYSHIP_INIT.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	public void shipOnFire() {
		String imageName = Sprite.MYSHIP_ON_FIRE.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	public void upsideDown() {
		String imageName = Sprite.MYSHIP_LIFEBAR.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	public void godMode() {
		String imageName = Sprite.MYSHIP_GOD.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	public void shakeShip() {

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

	@Override
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
		ShipMissile.missiles = new ArrayList<>();
		ShipRocket.rockets = new ArrayList<>();
	}

	public List<ShipMissile> getMissiles() {
		return ShipMissile.missiles;
	}

	public List<ShipRocket> getRockets() {
		return ShipRocket.rockets;
	}

	public List<ShipMissile> loadMissiles() {
		String soundName = SoundEffect.LASER.getSound();
		assert soundName != null;
		ShipMissile.missiles.add(new ShipMissile(x + width, y-15 + height / 2));
		new PlayWave1st(soundName).start();
		return ShipMissile.missiles;
	}

	public List<ShipRocket> loadRockets() {
		String soundName = SoundEffect.ROCKET.getSound();
		assert soundName != null;
		ShipRocket.rockets.add(new ShipRocket(x + width, y + height / 2));
		new PlayWave1st(soundName).start();
		return ShipRocket.rockets;
	}

	public void gunLocked(KeyEvent e) {
		assert SoundEffect.DENIED.getSound() != null;
		LoadSounds.DENIED.play();
		if(e.isConsumed()) {
			LoadSounds.DENIED.stop();	
		}
	}

	public static void keyPressed(KeyEvent e) {

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
