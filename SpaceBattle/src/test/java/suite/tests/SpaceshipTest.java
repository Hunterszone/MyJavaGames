package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.particles.ConfigurableEmitter;

import allinone.ShotMoon;
import allinone.ShotUfo;
import allinone.Spaceship;

public class SpaceshipTest {

	private Spaceship spaceship;
	private ShotUfo shotUfo;
	private ShotMoon shotMoon;
	private Sound sound;
	private ConfigurableEmitter emitter;
	private int x, y;
	private BufferedImage image;

	@Before
	public void setUp() throws Exception {
		spaceship = new Spaceship(x, y, image);
	}

	@Test
	public void testAdvUfo() {
		assertNotNull(spaceship.collisionSurface = new Ellipse(x, y, 60, 60));
	}

	@Test
	public void testDraw() {
		try {
			image = ImageIO.read(new FileInputStream("res/spaceship.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(image);
		assertNotEquals("Image name is empty", "", image);
	}

	@Test
	public void testCheckCollision() {
		shotUfo = new ShotUfo(x, y, sound, emitter);
		shotMoon = new ShotMoon(x, y, sound, emitter);
		assertFalse(spaceship.checkCollision(shotUfo));
		assertFalse(spaceship.checkCollision(shotMoon));
	}

	@After
	public void tearDown() throws Exception {
		x = 0;
		y = 0;
		spaceship = null;
	}
}
