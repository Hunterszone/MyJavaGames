package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.junit.Test;

import sokoban.LevelsBgsEngine;

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
				path = LevelsBgsEngine.addLevels();
				assertTrue("File " + path + " does not exist", Files.exists(Paths.get(path)));
				assertNotNull(LevelsBgsEngine.readFile(path, encoding));
				assertNotEquals("", LevelsBgsEngine.readFile(path, encoding));
			}
			if (Files.exists(Paths.get("backgrounds"))) {
				sourceImage = LevelsBgsEngine.addBackgrounds();
				assertTrue("File " + sourceImage.toString() + " does not exist",
						Files.exists(Paths.get(sourceImage.toString())));
				assertNotNull(ImageIO.read(sourceImage));
				assertNotEquals("", ImageIO.read(sourceImage));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}