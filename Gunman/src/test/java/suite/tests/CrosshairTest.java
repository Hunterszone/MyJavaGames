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

import entities.EnemyEntity;
import entities.Entity;
import game_engine.MySprite;
import main.Game;

@Ignore
public class CrosshairTest {

	private MySprite crosshair;
	private Entity enemyEntity;
	private Rectangle crosshRect, enemyRect;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		Display.create();
		crosshair = new MySprite(
				TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/pointer.png")));
		enemyEntity = new EnemyEntity(
				new MySprite(
						TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/bird.png"))),
				x, y);
		enemyRect = new Rectangle();
		crosshRect = new Rectangle();
	}

	@Test
	public void testCrosshair() {
		assertNotNull(Game.initCrosshair(crosshair));
	}

	@Test
	public void testCollidedWithEnemy() {
		if (crosshRect.intersects(enemyRect))
			assertTrue(Game.initCrosshair(crosshair).collidesWith(enemyEntity));
	}

	@Test
	public void testRemove() {
		assertTrue(Game.initCrosshair(crosshair).removedByHero(enemyEntity));
	}

	@After
	public void tearDown() throws Exception {
		Display.destroy();
		crosshair = null;
		enemyEntity = null;
		enemyRect = null;
		crosshRect = null;
	}
}