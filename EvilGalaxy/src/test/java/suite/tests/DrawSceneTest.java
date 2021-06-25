package suite.tests;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import entities.Alien;
import entities.Dragon;
import game_engine.InitObjects;
import game_engine.UpdateObjects;
import items.Gold;

public class DrawSceneTest {
	
	@Before
	public void setUp() throws Exception {
		InitObjects.ingame = true;
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		Gold.goldstack = new ArrayList<Gold>();
	}

	@Test
	public void testDrawGameStateL1() {
		Alien.aliens.add(new Alien(0, 0));
		Assert.assertTrue(Alien.aliens.size() > 0);
	}
	
	@Test
	public void testDrawGameStateL2() {
		Assert.assertTrue(Alien.aliens.isEmpty());
		Assert.assertTrue(Dragon.dragons.add(new Dragon(0, 0)));
	}
	
	@Test
	public void testDrawGameStateL3() {
		UpdateObjects.lifeBunker = 45;
		Assert.assertTrue(Alien.aliens.isEmpty());
		Assert.assertTrue(Dragon.dragons.isEmpty());
	}
	
	@Test
	public void testDrawGameStateL4() {
		UpdateObjects.lifeBunker = 50;
		Assert.assertTrue(Dragon.dragons.isEmpty());
		Assert.assertTrue(Gold.goldstack.add(new Gold(0, 0)));
	}
	
	@Test
	public void testDrawGameStateL5() {
		UpdateObjects.lifeBunker = 50;
		Assert.assertTrue(Dragon.dragons.isEmpty());
		Assert.assertTrue(Gold.goldstack.isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		Alien.aliens = null;
		Dragon.dragons = null;
	}
}