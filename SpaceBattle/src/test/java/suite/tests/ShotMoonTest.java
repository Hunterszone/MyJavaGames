package suite.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.newdawn.slick.Sound;
import org.newdawn.slick.particles.ConfigurableEmitter;

import entities.ShotMoon;

public class ShotMoonTest {

	private Sound sound;
	private ConfigurableEmitter emitter;
	private int x, y;

	@Test
	public void testShotMoon() {
		ShotMoon shotMoon = new ShotMoon(x, y, sound, emitter);
		assertNotNull(shotMoon);
	}
}