package suite.tests;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.particles.ParticleIO;

import potogold.Effects;

public class EffectsTest {
	
	private BufferedImage image;
	private Effects effects;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		x = 60;
		y = 60;
		effects = new Effects();
	}

	@Test
	public void testEffects() {
		try {
			assertNotNull(effects.particleSystem = ParticleIO.loadConfiguredSystem("res/particles/empty_system.xml"));
			assertNotNull(effects.lepriconSmoke = ParticleIO.loadEmitter("res/particles/rocket_smoke.xml"));
			assertNotNull(effects.objCollision = ParticleIO.loadEmitter("res/particles/ufo_explosion.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDraw() {
		try {
			image = ImageIO.read(new FileInputStream("res/mine.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(image);
		assertNotEquals("Image name is empty", "", image);
	}

	@After
	public void tearDown() throws Exception {
		x = 0;
		y = 0;
		effects = null;
	}
}
