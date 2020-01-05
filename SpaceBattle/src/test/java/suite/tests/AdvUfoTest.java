package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Ellipse;

import entities.AdvUfo;
import entities.ShotShip;
import game_engine.Images;

public class AdvUfoTest {

	private AdvUfo advUfo;
	private ShotShip shotShip;
	private int x, y;
	private BufferedImage image;

	@Before
	public void setUp() throws Exception {
		advUfo = new AdvUfo(x, y, image);
	}

	@Test
	public void testAdvUfo() {
		assertNotNull(advUfo.collisionSurface = new Ellipse(x, y, 60, 60));
	}

	@Test
	public void testDraw() {
		try {
			assertNotNull(ImageIO.read(new FileInputStream(Images.ADVUFO.getImg())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse("Image name is empty", Images.ADVUFO.getImg().isEmpty());
	}

	@Test
	public void testCheckCollision() {
		shotShip = new ShotShip(x, y, image);
		assertFalse(advUfo.checkCollision(shotShip));
	}

	@After
	public void tearDown() throws Exception {
		x = 0;
		y = 0;
		advUfo = null;
	}
}
