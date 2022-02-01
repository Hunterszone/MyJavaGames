package suite.tests;

import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import enums.Images;
import enums.SoundEffects;

public class LoadResourcesTest {

	@Test
	public void testReadFile() {
		assertTrue("Folder 'images' does not exist", Files.exists(Paths.get("images")));
		assertTrue("Folder 'sounds' does not exist", Files.exists(Paths.get("sounds")));

		if (Files.exists(Paths.get("images"))) {
			for (int i = 0; i < Images.values().length; i++) {
				assertTrue(Images.values()[i].toString() + " image file does not exist",
						Files.exists(Paths.get(Images.values()[i].getImg())));
			}
		}

		if (Files.exists(Paths.get("sounds"))) {
			for (int i = 0; i < SoundEffects.values().length; i++) {
				assertTrue(SoundEffects.values()[i].toString() + " sound file does not exist",
						Files.exists(Paths.get(SoundEffects.values()[i].getSound())));
			}
		}
	}
}