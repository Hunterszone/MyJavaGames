package suite.tests;

import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;

import org.junit.Test;
import org.newdawn.slick.AppGameContainer;

import game_engine.LoadIcon;

public class LoadIconTest {

	private AppGameContainer app;
	private BufferedImage image;

	@Test
	public void testLoadIcon() {
		assertNotNull(LoadIcon.loadIcon("res/gameico.png", app));
	}

	@Test
	public void testLoadIconInstance() {
		assertNotNull(LoadIcon.loadIconInstance(image, 128));
	}
}
