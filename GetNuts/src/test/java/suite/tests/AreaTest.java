package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import enums.Images;
import game_engine.Area;

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
		assertFalse("Area img name is empty", Images.HOLDER.getImg().isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		area = null;
	}

}