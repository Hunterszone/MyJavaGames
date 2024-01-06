// Collisions.java
// 
// Creator: Konstantin
// 

package game_engine;

// import java libraries:
import java.awt.Rectangle;
import java.util.List;

import enemy.Alien;
import enemy.Bunker;
import enemy.EvilHead;
import enemy.PlayerShip;
import items.BunkerBullet;
import items.CanonBall;
import items.Gold;
import items.HealthPack;
import items.PlasmaBall;
import items.ShipMissile;
import items.ShipRocket;
import util.LoadSounds;

public abstract class Collisions {

	public static void checkBulletCollisionWithPlayerShip(List<BunkerBullet> bullets) {
		for (final BunkerBullet bullet : bullets) {
			bullet.intersects(PlayerShip.playerShip.getBounds());
		}
	}

	public static void checkCanonCollisionWithPlayerShip() {
		for (final CanonBall canon : CanonBall.canonBalls) {
			canon.intersects(PlayerShip.playerShip.getBounds());
		}
	}

	public static void checkHealthCollisionWithPlayerShip() {
		for (final HealthPack health : HealthPack.healthpacks) {
			health.intersects(PlayerShip.playerShip.getBounds());
		}
	}

	public static void checkGoldCollisionWithPlayerShip() {
		for (Gold gold : Gold.goldstack) {
			gold.intersects(PlayerShip.playerShip.getBounds());
		}
	}

	public static void checkAlienCollisionWithPlayerShip() {
		for (final Alien alien : Alien.aliens) {
			alien.intersects(PlayerShip.playerShip.getBounds());
		}
	}

	public static void checkEvilHeadCollisionWithPlayerShip() {
		EvilHead.evilHead.intersects(PlayerShip.playerShip.getBounds());
	}

	public static void checkBunkerCollisionWithPlayerShip() {
		Bunker.bunkerObj.intersects(PlayerShip.playerShip.getBounds());
	}

	public static void checkPlasmaCollisionWithPlayerShip() {
		for (final PlasmaBall plasmaBall : EvilHead.evilHead.getEvilPlasmaBalls()) {
			plasmaBall.intersects(PlayerShip.playerShip.getBounds());
		}
	}

	public static void checkRocketCollisions() {
		for (final ShipRocket rocket : PlayerShip.playerShip.getRockets()) {
			rocket.intersects(new Rectangle()); // rocket intersects Dragon entity
		}

		for (final ShipRocket rocket : PlayerShip.playerShip.getRockets()) {
			rocket.intersects(EvilHead.evilHead.getBounds());
		}

		for (final ShipRocket rocket : PlayerShip.playerShip.getRockets()) {
			rocket.intersects(Bunker.bunkerObj.getBounds());
		}
	}

	public static void checkMissileCollisions() {
		for (final ShipMissile missile : PlayerShip.playerShip.getMissiles()) {
			missile.intersects(new Rectangle()); // missile intersects Alien entity
		}

		for (final ShipMissile missile : PlayerShip.playerShip.getMissiles()) {
			missile.intersects(EvilHead.evilHead.getBounds());
		}
	}

	public static void checkDragonCollisionWithPlayerShip() {
		if (Alien.aliens.isEmpty()) {
			LoadSounds.TAUNT.loop();
			PlayerShip.playerShip.intersects(new Rectangle());
		}
	}
}