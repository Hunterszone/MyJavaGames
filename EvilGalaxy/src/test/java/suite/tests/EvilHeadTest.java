package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;

import entities.EvilHead;

public class EvilHeadTest {

	private EvilHead evilHead;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		evilHead = new EvilHead(x, y);
	}

	public void testEvilHead() {
		assertNotNull(evilHead.loadImage(evilHead.initHead()));
		assertNotEquals("", evilHead.initHead());
	}

	public void testListsOfAmmos() {
		assertFalse(evilHead.throwCanons().isEmpty());
		assertFalse(evilHead.throwFireballs().isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		evilHead = null;
	}
}