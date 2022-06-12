// Collisions.java
// 
// Creator: Konstantin
// 

package game_engine;

// import java libraries:
import java.awt.Rectangle;
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

	public static void checkCollisions() {

		final Rectangle myship = PlayerShip.playerShip.getBounds();

		final Rectangle evilhead = EvilHead.evilHead.getBounds();

		final Rectangle bunker = Bunker.bunkerObj.getBounds();

		checkAlienCollision(myship);

		checkDragonCollision(myship);

		shipIntersectsHead(myship, evilhead);

		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && lifeBunker < 50) {
			shipIntersectsBunker(myship, bunker);
		}

		checkGoldCollision(myship);

		checkHealthCollision(myship);

		checkMissileCollision(evilhead);

		checkRocketCollision(evilhead, bunker);

		checkPlasmaCollision();

		Bunker.bulletsLeft = Bunker.bunkerObj.getBulletsLeft();
		iterateBunkerBullets(Bunker.bulletsLeft);

		Bunker.bulletsRight = Bunker.bunkerObj.getBulletsRight();
		iterateBunkerBullets(Bunker.bulletsRight);

		checkCanonCollision(myship);
	}

	public static void shipIntersectsAlien(Rectangle myship, Alien alien, Rectangle alienUnit) {
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

	public static void shipIntersectsDragon(Rectangle myship, Dragon dragon, Rectangle dragonunit) {
		if (myship.intersects(dragonunit) && Alien.aliens.isEmpty()) {
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

	public static void shipIntersectsBunker(Rectangle myship, Rectangle bunker) {
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

	public static void shipIntersectsGold(Rectangle myship, Gold gold, Rectangle goldUnit) {
		if (myship.intersects(goldUnit) && UpdateObjects.lifeBunker >= 50) {
			gold.setVisible(false);
			LoadSounds.GOT_GOLD.play();
		}
	}

	public static void shipIntersectsHealth(Rectangle myship, HealthPack health, Rectangle healthUnit) {
		if (myship.intersects(healthUnit) && Dragon.dragons.isEmpty()
				&& (UpdateObjects.lifeBunker < 50 || Gold.goldstack.isEmpty())) {
			health.setVisible(false);
			LoadSounds.GOT_HP.play();
		}
	}

	public static void missileIntersectsAlien(ShipMissile missile, Rectangle missileUnit, Alien alien,
			Rectangle alienUnit) {
		if (missileUnit.intersects(alienUnit)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			missile.setVisible(false);
			alien.setVisible(false);
			new PlayWave1st(SoundEffects.BLOOP.getSound()).start();
			alienKilled++;
		}
	}

	public static void shipIntersectsHead(Rectangle myship, Rectangle evilhead) {
		if (myship.intersects(evilhead)) {
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

	public static void missileIntersectsHead(Rectangle evilhead, ShipMissile missile, Rectangle missileUnit) {
		if (missileUnit.intersects(evilhead)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			new PlayWave1st(SoundEffects.BLOOP.getSound()).start();
			missile.setVisible(false);
			if (timerHard.isRunning()) {
				EvilHead.evilHead.plasmaBalls();
			} else {
				EvilHead.evilHead.canons();
			}
			EvilHead.evilHead.strikeHead();
			lifeEvilHead++;
		}
	}

	public static void rocketIntersectsDragon(ShipRocket rocket, Rectangle rocketUnit, Dragon dragon,
			Rectangle dragonunit) {
		if (rocketUnit.intersects(dragonunit)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			new PlayWave1st(SoundEffects.BLOOP.getSound()).start();
			rocket.setVisible(false);
			dragon.setVisible(false);
		}
	}

	public static void rocketIntersectsHead(Rectangle evilhead, ShipRocket rocket, Rectangle rocketUnit) {
		if (rocketUnit.intersects(evilhead)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			rocket.setVisible(false);
			if (timerHard.isRunning()) {
				EvilHead.evilHead.plasmaBalls();
			} else {
				EvilHead.evilHead.canons();
			}
			EvilHead.evilHead.strikeHead();
			lifeEvilHead++;
		}
	}

	public static void rocketIntersectsBunker(Rectangle bunker, ShipRocket rocket, Rectangle rocketUnit) {
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

	public static void plasmaBallIntersectsShip(PlasmaBall plasmaBall, Rectangle plasmaBallUnit, Rectangle ship) {
		if (plasmaBallUnit.intersects(ship)) {
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

	public static void bulletIntersectsShip(BunkerBullet bullet, Rectangle bulletUnit, Rectangle ship) {
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

	public static void canonIntersectsShip(Rectangle myship, CanonBall canon, Rectangle canonUnit) {
		if (canonUnit.intersects(myship)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			lifePlayerShip--;
			canon.setVisible(false);
			InitObjects.explosions.add(new Explosion(myship.getX(), myship.getY(), 30, 30));
			new PlayWave1st(SoundEffects.BURNED.getSound()).start();
//			PlayerShip.playerOne.upsideDown();
			PlayerShip.playerShip.x = -PlayerShip.playerShip.getX();
			Crosshair.crosshair.x = -Crosshair.crosshair.getX();
			if (lifePlayerShip == 0) {
				killedByDragon = false;
				killedByAlien = false;
				killedByBunker = false;
				killedByEvilHead = true;
			}
		}
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

	private static void iterateBunkerBullets(List<BunkerBullet> bullets) {
		bullets = bullets.stream().filter(bullet -> bullet != null).collect(Collectors.toList());

		final Rectangle ship = PlayerShip.playerShip.getBounds();

		for (final BunkerBullet bullet : bullets) {
			final Rectangle bulletUnit = bullet.getBounds();
			bulletIntersectsShip(bullet, bulletUnit, ship);
		}
	}

	private static void checkCanonCollision(final Rectangle myship) {
		final List<CanonBall> canons = EvilHead.evilHead.getCanons().stream().filter(canon -> canon != null)
				.collect(Collectors.toList());

		for (final CanonBall canon : canons) {
			final Rectangle canonUnit = canon.getBounds();
			canonIntersectsShip(myship, canon, canonUnit);
		}
	}

	private static void checkHealthCollision(final Rectangle myship) {
		HealthPack.healthpack = HealthPack.healthpack.stream().filter(hp -> hp != null).collect(Collectors.toList());

		for (final HealthPack health : HealthPack.healthpack) {
			final Rectangle healthUnit = health.getBounds();
			shipIntersectsHealth(myship, health, healthUnit);
		}
	}

	private static void checkGoldCollision(final Rectangle myship) {
		Gold.goldstack = Gold.goldstack.stream().filter(gold -> gold != null).collect(Collectors.toList());

		for (final Gold gold : Gold.goldstack) {
			final Rectangle goldUnit = gold.getBounds();
			shipIntersectsGold(myship, gold, goldUnit);
		}
	}

	private static void checkDragonCollision(final Rectangle myship) {
		Dragon.dragons = Dragon.dragons.stream().filter(dragon -> dragon != null).collect(Collectors.toList());

		for (final Dragon dragon : Dragon.dragons) {
			final Rectangle dragonUnit = dragon.getBounds();
			shipIntersectsDragon(myship, dragon, dragonUnit);
		}
	}

	private static void checkAlienCollision(final Rectangle myship) {
		Alien.aliens = Alien.aliens.stream().filter(alien -> alien != null).collect(Collectors.toList());

		for (final Alien alien : Alien.aliens) {
			final Rectangle alienUnit = alien.getBounds();
			shipIntersectsAlien(myship, alien, alienUnit);
		}
	}

	private static void checkPlasmaCollision() {
		final List<PlasmaBall> plasmaBalls = EvilHead.evilHead.getEvilPlasmaBalls().stream()
				.filter(plasmaBall -> plasmaBall != null).collect(Collectors.toList());

		final Rectangle ship = PlayerShip.playerShip.getBounds();

		for (final PlasmaBall plasmaBall : plasmaBalls) {
			final Rectangle plasmaBallUnit = plasmaBall.getBounds();
			plasmaBallIntersectsShip(plasmaBall, plasmaBallUnit, ship);
		}
	}

	private static void checkRocketCollision(final Rectangle evilhead, final Rectangle bunker) {
		List<ShipRocket> rockets = PlayerShip.playerShip.getRockets().stream().filter(rocket -> rocket != null)
				.collect(Collectors.toList());
		Dragon.dragons = Dragon.dragons.stream().filter(dragon -> dragon != null).collect(Collectors.toList());

		for (final ShipRocket rocket : rockets) {
			for (final Dragon dragon : Dragon.dragons) {
				rocketIntersectsDragon(rocket, rocket.getBounds(), dragon, dragon.getBounds());
			}

			if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && lifeBunker >= 50) {
				rocketIntersectsHead(evilhead, rocket, rocket.getBounds());
			}

			if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && lifeBunker < 50) {
				rocketIntersectsBunker(bunker, rocket, rocket.getBounds());
			}
		}
	}

	private static void checkMissileCollision(final Rectangle evilhead) {
		List<ShipMissile> missiles = PlayerShip.playerShip.getMissiles().stream().filter(missile -> missile != null)
				.collect(Collectors.toList());
		Alien.aliens = Alien.aliens.stream().filter(alien -> alien != null).collect(Collectors.toList());

		for (final ShipMissile missile : missiles) {
			for (final Alien alien : Alien.aliens) {
				missileIntersectsAlien(missile, missile.getBounds(), alien, alien.getBounds());
			}
			if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && lifeBunker >= 50) {
				missileIntersectsHead(evilhead, missile, missile.getBounds());
			}
		}
	}
}
