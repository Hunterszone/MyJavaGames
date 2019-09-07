package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sokoban.Player;

public class PlayerTest {

	private Player player;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		player = new Player(x, y);
	}

	@Test
	public void testPlayer() {
		assertNotNull(player.getImage());
		assertFalse("Player img name is empty", player.img.isEmpty());
	}

	@After
	public void tearDown() throws Exception {
		player = null;
	}

}