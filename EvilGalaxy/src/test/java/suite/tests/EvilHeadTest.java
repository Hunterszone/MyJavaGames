package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import enemy.EvilHead;
import game_engine.AIEngine;
import util.Constants;

public class EvilHeadTest {

	private AIEngine aiEngine;
	private EvilHead evilHead;

	@Before
	public void setUp() throws Exception {
		aiEngine = new AIEngine(0, 0);
		evilHead = new EvilHead(Constants.EVILHEAD_X, Constants.EVILHEAD_Y);
	}
	
	@Test
	public void testEvilHeadPositionOnEasy() {
		aiEngine.evilHeadOnEasy();
		assertNotEquals("evilHead is not X-moving on EASY difficulty!", Constants.EVILHEAD_X, aiEngine.getX());
		assertNotEquals("evilHead is not Y-moving on EASY difficulty!", Constants.EVILHEAD_Y, aiEngine.getY());
	}
	
	@Test
	public void testEvilHeadPositionOnMedium() {
		aiEngine.evilHeadOnMedium();
		assertNotEquals("evilHead is not X-moving on MEDIUM difficulty!", Constants.EVILHEAD_X, aiEngine.getX());
		assertNotEquals("evilHead is not Y-moving on MEDIUM difficulty!", Constants.EVILHEAD_Y, aiEngine.getY());
	}
	
	@Test
	public void testEvilHeadPositionOnHard() {
		aiEngine.evilHeadOnHard();
		assertNotEquals("evilHead is not X-moving on HARD difficulty!", Constants.EVILHEAD_X, aiEngine.getX());
		assertNotEquals("evilHead is not Y-moving on HARD difficulty!", Constants.EVILHEAD_Y, aiEngine.getY());
	}
	
	@Test
	public void testListsOfCanons() {
		assertNotNull(evilHead.getCanons());
	}
	
	@Test
	public void testListsOfPlasmaBalls() {
		assertNotNull(evilHead.getEvilPlasmaBalls());
	}

	@After
	public void tearDown() throws Exception {
		aiEngine = null;
		evilHead = null;
	}
}