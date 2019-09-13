package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.particles.ConfigurableEmitter;

import allinone.Images;
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
	private Image image;

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
			assertNotNull(ImageIO.read(new FileInputStream(Images.SPACESHIP.getImg())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse("Image name is empty", Images.SPACESHIP.getImg().isEmpty());
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
		spaceship = null;
	}
}
