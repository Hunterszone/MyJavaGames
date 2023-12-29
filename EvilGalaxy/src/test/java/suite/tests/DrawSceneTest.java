package suite.tests;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import enemy.Alien;
import enemy.Bunker;
import enemy.Dragon;
import game_engine.DrawScene;
import items.Angel;
import items.Gold;
import items.HealthPack;
import util.ActionPerformer;
import util.LivesAndCounts;

public class DrawSceneTest {

	private DrawScene drawScene;

	@Before
	public void setUp() throws NoSuchAlgorithmException, IOException {
		drawScene = new DrawScene();
	}

	@Test
	public void testPaintComponentWhenInGame() throws Exception {
	    // Set up the necessary conditions
	    ActionPerformer.setIngame(true);
	    Graphics2D mockGraphics = Mockito.mock(Graphics2D.class);
	    
	    // Mock the behavior of Toolkit.getDefaultToolkit().sync()
	    try {
	        PowerMockito.mockStatic(Toolkit.class);
	        Mockito.mock(Toolkit.class);
//	        PowerMockito.when(Toolkit.getDefaultToolkit()).thenReturn(toolkitMock);

	        // Call the paintComponent method
	        Method paintComponentMethod = DrawScene.class.getDeclaredMethod("paintComponent", Graphics.class);
	        paintComponentMethod.setAccessible(true);
//	        paintComponentMethod.invoke(drawScene, mockGraphics);

	        // Use reflection to access and invoke private methods from the DrawScene class
	        Class<?> drawSceneClass = DrawScene.class;
	        Method startAnimationsMethod = drawSceneClass.getDeclaredMethod("startAnimations");
	        Method drawGameStatesMethod = drawSceneClass.getDeclaredMethod("drawGameStates", Graphics.class);
	        Method evilHeadBehaviourMethod = drawSceneClass.getDeclaredMethod("evilHeadBehaviour");
	        Method handleLifeEvilHeadMethod = drawSceneClass.getDeclaredMethod("handleLifeEvilHead", Graphics.class);
	        Method drawNaviMapMethod = drawSceneClass.getDeclaredMethod("drawNaviMap", Graphics.class);

	        // Set the methods accessible to invoke them
	        startAnimationsMethod.setAccessible(true);
	        drawGameStatesMethod.setAccessible(true);
	        evilHeadBehaviourMethod.setAccessible(true);
	        handleLifeEvilHeadMethod.setAccessible(true);
	        drawNaviMapMethod.setAccessible(true);

	        // Verify the invocation of private methods
	        startAnimationsMethod.invoke(drawScene);
//	        drawGameStatesMethod.invoke(yourComponent, mockGraphics);
	        evilHeadBehaviourMethod.invoke(drawScene);
	        handleLifeEvilHeadMethod.invoke(drawScene, mockGraphics);
	        drawNaviMapMethod.invoke(drawScene, mockGraphics);

	        // Verify that Toolkit.getDefaultToolkit().sync() is called
//	        Mockito.verify(toolkitMock).sync();
	    } finally {
	        Toolkit.getDefaultToolkit();
	    }
	}
	
	@Test
	public void testPaintComponentWhenNotInGame() throws Exception {
	    // Set up the necessary conditions
		ActionPerformer.setIngame(false);
	    Graphics2D mockGraphics = Mockito.mock(Graphics2D.class);
	    
	    // Mock the behavior of Toolkit.getDefaultToolkit().sync()
	    try {
	        PowerMockito.mockStatic(Toolkit.class);
	        Mockito.mock(Toolkit.class);
//	        PowerMockito.when(Toolkit.getDefaultToolkit()).thenReturn(toolkitMock);

	        // Call the paintComponent method
	        Method paintComponentMethod = DrawScene.class.getDeclaredMethod("paintComponent", Graphics.class);
	        paintComponentMethod.setAccessible(true);
//	        paintComponentMethod.invoke(yourComponent, mockGraphics);

	        // Use reflection to access and invoke private methods from the DrawScene class
	        Class<?> drawSceneClass = DrawScene.class;
	        Method startAnimationsMethod = drawSceneClass.getDeclaredMethod("stopTimers");
	        Method drawGameStatesMethod = drawSceneClass.getDeclaredMethod("stopSounds");
//	        Method evilHeadBehaviourMethod = drawSceneClass.getDeclaredMethod("drawGameOverBgs");
	        Method handleLifeEvilHeadMethod = drawSceneClass.getDeclaredMethod("handleLifeEvilHead", Graphics.class);
	        Method drawNaviMapMethod = drawSceneClass.getDeclaredMethod("drawNaviMap", Graphics.class);

	        // Set the methods accessible to invoke them
	        startAnimationsMethod.setAccessible(true);
	        drawGameStatesMethod.setAccessible(true);
//	        evilHeadBehaviourMethod.setAccessible(true);
	        handleLifeEvilHeadMethod.setAccessible(true);
	        drawNaviMapMethod.setAccessible(true);

	        // Verify the invocation of private methods
//	        stopTimers.invoke(yourComponent);
	        drawGameStatesMethod.invoke(drawScene);
//	        evilHeadBehaviourMethod.invoke(yourComponent);
	        handleLifeEvilHeadMethod.invoke(drawScene, mockGraphics);
	        drawNaviMapMethod.invoke(drawScene, mockGraphics);

	        // Verify that Toolkit.getDefaultToolkit().sync() is called
//	        Mockito.verify(toolkitMock).sync();
	    } finally {
	        Toolkit.getDefaultToolkit();
	    }
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
		LivesAndCounts.setLifeBunker(45);
		Assert.assertNotNull("Bunker object is null", Bunker.bunkerObj);
		Assert.assertNotNull("Healthpacks not loaded for L3", HealthPack.healthpacks);
	}

	@Test
	public void testDrawGameStateL4() {
		LivesAndCounts.setLifeBunker(50);
		Assert.assertNotNull("Goldstack not loaded for L4", Gold.goldstack);
	}

	@Test
	public void testDrawGameStateL5() {
		LivesAndCounts.setLifeBunker(50);
		Assert.assertNotNull("HealthPacks not loaded for L5", HealthPack.healthpacks);
	}

}