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

import gameDevelopment.Crosshair;
import gameDevelopment.Entity;
import gameDevelopment.Game;
import gameDevelopment.HeroEntity;
import gameDevelopment.MySprite;

public class EnemyEntityTest {

	private MySprite hero, crosshair, enemy;
	private Entity heroEntity, crosshairEntity;
	private Rectangle heroRect, enemyRect, crosshRect;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		Display.create();
		hero = new MySprite(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/gunman.png")));
		crosshair = new MySprite(
				TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/pointer.png")));
		enemy = new MySprite(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/bird.png")));
		heroEntity = new HeroEntity(hero, x, y);
		crosshairEntity = new Crosshair(crosshair, x, y);
		heroRect = new Rectangle();
		enemyRect = new Rectangle();
		crosshRect = new Rectangle();
	}

	@Test
	public void testEnemyEntity() {
		assertNotNull(Game.initEnemies(enemy));
	}

	@Test
	public void testCollidedWithHero() {
		if (heroRect.intersects(enemyRect))
			assertTrue(Game.initEnemies(enemy).collidesWith(heroEntity));
	}

	@Test
	public void testCollidedWithCrosshair() {
		if (crosshRect.intersects(enemyRect))
			assertTrue(Game.initEnemies(enemy).collidesWith(crosshairEntity));
	}

	@Test
	public void testRemove() {
		assertTrue(Game.initEnemies(enemy).removedByHero(heroEntity));
	}

	@After
	public void tearDown() throws Exception {
		Display.destroy();
		hero = null;
		crosshair = null;
		enemy = null;
		heroEntity = null;
		crosshairEntity = null;
		heroRect = null;
		enemyRect = null;
		crosshRect = null;
	}
}