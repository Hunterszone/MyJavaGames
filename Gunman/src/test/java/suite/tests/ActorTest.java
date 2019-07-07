package suite.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Image;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sokoban.Actor;

public class ActorTest {

	private Actor actor;
	private int x, y;

	@Before
	public void setUp() {
		actor = new Actor(x, y);
	}

	@Test
	public void testActor() {
		assertTrue(actor instanceof Object);
	}

	@Test
	public void testGetImage() {
		Image image = null;
		if (image instanceof Image)
			assertEquals(image, actor.getImage());
	}

	@Test
	public void testIsLeftCollision() {
		assertFalse(actor.isLeftCollision(actor));
	}

	@Test
	public void testIsRightCollision() {
		assertFalse(actor.isRightCollision(actor));
	}

	@Test
	public void testIsTopCollision() {
		assertFalse(actor.isTopCollision(actor));
	}

	@Test
	public void testIsBottomCollision() {
		assertFalse(actor.isBottomCollision(actor));
	}

	@After
	public void tearDown() {
		actor = null;
	}
}
