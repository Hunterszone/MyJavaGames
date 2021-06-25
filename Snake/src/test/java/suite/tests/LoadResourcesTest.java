package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.junit.Test;

import snake.Background;

public class LoadResourcesTest {
	@Test
	public void testReadFile() {
		File sourceImage = null;
		assertTrue("Folder 'backgrounds' does not exist", Files.exists(Paths.get("backgrounds")));
		try {
			if (Files.exists(Paths.get("backgrounds"))) {
				sourceImage = Background.addBackgrounds();
				assertTrue("File " + sourceImage.toString() + " does not exist",
						Files.exists(Paths.get(sourceImage.toString())));
				assertNotEquals("Image name is empty", sourceImage.getName(), "");
				assertNotNull(ImageIO.read(sourceImage));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}