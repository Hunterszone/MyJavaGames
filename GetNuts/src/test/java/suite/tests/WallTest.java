package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sokoban.Wall;

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
		assertNotEquals("Wall img name is empty", "", wall.img);
	}

	@After
	public void tearDown() throws Exception {
		wall = null;
	}
}
