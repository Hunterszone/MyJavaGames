package game_engine;

import java.awt.Rectangle;
import java.util.List;

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
import sound_engine.SoundEffects;

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

			if (alien != null) {
				Rectangle alienUnit = alien.getBounds();
				shipIntersectsAlien(myship, alien, alienUnit);
			}
		}

		for (Dragon dragon : Dragon.dragons) {

			if (dragon != null) {
				Rectangle dragonunit = dragon.getBounds();
				shipIntersectsDragon(myship, dragon, dragonunit);
			}
		}

		shipIntersectsHead(myship, evilhead);

		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && lifeBunker < 50) {

			shipIntersectsBunker(myship, bunker);
		}

		for (Gold gold : Gold.goldstack) {

			if (gold != null) {
				Rectangle goldUnit = gold.getBounds();
				shipIntersectsGold(myship, gold, goldUnit);
			}
		}

		for (HealthPack health : HealthPack.healthpack) {
			if (health != null) {
				Rectangle healthUnit = health.getBounds();
				shipIntersectsHealth(myship, health, healthUnit);
			}
		}

		List<ShipMissile> missiles = MyShip.myShip.getMissiles();

		for (ShipMissile missile : missiles) {

			if (missile != null) {
				Rectangle missileUnit = missile.getBounds();

				for (Alien alien : Alien.aliens) {
					if (alien != null) {
						Rectangle alienUnit = alien.getBounds();
						missileIntersectsAlien(missile, missileUnit, alien, alienUnit);
					}
				}

				if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && lifeBunker < 50) {
					missileIntersectsBunker(bunker, missile, missileUnit);
				}

				if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty()
						&& lifeBunker >= 50) {
					missileIntersectsHead(evilhead, missile, missileUnit);
				}
			}
		}

		List<ShipRocket> rockets = MyShip.myShip.getRockets();

		for (ShipRocket rocket : rockets) {

			if (rocket != null) {
				Rectangle rocketUnit = rocket.getBounds();

				for (Dragon dragon : Dragon.dragons) {
					if (dragon != null) {
						Rectangle dragonunit = dragon.getBounds();
						rocketIntersectsDragon(rocket, rocketUnit, dragon, dragonunit);
					}
				}

				if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty()
						&& lifeBunker >= 50) {
					rocketIntersectsHead(evilhead, rocket, rocketUnit);
				}

				if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && lifeBunker < 50) {
					rocketIntersectsBunker(bunker, rocket, rocketUnit);
				}
			}
		}

		List<FireBall> fireballs = EvilHead.evilHead.getEvilMissiles();

		for (FireBall fireball : fireballs) {
			if (fireball != null) {
				Rectangle fireballUnit = fireball.getBounds();
				Rectangle ship = MyShip.myShip.getBounds();
				fireBallIntersectsShip(fireball, fireballUnit, ship);
			}
		}

		List<BunkerBullet> bullets = Bunker.bunkerObj.getBullets();

		for (BunkerBullet bullet : bullets) {

			if (bullet != null) {
				Rectangle bulletUnit = bullet.getBounds();
				Rectangle ship = MyShip.myShip.getBounds();
				bulletOneIntersectsShip(bullet, bulletUnit, ship);
			}
		}

		List<BunkerBullet> bullets2 = Bunker.bunkerObj.getBullets2();

		for (BunkerBullet bullet : bullets2) {
			if (bullet != null) {
				Rectangle bulletUnit2 = bullet.getBounds();
				Rectangle ship = MyShip.myShip.getBounds();
				bulletTwoIntersectsShip(bullet, bulletUnit2, ship);
			}
		}

		List<CanonBall> canons = EvilHead.evilHead.getCanons();

		for (CanonBall canon : canons) {
			if (canon != null) {
				Rectangle canonUnit = canon.getBounds();
				canonIntersectsShip(myship, canon, canonUnit);
			}
		}
	}

	public static boolean shipIntersectsAlien(Rectangle myship, Alien alien, Rectangle alienUnit) {
		if (myship.intersects(alienUnit)) {
			lifeMyShip++;
			alien.setVisible(false);
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			MyShip.myShip.upsideDown();
			MyShip.myShip.x = -MyShip.myShip.getX();
			alienKilled++;
			killedByBunker = false;
			killedByDragon = false;
			killedByEvilHead = false;
			killedByAlien = true;
			if (alien.isVisible())
				return false;
		}
		return true;
	}

	public static boolean shipIntersectsDragon(Rectangle myship, Dragon dragon, Rectangle dragonunit) {
		if (myship.intersects(dragonunit)) {
			lifeMyShip++;
			dragon.setVisible(false);
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			MyShip.myShip.upsideDown();
			killedByBunker = false;
			killedByEvilHead = false;
			killedByAlien = false;
			killedByDragon = true;
			if (dragon.isVisible())
				return false;
		}
		return true;
	}

	public static boolean shipIntersectsBunker(Rectangle myship, Rectangle bunker) {
		if (myship.intersects(bunker)) {
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			new PlayWave1st(SoundEffects.EXPLOSION.getSound()).start();
			MyShip.myShip.setVisible(false);
			Bunker.bunkerObj.setVisible(false);
			lifeMyShip = 7;
			killedByAlien = false;
			killedByDragon = false;
			killedByEvilHead = false;
			killedByBunker = true;
			if (MyShip.myShip.isVisible() || Bunker.bunkerObj.isVisible())
				return false;
		}
		return true;
	}

	public static boolean shipIntersectsGold(Rectangle myship, Gold gold, Rectangle goldUnit) {
		if (myship.intersects(goldUnit) && UpdateObjects.lifeBunker >= 50) {
			gold.setVisible(false);
			if (gold.isVisible())
				return false;
		}
		return true;
	}

	public static boolean shipIntersectsHealth(Rectangle myship, HealthPack health, Rectangle healthUnit) {
		if (myship.intersects(healthUnit) && UpdateObjects.lifeBunker >= 50 && Gold.goldstack.isEmpty()) {
			health.setVisible(false);
			LoadSounds.gotHealthPack.play();
			if (health.isVisible())
				return false;
		}
		return true;
	}

	public static boolean missileIntersectsAlien(ShipMissile missile, Rectangle missileUnit, Alien alien,
			Rectangle alienUnit) {
		if (missileUnit.intersects(alienUnit)) {
			missile.setVisible(false);
			alien.setVisible(false);
			new PlayWave1st(SoundEffects.BLOOP.getSound()).start();
			alienKilled++;
			if (missile.isVisible())
				return false;
		}
		return true;
	}

	public static boolean missileIntersectsBunker(Rectangle bunker, ShipMissile missile, Rectangle missileUnit) {
		if (missileUnit.intersects(bunker)) {
			Bunker.bunkerObj.drawBunkerHit();
			Bunker.bunkerObj.loadBullet();
			Bunker.bunkerObj.loadBullet2();
			missile.setVisible(false);
			new PlayWave1st(SoundEffects.BLOOP.getSound()).start();
			LoadSounds.fuse.play();
			lifeBunker++;
			if (missile.isVisible())
				return false;
		} else {
			if (Bunker.bunkerObj != null)
				Bunker.bunkerObj.drawBunker();
		}
		return true;
	}

	public static boolean shipIntersectsHead(Rectangle myship, Rectangle evilhead) {
		if (myship.intersects(evilhead)) {
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			MyShip.myShip.setVisible(false);
			EvilHead.evilHead.setVisible(false);
			lifeMyShip = 7;
			killedByBunker = false;
			killedByAlien = false;
			killedByDragon = false;
			killedByEvilHead = true;
			if (MyShip.myShip.isVisible() || EvilHead.evilHead.isVisible())
				return false;
		}
		return true;
	}

	public static boolean missileIntersectsHead(Rectangle evilhead, ShipMissile missile, Rectangle missileUnit) {
		if (missileUnit.intersects(evilhead)) {
			missile.setVisible(false);
			if (timerHard.isRunning() == true) {
				EvilHead.evilHead.throwFireballs();
			} else if (missile.isVisible())
				return false;
			else {
				EvilHead.evilHead.throwCanons();
			}
			EvilHead.evilHead.strikeHead();
			lifeEvilHead++;
		}
		return true;
	}

	public static boolean rocketIntersectsDragon(ShipRocket rocket, Rectangle rocketUnit, Dragon dragon,
			Rectangle dragonunit) {
		if (rocketUnit.intersects(dragonunit)) {
			rocket.setVisible(false);
			dragon.setVisible(false);
//					new PlayWave1st(SoundEffects.BLOOP.getSound()).start();
			if (rocket.isVisible())
				return false;
		}
		return true;
	}

	public static boolean rocketIntersectsHead(Rectangle evilhead, ShipRocket rocket, Rectangle rocketUnit) {
		if (rocketUnit.intersects(evilhead)) {
			rocket.setVisible(false);
			if (timerHard.isRunning() == true) {
				EvilHead.evilHead.throwFireballs();
			} else if (rocket.isVisible())
				return false;
			else {
				EvilHead.evilHead.throwCanons();
			}
			EvilHead.evilHead.strikeHead();
			lifeEvilHead++;
		}
		return true;
	}

	public static boolean rocketIntersectsBunker(Rectangle bunker, ShipRocket rocket, Rectangle rocketUnit) {
		if (rocketUnit.intersects(bunker)) {
			Bunker.bunkerObj.drawBunkerHit();
			Bunker.bunkerObj.loadBullet();
			Bunker.bunkerObj.loadBullet2();
			rocket.setVisible(false);
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			LoadSounds.fuse.play();
			lifeBunker++;
			if (rocket.isVisible())
				return false;
		} else {
			if (Bunker.bunkerObj != null)
				Bunker.bunkerObj.drawBunker();
		}
		return true;
	}

	public static boolean fireBallIntersectsShip(FireBall fireball, Rectangle fireballUnit, Rectangle ship) {
		if (fireballUnit.intersects(ship)) {
			lifeMyShip++;
			fireball.setVisible(false);
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			MyShip.myShip.upsideDown();
			if (lifeMyShip > 6) {
				killedByBunker = false;
				killedByDragon = false;
				killedByAlien = false;
				killedByEvilHead = true;
			}
			if (fireball.isVisible())
				return false;
		}
		return true;
	}

	public static boolean bulletOneIntersectsShip(BunkerBullet bullet, Rectangle bulletUnit, Rectangle ship) {
		if (bulletUnit.intersects(ship)) {
			lifeMyShip++;
			bullet.setVisible(false);
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			new PlayWave1st(SoundEffects.EXPLOSION.getSound()).start();
			MyShip.myShip.upsideDown();
			if (lifeMyShip > 6) {
				killedByDragon = false;
				killedByAlien = false;
				killedByEvilHead = false;
				killedByBunker = true;
			}
			if (bullet.isVisible())
				return false;
		}
		return true;
	}

	public static boolean bulletTwoIntersectsShip(BunkerBullet bullet, Rectangle bulletUnit2, Rectangle ship) {
		if (bulletUnit2.intersects(ship)) {
			lifeMyShip++;
			bullet.setVisible(false);
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			new PlayWave1st(SoundEffects.EXPLOSION.getSound()).start();
			MyShip.myShip.upsideDown();
			if (lifeMyShip > 6) {
				killedByDragon = false;
				killedByAlien = false;
				killedByEvilHead = false;
				killedByBunker = true;
			}
			if (bullet.isVisible())
				return false;
		}
		return true;
	}

	public static boolean canonIntersectsShip(Rectangle myship, CanonBall canon, Rectangle canonUnit) {
		if (canonUnit.intersects(myship)) {
			lifeMyShip++;
			canon.setVisible(false);
			new PlayWave1st(SoundEffects.BURNED.getSound()).start();
			MyShip.myShip.upsideDown();
			if (lifeMyShip > 6) {
				killedByDragon = false;
				killedByAlien = false;
				killedByBunker = false;
				killedByEvilHead = true;
			}
			if (canon.isVisible())
				return false;
		}
		return true;
	}

	public static String[] getNameAndKilledCount(String enemyName) {
		if (enemyName.equalsIgnoreCase(Alien.class.getName())) {
			Integer alienKilledd = alienKilled;
			return new String[] { enemyName, alienKilledd.toString() };
		}
		if (enemyName.equalsIgnoreCase(Dragon.class.getName())) {
			Integer dragonKilledd = dragonKilled;
			return new String[] { enemyName, dragonKilledd.toString() };
		}
		return new String[] { "" };
	}
}