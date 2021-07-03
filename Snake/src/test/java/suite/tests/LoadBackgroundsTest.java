package suite.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Test;

import util.Background;

public class LoadBackgroundsTest {
	
	@Test
	public void testReadBackgrounds() {
		File sourceImage = null;
		Assert.assertTrue("Folder 'backgrounds' does not exist!", Files.exists(Paths.get("backgrounds")));
		try {
			if (Files.exists(Paths.get("backgrounds"))) {
				sourceImage = Background.addBackgrounds();
				Assert.assertTrue("File " + sourceImage.toString() + " does not exist!",
						Files.exists(Paths.get(sourceImage.toString())));
				Assert.assertNotNull("Image is null!", ImageIO.read(sourceImage));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}