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

public class BombTest {

	private BufferedImage image;
	private Bomb bomb;
	private GameObject lepricon;
	private Shape collisionSurface;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		x = 60;
		y = 60;
		bomb = new Bomb(x, y, image);
	}

	@Test
	public void testBomb() {
		assertNotNull(bomb);
	}

	@Test
	public void testDraw() {
		try {
			image = ImageIO.read(new FileInputStream("res/mine.png"));
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
		lepricon = new Lepricon(x, y, image);
		assertFalse(bomb.checkCollision(lepricon));
	}

	@After
	public void tearDown() throws Exception {
		x = 0;
		y = 0;
		bomb = null;
	}
}