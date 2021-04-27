package suite.tests;

import static org.junit.Assert.assertTrue;
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
import org.newdawn.slick.Image;

import entities.AdvUfo;
import entities.ShotShip;
import entities.Spaceship;
import entities.Ufo;
import game_engine.Images;

public class UfoCollisionTest {

	private Ufo ufo;
	private AdvUfo advUfo;
	private ShotShip shotShip;
	private int x, y;
	private BufferedImage image;
	private Image img;
	private Spaceship ship;

	@Before
	public void setUp() throws Exception {
		advUfo = new AdvUfo(x, y, image);
		ufo = new Ufo(x, y, img);
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
	public void testShotShipToUfoCollision() {
		shotShip = new ShotShip(x, y, image);
		assertTrue("Ufo colides with shot", ufo.checkCollision(shotShip));
		assertFalse("AdvUfo colides with shot", advUfo.checkCollision(shotShip));
	}
	
	@Test
	public void testShipToUfoCollision() {
		ship = new Spaceship(x, y, img);
		assertTrue("Ufo colides with ship", ufo.checkCollision(ship));
		assertFalse("AdvUfo colides with ship", advUfo.checkCollision(ship));
	}

	@After
	public void tearDown() throws Exception {
		x = 0;
		y = 0;
		advUfo = null;
	}
}
