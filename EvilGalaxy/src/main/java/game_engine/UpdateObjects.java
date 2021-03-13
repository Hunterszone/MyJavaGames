package game_engine;

import java.awt.event.ActionEvent;
import java.util.List;

import entities.Alien;
import entities.Bunker;
import entities.Crosshair;
import entities.Dragon;
import entities.EvilHead;
import entities.PlayerShip;
import enums.SoundEffects;
import items.BunkerBullet;
import items.CanonBall;
import items.FireBall;
import items.Gold;
import items.HealthPack;
import items.ShipMissile;
import items.ShipRocket;
import sound_engine.PlayWave1st;
import util.LoadSounds;

public abstract class UpdateObjects extends InitObjects {

	private static final long serialVersionUID = 1L;
	public static int lifeEvilHead = 3;
	public static int lifePlayerShip = 3;
	public static int lifeBunker = 3;

	@Override
	public void actionPerformed(ActionEvent e) {

		inGame();

		updateMyShip();
		updateMyCrosshair();
		updateMyShipMissiles();
		updateEHFireballs();
		updateEHCanons();
		updateRockets();
		updateAliens();
		updateEvilHead();
		updateGold();
		updateHealth();
		updateBullets();

		Collisions.checkCollisions();

		repaint();

	}

	private void inGame() {

		if (!ingame) {
			timerEasy.stop();
			timerMedium.stop();
			timerHard.stop();
		}
	}

	private void updateMyShip() {

		if (PlayerShip.playerOne.isVisible()) {
			PlayerShip.playerOne.move();
		}
	}

	private void updateMyCrosshair() {

		if (Crosshair.crosshair.isVisible()) {
			Crosshair.crosshair.move();
		} else {
			Crosshair.crosshair = null;
		}
	}

	private void updateMyShipMissiles() {

		List<ShipMissile> missiles = PlayerShip.playerOne.getMissiles();

		missiles.removeIf(missile -> missile.isVisible() == false);

		missiles.stream().filter(missile -> missile.isVisible()).forEach(missile -> missile.moveMissile());

	}

	private void updateBullets() {

		List<BunkerBullet> bulletsGroupOne = Bunker.bunkerObj.getBulletsLeft();
		List<BunkerBullet> bulletsGroupTwo = Bunker.bunkerObj.getBulletsRight();

		if (bulletsGroupOne.removeIf(bullet -> bullet.isVisible() == false)
				|| bulletsGroupTwo.removeIf(bullet -> bullet.isVisible() == false)) {
			LoadSounds.fuse.stop();
		}

		bulletsGroupOne.stream().filter(bullet -> bullet.isVisible()).forEach(bullet -> {
			bullet.moveDiagLeft();
			if (PlayerShip.playerOne.x > 200) {
				bullet.moveDiagRight();
				bullet.moveRight();
			} else if (PlayerShip.playerOne.y > 300) {
				bullet.moveDown();
				bullet.moveLeft();
			}
		});

		bulletsGroupTwo.stream().filter(bullet -> bullet.isVisible()).forEach(bullet -> {
			bullet.moveDiagRight();
			if (PlayerShip.playerOne.x > 200) {
				bullet.moveDiagLeft();
				bullet.moveLeft();
			} else if (PlayerShip.playerOne.y > 300) {
				bullet.moveDown();
				bullet.moveLeft();
			}
		});
	}

	private void updateEHFireballs() {

		List<FireBall> fireballs = EvilHead.evilHead.getEvilFireballs();

		fireballs.removeIf(fireball -> fireball.isVisible() == false);

		fireballs.stream().filter(fireball -> fireball.isVisible()).forEach(fireball -> {
			if (Dragon.dragons.isEmpty() && timerHard.isRunning()) {
				if (Gold.goldstack.isEmpty() && lifePlayerShip <= 3) {
					fireball.evilShotDiagUp();
					if (fireball.y < 0) {
						fireball.y = 0;
						fireball.evilShot();
					}
				}
				if (Gold.goldstack.size() > 0 && lifePlayerShip <= 3) {
					fireball.evilShotDiagDown();
					if (fireball.y > 768) {
						fireball.y = 768;
						fireball.evilShot();
					}
				}
			} else {
				fireball.evilShot();
			}
		});
	}

