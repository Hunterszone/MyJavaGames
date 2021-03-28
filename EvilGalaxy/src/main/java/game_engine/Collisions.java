// Collisions.java
// 
// Creator: Konstantin
// 

package game_engine;

// import java libraries:
import java.awt.Rectangle;
import java.util.List;

// import game packages:
import entities.Alien;
import entities.Bunker;
import entities.Crosshair;
import entities.Dragon;
import entities.EvilHead;
import entities.PlayerShip;
import enums.SoundEffects;
import items.BunkerBullet;
import items.CanonBall;
import items.Explosion;
import items.Gold;
import items.HealthPack;
import items.PlasmaBall;
import items.ShipMissile;
import items.ShipRocket;
import sound_engine.PlayWave1st;
import util.LoadSounds;

public abstract class Collisions extends UpdateObjects {

	public static int alienKilled = 0;
	public static int dragonKilled = 0;

	public static boolean killedByAlien = false;
	public static boolean killedByDragon = false;
	public static boolean killedByBunker = false;
	public static boolean killedByEvilHead = false;

	private static final long serialVersionUID = 1L;

	public static void checkCollisions() {

		final Rectangle myship = PlayerShip.playerOne.getBounds();

		final Rectangle evilhead = EvilHead.evilHead.getBounds();

		final Rectangle bunker = Bunker.bunkerObj.getBounds();

		for (final Alien alien : Alien.aliens) {

			if (alien != null) {
				final Rectangle alienUnit = alien.getBounds();
				shipIntersectsAlien(myship, alien, alienUnit);
			}
		}

		for (final Dragon dragon : Dragon.dragons) {

			if (dragon != null) {
				final Rectangle dragonunit = dragon.getBounds();
				shipIntersectsDragon(myship, dragon, dragonunit);
			}
		}

		shipIntersectsHead(myship, evilhead);

		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && lifeBunker < 50) {

			shipIntersectsBunker(myship, bunker);
		}

		for (final Gold gold : Gold.goldstack) {

			if (gold != null) {
				final Rectangle goldUnit = gold.getBounds();
				shipIntersectsGold(myship, gold, goldUnit);
			}
		}

		for (final HealthPack health : HealthPack.healthpack) {
			if (health != null) {
				final Rectangle healthUnit = health.getBounds();
				shipIntersectsHealth(myship, health, healthUnit);
			}
		}

		final List<ShipMissile> missiles = PlayerShip.playerOne.getMissiles();

