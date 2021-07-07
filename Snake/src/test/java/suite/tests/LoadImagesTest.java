package suite.tests;

import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Test;

import enums.Images;

public class LoadImagesTest {

	@Test
	public void testReadImageFiles() {
		assertTrue("Folder 'images' does not exist!", Files.exists(Paths.get("images")));
		if (Files.exists(Paths.get("images"))) {
			Arrays.asList(Images.values()).stream().forEach(imageName -> {
				assertTrue("File " + imageName + " does not exist!", Files.exists(Paths.get(imageName.getImg())));
			});
		}
	}
}
