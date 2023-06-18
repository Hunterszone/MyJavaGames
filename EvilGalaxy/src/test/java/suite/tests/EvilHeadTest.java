package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import enemy.EvilHead;
import game_engine.AIEngine;
import util.Constants;

public class EvilHeadTest {

	@Before
	public void setUp() throws Exception {
		EvilHead.evilHead = new EvilHead(Constants.EVILHEAD_X, Constants.EVILHEAD_Y);
		AIEngine.aiEngine = new AIEngine(Constants.EVILHEAD_X, Constants.EVILHEAD_Y);
	}
	
	@Test
	public void testEvilHeadPositionOnEasy() {
		AIEngine.aiEngine.evilHeadOnEasy();

		assertNotEquals("evilHead is not X-moving on EASY difficulty!", Constants.EVILHEAD_X, EvilHead.evilHead.getX());
		assertNotEquals("evilHead is not Y-moving on EASY difficulty!", Constants.EVILHEAD_Y, EvilHead.evilHead.getY());
	}
	
	@Test
	public void testEvilHeadPositionOnMedium() {
		AIEngine.aiEngine.evilHeadOnMedium();

		assertNotEquals("evilHead is not X-moving on MEDIUM difficulty!", Constants.EVILHEAD_X, EvilHead.evilHead.getX());
		assertNotEquals("evilHead is not Y-moving on MEDIUM difficulty!", Constants.EVILHEAD_Y, EvilHead.evilHead.getY());
	}
	
	@Test
	public void testEvilHeadPositionOnHard() {
		AIEngine.aiEngine.evilHeadOnHard();
		
		assertNotEquals("evilHead is not X-moving on HARD difficulty!", Constants.EVILHEAD_X, EvilHead.evilHead.getX());
		assertNotEquals("evilHead is not Y-moving on HARD difficulty!", Constants.EVILHEAD_Y, EvilHead.evilHead.getY());
	}
	
	@Test
	public void testListsOfCanons() {
		assertNotNull(EvilHead.evilHead.getCanons());
	}
	
	@Test
	public void testListsOfPlasmaBalls() {
		assertNotNull(EvilHead.evilHead.getEvilPlasmaBalls());
	}

}