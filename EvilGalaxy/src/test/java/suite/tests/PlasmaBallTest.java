package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.PlasmaBall;

public class PlasmaBallTest {

private PlasmaBall plasmaBall;
private int x, y;
	
	@Before
	public void setUp() {
		plasmaBall = new PlasmaBall(x, y);
	}
	
	@Test
	public void testPlasmaBallImg() {
		assertNotNull(plasmaBall.getImage());
	}
	
	@Test
	public void testPlasmaBallPositionRight() {
		plasmaBall.movePlasmaBallRight();
		assertNotEquals("PlasmaBall is not X-moving left!", TestUtils.POS_X, plasmaBall.getX());
	}
	
	@Test
	public void testPlasmaBallPositionDiagUp() {
		plasmaBall.movePlasmaBallDiagUp();
		assertNotEquals("PlasmaBall is not X-moving left!", TestUtils.POS_X, plasmaBall.getX());
		assertNotEquals("PlasmaBall is not Y-moving left!", TestUtils.POS_Y, plasmaBall.getY());
	}
	
	@Test
	public void testPlasmaBallPositionDiagDown() {
		plasmaBall.movePlasmaBallDiagDown();
		assertNotEquals("PlasmaBall is not X-moving left!", TestUtils.POS_X, plasmaBall.getX());
		assertNotEquals("PlasmaBall is not Y-moving left!", TestUtils.POS_Y, plasmaBall.getY());
	}
	
	@After
	public void tearDown() {
		plasmaBall = null;
	}
	
}
