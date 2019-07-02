package tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sokoban.Area;

public class AreaTest {

	private Area area;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		area = new Area(x, y);
	}

	@Test
	public void testArea() {
		assertNotNull(area.getImage());
		assertNotEquals("", area.img);
	}

	@After
	public void tearDown() throws Exception {
		area = null;
	}

}
