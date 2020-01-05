package suite.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.newdawn.slick.Sound;
import org.newdawn.slick.particles.ConfigurableEmitter;

import entities.ShotUfo;

public class ShotUfoTest {

	private Sound sound;
	private ConfigurableEmitter emitter;
	private int x, y;

	@Test
	public void testShotUfo() {
		ShotUfo shotUfo = new ShotUfo(x, y, sound, emitter);
		assertNotNull(shotUfo);
	}
}