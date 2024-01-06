package suite.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Timer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import enemy.Alien;
import enemy.Bunker;
import enemy.Crosshair;
import enemy.Dragon;
import enemy.EvilHead;
import enemy.PlayerShip;
import game_engine.Collisions;
import items.Angel;
import items.BunkerBullet;
import items.CanonBall;
import items.Explosion;
import items.Gold;
import items.HealthPack;
import items.PlasmaBall;
import items.ShipMissile;
import items.ShipRocket;
import util.ActionPerformer;
import util.Flags;
import util.LivesAndCounts;

public class CollisionsTest {

	private int x, y;
	
	@Before
	public void setUp() {
		PlayerShip.playerShip = new PlayerShip(x, y);
		Bunker.bunkerObj = new Bunker(x, y);
		EvilHead.evilHead = new EvilHead(x, y);
		Crosshair.crosshair = new Crosshair(x, y);
		Alien.aliens = new ArrayList<>(Collections.singletonList(new Alien(x, y)));
		Dragon.dragons = new ArrayList<>(Collections.singletonList(new Dragon(x, y)));
		HealthPack.healthpacks = new ArrayList<>(Collections.singletonList(new HealthPack(x, y)));
		Gold.goldstack = new ArrayList<>(Collections.singletonList(new Gold(x, y)));
		CanonBall.canonBalls = new ArrayList<>(Collections.singletonList(new CanonBall(x, y)));
		PlasmaBall.plasmaBalls = new ArrayList<>(Collections.singletonList(new PlasmaBall(x, y)));
		ShipRocket.rockets = new ArrayList<>(Collections.singletonList(new ShipRocket(x, y)));
		BunkerBullet.bulletsLeft = new ArrayList<>(Collections.singletonList(new BunkerBullet(x, y)));
		BunkerBullet.bulletsRight = new ArrayList<>(Collections.singletonList(new BunkerBullet(x, y)));
		ShipMissile.missiles = new ArrayList<>(Collections.singletonList(new ShipMissile(x, y)));
		Explosion.explosions = new ArrayList<Explosion>();
		Angel.angels = new ArrayList<Angel>();
	}

	@Test
	public void shipIntersectsAlien() {
		LivesAndCounts.setAlienKilled(0);
		
		Collisions.checkAlienCollision(PlayerShip.playerShip.getBounds());

		assertTrue("ship does not intersect alien", LivesAndCounts.getAlienKilled() == 1);
	}

	@Test
	public void missileIntersectsAlien() {
		LivesAndCounts.setAlienKilled(0);
				
		Collisions.checkMissileCollision(EvilHead.evilHead.getBounds());

		assertTrue("ship does not intersect alien", LivesAndCounts.getAlienKilled() == 1);
	}

	@Test
	public void shipIntersectsDragon() {
		Alien.aliens = new ArrayList<Alien>();
		LivesAndCounts.setDragonKilled(0);

		Collisions.checkDragonCollision(PlayerShip.playerShip.getBounds());

		assertTrue("ship does not intersect dragon", LivesAndCounts.getDragonKilled() == 1);
	}

	@Test
	public void canonIntersectsShip() {
		LivesAndCounts.setLifePlayerShip(3);
		
		Collisions.checkCanonCollision(PlayerShip.playerShip.getBounds());

		assertTrue("canon does not intersect ship", LivesAndCounts.getLifePlayerShip() == 2);
	}

	@Test
	public void plasmaBallIntersectsShip() {
		LivesAndCounts.setLifePlayerShip(3);
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		LivesAndCounts.setLifeBunker(50);
		
		Collisions.checkPlasmaCollision(PlayerShip.playerShip.getBounds());

		assertTrue("plasmaBall does not intersect ship", LivesAndCounts.getLifePlayerShip() == 2);
	}

	@Test
	public void bulletLeftIntersectsShip() {
		LivesAndCounts.setLifePlayerShip(3);
		
		Collisions.checkBulletCollisionWithPlayerShip(BunkerBullet.bulletsLeft);

		assertTrue("left bullet does not intersect ship", LivesAndCounts.getLifePlayerShip() == 2);
	}

	@Test
	public void bulletRightIntersectsShip() {
		LivesAndCounts.setLifePlayerShip(3);
		
		Collisions.checkBulletCollisionWithPlayerShip(BunkerBullet.bulletsRight);

		assertTrue("right bullet does not intersect ship", LivesAndCounts.getLifePlayerShip() == 2);
	}

