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
import org.newdawn.slick.geom.Ellipse;

import potogold.Gift1;
import potogold.Lepricon;

public class Gift1Test {

	private BufferedImage image;
	private Gift1 gift1;
	private Lepricon lepricon;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		x = 60;
		y = 60;
		gift1 = new Gift1(x, y, image);
	}

	@Test
	public void testGift1IntIntImage() {
		assertNotNull(gift1.collisionSurface = new Ellipse(x, y, 60, 60));
	}

	public void testDraw() {
		try {
			image = ImageIO.read(new FileInputStream("res/gift1.png"));
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
		assertFalse(gift1.checkCollision(lepricon));
	}

	@After
	public void tearDown() throws Exception {
		x = 0;
		y = 0;
		lepricon = null;
		gift1 = null;
	}
}
