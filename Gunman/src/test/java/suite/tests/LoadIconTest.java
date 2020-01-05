package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import game_engine.Images;

public class LoadIconTest {

	private String filePath;

	@Before
	public void setUp() throws Exception {
		filePath = Images.GUNMAN.getImg();
	}

	@Test
	public void testLoadIcon() {
		assertNotNull(filePath);
		assertFalse("Game icon is undefined", filePath.isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		filePath = null;
	}
}