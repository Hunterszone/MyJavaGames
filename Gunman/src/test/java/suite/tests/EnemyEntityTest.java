package suite.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entities.Crosshair;
import entities.Entity;
import entities.HeroEntity;
import game_engine.Logic;
import game_engine.MySprite;

public class EnemyEntityTest {

	private MySprite hero, crosshair, enemy;
	private Entity heroEntity, crosshairEntity;
	private Rectangle heroRect, enemyRect, crosshRect;
	private int x, y;

	@Before
	public void setUp() {
		try {
			Display.create();
			hero = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/gunman.png")));
			crosshair = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/pointer.png")));
			enemy = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/bird.png")));
		} catch (org.lwjgl.LWJGLException e) {
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		heroEntity = new HeroEntity(hero, x, y);
		crosshairEntity = new Crosshair(crosshair, x, y);
		heroRect = new Rectangle();
		enemyRect = new Rectangle();
		crosshRect = new Rectangle();
	}

	@Test
	public void testEnemyEntity() {
		assertNotNull(Logic.initEnemies(enemy));
	}

	@Test
	public void testCollidedWithHero() {
		if (heroRect.intersects(enemyRect))
			assertTrue(Logic.initEnemies(enemy).iterator().next().collidesWith(heroEntity));
	}

	@Test
	public void testCollidedWithCrosshair() {
		if (crosshRect.intersects(enemyRect))
			assertTrue(Logic.initEnemies(enemy).iterator().next().collidesWith(crosshairEntity));
	}

	@Test
	public void testRemove() {
		assertTrue(Logic.initEnemies(enemy).iterator().next().removedByHero(heroEntity));
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
