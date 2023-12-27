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

import enemy.Alien;
import enemy.Bunker;
import enemy.Crosshair;
import enemy.Dragon;
import enemy.EvilHead;
import enemy.PlayerShip;
import enums.SoundEffect;
import items.Angel;
import items.BunkerBullet;
import items.CanonBall;
import items.Explosion;
import items.Gold;
import items.HealthPack;
import items.PlasmaBall;
import items.ShipMissile;
import items.ShipRocket;
import sound_engine.PlayWave1st;
import util.Flags;
import util.LivesAndCounts;
import util.ActionPerformer;
import util.LoadSounds;

public abstract class Collisions {

	public static void checkBulletCollision(List<BunkerBullet> bullets) {
		bullets = bullets.stream().filter(bullet -> bullet != null).collect(Collectors.toList());

		final Rectangle ship = PlayerShip.playerShip.getBounds();

		for (final BunkerBullet bullet : bullets) {
			final Rectangle bulletUnit = bullet.getBounds();
			bulletIntersectsShip(bullet, bulletUnit, ship);
		}
	}

	public static void checkCanonCollision(final Rectangle myShip) {
		final List<CanonBall> canons = CanonBall.canonBalls.stream().filter(canon -> canon != null)
				.collect(Collectors.toList());

		for (final CanonBall canon : canons) {
			final Rectangle canonUnit = canon.getBounds();
			canonIntersectsShip(myShip, canon, canonUnit);
		}
	}

	public static void checkHealthCollision(final Rectangle myShip) {
		HealthPack.healthpacks = HealthPack.healthpacks.stream().filter(hp -> hp != null).collect(Collectors.toList());

		for (final HealthPack health : HealthPack.healthpacks) {
			shipIntersectsHealth(myShip, health, health.getBounds());
		}
	}

	public static void checkGoldCollision(final Rectangle myShip) {
		shipIntersectsGold(myShip);
	}

	public static void checkAlienCollision(final Rectangle myShip) {
		for (final Alien alien : Alien.aliens) {
			shipIntersectsAlien(myShip, alien);
		}
	}

	public static void checkEvilHeadCollision(final Rectangle myShip, final Rectangle evilHead) {
		shipIntersectsHead(myShip, evilHead);
	}

