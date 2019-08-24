package suite.tests;

import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;

import org.junit.Test;
import org.newdawn.slick.Sound;
import org.newdawn.slick.particles.ConfigurableEmitter;

import allinone.ShotMoon;

public class ShotMoonTest {

	private ShotMoon shotMoon;
	private Sound sound;
	private ConfigurableEmitter emitter;
	private int x, y;
	private BufferedImage image;

	@Test
	public void testShotMoon() {
		ShotMoon shotMoon = new ShotMoon(x, y, sound, emitter);
		assertNotNull(shotMoon);
	}
}