package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Dragon;
import game_engine.InitObjects;

public class DragonTest {

	private Dragon dragon;

	@Before
	public void setUp() throws Exception {
		dragon = new Dragon(TestUtils.POS_X, TestUtils.POS_Y);
	}

	@Test
	public void testDragonImg() {
		assertNotNull(dragon.getImage());
	}
	
	@Test
	public void testListOfDragons() {
		InitObjects.initDragons();
		
		assertNotNull(Dragon.dragons);
		assertFalse(Dragon.dragons.isEmpty());
	}

	@Test
	public void testDragonPosition() {
		dragon.move();
		assertNotEquals("Dragon is not X-moving!", TestUtils.POS_X, dragon.getX());
	}
	
	@After
	public void tearDown() throws Exception {
		dragon = null;
	}
}