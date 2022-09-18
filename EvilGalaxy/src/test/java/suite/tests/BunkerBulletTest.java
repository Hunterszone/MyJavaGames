package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.BunkerBullet;

public class BunkerBulletTest {

private BunkerBullet bunkerBullet;
	
	@Before
	public void setUp() {
		bunkerBullet = new BunkerBullet(TestUtils.POS_X, TestUtils.POS_Y);
	}
	
	@Test
	public void testBunkerBulletImg() {
		assertNotNull(bunkerBullet.getImage());
	}
	
	@Test
	public void testBunkerBulletPositionDiagonalLeft() {
		bunkerBullet.moveDiagLeft();
		assertNotEquals("BunkerBullet is not X-moving on the left diagonal!", TestUtils.POS_X, bunkerBullet.getX());
		assertNotEquals("BunkerBullet is not Y-moving on the left diagonal!", TestUtils.POS_Y, bunkerBullet.getY());
	}
	
	@Test
	public void testBunkerBulletPositionDiagonalRight() {
		bunkerBullet.moveDiagRight();
		assertNotEquals("BunkerBullet is not X-moving on the right diagonal!", TestUtils.POS_X, bunkerBullet.getX());
		assertNotEquals("BunkerBullet is not Y-moving on the right diagonal!", TestUtils.POS_Y, bunkerBullet.getY());
	}
	
	@Test
	public void testBunkerBulletPositionDown() {
		bunkerBullet.moveDown();
		assertNotEquals("BunkerBullet is not moving down!", TestUtils.POS_Y, bunkerBullet.getY());
	}
	
	@Test
	public void testBunkerBulletPositionLeft() {
		bunkerBullet.moveLeft();
		assertNotEquals("BunkerBullet is not moving left!", TestUtils.POS_Y, bunkerBullet.getX());
	}
	
	@Test
	public void testBunkerBulletPositionRight() {
		bunkerBullet.moveRight();
		assertNotEquals("BunkerBullet is not moving right!", TestUtils.POS_Y, bunkerBullet.getX());
	}
	
	@After
	public void tearDown() {
		bunkerBullet = null;
	}
	
}
