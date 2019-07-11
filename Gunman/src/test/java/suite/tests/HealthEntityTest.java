package suite.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import gameDevelopment.Entity;
import gameDevelopment.Game;
import gameDevelopment.HealthEntity;
import gameDevelopment.HeroEntity;
import gameDevelopment.MySprite;

public class HealthEntityTest {

	private MySprite sprite;
	private Rectangle heroRect, healthRect;
	private Entity healthpack, hero;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		Display.create();
		sprite = new MySprite(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/health.png")));
		heroRect = new Rectangle();
		healthRect = new Rectangle();
		healthpack = new HealthEntity(sprite, x, y);
		hero = new HeroEntity(sprite, x, y);
	}

	@Test
	public void testHealthEntity() {
		assertNotNull(Game.initHealth(sprite));
	}

	@Test
	public void testRemove() {
		assertTrue(Game.initHealth(sprite).remove(healthpack));
	}

	@Test
	public void testCollidesWithHero() {
		if (heroRect.intersects(healthRect))
			assertTrue(Game.initHealth(sprite).collidesWith(hero));
	}

	@After
	public void tearDown() throws Exception {
		Display.destroy();
		sprite = null;
		heroRect = null;
		healthRect = null;
		healthpack = null;
		hero = null;
	}
}
