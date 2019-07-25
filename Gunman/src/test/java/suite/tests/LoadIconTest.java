package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gameDevelopment.Images;

public class LoadIconTest {

	private String filePath;

	@Before
	public void setUp() throws Exception {
		filePath = Images.GUNMAN.getImg();
	}

	@Test
	public void testLoadIcon() {
		assertNotNull(filePath);
		assertNotEquals("Game icon is undefined", "", filePath);
	}

	@After
	public void tearDown() throws Exception {
		filePath = null;
	}
}