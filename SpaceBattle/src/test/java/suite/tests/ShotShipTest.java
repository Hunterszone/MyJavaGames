package suite.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.newdawn.slick.Sound;
import org.newdawn.slick.particles.ConfigurableEmitter;

import entities.ShotShip;

public class ShotShipTest {

	private Sound sound;
	private ConfigurableEmitter emitter;
	private int x, y;

	@Test
	public void testShotMoon() {
		ShotShip shotShip = new ShotShip(x, y, sound, emitter);
		assertNotNull(shotShip);
	}
}