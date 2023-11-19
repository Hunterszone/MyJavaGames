package suite.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Timer;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import enemy.Alien;
import enemy.Bunker;
import enemy.Crosshair;
import enemy.Dragon;
import enemy.EvilHead;
import enemy.PlayerShip;
import game_engine.Collisions;
import game_engine.UpdateObjects;
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
		Explosion.explosions = new ArrayList<Explosion>();
		Crosshair.crosshair = new Crosshair(x, y);
		ShipMissile.missiles = Collections.singletonList(new ShipMissile(x, y));
	}

	@Test
	public void shipIntersectsAlien() {
		UpdateObjects.setAlienKilled(0);
		
		Collisions.checkAlienCollision(PlayerShip.playerShip.getBounds());

		assertTrue("ship does not intersect alien", UpdateObjects.getAlienKilled() == 1);
	}

	@Test
	public void missileIntersectsAlien() {
		UpdateObjects.setAlienKilled(0);
				
		Collisions.checkMissileCollision(EvilHead.evilHead.getBounds());

		assertTrue("ship does not intersect alien", UpdateObjects.getAlienKilled() == 1);
	}

	@Test
	public void shipIntersectsDragon() {
		UpdateObjects.setDragonKilled(0);

		Collisions.checkDragonCollision(Dragon.dragons.get(0));

		assertTrue("ship does not intersect dragon", UpdateObjects.getDragonKilled() == 1);
	}

	@Test
	public void canonIntersectsShip() {
		UpdateObjects.setLifePlayerShip(3);
		
		Collisions.checkCanonCollision(PlayerShip.playerShip.getBounds());

		assertTrue("canon does not intersect ship", UpdateObjects.getLifePlayerShip() == 2);
	}

	@Test
	public void plasmaBallIntersectsShip() {
		UpdateObjects.setLifePlayerShip(3);
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		UpdateObjects.setLifeBunker(50);
		
		Collisions.checkPlasmaCollision(PlayerShip.playerShip.getBounds());

		assertTrue("plasmaBall does not intersect ship", UpdateObjects.getLifePlayerShip() == 2);
	}

	@Test
	public void bulletLeftIntersectsShip() {
		UpdateObjects.setLifePlayerShip(3);
		
		Collisions.checkBulletCollision(BunkerBullet.bulletsLeft);

		assertTrue("left bullet does not intersect ship", UpdateObjects.getLifePlayerShip() == 2);
	}

	@Test
	public void bulletRightIntersectsShip() {
		UpdateObjects.setLifePlayerShip(3);
		
		Collisions.checkBulletCollision(BunkerBullet.bulletsRight);

		assertTrue("right bullet does not intersect ship", UpdateObjects.getLifePlayerShip() == 2);
	}

	@Test
	public void shipIntersectsBunker() {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		UpdateObjects.setLifeBunker(3);

		Collisions.checkBunkerCollision(PlayerShip.playerShip.getBounds(), Bunker.bunkerObj.getBounds());

		assertTrue("ship does not intersect bunker", Flags.killedByBunker);
	}
	
	@Test
	public void shipIntersectsEvilHead() {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		UpdateObjects.setLifeBunker(50);
		
		Collisions.checkEvilHeadCollision(PlayerShip.playerShip.getBounds(), EvilHead.evilHead.getBounds());

		assertTrue("ship does not intersect EvilHead", Flags.killedByEvilHead);
	}

	@Test
	public void rocketIntersectsBunker() {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		BunkerBullet.bulletsLeft = new ArrayList<BunkerBullet>();
		BunkerBullet.bulletsRight = new ArrayList<BunkerBullet>();
		UpdateObjects.setLifeBunker(3);

		Collisions.checkRocketCollision(EvilHead.evilHead.getBounds(), Bunker.bunkerObj.getBounds());

		assertTrue("rocket does not intersect bunker", UpdateObjects.getLifeBunker() == 4);
	}

	@Test
	public void rocketIntersectsEvilHead() {
		triggerEvilHeadCollision();

		Collisions.checkRocketCollision(EvilHead.evilHead.getBounds(), Bunker.bunkerObj.getBounds());

		assertTrue("rocket does not intersect EvilHead", UpdateObjects.getLifeEvilHead() == 4);
	}

	@Test
	public void missileIntersectsEvilHead() {
		triggerEvilHeadCollision();
		
		Collisions.checkMissileCollision(EvilHead.evilHead.getBounds());
		
		assertTrue("missile does not intersect EvilHead", UpdateObjects.getLifeEvilHead() == 4);
	}

	@Test
	public void rocketIntersectsDragon() {
		UpdateObjects.setDragonKilled(0);
				
		Collisions.checkDragonCollision(Dragon.dragons.get(0));

		assertTrue("rocket does not intersect dragon", UpdateObjects.getDragonKilled() == 1);
	}
	
	@Test
	public void shipIntersectsHealthOnLevel3() {
		Dragon.dragons = new ArrayList<Dragon>();
		UpdateObjects.setLifePlayerShip(2);
		
		Collisions.checkHealthCollision(PlayerShip.playerShip.getBounds());
		
		assertTrue("ship does not intersect healthPack", UpdateObjects.getLifePlayerShip() == 3);
	}

	@Test
	public void shipIntersectsHealthOnLevel5() {
		triggerEvilHeadCollision();
		UpdateObjects.setLifePlayerShip(2);

		Collisions.checkHealthCollision(PlayerShip.playerShip.getBounds());
		
		assertTrue("ship does not intersect healthPack", UpdateObjects.getLifePlayerShip() == 3);
	}

	@Test
	public void shipIntersectsGold() {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		UpdateObjects.setLifeBunker(50);
		
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
		Explosion.explosions = null;
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
		UpdateObjects.setLifeBunker(50);
		UpdateObjects.setLifeEvilHead(3);
		ActionPerformer.setTimerHard(new Timer(15, null));
	}
}