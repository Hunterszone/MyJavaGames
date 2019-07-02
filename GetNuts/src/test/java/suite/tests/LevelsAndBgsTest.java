package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.junit.Test;

import sokoban.LevelsAndBgs;

public class LevelsAndBgsTest {

	@Test
	public void testReadFile() {
		String path = null;
		File sourceImage = null;
		Charset encoding = Charset.defaultCharset();
		try {
			if (Files.exists(Paths.get("levels"))) {
				path = "levels/level0.txt";
				assertNotNull(LevelsAndBgs.readFile(path, encoding));
				assertNotEquals("", LevelsAndBgs.readFile(path, encoding));
			} else {
				throw new FileNotFoundException("Folder 'levels' does not exist!");
			}
			if (Files.exists(Paths.get("backgrounds"))) {
				sourceImage = new File("backgrounds/bg0.jpg");
				assertNotNull(ImageIO.read(sourceImage));
				assertNotEquals("", ImageIO.read(sourceImage));
			} else {
				throw new FileNotFoundException("Folder 'backgrounds' does not exist!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