	public static void checkBunkerCollision(final Rectangle myShip, final Rectangle bunker) {
		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && LivesAndCounts.getLifeBunker() < 50) {
			shipIntersectsBunker(myShip, bunker);
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

		List<ShipRocket> rockets = PlayerShip.playerShip.getRockets()
				.stream()
				.filter(rocket -> rocket != null)
				.collect(Collectors.toList());

		if (Alien.aliens.isEmpty()) {
			for (final ShipRocket rocket : rockets) {
				rocketIntersectsDragon(rocket);
			}
		}

		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty()
				&& LivesAndCounts.getLifeBunker() >= 50) {
			for (final ShipRocket rocket : rockets) {
				rocketIntersectsHead(evilhead, rocket, rocket.getBounds());
			}
		}

		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && LivesAndCounts.getLifeBunker() < 50) {
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
			for (final ShipMissile missile : missiles) {
				missileIntersectsAlien(missile);
			}
		}

		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty()
				&& LivesAndCounts.getLifeBunker() >= 50) {
			for (final ShipMissile missile : missiles) {
				missileIntersectsHead(evilhead, missile, missile.getBounds());
			}
		}
	}

	public static void checkDragonCollision(final Rectangle myShip) {
		if (Alien.aliens.isEmpty()) {
			LoadSounds.TAUNT.loop();
			shipIntersectsDragon(myShip);
		}
	}

	private static void shipIntersectsAlien(Rectangle myShip, Alien alien) {
		if (myShip.intersects(alien.getBounds())) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			alien.setVisible(false);
			Explosion.explosions.add(new Explosion(myShip.getX(), myShip.getY(), 30, 30));
			new PlayWave1st(SoundEffect.SCREAM.getSound()).start();
//			PlayerShip.playerOne.upsideDown();
			PlayerShip.playerShip.x = -PlayerShip.playerShip.getX();
			Crosshair.crosshair.x = -Crosshair.crosshair.getX();
			LivesAndCounts.decreasePlayerShipLife(1);
			LivesAndCounts.increaseAlienKilledCount(1);
			Flags.killedByBunker = false;
			Flags.killedByDragon = false;
			Flags.killedByEvilHead = false;
			Flags.killedByAlien = true;
		}
	}

	private static void shipIntersectsDragon(Rectangle myShip) {
		for (Iterator<Dragon> it = Dragon.dragons.iterator(); it.hasNext();) {
			Dragon dragon = it.next();
			dragon.setVisible(true);
			if (myShip.intersects(dragon.getBounds())) {
//				FrameUtils.vibrate(MouseInputHandler.main);
				dragon.setVisible(false);
				LivesAndCounts.decreasePlayerShipLife(1);
				LivesAndCounts.increaseDragonKilledCount(1);
				Angel.angels.add(new Angel(dragon.getX(), dragon.getY()));
				Explosion.explosions.add(new Explosion(dragon.getX(), dragon.getY(), 30, 30));
				new PlayWave1st(SoundEffect.SCREAM.getSound()).start();
//				PlayerShip.playerOne.upsideDown();
				Flags.killedByBunker = false;
				Flags.killedByEvilHead = false;
				Flags.killedByAlien = false;
				Flags.killedByDragon = true;
				it.remove();
			}
		}
	}

	private static void shipIntersectsBunker(Rectangle myShip, Rectangle bunker) {
		if (myShip.intersects(bunker)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			Explosion.explosions.add(new Explosion(myShip.getX(), myShip.getY(), 30, 30));
			new PlayWave1st(SoundEffect.SCREAM.getSound()).start();
			new PlayWave1st(SoundEffect.EXPLOSION.getSound()).start();
			LivesAndCounts.setLifePlayerShip(0);
			Flags.killedByAlien = false;
			Flags.killedByDragon = false;
			Flags.killedByEvilHead = false;
			Flags.killedByBunker = true;
		}
	}

	private static void shipIntersectsGold(Rectangle myShip) {
		for (Iterator<Gold> it = Gold.goldstack.iterator(); it.hasNext();) {
			Gold gold = it.next();
			if (myShip.intersects(gold.getBounds()) && LivesAndCounts.getLifeBunker() >= 50) {
				gold.setVisible(false);
				LoadSounds.GOT_GOLD.play();
				it.remove();
			}
		}
	}

	private static void shipIntersectsHealth(Rectangle myShip, HealthPack health, Rectangle healthUnit) {
		if (myShip.intersects(healthUnit) && (!Alien.aliens.isEmpty()
				|| (Dragon.dragons.isEmpty() && LivesAndCounts.getLifeBunker() < 50) || Gold.goldstack.isEmpty())) {
			health.setVisible(false);
			LoadSounds.GOT_HP.play();
			if (LivesAndCounts.getLifePlayerShip() <= 3) {
				LivesAndCounts.setLifePlayerShip(LivesAndCounts.getLifePlayerShip() + 1);
			}
		}
	}

	private static void missileIntersectsAlien(ShipMissile missile) {
		for (Alien alien : Alien.aliens) {
			if (missile.getBounds().intersects(alien.getBounds())) {
//				FrameUtils.vibrate(MouseInputHandler.main);
				missile.setVisible(false);
				alien.loadCanon();
				alien.setVisible(false);
				new PlayWave1st(SoundEffect.BLOOP.getSound()).start();
				LivesAndCounts.setAlienKilled(LivesAndCounts.getAlienKilled() + 1);
			}
		}
	}

	private static void shipIntersectsHead(Rectangle myShip, Rectangle evilhead) {
		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && LivesAndCounts.getLifeBunker() >= 50
				&& myShip.intersects(evilhead)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			HealthPack.healthpacks.add(new HealthPack((int) evilhead.getX(), (int) evilhead.getY()));
			Explosion.explosions.add(new Explosion(myShip.getX(), myShip.getY(), 30, 30));
			new PlayWave1st(SoundEffect.SCREAM.getSound()).start();
			LivesAndCounts.setLifePlayerShip(0);
			Flags.killedByBunker = false;
			Flags.killedByAlien = false;
			Flags.killedByDragon = false;
			Flags.killedByEvilHead = true;
		}
	}

	private static void missileIntersectsHead(Rectangle evilhead, ShipMissile missile, Rectangle missileUnit) {
		if (missileUnit.intersects(evilhead)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			new PlayWave1st(SoundEffect.BLOOP.getSound()).start();
			missile.setVisible(false);
			if (ActionPerformer.getTimerHard().isRunning()) {
				EvilHead.evilHead.loadPlasmaBall();
			} else {
				EvilHead.evilHead.loadCanon();
			}
			HealthPack.healthpacks.add(new HealthPack((int) evilhead.getX(), (int) evilhead.getY()));
			EvilHead.evilHead.strikeHead();
			LivesAndCounts.decreaseEvilHeadLife(1);
		}
	}

	private static void rocketIntersectsDragon(ShipRocket rocket) {
//		List<Dragon> dragonsList = new ArrayList<>(Dragon.dragons);
		for (Iterator<Dragon> it = Dragon.dragons.iterator(); it.hasNext();) {
			Dragon dragon = it.next();
			if (rocket.getBounds().intersects(dragon.getBounds())) {
//				FrameUtils.vibrate(MouseInputHandler.main);
				new PlayWave1st(SoundEffect.BLOOP.getSound()).start();
				rocket.setVisible(false);
				dragon.setVisible(false);
				Angel.angels.add(new Angel(dragon.getX(), dragon.getY()));
				LivesAndCounts.increaseDragonKilledCount(1);
				it.remove();
			}
		}
	}

	private static void rocketIntersectsHead(Rectangle evilhead, ShipRocket rocket, Rectangle rocketUnit) {
		if (rocketUnit.intersects(evilhead)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			if (ActionPerformer.getTimerHard().isRunning()) {
				EvilHead.evilHead.loadPlasmaBall();
			} else {
				EvilHead.evilHead.loadCanon();
			}
			HealthPack.healthpacks.add(new HealthPack((int) evilhead.getX(), (int) evilhead.getY()));
			EvilHead.evilHead.strikeHead();
			rocket.setVisible(false);
			LivesAndCounts.decreaseEvilHeadLife(1);
		}
	}

	private static void rocketIntersectsBunker(Rectangle bunker, ShipRocket rocket, Rectangle rocketUnit) {
		if (rocketUnit.intersects(bunker)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			Bunker.bunkerObj.drawBunkerHit();
			Bunker.bunkerObj.loadBulletsLeft();
			Bunker.bunkerObj.loadBulletsRight();
			rocket.setVisible(false);
			new PlayWave1st(SoundEffect.SCREAM.getSound()).start();
			LoadSounds.HIT.play();
			LivesAndCounts.decreaseBunkerLife(1);
		}
	}

	private static void plasmaBallIntersectsShip(PlasmaBall plasmaBall, Rectangle plasmaBallUnit, Rectangle ship) {
		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && LivesAndCounts.getLifeBunker() >= 50
				&& plasmaBallUnit.intersects(ship)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			LivesAndCounts.setLifePlayerShip(LivesAndCounts.getLifePlayerShip() - 1);
			plasmaBall.setVisible(false);
			Explosion.explosions.add(new Explosion(ship.getX(), ship.getY(), 30, 30));
			new PlayWave1st(SoundEffect.SCREAM.getSound()).start();
			PlayerShip.playerShip.x = -PlayerShip.playerShip.getX();
			if (LivesAndCounts.getLifePlayerShip() == 0) {
				Flags.killedByBunker = false;
				Flags.killedByDragon = false;
				Flags.killedByAlien = false;
				Flags.killedByEvilHead = true;
			}
		}
	}

	private static void bulletIntersectsShip(BunkerBullet bullet, Rectangle bulletUnit, Rectangle ship) {
		if (bulletUnit.intersects(ship)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			LivesAndCounts.setLifePlayerShip(LivesAndCounts.getLifePlayerShip() - 1);
			bullet.setVisible(false);
			Explosion.explosions.add(new Explosion(ship.getX(), ship.getY(), 30, 30));
			new PlayWave1st(SoundEffect.SCREAM.getSound()).start();
			new PlayWave1st(SoundEffect.EXPLOSION.getSound()).start();
//			PlayerShip.playerOne.upsideDown();
			PlayerShip.playerShip.x = -PlayerShip.playerShip.getX();
			Crosshair.crosshair.x = -Crosshair.crosshair.getX();
			if (LivesAndCounts.getLifePlayerShip() == 0) {
				Flags.killedByDragon = false;
				Flags.killedByAlien = false;
				Flags.killedByEvilHead = false;
				Flags.killedByBunker = true;
			}
		}
	}

	private static void canonIntersectsShip(Rectangle myShip, CanonBall canon, Rectangle canonUnit) {
		if ((!Alien.aliens.isEmpty() || LivesAndCounts.getLifeBunker() >= 50) && canonUnit.intersects(myShip)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			LivesAndCounts.setLifePlayerShip(LivesAndCounts.getLifePlayerShip() - 1);
			canon.setVisible(false);
			Explosion.explosions.add(new Explosion(myShip.getX(), myShip.getY(), 30, 30));
			new PlayWave1st(SoundEffect.BURNED.getSound()).start();
//			PlayerShip.playerOne.upsideDown();
			PlayerShip.playerShip.x = -PlayerShip.playerShip.getX();
			Crosshair.crosshair.x = -Crosshair.crosshair.getX();
			if (LivesAndCounts.getLifePlayerShip() == 0) {
				if (!Alien.aliens.isEmpty()) {
					Flags.killedByDragon = false;
					Flags.killedByBunker = false;
					Flags.killedByEvilHead = false;
					Flags.killedByAlien = true;
				} else {
					Flags.killedByDragon = false;
					Flags.killedByAlien = false;
					Flags.killedByBunker = false;
					Flags.killedByEvilHead = true;
				}
			}
		}
	}
}