	private void updateEHCanons() {

		List<CanonBall> canonballs = EvilHead.evilHead.getCanons();

		canonballs.removeIf(canon -> canon.isVisible() == false);

		canonballs.stream().filter(canon -> canon.isVisible()).forEach(canon -> {
			if (EvilHead.evilHead.x - PlayerShip.playerOne.x > 0) {
				canon.moveCanonLeft();
			}
		});
	}

	private void updateRockets() {

		List<ShipRocket> rocketStack = PlayerShip.playerOne.getRockets();

		rocketStack.removeIf(rocket -> rocket.isVisible() == false);

		rocketStack.stream().filter(rocket -> rocket.isVisible()).forEach(rocket -> rocket.moveRocket());
	}

	private void updateAliens() {

		if (Alien.aliens.removeIf(alien -> alien.isVisible() == false)) {
			SoundEffects.BLOOP.getSound();
		}

		Alien.aliens.stream().filter(alien -> alien.isVisible()).forEach(alien -> {
			if (timerHard.isRunning()) {
				alien.moveFaster();
			}
			alien.move();
		});
	}

	protected static int updateDragons() {

		Dragon.dragons.stream().forEach(dragon -> {
			dragon.setVisible(true);
			Collisions.checkCollisions();
			if (dragon.isVisible()) {
				dragon.move();
			} else {
				SoundEffects.BLOOP.getSound();
				Collisions.dragonKilled++;
				Dragon.dragons.remove(dragon);
			}
		});

		return Collisions.dragonKilled;
	}

	private void updateEvilHead() {
		if (EvilHead.evilHead.isVisible() && timerEasy.isRunning()) {
			if (Alien.aliens.size() > 0 || Dragon.dragons.size() > 0) {
				EvilHead.evilHead.AIOnEasy();
			}
			if (Dragon.dragons.isEmpty() && Gold.goldstack.size() >= 0) {
				EvilHead.evilHead.AIOnEasy();
			}
		}

		if (EvilHead.evilHead.isVisible() && timerMedium.isRunning() == true) {
			if (Alien.aliens.size() > 0 || Dragon.dragons.size() > 0) {
				EvilHead.evilHead.AIOnMedium();
			}
			if (Dragon.dragons.isEmpty() && Gold.goldstack.size() >= 0) {
				EvilHead.evilHead.AIOnMedium();
			}
		}

		if (EvilHead.evilHead.isVisible() && timerHard.isRunning() == true) {
			if (Alien.aliens.size() > 0 || Dragon.dragons.size() > 0) {
				EvilHead.evilHead.AIOnHard();
			}
			if (Dragon.dragons.isEmpty() && Gold.goldstack.size() >= 0) {
				EvilHead.evilHead.AIOnHard();
			}
		}
	}

	private void updateGold() {

		if (Gold.goldstack.removeIf(goldBar -> goldBar.isVisible() == false)) {
			new PlayWave1st("sounds/collect.wav").start();
		}

		Gold.goldstack.stream().filter(goldBar -> goldBar.isVisible()).forEach(goldBar -> goldBar.move());
	}

	private void updateHealth() {
		
		if (HealthPack.healthpack.removeIf(healthPack -> healthPack.isVisible() == false)) {
			new PlayWave1st("sounds/collect.wav").start();
			if (lifePlayerShip > 3) {
				lifePlayerShip--;
			}
		}
		
		if (HealthPack.healthpack.size() < 5 && lifePlayerShip > 3) {
			HealthPack.healthpack.add(new HealthPack(EvilHead.evilHead.x, 0));
		}

		HealthPack.healthpack.stream().filter(healthPack -> healthPack.isVisible()).forEach(healthPack -> healthPack.move());
	}
}