package game_engine;

import java.awt.event.ActionEvent;
import java.util.List;

import entities.Alien;
import entities.Bunker;
import entities.Crosshair;
import entities.Dragon;
import entities.EvilHead;
import entities.MyShip;
import items.BunkerBullet;
import items.CanonBall;
import items.FireBall;
import items.Gold;
import items.HealthPack;
import items.ShipMissile;
import items.ShipRocket;
import sound_engine.LoadSounds;
import sound_engine.PlayWave1st;
import sound_engine.SoundEffects;

public abstract class UpdateObjects extends InitObjects {

	private static final long serialVersionUID = 1L;
	public static int lifeEvilHead = 3;
	public static int lifeMyShip = 3;
	public static int lifeBunker = 3;

	@Override
	public void actionPerformed(ActionEvent e) {

		inGame();

		updateMyShip();
		updateMyCrosshair();
		updateMyShipMissiles();
		updateEvilHeadMissiles();
		updateEvilHeadCanons();
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

		if (MyShip.myShip.isVisible()) {
			MyShip.myShip.move();
		}
	}

	private void updateMyCrosshair() {

		if (Crosshair.crosshair.isVisible()) {
			Crosshair.crosshair.move();
		}
	}

	private void updateMyShipMissiles() {

		List<ShipMissile> missiles = MyShip.myShip.getMissiles();

		for (int nextmissile = 0; nextmissile < missiles.size(); nextmissile++) {

			ShipMissile missile = missiles.get(nextmissile);

			if (missile.isVisible()) {
				missile.moveMissile();
			} else {
				missiles.remove(nextmissile);
			}
		}

	}

	private void updateBullets() {

		List<BunkerBullet> bullets1 = Bunker.bunkerObj.getBullets();

		List<BunkerBullet> bullets2 = Bunker.bunkerObj.getBullets2();

		for (int nextbullet = 0; nextbullet < bullets1.size(); nextbullet++) {

			BunkerBullet bullet1 = bullets1.get(nextbullet);

			if (bullet1.isVisible()) {
				bullet1.moveDiagLeft();
				if (MyShip.myShip.x > 200) {
					bullet1.moveDiagRight();
					bullet1.moveRight();
				} else if (MyShip.myShip.y > 300) {
					bullet1.moveDown();
					bullet1.moveLeft();
				}
			} else {
				LoadSounds.fuse.stop();
				bullets1.remove(nextbullet);
			}
		}

		for (int nextbullet = 0; nextbullet < bullets2.size(); nextbullet++) {
			BunkerBullet bullet2 = bullets2.get(nextbullet);
			if (bullet2.isVisible()) {
				bullet2.moveDiagRight();
				if (MyShip.myShip.x > 200) {
					bullet2.moveDiagLeft();
					bullet2.moveLeft();
				} else if (MyShip.myShip.y > 300) {
					bullet2.moveDown();
					bullet2.moveLeft();
				}

			} else {
				LoadSounds.fuse.stop();
				bullets2.remove(nextbullet);
			}
		}
	}

	private void updateEvilHeadMissiles() {
		List<FireBall> fireballs = EvilHead.evilHead.getEvilMissiles();
		for (int nextball = 0; nextball < fireballs.size(); nextball++) {
			FireBall fireball = fireballs.get(nextball);
			if (fireball.isVisible() && Dragon.dragons.isEmpty() && timerHard.isRunning() == true) {
				if (Gold.goldstack.isEmpty() && lifeMyShip <= 3) {
					fireball.evilShotDiagUp();
					if (fireball.y < 0) {
						fireball.y = 0;
						fireball.evilShot();
					}
				}
				if (Gold.goldstack.size() > 0 && lifeMyShip <= 3) {
					fireball.evilShotDiagDown();
					if (fireball.y > 768) {
						fireball.y = 768;
						fireball.evilShot();
					}
				}

			}

			if (fireball.isVisible()) {
				fireball.evilShot();
			} else {
				fireballs.remove(nextball);
			}
		}
	}

	private void updateEvilHeadCanons() {
		List<CanonBall> canonballs = EvilHead.evilHead.getCanons();
		for (int nextcanon = 0; nextcanon < canonballs.size(); nextcanon++) {
			CanonBall canonball = canonballs.get(nextcanon);
			if (canonball.isVisible() && (EvilHead.evilHead.x - MyShip.myShip.x > 0)) {
				canonball.moveCanonLeft();
			} else if (canonball.isVisible() && (EvilHead.evilHead.x - MyShip.myShip.x < 0)) {
				canonball.moveCanonRight();
			} else {
				canonballs.remove(nextcanon);
			}
		}
	}

	private void updateRockets() {
		List<ShipRocket> rocketstack = MyShip.myShip.getRockets();
		for (int nextrocket = 0; nextrocket < rocketstack.size(); nextrocket++) {
			ShipRocket shiprocket = rocketstack.get(nextrocket);
			if (shiprocket.isVisible()) {
				shiprocket.moveRocket();
			} else {
				rocketstack.remove(nextrocket);
			}
		}
	}

	private void updateAliens() {
		for (int nextalien = 0; nextalien < Alien.aliens.size(); nextalien++) {
			Alien alien = Alien.aliens.get(nextalien);
			if (alien.isVisible() && timerHard.isRunning() == true) {
				alien.moveFaster();
			}

			if (alien.isVisible()) {
				alien.move();
			} else {
				Alien.aliens.remove(nextalien);
				SoundEffects.BLOOP.getSound();
			}
		}
	}

	protected static int updateDragons() {
		for (int nextdragon = 0; nextdragon < Dragon.dragons.size(); nextdragon++) {
			Dragon dragon = Dragon.dragons.get(nextdragon);
			dragon.setVisible(true);
			Collisions.checkCollisions();
			if (dragon.isVisible()) {
				dragon.move();
			} else {
				Collisions.dragonKilled++;
				Dragon.dragons.remove(nextdragon);
				SoundEffects.BLOOP.getSound();
				return Collisions.dragonKilled;
			}
		}
		return 0;
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
		for (int nextgold = 0; nextgold < Gold.goldstack.size(); nextgold++) {
			Gold goldpiece = Gold.goldstack.get(nextgold);
			if (goldpiece.isVisible()) {
				goldpiece.move();
			} else {
				Gold.goldstack.remove(nextgold);
				new PlayWave1st("sounds/collect.wav").start();
			}
		}
	}

	private void updateHealth() {
		for (int nextpack = 0; nextpack < HealthPack.healthpack.size(); nextpack++) {
			HealthPack healthpiece = HealthPack.healthpack.get(nextpack);
			if (HealthPack.healthpack.size() < 5 && lifeMyShip > 3) {
				HealthPack.healthpack.add(nextpack, new HealthPack(EvilHead.evilHead.x, EvilHead.evilHead.y));
			}
			if (healthpiece.isVisible()) {
				healthpiece.move();
			} else {
				HealthPack.healthpack.remove(nextpack);
				if (lifeMyShip > 3) {
					lifeMyShip--;
				}
			}
		}
	}
}