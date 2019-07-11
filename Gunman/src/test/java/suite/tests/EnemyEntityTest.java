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

	private MySprite heroSprite, crosshairSprite, enemySprite;
	private Entity hero, crosshair;
	private Rectangle heroRect, enemyRect, crosshRect;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		Display.create();
		heroSprite = new MySprite(
				TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/gunman.png")));
		crosshairSprite = new MySprite(
				TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/pointer.png")));
		enemySprite = new MySprite(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/bird.png")));
		hero = new HeroEntity(heroSprite, x, y);
		crosshair = new Crosshair(crosshairSprite, x, y);
		heroRect = new Rectangle();
		enemyRect = new Rectangle();
		crosshRect = new Rectangle();
	}

	@Test
	public void testEnemyEntity() {
		assertNotNull(Game.initEnemies(enemySprite));
	}

	@Test
	public void testCollidedWithHero() {
		if (heroRect.intersects(enemyRect))
			assertTrue(Game.initEnemies(enemySprite).collidesWith(hero));
	}

	@Test
	public void testCollidedWithCrosshair() {
		if (enemyRect.intersects(crosshRect))
			assertTrue(Game.initEnemies(enemySprite).collidesWith(crosshair));
	}

	@Test
	public void testRemove() {
		assertTrue(Game.initEnemies(enemySprite).remove(hero));
	}

	@After
	public void tearDown() throws Exception {
		Display.destroy();
		heroSprite = null;
		crosshairSprite = null;
		enemySprite = null;
		hero = null;
		crosshair = null;
		heroRect = null;
		enemyRect = null;
		crosshRect = null;
	}
}
