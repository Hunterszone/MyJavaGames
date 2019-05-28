package game_engine;

import java.awt.Rectangle;
import java.util.ArrayList;

import entities.Alien;
import entities.Bunker;
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

public abstract class Collisions extends UpdateObjects {
	
	public static int alienKilled = 0;
	public static int dragonKilled = 0;
	
	public static boolean killedByAlien = false;
	public static boolean killedByDragon = false;
	public static boolean killedByBunker = false;
	public static boolean killedByEvilHead = false;

	private static final long serialVersionUID = 1L;

	public static void checkCollisions() {

		Rectangle myship = MyShip.myShip.getBounds();

		Rectangle evilhead = EvilHead.evilHead.getBounds();

		Rectangle bunker = Bunker.bunkerObj.getBounds();

		for (Alien alien : Alien.aliens) {

			Rectangle alienUnit = alien.getBounds();

			if (myship.intersects(alienUnit)) {
				lifeMyShip++;
				alien.setVisible(false);
				new PlayWave1st("sounds/scream.wav").start();
				MyShip.myShip.shipDamaged();
				MyShip.myShip.upsideDown();
				alienKilled++;
				killedByBunker = false;
				killedByDragon = false;
				killedByEvilHead = false;
				killedByAlien = true;
			}

		}

		for (Dragon dragon : Dragon.dragons) {
			Rectangle dragonunit = dragon.getBounds();

			if (myship.intersects(dragonunit)) {
				lifeMyShip++;
				dragon.setVisible(false);
				new PlayWave1st("sounds/scream.wav").start();
				MyShip.myShip.shipDamaged();
				MyShip.myShip.upsideDown();
				dragonKilled++;
				killedByBunker = false;
				killedByEvilHead = false;
				killedByAlien = false;
				killedByDragon = true;
			}
		}

		if (myship.intersects(evilhead)) {

			new PlayWave1st("sounds/scream.wav").start();
			MyShip.myShip.setVisible(false);
			EvilHead.evilHead.setVisible(false);
			lifeMyShip = 7;
			killedByBunker = false;
			killedByAlien = false;
			killedByDragon = false;
			killedByEvilHead = true;
			return;
		}

		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && lifeBunker < 50) {

			if (myship.intersects(bunker)) {

				new PlayWave1st("sounds/scream.wav").start();
				new PlayWave1st("sounds/explosion.wav").start();
				MyShip.myShip.setVisible(false);
				Bunker.bunkerObj.setVisible(false);
				lifeMyShip = 7;
				killedByAlien = false;
				killedByDragon = false;
				killedByEvilHead = false;
				killedByBunker = true;
				return;
			}
		}

		for (Gold gold : Gold.goldstack) {
			Rectangle goldUnit = gold.getBounds();

			if (myship.intersects(goldUnit)) {
				gold.setVisible(false);
			}
		}

		for (HealthPack health : HealthPack.healthpack) {
			Rectangle healthUnit = health.getBounds();

			if (myship.intersects(healthUnit)) {
				health.setVisible(false);
				LoadSounds.gotHealthPack.play();
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<ShipMissile> missiles = MyShip.myShip.getMissiles();

		for (ShipMissile missile : missiles) {

			Rectangle missileUnit = missile.getBounds();

			for (Alien alien : Alien.aliens) {

				Rectangle alienUnit = alien.getBounds();

				if (missileUnit.intersects(alienUnit)) {
					missile.setVisible(false);
					alien.setVisible(false);
					alienKilled++;				}
			}

			if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && lifeBunker < 50) {
				if (missileUnit.intersects(bunker)) {
					Bunker.bunkerObj.initBunkerHit();
					Bunker.bunkerObj.loadBullet();
					Bunker.bunkerObj.loadBullet2();
					missile.setVisible(false);
					new PlayWave1st("sounds/bloop.wav").start();
					LoadSounds.fuse.play();
					lifeBunker++;
				} else {
					Bunker.bunkerObj.initBunker();
				}

			}

			if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && lifeBunker >= 50) {
				if (missileUnit.intersects(evilhead)) {
					missile.setVisible(false);
					if (timerHard.isRunning() == true) {
						EvilHead.evilHead.throwFireballs();
					} else {
						EvilHead.evilHead.throwCanons();
					}
					EvilHead.evilHead.strikeHead();
					lifeEvilHead++;
				}

			}

		}

