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

public class GiftTest {

	private BufferedImage image;
	private Gift gift1, gift2;
	private Lepricon lepricon;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		x = 60;
		y = 60;
		gift1 = new Gift(x, y, image);
		gift2 = new Gift(x, y, image);
	}

	@Test
	public void testGift1IntIntImage() {
		assertNotNull(gift1.collisionSurface = new Ellipse(x, y, 60, 60));
	}

	public void testDraw() {
		try {
			assertNotNull(ImageIO.read(new FileInputStream(Images.GIFT1.getImg())));
			assertNotNull(ImageIO.read(new FileInputStream(Images.GIFT2.getImg())));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertFalse("Image name is empty", Images.GIFT1.getImg().isEmpty());
		assertFalse("Image name is empty", Images.GIFT2.getImg().isEmpty());
	}

	@Test
	public void testCheckCollision() {
		assertFalse(gift1.checkCollision(lepricon));
		assertFalse(gift2.checkCollision(lepricon));
	}

	@After
	public void tearDown() throws Exception {
		x = 0;
		y = 0;
		lepricon = null;
		gift1 = null;
		gift2 = null;
	}
}
