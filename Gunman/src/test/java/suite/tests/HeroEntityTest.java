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

import entities.EnemyEntity;
import entities.Entity;
import entities.HealthEntity;
import entities.TreasureEntity;
import game_engine.MySprite;
import main.Game;

public class HeroEntityTest {

	private MySprite hero, enemy, treasure, healthpack;
	private Entity enemyEntity, treasureEntity, healthpackEntity;
	private Rectangle heroRect, enemyRect, treasureRect, healthRect;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		Display.create();
		hero = new MySprite(
				TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/gunman.png")));
		enemy = new MySprite(
				TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/bird.png")));
		treasure = new MySprite(
				TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/chest.png")));
		healthpack = new MySprite(
				TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/health.png")));
		enemyEntity = new EnemyEntity(enemy, x, y);
		treasureEntity = new TreasureEntity(treasure, x, y);
		healthpackEntity = new HealthEntity(healthpack, x, y);
		heroRect = new Rectangle();
		enemyRect = new Rectangle();
		treasureRect = new Rectangle();
		healthRect = new Rectangle();
	}

	@Test
	public void testHeroEntity() {
		assertNotNull(Game.initHero(hero));
	}

	@Test
	public void testCollidesWithEnemy() {
		if (heroRect.intersects(enemyRect))
			assertTrue(Game.initHero(hero).collidesWith(enemyEntity));
	}

	@Test
	public void testCollidesWithTreasure() {
		if (heroRect.intersects(treasureRect))
			assertTrue(Game.initHero(hero).collidesWith(treasureEntity));
	}

	@Test
	public void testCollidesWithHealth() {
		if (heroRect.intersects(healthRect))
			assertTrue(Game.initHero(hero).collidesWith(healthpackEntity));
	}

	@Test
	public void testRemoveEnemy() {
		assertTrue(Game.initHero(hero).removedByHero(enemyEntity));
	}

	@Test
	public void testRemoveTreasure() {
		assertTrue(Game.initHero(hero).removedByHero(treasureEntity));
	}

	@Test
	public void testRemoveHealth() {
		assertTrue(Game.initHero(hero).removedByHero(healthpackEntity));
	}

	@After
	public void tearDown() throws Exception {
		Display.destroy();
	}
}
