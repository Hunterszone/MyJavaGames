package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.EvilHead;
import enums.Images;

public class EvilHeadTest {

	private EvilHead evilHead;

	@Before
	public void setUp() throws Exception {
		evilHead = new EvilHead(450, 450);
	}
	
	@Test
	public void testAIOnEasyBorders() {
		evilHead.AIOnEasy();
		assertFalse(evilHead.getX() < 0 || evilHead.getX() > 1200);
		assertFalse(evilHead.getY() < 0 || evilHead.getY() > 700);
	}
	
	@Test
	public void testAIOnMediumBorders() {
		evilHead.AIOnMedium();
		assertFalse(evilHead.getX() < 0 || evilHead.getX() > 1200);
		assertFalse(evilHead.getY() < 0 || evilHead.getY() > 700);
	}
	
	@Test
	public void testAIOnHardBorders() {
		evilHead.AIOnHard();
		assertFalse(evilHead.getX() < 0 || evilHead.getX() > 1200);
		assertFalse(evilHead.getY() < 0 || evilHead.getY() > 700);
	}

	@Test
	public void testEvilHead() {
		assertNotNull(evilHead.loadImage(Images.EVILHEAD.getImg()));
		assertFalse(Images.EVILHEAD.getImg().isEmpty());
	}
	
	@Test
	public void testStrikeHead() {
		evilHead.strikeHead();
		assertNotNull(evilHead.loadImage(Images.STRIKEHEAD.getImg()));
	}

	@Test
	public void testListsOfAmmos() {
		assertFalse(evilHead.canons().isEmpty());
		assertFalse(evilHead.plasmaBalls().isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		evilHead = null;
	}
}