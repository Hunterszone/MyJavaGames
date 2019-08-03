package potogold;

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

public class LepriconTest {

	private BufferedImage image;
	private Lepricon lepricon;
	private GameObject gift1, gift2;
	private Shape collisionSurface;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		x = 60;
		y = 60;
		lepricon = new Lepricon(x, y, image);
	}

	@Test
	public void testLepricon() {
		assertNotNull(lepricon);
	}

	@Test
	public void testDraw() {
		try {
			image = ImageIO.read(new FileInputStream("res/santa.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(image);
		assertNotEquals("Not empty", "", image);
	}

	@Test
	public void testCheckCollision() {
		collisionSurface = new Ellipse(x, y, 60, 60);
		gift1 = new Gift1(x, y, image);
		gift2 = new Gift2(x, y, image);
		assertFalse(lepricon.checkCollision(gift1));
		assertFalse(lepricon.checkCollision(gift2));
	}

	@After
	public void tearDown() throws Exception {
		x = 0;
		y = 0;
		lepricon = null;
	}
}