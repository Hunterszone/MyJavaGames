package suite.tests;

import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;

import org.junit.Test;
import org.newdawn.slick.Sound;
import org.newdawn.slick.particles.ConfigurableEmitter;

import allinone.ShotShip;

public class ShotShipTest {

	private ShotShip shotShip;
	private Sound sound;
	private ConfigurableEmitter emitter;
	private int x, y;
	private BufferedImage image;

	@Test
	public void testShotMoon() {
		ShotShip shotShip = new ShotShip(x, y, sound, emitter);
		assertNotNull(shotShip);
	}
}