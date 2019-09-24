package suite.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import gameDevelopment.Entity;
import gameDevelopment.Game;
import gameDevelopment.HealthEntity;
import gameDevelopment.HeroEntity;
import gameDevelopment.MySprite;

@Ignore
public class HealthEntityTest {

	private MySprite healthpack, hero;
	private Rectangle heroRect, healthRect;
	private Entity healthpackEntity, heroEntity;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		Display.create();
		healthpack = new MySprite(
				TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/health.png")));
		hero = new MySprite(
				TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/gunman.png")));
		heroRect = new Rectangle();
		healthRect = new Rectangle();
		healthpackEntity = new HealthEntity(healthpack, x, y);
		heroEntity = new HeroEntity(hero, x, y);
	}

	@Test
	public void testHealthEntity() {
		assertNotNull(Game.initHealth(healthpack));
	}

	@Test
	public void testRemove() {
		assertTrue(Game.initHealth(healthpack).removedByHero(healthpackEntity));
	}

	@Test
	public void testCollidesWithHero() {
		if (heroRect.intersects(healthRect))
			assertTrue(Game.initHealth(healthpack).collidesWith(heroEntity));
	}

	@After
	public void tearDown() throws Exception {
		Display.destroy();
		healthpack = null;
		hero = null;
		heroRect = null;
		healthRect = null;
		healthpackEntity = null;
		heroEntity = null;
	}
}
