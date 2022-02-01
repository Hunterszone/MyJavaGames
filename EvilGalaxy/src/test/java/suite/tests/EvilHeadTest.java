package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.EvilHead;
import enums.Images;
import util.Constants;

public class EvilHeadTest {

	private EvilHead evilHead;

	@Before
	public void setUp() throws Exception {
		evilHead = new EvilHead(Constants.EVILHEAD_X, Constants.EVILHEAD_Y);
	}
	
	@Test
	public void testAIOnEasyBorders() {
		evilHead.AIOnEasy();
		assertFalse("EvilHead X out of bounds!", evilHead.getX() < 0 || evilHead.getX() > 1200);
		assertFalse("EvilHead Y out of bounds!", evilHead.getY() < 0 || evilHead.getY() > 700);
	}
	
	@Test
	public void testAIOnMediumBorders() {
		evilHead.AIOnMedium();
		assertFalse("EvilHead X out of bounds!", evilHead.getX() < 0 || evilHead.getX() > 1200);
		assertFalse("EvilHead Y out of bounds!", evilHead.getY() < 0 || evilHead.getY() > 700);
	}
	
	@Test
	public void testAIOnHardBorders() {
		evilHead.AIOnHard();
		assertFalse("EvilHead X out of bounds!", evilHead.getX() < 0 || evilHead.getX() > 1200);
		assertFalse("EvilHead Y out of bounds!", evilHead.getY() < 0 || evilHead.getY() > 700);
	}

	@Test
	public void testEvilHead() {
		assertNotNull(evilHead.loadImage(Images.EVILHEAD.getImg()));
	}
	
	@Test
	public void testStrikeHead() {
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