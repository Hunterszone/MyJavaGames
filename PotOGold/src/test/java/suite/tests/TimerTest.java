package suite.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.newdawn.slick.Font;

import entities.Timer;

public class TimerTest {

	private Font font;
	private int x, y;

	@Test
	public void testTimer() {
		Timer timer = new Timer(x, y, font);
		assertNotNull(timer.decrementTime());
	}
}
