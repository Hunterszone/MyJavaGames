package suite.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.OpenGLException;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import entities.Entity;
import entities.HeroEntity;
import entities.TreasureEntity;
import game_engine.Logic;
import game_engine.MySprite;

public class TreasureEntityTest {

	private MySprite sprite;
	private Rectangle heroRect, treasureRect;
	private Entity treasure, hero;
	private int x, y;

	@Before
	public void setUp() throws OpenGLException {
		try {
			Display.create();
			sprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/chest.png")));
		} catch (org.lwjgl.LWJGLException e) {
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		heroRect = new Rectangle();
		treasureRect = new Rectangle();
		treasure = new TreasureEntity(sprite, x, y);
		hero = new HeroEntity(sprite, x, y);
	}

	@Test
	public void testTreasureEntity() {
		try {
			assertNotNull(Logic.initTreasures(sprite));
		} catch (NullPointerException e) {
			// TODO: handle exception
		}
	}

	@Test
	public void testRemove() {
		try {
			assertTrue(Logic.initTreasures(sprite).iterator().next().removedByHero(treasure));
		} catch (NullPointerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCollidesWithHero() {
		if (heroRect.intersects(treasureRect))
			assertTrue(Logic.initTreasures(sprite).iterator().next().collidesWith(hero));
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
