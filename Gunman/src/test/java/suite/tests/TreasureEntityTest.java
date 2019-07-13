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
import gameDevelopment.HeroEntity;
import gameDevelopment.MySprite;
import gameDevelopment.TreasureEntity;

public class TreasureEntityTest {

	private MySprite sprite;
	private Rectangle heroRect, treasureRect;
	private Entity treasure, hero;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		Display.create();
		sprite = new MySprite(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/chest.png")));
		heroRect = new Rectangle();
		treasureRect = new Rectangle();
		treasure = new TreasureEntity(sprite, x, y);
		hero = new HeroEntity(sprite, x, y);
	}

	@Test
	public void testTreasureEntity() {
		assertNotNull(Game.initTreasures(sprite));
	}

	@Test
	public void testRemove() {
		assertTrue(Game.initTreasures(sprite).removedByHero(treasure));
	}

	@Test
	public void testCollidesWithHero() {
		if (heroRect.intersects(treasureRect))
			assertTrue(Game.initTreasures(sprite).collidesWith(hero));
	}

	@After
	public void tearDown() throws Exception {
		Display.destroy();
		sprite = null;
		heroRect = null;
		treasureRect = null;
		treasure = null;
		hero = null;
	}
}
