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
import org.newdawn.slick.geom.Shape;

import potogold.Gift2;
import potogold.Lepricon;

public class Gift2Test {

	private BufferedImage image;
	private Gift2 gift2;
	private Lepricon lepricon;
	private Shape collisionSurface;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		x = 60;
		y = 60;
		gift2 = new Gift2(x, y, image);
	}

	@Test
	public void testGift1IntIntImage() {
		assertNotNull(gift2);
	}

	public void testDraw() {
		try {
			image = ImageIO.read(new FileInputStream("res/gift2.png"));
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
		collisionSurface = new Ellipse(x, y, 60, 60);
		gift2 = new Gift2(x, y, image);
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
