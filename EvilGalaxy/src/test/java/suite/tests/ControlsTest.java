package suite.tests;

import static org.junit.Assert.assertTrue;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import enemy.Alien;
import enemy.Dragon;
import game_engine.Controls;
import game_engine.DrawScene;
import game_engine.UpdateObjects;
import util.Flags;

public class ControlsTest {

	private Component component;
	private Controls controls;

	@Before
	public void setUp() throws Exception {
		component = new DrawScene();
		controls = new Controls();
	}

	@Test
	public void testThatSKeyIsPressed() throws IOException {
		KeyEvent sKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_S, '\u0053');
		
		controls.keyPressed(sKey);

		assertTrue("'S' key was not pressed!", Flags.isSPressed);
	}
	
	@Test
	public void testThatAKeyIsPressed() throws IOException {
		KeyEvent aKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_A, '\u0041');
		
		controls.keyPressed(aKey);

		assertTrue("'A' key was not pressed!", !Flags.isSPressed);
	}
	
	@Test
	public void testThatPKeyIsPressed() throws IOException {
		KeyEvent pKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_P, '\u0050');
		
		controls.keyPressed(pKey);

		assertTrue("'P' key was not pressed!", pKey.getKeyCode() == KeyEvent.VK_P);
	}
	
	@Test
	public void testThatRightArrowKeyIsPressedWhenOnHard() throws IOException {
		KeyEvent rightArrowKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_RIGHT, '\u2192');
		Flags.isHPressed = true;
		
		controls.keyPressed(rightArrowKey);

		assertTrue("'rightArrowKey' key was not pressed!", rightArrowKey.getKeyCode() == KeyEvent.VK_RIGHT);
	}
	
	@Test
	public void testThatRightArrowKeyIsPressedWhenOnMedium() throws IOException {
		KeyEvent rightArrowKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_RIGHT, '\u2192');
		Flags.isMPressed = true;
		
		controls.keyPressed(rightArrowKey);

		assertTrue("'rightArrowKey' key was not pressed!", rightArrowKey.getKeyCode() == KeyEvent.VK_RIGHT);
	}
	
	@Test
	public void testThatRightArrowKeyIsPressedWhenOnEasy() throws IOException {
		KeyEvent rightArrowKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_RIGHT, '\u2192');
		Flags.isEPressed = true;
		
		controls.keyPressed(rightArrowKey);

		assertTrue("'rightArrowKey' key was not pressed!", rightArrowKey.getKeyCode() == KeyEvent.VK_RIGHT);
	}
	
	@Test
	public void testThatControlKeyIsPressedWhenOnL2OrL4() throws IOException {
		Alien.aliens = new ArrayList<Alien>();
		UpdateObjects.setLifeBunker(40);
		KeyEvent controlKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_CONTROL, '\u0011');
		
		controls.keyPressed(controlKey);

		assertTrue("'controlKey' key was not pressed!", controlKey.getKeyCode() == KeyEvent.VK_CONTROL);
	}
	
	@Test
	public void testThatControlKeyIsPressedWhenOnL1OrL4() throws IOException {
		Dragon.dragons = new ArrayList<Dragon>();
		Alien.aliens = new ArrayList<Alien>();
		Alien.aliens.add(new Alien(TestUtils.POS_X, TestUtils.POS_Y));
		UpdateObjects.setLifeBunker(50);
		KeyEvent controlKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_CONTROL, '\u0011');
		
		controls.keyPressed(controlKey);

		assertTrue("'controlKey' key was not pressed!", controlKey.getKeyCode() == KeyEvent.VK_CONTROL);
	}
	
	@Test
	public void testThatSpaceKeyIsPressedWhenOnL1OrL4() throws IOException {
		Alien.aliens = new ArrayList<Alien>();
		Alien.aliens.add(new Alien(TestUtils.POS_X, TestUtils.POS_Y));
		KeyEvent spaceKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_SPACE, '\u0020');
		
		controls.keyPressed(spaceKey);

		assertTrue("'spaceKey' key was not pressed!", spaceKey.getKeyCode() == KeyEvent.VK_SPACE);
	}
	
	@Test
	public void testThatSpaceKeyIsPressedWhenOnL2OrL4() throws IOException {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		Dragon.dragons.add(new Dragon(TestUtils.POS_X, TestUtils.POS_Y));
		KeyEvent spaceKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_SPACE, '\u0020');
		
		controls.keyPressed(spaceKey);

		assertTrue("'spaceKey' key was not pressed!", spaceKey.getKeyCode() == KeyEvent.VK_SPACE);
	}

}
