package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Wall;

public class WallTest {

	Wall wall;
	int x, y;

	@Before
	public void setUp() throws Exception {
		wall = new Wall(x, y);
	}

	@Test
	public void testWall() {
		assertNotNull(wall.getImage());
		assertFalse("Wall img name is empty", wall.img.isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		wall = null;
	}
}
