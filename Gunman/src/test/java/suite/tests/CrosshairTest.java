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

	private MySprite sprite;
	private Entity entity;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		Display.create();
		sprite = new MySprite(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/pointer.png")));
		entity = new EnemyEntity(sprite, x, y);
	}

	@Test
	public void testCrosshair() {
		assertNotNull(Game.initCrosshair(sprite));
	}

	@Test
	public void testRemove() {
		assertTrue(Game.initCrosshair(sprite).remove(entity));
		Display.destroy();
	}

	@After
	public void tearDown() throws Exception {
		sprite = null;
	}
}
