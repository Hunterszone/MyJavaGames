package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.junit.Test;

import snake.Backgrounds;

public class LoadResourcesTest {
	@Test
	public void testReadFile() {
		File sourceImage = null;
		assertTrue("Folder 'backgrounds' does not exist", Files.exists(Paths.get("backgrounds")));
		try {
			if (Files.exists(Paths.get("backgrounds"))) {
				sourceImage = Backgrounds.addBackgrounds();
				assertTrue("File " + sourceImage.toString() + " does not exist",
						Files.exists(Paths.get(sourceImage.toString())));
				assertFalse("Image name is empty", ImageIO.read(sourceImage).getProperty(sourceImage.toString()).equals(""));
				assertNotNull(ImageIO.read(sourceImage));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}