		for (final ShipMissile missile : missiles) {

			if (missile != null) {
				final Rectangle missileUnit = missile.getBounds();

				for (final Alien alien : Alien.aliens) {
					if (alien != null) {
						final Rectangle alienUnit = alien.getBounds();
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

		final List<ShipRocket> rockets = PlayerShip.playerOne.getRockets();

		for (final ShipRocket rocket : rockets) {

			if (rocket != null) {
				final Rectangle rocketUnit = rocket.getBounds();

				for (final Dragon dragon : Dragon.dragons) {
					if (dragon != null) {
						final Rectangle dragonunit = dragon.getBounds();
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

		final List<PlasmaBall> plasmaBalls = EvilHead.evilHead.getEvilPlasmaBalls();

		for (final PlasmaBall plasmaBall : plasmaBalls) {
			if (plasmaBall != null) {
				final Rectangle plasmaBallUnit = plasmaBall.getBounds();
				final Rectangle ship = PlayerShip.playerOne.getBounds();
				plasmaBallIntersectsShip(plasmaBall, plasmaBallUnit, ship);
			}
		}

		Bunker.bullets = Bunker.bunkerObj.getBulletsLeft();

		for (final BunkerBullet bullet : Bunker.bullets) {

			if (bullet != null) {
				final Rectangle bulletUnit = bullet.getBounds();
				final Rectangle ship = PlayerShip.playerOne.getBounds();
				bulletOneIntersectsShip(bullet, bulletUnit, ship);
			}
		}

		Bunker.bullets2 = Bunker.bunkerObj.getBulletsRight();

		for (final BunkerBullet bullet : Bunker.bullets2) {
			if (bullet != null) {
				final Rectangle bulletUnit2 = bullet.getBounds();
				final Rectangle ship = PlayerShip.playerOne.getBounds();
				bulletTwoIntersectsShip(bullet, bulletUnit2, ship);
			}
		}

		final List<CanonBall> canons = EvilHead.evilHead.getCanons();

		for (final CanonBall canon : canons) {
			if (canon != null) {
				final Rectangle canonUnit = canon.getBounds();
				canonIntersectsShip(myship, canon, canonUnit);
			}
		}
	}

	public static boolean shipIntersectsAlien(Rectangle myship, Alien alien, Rectangle alienUnit) {
		if (myship.intersects(alienUnit)) {

//			FrameUtils.vibrate(MouseInputHandler.main);

			lifePlayerShip++;
			alien.setVisible(false);
			InitObjects.explosions.add(new Explosion(myship.getX(), myship.getY(), 30, 30));
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			PlayerShip.playerOne.upsideDown();
			PlayerShip.playerOne.x = -PlayerShip.playerOne.getX();
			Crosshair.crosshair.x = -Crosshair.crosshair.getX();
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
		if (myship.intersects(dragonunit) && Alien.aliens.isEmpty()) {

//			FrameUtils.vibrate(MouseInputHandler.main);

			lifePlayerShip++;
			dragon.setVisible(false);
			InitObjects.explosions.add(new Explosion(dragon.getX(), dragon.getY(), 30, 30));
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			PlayerShip.playerOne.upsideDown();
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

//			FrameUtils.vibrate(MouseInputHandler.main);
			
			InitObjects.explosions.add(new Explosion(myship.getX(), myship.getY(), 30, 30));
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			new PlayWave1st(SoundEffects.EXPLOSION.getSound()).start();
			PlayerShip.playerOne.setVisible(false);
			Bunker.bunkerObj.setVisible(false);
			lifePlayerShip = 7;
			killedByAlien = false;
			killedByDragon = false;
			killedByEvilHead = false;
			killedByBunker = true;
			if (PlayerShip.playerOne.isVisible() || Bunker.bunkerObj.isVisible())
				return false;
		}
		return true;
	}

	public static boolean shipIntersectsGold(Rectangle myship, Gold gold, Rectangle goldUnit) {
		if (myship.intersects(goldUnit) && UpdateObjects.lifeBunker >= 50) {
			gold.setVisible(false);
			LoadSounds.gotGoldBar.play();
			if (gold.isVisible())
				return false;
		}
		return true;
	}

	public static boolean shipIntersectsHealth(Rectangle myship, HealthPack health, Rectangle healthUnit) {
		if (myship.intersects(healthUnit) && Dragon.dragons.isEmpty() && (UpdateObjects.lifeBunker < 50 || Gold.goldstack.isEmpty())) {
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
			
//			FrameUtils.vibrate(MouseInputHandler.main);
			
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
			
//			FrameUtils.vibrate(MouseInputHandler.main);
			
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

//			FrameUtils.vibrate(MouseInputHandler.main);

			InitObjects.explosions.add(new Explosion(myship.getX(), myship.getY(), 30, 30));
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			PlayerShip.playerOne.setVisible(false);
			EvilHead.evilHead.setVisible(false);
			lifePlayerShip = 7;
			killedByBunker = false;
			killedByAlien = false;
			killedByDragon = false;
			killedByEvilHead = true;
			if (PlayerShip.playerOne.isVisible() || EvilHead.evilHead.isVisible())
				return false;
		}
		return true;
	}

	public static boolean missileIntersectsHead(Rectangle evilhead, ShipMissile missile, Rectangle missileUnit) {
		if (missileUnit.intersects(evilhead)) {

//			FrameUtils.vibrate(MouseInputHandler.main);
			
			new PlayWave1st(SoundEffects.BLOOP.getSound()).start();
			missile.setVisible(false);
			if (timerHard.isRunning() == true) {
				EvilHead.evilHead.throwPlasmaBalls();
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
			
//			FrameUtils.vibrate(MouseInputHandler.main);
			
			new PlayWave1st(SoundEffects.BLOOP.getSound()).start();
			rocket.setVisible(false);
			dragon.setVisible(false);
			if (rocket.isVisible())
				return false;
		}
		return true;
	}

	public static boolean rocketIntersectsHead(Rectangle evilhead, ShipRocket rocket, Rectangle rocketUnit) {
		
		if (rocketUnit.intersects(evilhead)) {

//			FrameUtils.vibrate(MouseInputHandler.main);
			
			rocket.setVisible(false);
			if (timerHard.isRunning() == true) {
				EvilHead.evilHead.throwPlasmaBalls();
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

//			FrameUtils.vibrate(MouseInputHandler.main);
			
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

	public static boolean plasmaBallIntersectsShip(PlasmaBall plasmaBall, Rectangle plasmaBallUnit, Rectangle ship) {
	
		if (plasmaBallUnit.intersects(ship)) {

//			FrameUtils.vibrate(MouseInputHandler.main);

			lifePlayerShip++;
			plasmaBall.setVisible(false);
			InitObjects.explosions.add(new Explosion(ship.getX(), ship.getY(), 30, 30));
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			PlayerShip.playerOne.upsideDown();
			PlayerShip.playerOne.x = -PlayerShip.playerOne.getX();
			if (lifePlayerShip > 6) {
				killedByBunker = false;
				killedByDragon = false;
				killedByAlien = false;
				killedByEvilHead = true;
			}
			if (plasmaBall.isVisible())
				return false;
		}
		return true;
	}

	public static boolean bulletOneIntersectsShip(BunkerBullet bullet, Rectangle bulletUnit, Rectangle ship) {
	
		if (bulletUnit.intersects(ship)) {

//			FrameUtils.vibrate(MouseInputHandler.main);

			lifePlayerShip++;
			bullet.setVisible(false);
			InitObjects.explosions.add(new Explosion(ship.getX(), ship.getY(), 30, 30));
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			new PlayWave1st(SoundEffects.EXPLOSION.getSound()).start();
			PlayerShip.playerOne.upsideDown();
			PlayerShip.playerOne.x = -PlayerShip.playerOne.getX();
			Crosshair.crosshair.x = -Crosshair.crosshair.getX();
			if (lifePlayerShip > 6) {
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

//			FrameUtils.vibrate(MouseInputHandler.main);

			lifePlayerShip++;
			bullet.setVisible(false);
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			new PlayWave1st(SoundEffects.EXPLOSION.getSound()).start();
			InitObjects.explosions.add(new Explosion(ship.getX(), ship.getY(), 30, 30));
			PlayerShip.playerOne.upsideDown();
			PlayerShip.playerOne.x = -PlayerShip.playerOne.getX();
			Crosshair.crosshair.x = -Crosshair.crosshair.getX();
			if (lifePlayerShip > 6) {
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

//			FrameUtils.vibrate(MouseInputHandler.main);

			lifePlayerShip++;
			canon.setVisible(false);
			InitObjects.explosions.add(new Explosion(myship.getX(), myship.getY(), 30, 30));
			new PlayWave1st(SoundEffects.BURNED.getSound()).start();
			PlayerShip.playerOne.upsideDown();
			PlayerShip.playerOne.x = -PlayerShip.playerOne.getX();
			Crosshair.crosshair.x = -Crosshair.crosshair.getX();
			if (lifePlayerShip > 6) {
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
			final Integer alienKilledd = alienKilled;
			return new String[] { enemyName, alienKilledd.toString() };
		}
		if (enemyName.equalsIgnoreCase(Dragon.class.getName())) {
			final Integer dragonKilledd = dragonKilled;
			return new String[] { enemyName, dragonKilledd.toString() };
		}
		return new String[] { "" };
	}
}
