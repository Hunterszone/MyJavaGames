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

import potogold.Gift2;
import potogold.Images;
import potogold.Lepricon;

public class Gift2Test {

	private BufferedImage image;
	private Gift2 gift2;
	private Lepricon lepricon;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		x = 60;
		y = 60;
		gift2 = new Gift2(x, y, image);
	}

	@Test
	public void testGift1IntIntImage() {
		assertNotNull(gift2.collisionSurface = new Ellipse(x, y, 60, 60));
	}

	public void testDraw() {
		try {
			assertNotNull(ImageIO.read(new FileInputStream(Images.GIFT2.getImg())));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertFalse("Image name is empty", Images.GIFT2.getImg().isEmpty());
	}

	@Test
	public void testCheckCollision() {
		assertFalse(gift2.checkCollision(lepricon));
	}

	@After
	public void tearDown() throws Exception {
		x = 0;
		y = 0;
		lepricon = null;
		gift2 = null;
	}
}
