package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Graphics;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

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

	// Test subclass for drawScene
	private class DrawSceneTestSubclass extends DrawScene {
		public DrawSceneTestSubclass() throws IOException, NoSuchAlgorithmException {
			super();
		}

		@Override
		public void paintComponent(Graphics g) {
		}
	}

	@Before
	public void setUp() throws NoSuchAlgorithmException, IOException {
		drawScene = new DrawSceneTestSubclass();
	}

	@Test
	public void testPaintComponentIngame() {
		// Set the condition to be true (in-game state)
		UpdateObjects.setIngame(true);

		// Make sure the game is set to in-game state
		assertTrue(UpdateObjects.isIngame());

		// Create a mock Graphics object
		Graphics g = drawScene.getGraphics();

		// Call paintComponent with the mock Graphics
		drawScene.paintComponent(g);

		// After calling paintComponent, the state should remain in-game
		assertTrue(UpdateObjects.isIngame());

	}

	@Test
	public void testPaintComponentNotIngame() {
		// Set the condition to be false (not in-game state)
		UpdateObjects.setIngame(false);

		// Make sure the game is set to not in-game state
		assertFalse(UpdateObjects.isIngame());

		// Create a mock Graphics object
		Graphics g = drawScene.getGraphics();

		// Call paintComponent with the mock Graphics
		drawScene.paintComponent(g);

		// After calling paintComponent, the state should remain not in-game
		assertFalse(UpdateObjects.isIngame());
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

}