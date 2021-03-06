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

import entities.Gift;
import entities.Lepricon;
import resources.Images;

public class LepriconTest {

	private BufferedImage image;
	private Lepricon lepricon;
	private Gift gift;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		x = 60;
		y = 60;
		lepricon = new Lepricon(x, y, image);
	}

	@Test
	public void testLepricon() {
		assertNotNull(lepricon.collisionSurface = new Ellipse(x, y, 60, 60));
	}

	@Test
	public void testDraw() {
		try {
			assertNotNull(ImageIO.read(new FileInputStream(Images.LEPRICON.getImg())));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertFalse("Image name is empty", Images.LEPRICON.getImg().isEmpty());
	}

	@Test
	public void testCheckCollision() {
		gift = new Gift(x, y, image);
		assertFalse(lepricon.checkCollision(gift));
	}

	@After
	public void tearDown() throws Exception {
		x = 0;
		y = 0;
		lepricon = null;
		gift = null;
	}
}