		@SuppressWarnings("unchecked")
		ArrayList<ShipRocket> rockets = MyShip.myShip.getRockets();

		for (ShipRocket rocket : rockets) {

			Rectangle rocketUnit = rocket.getBounds();

			for (Dragon dragon : Dragon.dragons) {

				Rectangle dragonunit = dragon.getBounds();

				if (rocketUnit.intersects(dragonunit)) {
					rocket.setVisible(false);
					dragon.setVisible(false);
					// new PlayWave1st("sounds/bloop.wav").start();
					dragonKilled++;
				}
			}

			if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && lifeBunker >= 50) {
				if (rocketUnit.intersects(evilhead)) {
					rocket.setVisible(false);
					if (timerHard.isRunning() == true) {
						EvilHead.evilHead.throwFireballs();
					} else {
						EvilHead.evilHead.throwCanons();
					}
					EvilHead.evilHead.strikeHead();
					lifeEvilHead++;
				}
			}

			if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && lifeBunker < 50) {
				if (rocketUnit.intersects(bunker)) {
					Bunker.bunkerObj.initBunkerHit();
					Bunker.bunkerObj.loadBullet();
					Bunker.bunkerObj.loadBullet2();
					rocket.setVisible(false);
					new PlayWave1st("sounds/scream.wav").start();
					LoadSounds.fuse.play();
					lifeBunker++;
				} else {

					Bunker.bunkerObj.initBunker();
				}

			}

		}

		@SuppressWarnings("unchecked")
		ArrayList<FireBall> fireballs = EvilHead.evilHead.getEvilMissiles();

		for (FireBall fireball : fireballs) {

			Rectangle fireballUnit = fireball.getBounds();

			Rectangle ship = MyShip.myShip.getBounds();

			if (fireballUnit.intersects(ship)) {
				lifeMyShip++;
				fireball.setVisible(false);
				new PlayWave1st("sounds/scream.wav").start();
				MyShip.myShip.shipDamaged();
				MyShip.myShip.upsideDown();
				if (lifeMyShip > 6) {
					killedByBunker = false;
					killedByDragon = false;
					killedByAlien = false;
					killedByEvilHead = true;
				}
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<BunkerBullet> bullets = Bunker.bunkerObj.getBullets();

		for (BunkerBullet bullet : bullets) {

			Rectangle bulletUnit = bullet.getBounds();

			Rectangle ship = MyShip.myShip.getBounds();

			if (bulletUnit.intersects(ship)) {
				lifeMyShip++;
				bullet.setVisible(false);
				new PlayWave1st("sounds/scream.wav").start();
				new PlayWave1st("sounds/explosion.wav").start();
				MyShip.myShip.shipDamaged();
				MyShip.myShip.upsideDown();
				if (lifeMyShip > 6) {
					killedByDragon = false;
					killedByAlien = false;
					killedByEvilHead = false;
					killedByBunker = true;
				}
			}

		}

		@SuppressWarnings("unchecked")
		ArrayList<BunkerBullet> bullets2 = Bunker.bunkerObj.getBullets2();

		for (BunkerBullet bullet : bullets2) {

			Rectangle bulletUnit2 = bullet.getBounds();

			Rectangle ship = MyShip.myShip.getBounds();

			if (bulletUnit2.intersects(ship)) {
				lifeMyShip++;
				bullet.setVisible(false);
				new PlayWave1st("sounds/scream.wav").start();
				new PlayWave1st("sounds/explosion.wav").start();
				MyShip.myShip.shipDamaged();
				MyShip.myShip.upsideDown();
				if (lifeMyShip > 6) {
					killedByDragon = false;
					killedByAlien = false;
					killedByEvilHead = false;
					killedByBunker = true;
				}
			}

		}

		@SuppressWarnings("unchecked")
		ArrayList<CanonBall> canons = EvilHead.evilHead.getCanons();

		for (CanonBall canon : canons) {

			Rectangle canonUnit = canon.getBounds();

			if (canonUnit.intersects(myship)) {
				lifeMyShip++;
				canon.setVisible(false);
				new PlayWave1st("sounds/burned.wav").start();
				MyShip.myShip.shipDamaged();
				MyShip.myShip.upsideDown();
				if (lifeMyShip > 6) {
					killedByDragon = false;
					killedByAlien = false;
					killedByBunker = false;
					killedByEvilHead = true;
				}
			}
		}

	}

}