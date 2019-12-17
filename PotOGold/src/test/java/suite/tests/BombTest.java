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

import entities.Bomb;
import entities.Lepricon;
import resources.Images;

public class BombTest {

	private BufferedImage image;
	private Bomb bomb;
	private Lepricon lepricon;
	private int x, y;

	@Before
	public void setUp() throws Exception {
		x = 60;
		y = 60;
		bomb = new Bomb(x, y, image);
	}

	@Test
	public void testBomb() {
		assertNotNull(bomb.collisionSurface = new Ellipse(x, y, 60, 60));
	}

	@Test
	public void testDraw() {
		try {
			assertNotNull(ImageIO.read(new FileInputStream(Images.BOMB.getImg())));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertFalse("Image name is empty", Images.BOMB.getImg().isEmpty());
	}

	@Test
	public void testCheckCollision() {
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