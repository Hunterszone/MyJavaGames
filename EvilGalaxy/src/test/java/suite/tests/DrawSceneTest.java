package suite.tests;

import java.awt.Graphics;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import entities.Alien;
import entities.Bunker;
import entities.Dragon;
import game_engine.DrawScene;
import game_engine.InitObjects;
import game_engine.UpdateObjects;
import items.Gold;
import items.HealthPack;

public class DrawSceneTest {
	
	private DrawScene drawScene;
	
	@Before
	public void setUp() throws Exception {
		drawScene = new DrawScene();
	}
	
	@Test
	public void testDrawGameStateL1() {
		drawScene.repaint();
		Assert.assertNotNull(Alien.aliens);
		Assert.assertNotNull(Gold.goldstack);
		Assert.assertNotNull(HealthPack.healthpack);
	}

	@Test
	public void testDrawGameStateL2() {
		drawScene.repaint();
		Assert.assertNotNull(Dragon.dragons);
		Assert.assertNotNull(Gold.goldstack);
		Assert.assertNotNull(HealthPack.healthpack);
	}
		
	@Test
	public void testDrawGameStateL3() {
		drawScene.repaint();
		UpdateObjects.lifeBunker = 45;
		Assert.assertNotNull(Bunker.bunkerObj);
		Assert.assertNotNull(HealthPack.healthpack);
	}
	
	@Test
	public void testDrawGameStateL4() {
		drawScene.repaint();
		UpdateObjects.lifeBunker = 50;
		Assert.assertNotNull(Gold.goldstack);
	}
	
	@Test
	public void testDrawGameStateL5() {
		drawScene.repaint();
		UpdateObjects.lifeBunker = 50;
		Assert.assertNotNull(HealthPack.healthpack);
	}

	@After
	public void tearDown() throws Exception {
		Alien.aliens = null;
		Dragon.dragons = null;
		Gold.goldstack = null;
		HealthPack.healthpack = null;
	}
}