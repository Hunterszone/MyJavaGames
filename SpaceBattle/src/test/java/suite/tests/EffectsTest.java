package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.particles.ParticleIO;

import allinone.Effects;

public class EffectsTest {

	private Effects effects;

	@Before
	public void setUp() throws Exception {
		effects = new Effects();
	}

	@Test
	public void testEffects() {
		try {
			assertNotNull(effects.particleSystem = ParticleIO.loadConfiguredSystem("res/particles/empty_system.xml"));
			assertNotNull(effects.rocketSmoke = ParticleIO.loadEmitter("res/particles/rocket_smoke.xml"));
			assertNotNull(effects.ufoExplosion = ParticleIO.loadEmitter("res/particles/ufo_explosion.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDraw() {
		assertFalse(effects.particleSystem.equals(null));
		assertNotEquals("Effect name is empty", "", effects.particleSystem);
	}

	@After
	public void tearDown() throws Exception {
		effects = null;
	}
}