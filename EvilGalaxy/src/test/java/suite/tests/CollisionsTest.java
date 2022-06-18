package suite.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Timer;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import entities.Alien;
import entities.Bunker;
import entities.Crosshair;
import entities.Dragon;
import entities.EvilHead;
import entities.PlayerShip;
import game_engine.Collisions;
import game_engine.InitObjects;
import items.BunkerBullet;
import items.CanonBall;
import items.Explosion;
import items.Gold;
import items.HealthPack;
import items.PlasmaBall;
import items.ShipMissile;
import items.ShipRocket;

public class CollisionsTest {

	private int x, y;

	@Before
	public void setUp() {
		PlayerShip.playerShip = new PlayerShip(x, y);
		Bunker.bunkerObj = new Bunker(x, y);
		EvilHead.evilHead = new EvilHead(x, y);
		Alien.aliens = Collections.singletonList(new Alien(x, y));
		Dragon.dragons = Collections.singletonList(new Dragon(x, y));
		HealthPack.healthpacks = Collections.singletonList(new HealthPack(x, y));
		Gold.goldstack = Collections.singletonList(new Gold(x, y));
		CanonBall.canonBalls = Collections.singletonList(new CanonBall(x, y));
		PlasmaBall.plasmaBalls = Collections.singletonList(new PlasmaBall(x, y));
		ShipRocket.rockets = Collections.singletonList(new ShipRocket(x, y));
		BunkerBullet.bulletsLeft = Collections.singletonList(new BunkerBullet(x, y));
		BunkerBullet.bulletsRight = Collections.singletonList(new BunkerBullet(x, y));
		InitObjects.explosions = new ArrayList<Explosion>();
		Crosshair.crosshair = new Crosshair(x, y);
		ShipMissile.missiles = Collections.singletonList(new ShipMissile(x, y));
	}

	@Test
	public void shipIntersectsAlien() {
		Collisions.checkAlienCollision(PlayerShip.playerShip.getBounds());

		assertTrue("ship does not intersect alien", Collisions.alienKilled > 0);
	}

	@Test
	public void missileIntersectsAlien() {
		Collisions.checkMissileCollision(EvilHead.evilHead.getBounds());

		assertTrue("ship does not intersect alien", Collisions.alienKilled > 0);
	}

	@Test
	public void shipIntersectsDragon() {
		Alien.aliens = new ArrayList<Alien>();

		Collisions.checkDragonCollision(Dragon.dragons.get(0));

		assertTrue("ship does not intersect dragon", Collisions.dragonKilled > 0);
	}

	@Test
	public void canonIntersectsShip() {
		Collisions.lifePlayerShip = 3;
		
		Collisions.checkCanonCollision(PlayerShip.playerShip.getBounds());

		assertTrue("canon does not intersect ship", Collisions.lifePlayerShip == 2);
	}

	@Test
	public void plasmaBallIntersectsShip() {
		Collisions.lifePlayerShip = 3;
		
		Collisions.checkPlasmaCollision(PlayerShip.playerShip.getBounds());

		assertTrue("plasmaBall does not intersect ship", Collisions.lifePlayerShip == 2);
	}

	@Test
	public void bulletLeftIntersectsShip() {
		Collisions.lifePlayerShip = 3;
		
		Collisions.checkBulletCollision(BunkerBullet.bulletsLeft);

		assertTrue("left bullet does not intersect ship", Collisions.lifePlayerShip == 2);
	}

	@Test
	public void bulletRightIntersectsShip() {
		Collisions.lifePlayerShip = 3;
		
		Collisions.checkBulletCollision(BunkerBullet.bulletsRight);

		assertTrue("right bullet does not intersect ship", Collisions.lifePlayerShip == 2);
	}

	@Test
	public void shipIntersectsBunker() {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		Collisions.lifeBunker = 3;

		Collisions.checkBunkerCollision(PlayerShip.playerShip.getBounds(), Bunker.bunkerObj.getBounds());

		assertTrue("ship does not intersect bunker", Collisions.killedByBunker);
	}
	
	@Test
	public void shipIntersectsEvilHead() {
		Collisions.checkEvilHeadCollision(PlayerShip.playerShip.getBounds(), EvilHead.evilHead.getBounds());

		assertTrue("ship does not intersect EvilHead", Collisions.killedByEvilHead);
	}

	@Test
	public void rocketIntersectsBunker() {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		BunkerBullet.bulletsLeft = new ArrayList<BunkerBullet>();
		BunkerBullet.bulletsRight = new ArrayList<BunkerBullet>();
		Collisions.lifeBunker = 3;

		Collisions.checkRocketCollision(EvilHead.evilHead.getBounds(), Bunker.bunkerObj.getBounds());

		assertTrue("rocket does not intersect bunker", Collisions.lifeBunker == 4);
	}

	@Test
	public void rocketIntersectsEvilHead() {
		triggerEvilHeadCollision();

		Collisions.checkRocketCollision(EvilHead.evilHead.getBounds(), Bunker.bunkerObj.getBounds());

		assertTrue("rocket does not intersect EvilHead", Collisions.lifeEvilHead == 4);
	}

	@Test
	public void missileIntersectsEvilHead() {
		triggerEvilHeadCollision();
		
		Collisions.checkMissileCollision(EvilHead.evilHead.getBounds());
		
		assertTrue("missile does not intersect EvilHead", Collisions.lifeEvilHead == 4);
	}

	@Test
	public void rocketIntersectsDragon() {
		Collisions.checkDragonCollision(Dragon.dragons.get(0));

		assertTrue("rocket does not intersect dragon", Collisions.dragonKilled > 0);
	}
	
	@Test
	public void shipIntersectsHealthOnLevel3() {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		Collisions.lifePlayerShip = 2;
		
		Collisions.checkHealthCollision(PlayerShip.playerShip.getBounds());
		
		assertTrue("ship does not intersect healthPack", Collisions.lifePlayerShip == 3);
	}

	@Test
	public void shipIntersectsHealthOnLevel5() {
		triggerEvilHeadCollision();
		Collisions.lifePlayerShip = 2;

		Collisions.checkHealthCollision(PlayerShip.playerShip.getBounds());
		
		assertTrue("ship does not intersect healthPack", Collisions.lifePlayerShip == 3);
	}

	@Test
	public void shipIntersectsGold() {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		Collisions.lifeBunker = 50;
		
		Collisions.checkGoldCollision(PlayerShip.playerShip.getBounds());
		
		assertTrue("ship does not intersect gold", Gold.goldstack.size() == 0);
	}

	@AfterClass
	public static void tearDown() {
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
		InitObjects.explosions = null;
		Crosshair.crosshair = null;
		ShipMissile.missiles = null;
	}
	
	private void triggerEvilHeadCollision() {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		Gold.goldstack = new ArrayList<Gold>();
		CanonBall.canonBalls = new ArrayList<CanonBall>();
		BunkerBullet.bulletsLeft = new ArrayList<BunkerBullet>();
		BunkerBullet.bulletsRight = new ArrayList<BunkerBullet>();
		Collisions.lifeBunker = 50;
		Collisions.lifeEvilHead = 3;
		InitObjects.timerHard = new Timer(15, null);
	}
}