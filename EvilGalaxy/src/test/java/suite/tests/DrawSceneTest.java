package suite.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import enemy.Alien;
import enemy.Bunker;
import enemy.Dragon;
import game_engine.DrawScene;
import game_engine.UpdateObjects;
import items.Angel;
import items.Gold;
import items.HealthPack;

public class DrawSceneTest {
	
	private DrawScene drawScene;
	
	@Before
	public void setUp() throws Exception {
		drawScene = new DrawScene();
		drawScene.repaint();
	}
	
	@Test
	public void testDrawGameStateL1() {
		Assert.assertNotNull(Alien.aliens);
		Assert.assertNotNull("Healthpacks not loaded for L1", HealthPack.healthpacks);
	}

	@Test
	public void testDrawGameStateL2() {
		Assert.assertNotNull(Dragon.dragons);
		Assert.assertNotNull("Angels not loaded for L2", Angel.angels);
	}
		
	@Test
	public void testDrawGameStateL3() {
		UpdateObjects.setLifeBunker(45);
		Assert.assertNotNull("Bunker object is null", Bunker.bunkerObj);
		Assert.assertNotNull("Healthpacks not loaded for L3", HealthPack.healthpacks);
	}
	
	@Test
	public void testDrawGameStateL4() {
		UpdateObjects.setLifeBunker(50);
		Assert.assertNotNull("Goldstack not loaded for L4", Gold.goldstack);
	}
	
	@Test
	public void testDrawGameStateL5() {
		UpdateObjects.setLifeBunker(50);
		Assert.assertNotNull("HealthPacks not loaded for L5", HealthPack.healthpacks);
	}

	@After
	public void tearDown() throws Exception {
		drawScene = null;
	}
}