package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.SaveSign;

public class SaveSignTest {

	private SaveSign sign;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		sign = new SaveSign(x, y);
	}

	@Test
	public void testSaveSign() {
		assertNotNull(sign.loadImage(sign.initSave()));
		assertFalse(sign.initSave().isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		sign = null;
	}
}