package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.EvilHead;
import game_engine.Images;

public class EvilHeadTest {

	private EvilHead evilHead;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		evilHead = new EvilHead(x, y);
	}

	@Test
	public void testEvilHead() {
		assertNotNull(evilHead.loadImage(Images.EVILHEAD.getImg()));
		assertFalse(Images.EVILHEAD.getImg().isEmpty());
	}
	
	@Test
	public void testStrikeHead() {
		assertNotNull(evilHead.loadImage(Images.STRIKEHEAD.getImg()));
		assertFalse(Images.STRIKEHEAD.getImg().isEmpty());
	}

	@Test
	public void testListsOfAmmos() {
		assertFalse(evilHead.throwCanons().isEmpty());
		assertFalse(evilHead.throwFireballs().isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		evilHead = null;
	}
}