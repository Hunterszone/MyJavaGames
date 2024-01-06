package items;

import java.awt.Shape;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import enemy.Alien;
import enemy.Bunker;
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
import util.LoadSounds;

public class ShipRocket extends SpritePattern implements Intersectable {

	public static List<ShipRocket> rockets;

	private static final long serialVersionUID = 1L;
	private final double ROCKET_SPEED_X = 7;
	private final double ROCKET_SPEED_Y = 2.5;
	private String imageName;

	public ShipRocket(int x, int y) {
		super(x, y);
		initRocket();
	}

	public void initRocket() {
		imageName = Objects.requireNonNull(Sprite.ROCKET_INIT.getImg());
		loadImage(imageName);
		getImageDimensions();
	}

	public void moveRocket() {

		x += ROCKET_SPEED_X;
		y += ROCKET_SPEED_Y;

		if (x > PlayerShip.playerShip.getX() + 320)
			vis = false;
	}

	@Override
	public void intersects(Shape entity) {
		rocketColidesWithDragon();
		rocketColidesWithBunker();
		rocketColidesWithEvilHead();
	}

	private void rocketColidesWithBunker() {
		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && LivesAndCounts.getLifeBunker() < 50) {
			if (this.getBounds().intersects(Bunker.bunkerObj.getBounds())) {
//				FrameUtils.vibrate(MouseInputHandler.main);
				Bunker.bunkerObj.drawBunkerHit();
				Bunker.bunkerObj.loadBulletsLeft();
				Bunker.bunkerObj.loadBulletsRight();
				this.setVisible(false);
				new PlayWave1st(SoundEffect.SCREAM.getSound()).start();
				LoadSounds.HIT.play();
				LivesAndCounts.decreaseBunkerLife(1);
			}
		}
	}

	private void rocketColidesWithEvilHead() {
		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty()
				&& LivesAndCounts.getLifeBunker() >= 50) {
			if (this.getBounds().intersects(EvilHead.evilHead.getBounds())) {
//				FrameUtils.vibrate(MouseInputHandler.main);
				if (ActionPerformer.getTimerHard().isRunning()) {
					EvilHead.evilHead.loadPlasmaBall();
				} else {
					EvilHead.evilHead.loadCanon();
				}
				HealthPack.healthpacks
						.add(new HealthPack((int) EvilHead.evilHead.getX(), (int) EvilHead.evilHead.getY()));
				EvilHead.evilHead.strikeHead();
				this.setVisible(false);
				LivesAndCounts.decreaseEvilHeadLife(1);
			}
		}
	}

	private void rocketColidesWithDragon() {
		if (Alien.aliens.isEmpty() && Dragon.dragons.size() > 0) {
			for (Iterator<Dragon> it = Dragon.dragons.iterator(); it.hasNext();) {
				Dragon dragon = it.next();
				if (this.getBounds().intersects(dragon.getBounds())) {
//				FrameUtils.vibrate(MouseInputHandler.main);
					new PlayWave1st(SoundEffect.BLOOP.getSound()).start();
					this.setVisible(false);
					dragon.setVisible(false);
					Angel.angels.add(new Angel(dragon.getX(), dragon.getY()));
					LivesAndCounts.increaseDragonKilledCount(1);
					it.remove();
				}
			}
		}
	}
}