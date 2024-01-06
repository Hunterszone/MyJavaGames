package items;

import java.awt.Shape;
import java.util.List;
import java.util.Objects;

import enemy.Alien;
import enemy.Dragon;
import enemy.EvilHead;
import enemy.PlayerShip;
import enums.SoundEffect;
import enums.Sprite;
import game_engine.SpritePattern;
import sound_engine.PlayWave1st;
import util.ActionPerformer;
import util.Intersectable;
import util.LivesAndCounts;

public class ShipMissile extends SpritePattern implements Intersectable {

	public static List<ShipMissile> missiles;

	private static final long serialVersionUID = 1L;
	private final double MISSILE_SPEED = 50;
	private String imageName;

	public ShipMissile(int x, int y) {
		super(x, y);
		initMissile();
	}

	public void initMissile() {
		imageName = Objects.requireNonNull(Sprite.MISSILE_INIT.getImg());
		loadImage(imageName);
		getImageDimensions();
	}

	public void moveMissile() {

		x += MISSILE_SPEED;

		if (x > PlayerShip.playerShip.getX() + 550)
			vis = false;
	}

	@Override
	public void intersects(Shape entity) {
		missileColidesWithAlien();
		missileColidesWithEvilHead(entity);
	}

	private void missileColidesWithEvilHead(Shape entity) {
		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty()
				&& LivesAndCounts.getLifeBunker() >= 50) {
			if (this.getBounds().intersects(entity.getBounds())) {
//					FrameUtils.vibrate(MouseInputHandler.main);
				new PlayWave1st(SoundEffect.BLOOP.getSound()).start();
				this.setVisible(false);
				if (ActionPerformer.getTimerHard().isRunning()) {
					EvilHead.evilHead.loadPlasmaBall();
				} else {
					EvilHead.evilHead.loadCanon();
				}
				HealthPack.healthpacks
						.add(new HealthPack((int) EvilHead.evilHead.getX(), (int) EvilHead.evilHead.getY()));
				EvilHead.evilHead.strikeHead();
				LivesAndCounts.decreaseEvilHeadLife(1);
			}
		}
	}

	private void missileColidesWithAlien() {
		if (!Alien.aliens.isEmpty()) {
			for (Alien alien : Alien.aliens) {
				if (this.getBounds().intersects(alien.getBounds())) {
//					FrameUtils.vibrate(MouseInputHandler.main);
					this.setVisible(false);
					alien.loadCanon();
					alien.setVisible(false);
					new PlayWave1st(SoundEffect.BLOOP.getSound()).start();
					HealthPack.healthpacks.add(new HealthPack(alien.getX(), alien.getY()));
					LivesAndCounts.setAlienKilled(LivesAndCounts.getAlienKilled() + 1);
				}
			}
		}
	}
}