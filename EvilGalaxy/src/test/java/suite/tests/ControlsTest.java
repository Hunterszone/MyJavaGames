package suite.tests;

import static org.junit.Assert.assertTrue;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import enemy.Alien;
import enemy.Dragon;
import game_engine.Controls;
import game_engine.DrawScene;
import game_engine.UpdateObjects;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
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
	public void testThatSKeyIsPressed() {
		KeyEvent sKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_S, '\u0053');
		
		controls.keyPressed(sKey);

		assertTrue("'S' key was not pressed!", Flags.isSPressed);
	}
	
	@Test
	public void testThatAKeyIsPressed() {
		KeyEvent aKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_A, '\u0041');
		
		controls.keyPressed(aKey);

		assertTrue("'A' key was not pressed!", !Flags.isSPressed);
	}
	
	@Test
	public void testThatPKeyIsPressed() {
		KeyEvent pKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_P, '\u0050');
		
		controls.keyPressed(pKey);

		assertTrue("'P' key was not pressed!", pKey.getKeyCode() == KeyEvent.VK_P);
	}
	
	@Test
	public void testThatRightArrowKeyIsPressedWhenOnHard() {
		KeyEvent rightArrowKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_RIGHT, '\u2192');
		Flags.isHPressed = true;
		
		controls.keyPressed(rightArrowKey);

		assertTrue("'rightArrowKey' key was not pressed!", rightArrowKey.getKeyCode() == KeyEvent.VK_RIGHT);
	}
	
	@Test
	public void testThatRightArrowKeyIsPressedWhenOnMedium() {
		KeyEvent rightArrowKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_RIGHT, '\u2192');
		Flags.isMPressed = true;
		
		controls.keyPressed(rightArrowKey);

		assertTrue("'rightArrowKey' key was not pressed!", rightArrowKey.getKeyCode() == KeyEvent.VK_RIGHT);
	}
	
	@Test
	public void testThatRightArrowKeyIsPressedWhenOnEasy() {
		KeyEvent rightArrowKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_RIGHT, '\u2192');
		Flags.isEPressed = true;
		
		controls.keyPressed(rightArrowKey);

		assertTrue("'rightArrowKey' key was not pressed!", rightArrowKey.getKeyCode() == KeyEvent.VK_RIGHT);
	}
	
	@Test
	public void testThatControlKeyIsPressedWhenOnL2OrL4() {
		Alien.aliens = new ArrayList<Alien>();
		UpdateObjects.setLifeBunker(40);
		KeyEvent controlKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_CONTROL, '\u0011');
		
		controls.keyPressed(controlKey);

		assertTrue("'controlKey' key was not pressed!", controlKey.getKeyCode() == KeyEvent.VK_CONTROL);
	}
	
	@Test
	public void testThatControlKeyIsPressedWhenOnL1OrL4() {
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
	public void testThatSpaceKeyIsPressedWhenOnL1OrL4() {
		Alien.aliens = new ArrayList<Alien>();
		Alien.aliens.add(new Alien(TestUtils.POS_X, TestUtils.POS_Y));
		KeyEvent spaceKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_SPACE, '\u0020');
		
		controls.keyPressed(spaceKey);

		assertTrue("'spaceKey' key was not pressed!", spaceKey.getKeyCode() == KeyEvent.VK_SPACE);
	}
	
	@Test
	public void testThatSpaceKeyIsPressedWhenOnL2OrL4() {
		Alien.aliens = new ArrayList<Alien>();
		Dragon.dragons = new ArrayList<Dragon>();
		Dragon.dragons.add(new Dragon(TestUtils.POS_X, TestUtils.POS_Y));
		KeyEvent spaceKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_SPACE, '\u0020');
		
		controls.keyPressed(spaceKey);

		assertTrue("'spaceKey' key was not pressed!", spaceKey.getKeyCode() == KeyEvent.VK_SPACE);
	}
	
	@Test
	public void testThatNumOneKeyIsPressed() throws MaryConfigurationException, SynthesisException {
		KeyEvent numOneKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_1, '\u0031');
		
		controls.keyPressed(numOneKey);
		
		assertTrue("'numOneKey' key was not pressed!", numOneKey.getKeyCode() == KeyEvent.VK_1);
	}
	
	@Test
	public void testThatNumTwoKeyIsPressed() throws MaryConfigurationException, SynthesisException {
		KeyEvent numTwoKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_2, '\u0032');
		
		controls.keyPressed(numTwoKey);
		
		assertTrue("'numTwoKey' key was not pressed!", numTwoKey.getKeyCode() == KeyEvent.VK_2);
	}
	
	@Test
	public void testThatNumThreeKeyIsPressed() throws MaryConfigurationException, SynthesisException {
		KeyEvent numThreeKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_3, '\u0033');
		
		controls.keyPressed(numThreeKey);
		
		assertTrue("'numTwoKey' key was not pressed!", numThreeKey.getKeyCode() == KeyEvent.VK_3);
	}
	
	@Test
	public void testThatNumFourKeyIsPressed() throws MaryConfigurationException, SynthesisException {
		KeyEvent numFourKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_4, '\u0034');
		
		controls.keyPressed(numFourKey);
		
		assertTrue("'numFourKey' key was not pressed!", numFourKey.getKeyCode() == KeyEvent.VK_4);
	}
	
	@Test
	public void testThatRKeyIsPressed() throws MaryConfigurationException, SynthesisException {
		KeyEvent rKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_R, '\u0052');
		
		controls.keyPressed(rKey);
		
		assertTrue("'numFourKey' key was not pressed!", rKey.getKeyCode() == KeyEvent.VK_R);
	}

	@Test
	public void testThatEKeyIsPressed() throws MaryConfigurationException, SynthesisException {
		KeyEvent eKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_E, '\u0045');
		
		controls.keyPressed(eKey);
		
		assertTrue("'eKey' key was not pressed!", eKey.getKeyCode() == KeyEvent.VK_E);
	}
	
	@Test
	public void testThatMKeyIsPressed() throws MaryConfigurationException, SynthesisException {
		KeyEvent mKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_M, '\u004d');
		
		controls.keyPressed(mKey);
		
		assertTrue("'mKey' key was not pressed!", mKey.getKeyCode() == KeyEvent.VK_M);
	}
	
	@Test
	public void testThatHKeyIsPressed() throws MaryConfigurationException, SynthesisException {
		KeyEvent hKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_H, '\u0048');
		
		controls.keyPressed(hKey);
		
		assertTrue("'hKey' key was not pressed!", hKey.getKeyCode() == KeyEvent.VK_H);
	}
	
	@Test
	public void testThatNKeyIsPressed() throws MaryConfigurationException, SynthesisException {
		KeyEvent nKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_N, '\u004e');
		
		controls.keyPressed(nKey);
		
		assertTrue("'nKey' key was not pressed!", nKey.getKeyCode() == KeyEvent.VK_N);
	}
	
	@Test
	public void testThatGKeyIsPressed() throws MaryConfigurationException, SynthesisException {
		KeyEvent gKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_G, '\u0047');
		
		controls.keyPressed(gKey);
		
		assertTrue("'gKey' key was not pressed!", gKey.getKeyCode() == KeyEvent.VK_G);
	}
	
	@Test
	public void testThatZKeyIsPressed() throws MaryConfigurationException, SynthesisException {
		KeyEvent zKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_Z, '\u005a');
		
		controls.keyPressed(zKey);
		
		assertTrue("'gKey' key was not pressed!", zKey.getKeyCode() == KeyEvent.VK_Z);
	}
	
	@Test
	public void testThatEscKeyIsPressed() throws MaryConfigurationException, SynthesisException {
		KeyEvent escKey = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
				KeyEvent.VK_ESCAPE, '\u005a');
		
		controls.keyPressed(escKey);
		
		assertTrue("'escKey' key was not pressed!", escKey.getKeyCode() == KeyEvent.VK_ESCAPE);
	}
}
