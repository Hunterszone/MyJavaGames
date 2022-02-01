package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import entities.Alien;
import entities.Dragon;
import items.BunkerBullet;
import items.CanonBall;
import items.Gold;
import items.HealthPack;
import items.PlasmaBall;
import items.ShipMissile;
import items.ShipRocket;

public class CollisionsTest {

	private static Rectangle myship, bunker, evilhead, missileUnit, rocketUnit, alienUnit, dragonUnit;
	private static Alien alien;
	private static Dragon dragon;
	private static CanonBall canon;
	private static BunkerBullet bullet;
	private static PlasmaBall plasmaBall;
	private static ShipRocket rocket;
	private static ShipMissile missile;
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
		plasmaBall = new PlasmaBall(x, y);
		rocket = new ShipRocket(x, y);
		missile = new ShipMissile(x, y);
	}

	@Test
	public void canonIntersectsShip() {
		Rectangle canonUnit = canon.getBounds();
		assertFalse("canonIntersectsShip", canonUnit.intersects(myship));
	}

	@Test
	public void bulletIntersectsShip() {
		Rectangle bulletUnit = bullet.getBounds();
		assertFalse("bulletIntersectsShip", bulletUnit.intersects(myship));
	}

	@Test
	public void plasmaBallIntersectsShip() {
		Rectangle plasmaBallUnit = plasmaBall.getBounds();
		assertFalse("plasmaBallIntersectsShip", plasmaBallUnit.intersects(myship));
	}

	@Test
	public void rocketIntersectsBunker() {
		rocketUnit = rocket.getBounds();
		assertFalse("rocketIntersectsBunker", rocketUnit.intersects(bunker));
	}

	@Test
	public void rocketIntersectsHead() {
		rocketUnit = rocket.getBounds();
		assertFalse("rocketIntersectsHead", rocketUnit.intersects(evilhead));
	}

	@Test
	public void missileIntersectsAlien() {
		missileUnit = missile.getBounds();
		alienUnit = alien.getBounds();
		assertTrue("missileIntersectsAlien", missileUnit.intersects(alienUnit));
	}

	@Test
	public void missileIntersectsHead() {
		missileUnit = missile.getBounds();
		assertFalse("missileIntersectsHead", missileUnit.intersects(evilhead));
	}

	@Test
	public void rocketIntersectsDragon() {
		rocketUnit = rocket.getBounds();
		dragonUnit = dragon.getBounds();
		assertTrue("rocketIntersectsDragon", rocketUnit.intersects(dragonUnit));
	}

	@Test
	public void shipIntersectsAlien() {
		alienUnit = alien.getBounds();
		assertFalse("shipIntersectsAlien", alienUnit.intersects(myship));
	}

	@Test
	public void shipIntersectsDragon() {
		dragonUnit = dragon.getBounds();
		assertFalse("shipIntersectsDragon", dragonUnit.intersects(myship));
	}

	@Test
	public void shipIntersectsHealth() {
		HealthPack health = new HealthPack(x, y);
		Rectangle healthUnit = health.getBounds();
		assertFalse("shipIntersectsHealth", healthUnit.intersects(myship));
	}

	@Test
	public void shipIntersectsGold() {
		Gold gold = new Gold(x, y);
		Rectangle goldUnit = gold.getBounds();
		assertFalse("shipIntersectsGold", goldUnit.intersects(myship));
	}

	@AfterClass
	public static void tearDown() {
		List<Object> objects = new ArrayList<Object>(); 
		objects.addAll(Arrays.asList(myship, bunker, evilhead, alien, alienUnit, dragon, dragonUnit, canon, bullet, plasmaBall,
				rocket, rocketUnit, missile, missileUnit));
		Iterator<Object> iter = objects.iterator();
		while(iter.hasNext()) {
			Object next = iter.next();
			iter.remove();
		}
		if(objects.isEmpty()) {
			objects = null;
		}
	}
}