	@Test
	public void shipIntersectsBunker() {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		LivesAndCounts.setLifeBunker(3);

		Collisions.checkBunkerCollision(PlayerShip.playerShip.getBounds(), Bunker.bunkerObj.getBounds());

		assertTrue("ship does not intersect bunker", Flags.killedByBunker);
	}
	
	@Test
	public void shipIntersectsEvilHead() {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		LivesAndCounts.setLifeBunker(50);
		
		Collisions.checkEvilHeadCollision(PlayerShip.playerShip.getBounds(), EvilHead.evilHead.getBounds());

		assertTrue("ship does not intersect EvilHead", Flags.killedByEvilHead);
	}

	@Test
	public void rocketIntersectsBunker() {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		BunkerBullet.bulletsLeft = new ArrayList<BunkerBullet>();
		BunkerBullet.bulletsRight = new ArrayList<BunkerBullet>();
		LivesAndCounts.setLifeBunker(3);

		Collisions.checkRocketCollision(EvilHead.evilHead.getBounds(), Bunker.bunkerObj.getBounds());

		assertTrue("rocket does not intersect bunker", LivesAndCounts.getLifeBunker() == 4);
	}

	@Test
	public void rocketIntersectsEvilHead() {
		triggerEvilHeadCollision();

		Collisions.checkRocketCollision(EvilHead.evilHead.getBounds(), Bunker.bunkerObj.getBounds());

		assertTrue("rocket does not intersect EvilHead", LivesAndCounts.getLifeEvilHead() == 4);
	}

	@Test
	public void missileIntersectsEvilHead() {
		triggerEvilHeadCollision();
		
		Collisions.checkMissileCollision(EvilHead.evilHead.getBounds());
		
		assertTrue("missile does not intersect EvilHead", LivesAndCounts.getLifeEvilHead() == 4);
	}

	@Test
	public void rocketIntersectsDragon() {
		Alien.aliens = new ArrayList<Alien>();
		LivesAndCounts.setDragonKilled(0);
				
		Collisions.checkRocketCollision(EvilHead.evilHead.getBounds(), Bunker.bunkerObj.getBounds());

		assertTrue("rocket does not intersect dragon", LivesAndCounts.getDragonKilled() == 1);
	}
	
	@Test
	public void shipIntersectsHealthOnLevel3() {
		Dragon.dragons = new ArrayList<Dragon>();
		LivesAndCounts.setLifePlayerShip(2);
		
		Collisions.checkHealthCollision(PlayerShip.playerShip.getBounds());
		
		assertTrue("ship does not intersect healthPack", LivesAndCounts.getLifePlayerShip() == 3);
	}

	@Test
	public void shipIntersectsHealthOnLevel5() {
		triggerEvilHeadCollision();
		LivesAndCounts.setLifePlayerShip(2);

		Collisions.checkHealthCollision(PlayerShip.playerShip.getBounds());
		
		assertTrue("ship does not intersect healthPack", LivesAndCounts.getLifePlayerShip() == 3);
	}

	@Test
	public void shipIntersectsGold() {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		LivesAndCounts.setLifeBunker(50);
		
		Collisions.checkGoldCollision(PlayerShip.playerShip.getBounds());
		
		assertTrue("ship does not intersect gold", Gold.goldstack.size() == 0);
	}

	@After
	public void tearDown() {
		PlayerShip.playerShip = null;
		Bunker.bunkerObj = null;
		EvilHead.evilHead = null;
		Alien.aliens = null;
		Dragon.dragons = null;
		HealthPack.healthpacks = null;
		Gold.goldstack = null;
		CanonBall.canonBalls = null;
		PlasmaBall.plasmaBalls = null;
		ShipRocket.rockets = null;
		BunkerBullet.bulletsLeft = null;
		BunkerBullet.bulletsRight = null;
		Explosion.explosions = null;
		Crosshair.crosshair = null;
		ShipMissile.missiles = null;
		Angel.angels = null;
	}
	
	private void triggerEvilHeadCollision() {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		Gold.goldstack = new ArrayList<Gold>();
		CanonBall.canonBalls = new ArrayList<CanonBall>();
		BunkerBullet.bulletsLeft = new ArrayList<BunkerBullet>();
		BunkerBullet.bulletsRight = new ArrayList<BunkerBullet>();
		LivesAndCounts.setLifeBunker(50);
		LivesAndCounts.setLifeEvilHead(3);
		ActionPerformer.setTimerHard(new Timer(15, null));
	}
}