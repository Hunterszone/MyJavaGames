package suite.tests;

import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import enemy.Alien;

public class AlienTest {

	private Alien alien;
	
	@Before
	public void setUp() throws Exception {
		alien = Mockito.mock(Alien.class);
		alien.setX(TestUtils.POS_X);
		alien.setY(TestUtils.POS_Y);
	}

	@Test
	public void testAlienPositionOnEasyAndMediumXAxis() {
		Mockito.doNothing().when(alien).move();
		alien.move();
		
		Mockito.verify(alien).move();
		
		assertNotEquals("Alien is not X-moving on EASY and MEDIUM difficulty!", TestUtils.POS_X, alien.getX());
	}
	
	@Test
	public void testAlienPositionYAxis() {
		Mockito.doNothing().when(alien).move();
		alien.move();
		
		Mockito.verify(alien).move();
		
		assertNotEquals("Alien is not Y-moving on EASY and MEDIUM difficulty!", TestUtils.POS_Y, alien.getY());
	}

	@Test
	public void testAlienPositionOnHardXAxis() {
		Mockito.doNothing().when(alien).moveFaster();
		alien.moveFaster();
		
		Mockito.verify(alien).moveFaster();
		
		assertNotEquals("Alien is not X-moving on HARD difficulty!", TestUtils.POS_X, alien.getX());
	}
	
}