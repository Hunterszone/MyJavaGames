package suite.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import entities.Alien;
import entities.Bunker;
import entities.Dragon;
import game_engine.DrawScene;
import game_engine.UpdateObjects;
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
	}

	@Test
	public void testDrawGameStateL2() {
		Assert.assertNotNull(Dragon.dragons);
	}
		
	@Test
	public void testDrawGameStateL3() {
		UpdateObjects.lifeBunker = 45;
		Assert.assertNotNull("Bunker object is null", Bunker.bunkerObj);
		Assert.assertNotNull("Healthpack is null L3", HealthPack.healthpack);
	}
	
	@Test
	public void testDrawGameStateL4() {
		UpdateObjects.lifeBunker = 50;
		Assert.assertNotNull("Goldstack is null", Gold.goldstack);
	}
	
	@Test
	public void testDrawGameStateL5() {
		UpdateObjects.lifeBunker = 50;
		Assert.assertNotNull("HealthPack is null L5", HealthPack.healthpack);
	}

	@After
	public void tearDown() throws Exception {
		drawScene = null;
	}
}