package suite.tests;

import static org.junit.Assert.assertNotEquals;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import enemy.Crosshair;
import game_engine.DrawScene;
import util.Constants;

public class CrosshairTest {

	private Crosshair crosshair;
	private Component component;

	@Before
	public void setUp() throws Exception {
		crosshair = new Crosshair(Constants.MYCROSSHAIR_X, Constants.MYCROSSHAIR_Y);
		component = new DrawScene();
	}

	@Test
	public void testCrosshairPositionX() throws IOException {
		KeyEvent rightArrowKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, '\u2192');
		crosshair.keyPressed(rightArrowKey);
		
		crosshair.move();
		
		assertNotEquals("Crosshair is not X-moving!", Constants.MYSHIP_X, crosshair.getX());
	}
	
	@Test
	public void testCrosshairShakedPositionX() throws IOException {
		KeyEvent rightArrowKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, '\u2192');
		crosshair.keyPressed(rightArrowKey);
		
		crosshair.shakeCrosshair();
		
		assertNotEquals("Crosshair is not X-moving!", Constants.MYSHIP_X, crosshair.getX());
	}

	@Test
	public void testCrosshairPositionY() throws IOException {
		KeyEvent downArrowKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, '\u2193');
		crosshair.keyPressed(downArrowKey);
		
		crosshair.move();
		
		assertNotEquals("Crosshair is not Y-moving!", Constants.MYSHIP_Y, crosshair.getY());
	}
	
	@Test
	public void testCrosshairShakedPositionY() throws IOException {
		KeyEvent downArrowKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, '\u2193');
		crosshair.keyPressed(downArrowKey);
		
		crosshair.shakeCrosshair();
		
		assertNotEquals("Crosshair is not Y-moving!", Constants.MYSHIP_Y, crosshair.getY());
	}

	@After
	public void tearDown() throws Exception {
		crosshair = null;
	}
}