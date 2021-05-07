package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.junit.Test;

import game_engine.LevelsBgsEngine;

public class LoadResourcesTest {

	@Test
	public void testReadFile() {
		String path = null;
		File sourceImage = null;
		Charset encoding = Charset.defaultCharset();
		assertTrue("Folder 'levels' does not exist", Files.exists(Paths.get("levels")));
		assertTrue("Folder 'backgrounds' does not exist", Files.exists(Paths.get("backgrounds")));
		try {
			if (Files.exists(Paths.get("levels"))) {
				path = Paths.get("levels").toString();
				assertTrue("File " + path + " does not exist", Files.exists(Paths.get(path)));
				assertFalse(LevelsBgsEngine.readFile(path, encoding).isEmpty());
				assertNotNull(LevelsBgsEngine.readFile(path, encoding));
			}
			if (Files.exists(Paths.get("backgrounds"))) {
				sourceImage = new File("someFile");
				assertTrue("File " + sourceImage.toString() + " does not exist",
						Files.exists(Paths.get(sourceImage.toString())));
				assertFalse("Image name is empty",
						ImageIO.read(sourceImage).getProperty(sourceImage.toString()).equals(""));
				assertNotNull(ImageIO.read(sourceImage));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}