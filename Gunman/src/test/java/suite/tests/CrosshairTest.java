package suite.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import gameDevelopment.EnemyEntity;
import gameDevelopment.Entity;
import gameDevelopment.Game;
import gameDevelopment.MySprite;

public class CrosshairTest {

	private MySprite crosshair;
	private Entity enemy;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		Display.create();
		crosshair = new MySprite(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/pointer.png")));
		enemy = new EnemyEntity(crosshair, x, y);
	}

	@Test
	public void testCrosshair() {
		assertNotNull(Game.initCrosshair(crosshair));
	}

	@Test
	public void testRemove() {
		assertTrue(Game.initCrosshair(crosshair).remove(enemy));
	}

	@After
	public void tearDown() throws Exception {
		Display.destroy();
		crosshair = null;
		enemy = null;
	}
}
