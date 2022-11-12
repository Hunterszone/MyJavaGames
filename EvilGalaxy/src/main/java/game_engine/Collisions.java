// Collisions.java
// 
// Creator: Konstantin
// 

package game_engine;

// import java libraries:
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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

	public static void checkBulletCollision(List<BunkerBullet> bullets) {
		bullets = bullets.stream().filter(bullet -> bullet != null).collect(Collectors.toList());

		final Rectangle ship = PlayerShip.playerShip.getBounds();

		for (final BunkerBullet bullet : bullets) {
			final Rectangle bulletUnit = bullet.getBounds();
			bulletIntersectsShip(bullet, bulletUnit, ship);
		}
	}

	public static void checkCanonCollision(final Rectangle myship) {
		final List<CanonBall> canons = CanonBall.canonBalls.stream().filter(canon -> canon != null)
				.collect(Collectors.toList());

		for (final CanonBall canon : canons) {
			final Rectangle canonUnit = canon.getBounds();
			canonIntersectsShip(myship, canon, canonUnit);
		}
	}

	public static void checkHealthCollision(final Rectangle myship) {
		HealthPack.healthpacks = HealthPack.healthpacks.stream().filter(hp -> hp != null).collect(Collectors.toList());

		for (final HealthPack health : HealthPack.healthpacks) {
			shipIntersectsHealth(myship, health, health.getBounds());
		}
	}

	public static void checkGoldCollision(final Rectangle myship) {
		shipIntersectsGold(myship);
	}

	public static void checkAlienCollision(final Rectangle myship) {
		Alien.aliens = Alien.aliens.stream().filter(alien -> alien != null).collect(Collectors.toList());

		for (final Alien alien : Alien.aliens) {
			final Rectangle alienUnit = alien.getBounds();
			shipIntersectsAlien(myship, alien, alienUnit);
		}
	}

	public static void checkEvilHeadCollision(final Rectangle myship, final Rectangle evilHead) {
		shipIntersectsHead(myship, evilHead);
	}

	public static void checkBunkerCollision(final Rectangle myship, final Rectangle bunker) {
		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && lifeBunker < 50) {
			shipIntersectsBunker(myship, bunker);
		}
	}

	public static void checkPlasmaCollision(final Rectangle myShip) {
		final List<PlasmaBall> plasmaBalls = EvilHead.evilHead.getEvilPlasmaBalls().stream()
				.filter(plasmaBall -> plasmaBall != null).collect(Collectors.toList());

		for (final PlasmaBall plasmaBall : plasmaBalls) {
			final Rectangle plasmaBallUnit = plasmaBall.getBounds();
			plasmaBallIntersectsShip(plasmaBall, plasmaBallUnit, myShip);
		}
	}

	public static void checkRocketCollision(final Rectangle evilhead, final Rectangle bunker) {
		List<ShipRocket> rockets = PlayerShip.playerShip.getRockets().stream().filter(rocket -> rocket != null)
				.collect(Collectors.toList());

		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && lifeBunker >= 50) {
			for (final ShipRocket rocket : rockets) {
				rocketIntersectsHead(evilhead, rocket, rocket.getBounds());
			}
		}

		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && lifeBunker < 50) {
			for (final ShipRocket rocket : rockets) {
				rocketIntersectsBunker(bunker, rocket, rocket.getBounds());
			}
		}
	}

	public static void checkMissileCollision(final Rectangle evilhead) {
		List<ShipMissile> missiles = PlayerShip.playerShip.getMissiles().stream().filter(missile -> missile != null)
				.collect(Collectors.toList());
		Alien.aliens = Alien.aliens.stream().filter(alien -> alien != null).collect(Collectors.toList());

		if (!Alien.aliens.isEmpty()) {
			for (final Alien alien : Alien.aliens) {
				for (final ShipMissile missile : missiles) {
					missileIntersectsAlien(missile, missile.getBounds(), alien, alien.getBounds());	
				}
			}
		}

		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && lifeBunker >= 50) {
			for (final ShipMissile missile : missiles) {
				missileIntersectsHead(evilhead, missile, missile.getBounds());
			}
		}
	}

	public static void checkDragonCollision(final Dragon dragon) {
		dragon.setVisible(true);
		
		shipIntersectsDragon(PlayerShip.playerShip.getBounds(), dragon, dragon.getBounds());
		
		List<ShipRocket> rockets = PlayerShip.playerShip.getRockets().stream().filter(rocket -> rocket != null)
				.collect(Collectors.toList());
		
		for (final ShipRocket rocket : rockets) {
			rocketIntersectsDragon(rocket, rocket.getBounds(), dragon, dragon.getBounds());
		}

	}

	private static void shipIntersectsAlien(Rectangle myship, Alien alien, Rectangle alienUnit) {
		if (myship.intersects(alienUnit)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			lifePlayerShip--;
			alien.setVisible(false);
			InitObjects.explosions.add(new Explosion(myship.getX(), myship.getY(), 30, 30));
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
//			PlayerShip.playerOne.upsideDown();
			PlayerShip.playerShip.x = -PlayerShip.playerShip.getX();
			Crosshair.crosshair.x = -Crosshair.crosshair.getX();
			alienKilled++;
			killedByBunker = false;
			killedByDragon = false;
			killedByEvilHead = false;
			killedByAlien = true;
		}
	}

	private static void shipIntersectsDragon(Rectangle myship, Dragon dragon, Rectangle dragonunit) {
		if (Alien.aliens.isEmpty() && myship.intersects(dragonunit)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			lifePlayerShip--;
			dragon.setVisible(false);
			InitObjects.explosions.add(new Explosion(dragon.getX(), dragon.getY(), 30, 30));
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
//			PlayerShip.playerOne.upsideDown();
			dragonKilled++;
			killedByBunker = false;
			killedByEvilHead = false;
			killedByAlien = false;
			killedByDragon = true;
		}
	}

	private static void shipIntersectsBunker(Rectangle myship, Rectangle bunker) {
		if (myship.intersects(bunker)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			InitObjects.explosions.add(new Explosion(myship.getX(), myship.getY(), 30, 30));
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			new PlayWave1st(SoundEffects.EXPLOSION.getSound()).start();
			lifePlayerShip = 0;
			killedByAlien = false;
			killedByDragon = false;
			killedByEvilHead = false;
			killedByBunker = true;
		}
	}

	private static void shipIntersectsGold(Rectangle myship) {
		Gold.goldstack = Gold.goldstack.stream().filter(gold -> gold != null).collect(Collectors.toList());
		
		for (Iterator<Gold> it = Gold.goldstack.iterator(); it.hasNext(); ) {
			Gold gold = it.next();
			if (myship.intersects(gold.getBounds()) && lifeBunker >= 50) {
				gold.setVisible(false);
				LoadSounds.GOT_GOLD.play();
				it.remove();
			}
		}
	}

	private static void shipIntersectsHealth(Rectangle myship, HealthPack health, Rectangle healthUnit) {
		if (myship.intersects(healthUnit) && 
				(!Alien.aliens.isEmpty() || lifeBunker < 50 || Gold.goldstack.isEmpty())) {
			health.setVisible(false);
			LoadSounds.GOT_HP.play();
			if (lifePlayerShip <= 3) {
				lifePlayerShip++;
			}
		}
	}

	private static void missileIntersectsAlien(ShipMissile missile, Rectangle missileUnit, Alien alien,
			Rectangle alienUnit) {
		if (missileUnit.intersects(alienUnit)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			missile.setVisible(false);
			alien.loadCanon();
			alien.setVisible(false);
			new PlayWave1st(SoundEffects.BLOOP.getSound()).start();
			alienKilled++;
		}
	}

	private static void shipIntersectsHead(Rectangle myship, Rectangle evilhead) {
		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && 
				lifeBunker >= 50 && myship.intersects(evilhead)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			InitObjects.explosions.add(new Explosion(myship.getX(), myship.getY(), 30, 30));
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			lifePlayerShip = 0;
			killedByBunker = false;
			killedByAlien = false;
			killedByDragon = false;
			killedByEvilHead = true;
		}
	}

	private static void missileIntersectsHead(Rectangle evilhead, ShipMissile missile, Rectangle missileUnit) {
		if (missileUnit.intersects(evilhead)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			new PlayWave1st(SoundEffects.BLOOP.getSound()).start();
			missile.setVisible(false);
			if (timerHard.isRunning()) {
				EvilHead.evilHead.loadPlasmaBall();
			} else {
				EvilHead.evilHead.loadCanon();
			}
			EvilHead.evilHead.strikeHead();
			lifeEvilHead++;
		}
	}

	private static void rocketIntersectsDragon(ShipRocket rocket, Rectangle rocketUnit, Dragon dragon,
			Rectangle dragonunit) {
		if (rocketUnit.intersects(dragonunit)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			new PlayWave1st(SoundEffects.BLOOP.getSound()).start();
			rocket.setVisible(false);
			dragon.setVisible(false);
			dragonKilled++;
		}
	}

	private static void rocketIntersectsHead(Rectangle evilhead, ShipRocket rocket, Rectangle rocketUnit) {
		if (rocketUnit.intersects(evilhead)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			if (timerHard.isRunning()) {
				EvilHead.evilHead.loadPlasmaBall();
			} else {
				EvilHead.evilHead.loadCanon();
			}
			EvilHead.evilHead.strikeHead();
			rocket.setVisible(false);
			lifeEvilHead++;
		}
	}

	private static void rocketIntersectsBunker(Rectangle bunker, ShipRocket rocket, Rectangle rocketUnit) {
		if (rocketUnit.intersects(bunker)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			Bunker.bunkerObj.drawBunkerHit();
			Bunker.bunkerObj.loadBulletsLeft();
			Bunker.bunkerObj.loadBulletsRight();
			rocket.setVisible(false);
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			LoadSounds.HIT.play();
			lifeBunker++;
		}
	}

	private static void plasmaBallIntersectsShip(PlasmaBall plasmaBall, Rectangle plasmaBallUnit, Rectangle ship) {
		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && 
				lifeBunker >= 50 && plasmaBallUnit.intersects(ship)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			lifePlayerShip--;
			plasmaBall.setVisible(false);
			InitObjects.explosions.add(new Explosion(ship.getX(), ship.getY(), 30, 30));
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			PlayerShip.playerShip.x = -PlayerShip.playerShip.getX();
			if (lifePlayerShip == 0) {
				killedByBunker = false;
				killedByDragon = false;
				killedByAlien = false;
				killedByEvilHead = true;
			}
		}
	}

	private static void bulletIntersectsShip(BunkerBullet bullet, Rectangle bulletUnit, Rectangle ship) {
		if (bulletUnit.intersects(ship)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			lifePlayerShip--;
			bullet.setVisible(false);
			InitObjects.explosions.add(new Explosion(ship.getX(), ship.getY(), 30, 30));
			new PlayWave1st(SoundEffects.SCREAM.getSound()).start();
			new PlayWave1st(SoundEffects.EXPLOSION.getSound()).start();
//			PlayerShip.playerOne.upsideDown();
			PlayerShip.playerShip.x = -PlayerShip.playerShip.getX();
			Crosshair.crosshair.x = -Crosshair.crosshair.getX();
			if (lifePlayerShip == 0) {
				killedByDragon = false;
				killedByAlien = false;
				killedByEvilHead = false;
				killedByBunker = true;
			}
		}
	}

	private static void canonIntersectsShip(Rectangle myship, CanonBall canon, Rectangle canonUnit) {
		if ((!Alien.aliens.isEmpty() || lifeBunker >= 50) && canonUnit.intersects(myship)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			lifePlayerShip--;
			canon.setVisible(false);
			InitObjects.explosions.add(new Explosion(myship.getX(), myship.getY(), 30, 30));
			new PlayWave1st(SoundEffects.BURNED.getSound()).start();
//			PlayerShip.playerOne.upsideDown();
			PlayerShip.playerShip.x = -PlayerShip.playerShip.getX();
			Crosshair.crosshair.x = -Crosshair.crosshair.getX();
			if (lifePlayerShip == 0) {
				if (!Alien.aliens.isEmpty()) {					
					killedByDragon = false;
					killedByBunker = false;
					killedByEvilHead = false;
					killedByAlien = true;
				} else {
					killedByDragon = false;
					killedByAlien = false;
					killedByBunker = false;
					killedByEvilHead = true;
				}
			} 
		}
	}
}
