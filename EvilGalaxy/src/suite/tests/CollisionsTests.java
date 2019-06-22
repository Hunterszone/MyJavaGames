package suite.tests;

import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Alien;
import entities.Dragon;
import game_engine.Collisions;
import items.BunkerBullet;
import items.CanonBall;
import items.FireBall;
import items.Gold;
import items.HealthPack;
import items.ShipMissile;
import items.ShipRocket;

public class CollisionsTests {

	private Rectangle myship, bunker, evilhead, missileUnit, rocketUnit, alienUnit, dragonUnit;
	private Alien alien;
	private Dragon dragon;
	private CanonBall canon;
	private BunkerBullet bullet;
	private FireBall fireball;
	private ShipRocket rocket;
	private ShipMissile missile;
	private int x, y;

	@Before
	public void setUp() {

		myship = new Rectangle();
		bunker = new Rectangle();
		evilhead = new Rectangle();
		alien = new Alien(x, y);
		dragon = new Dragon(x, y);
		canon = new CanonBall(x, y);
		bullet = new BunkerBullet(x, y);
		fireball = new FireBall(x, y);
		rocket = new ShipRocket(x, y);
		missile = new ShipMissile(x, y);
	}

	@Test
	public void canonIntersectsShip() {
		Rectangle canonUnit = canon.getBounds();
		assertTrue("", Collisions.canonIntersectsShip(myship, canon, canonUnit));
	}

	@Test
	public void bulletTwoIntersectsShip() {
		Rectangle bulletUnit2 = bullet.getBounds();
		assertTrue("", Collisions.bulletTwoIntersectsShip(bullet, myship, bulletUnit2));
	}

	@Test
	public void fireBallIntersectsShip() {
		Rectangle fireballUnit = fireball.getBounds();
		assertTrue("", Collisions.fireBallIntersectsShip(fireball, fireballUnit, myship));
	}

	@Test
	public void rocketIntersectsBunker() {
		rocketUnit = rocket.getBounds();
		assertTrue("", Collisions.rocketIntersectsBunker(bunker, rocket, rocketUnit));
	}

	@Test
	public void rocketIntersectsHead() {
		rocketUnit = rocket.getBounds();
		assertTrue("", Collisions.rocketIntersectsHead(evilhead, rocket, rocketUnit));
	}

	@Test
	public void missileIntersectsAlien() {
		missileUnit = missile.getBounds();
		alienUnit = alien.getBounds();
		assertTrue("missileIntersectsAlien", Collisions.missileIntersectsAlien(missile, missileUnit, alien, alienUnit));
	}

	@Test
	public void missileIntersectsHead() {
		missileUnit = missile.getBounds();
		assertTrue("missileIntersectsHead", Collisions.missileIntersectsHead(evilhead, missile, missileUnit));
	}

	@Test
	public void missileIntersectsBunker() {
		missileUnit = missile.getBounds();
		assertTrue("missileIntersectsBunker", Collisions.missileIntersectsBunker(bunker, missile, missileUnit));
	}

	@Test
	public void rocketIntersectsDragon() {
		rocketUnit = rocket.getBounds();
		dragonUnit = dragon.getBounds();
		assertTrue("rocketIntersectsDragon", Collisions.rocketIntersectsDragon(rocket, rocketUnit, dragon, dragonUnit));
	}

	@Test
	public void shipIntersectsAlien() {
		alienUnit = alien.getBounds();
		assertTrue("shipIntersectsBunker", Collisions.shipIntersectsAlien(myship, alien, alienUnit));
	}

	@Test
	public void shipIntersectsDragon() {
		dragonUnit = dragon.getBounds();
		assertTrue("shipIntersectsBunker", Collisions.shipIntersectsDragon(myship, dragon, dragonUnit));
	}

	@Test
	public void shipIntersectsHead() {
		assertTrue("shipIntersectsHead", Collisions.shipIntersectsHead(myship, evilhead));
	}

	@Test
	public void shipIntersectsHealth() {
		HealthPack health = new HealthPack(x, y);
		Rectangle healthUnit = health.getBounds();
		assertTrue("shipIntersectsHealth", Collisions.shipIntersectsHealth(myship, health, healthUnit));
	}

	@Test
	public void shipIntersectsGold() {
		Gold gold = new Gold(x, y);
		Rectangle goldUnit = gold.getBounds();
		assertTrue("shipIntersectsGold", Collisions.shipIntersectsGold(myship, gold, goldUnit));
	}

	@Test
	public void shipIntersectsBunker() {
		assertTrue("shipIntersectsBunker", Collisions.shipIntersectsBunker(myship, bunker));
	}

	@After
	public void tearDown() {
		Object[] objects = { myship, bunker, evilhead, alien, alienUnit, dragon, dragonUnit, canon, bullet, fireball,
				rocket, rocketUnit, missile, missileUnit };
		for (Object obj : objects)
			obj = null;
	}
}