package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.EvilHead;
import util.Constants;

public class EvilHeadTest {

	private EvilHead evilHead;

	@Before
	public void setUp() throws Exception {
		evilHead = new EvilHead(Constants.EVILHEAD_X, Constants.EVILHEAD_Y);
	}
	
	@Test
	public void testEvilHeadPositionOnEasy() {
		evilHead.AIOnEasy();
		assertNotEquals("EvilHead is not X-moving on EASY difficulty!", Constants.EVILHEAD_X, evilHead.getX());
		assertNotEquals("EvilHead is not Y-moving on EASY difficulty!", Constants.EVILHEAD_Y, evilHead.getY());
	}
	
	@Test
	public void testEvilHeadPositionOnMedium() {
		evilHead.AIOnMedium();
		assertNotEquals("EvilHead is not X-moving on MEDIUM difficulty!", Constants.EVILHEAD_X, evilHead.getX());
		assertNotEquals("EvilHead is not Y-moving on MEDIUM difficulty!", Constants.EVILHEAD_Y, evilHead.getY());
	}
	
	@Test
	public void testEvilHeadPositionOnHard() {
		evilHead.AIOnHard();
		assertNotEquals("EvilHead is not X-moving on HARD difficulty!", Constants.EVILHEAD_X, evilHead.getX());
		assertNotEquals("EvilHead is not Y-moving on HARD difficulty!", Constants.EVILHEAD_Y, evilHead.getY());
	}
	
	@Test
	public void testListsOfCanons() {
		assertNotNull(evilHead.canons());
		assertFalse(evilHead.canons().isEmpty());
	}
	
	@Test
	public void testListsOfPlasmaBalls() {
		assertNotNull(evilHead.plasmaBalls());
		assertFalse(evilHead.plasmaBalls().isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		evilHead = null;
	}
}