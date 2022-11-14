package suite.tests;

import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import enums.Sprite;
import enums.SoundEffect;

public class LoadResourcesTest {

	@Test
	public void testReadFile() {
		assertTrue("Folder 'images' does not exist", Files.exists(Paths.get("images")));
		assertTrue("Folder 'sounds' does not exist", Files.exists(Paths.get("sounds")));

		if (Files.exists(Paths.get("images"))) {
			for (int i = 0; i < Sprite.values().length; i++) {
				assertTrue(Sprite.values()[i].toString() + " image file does not exist",
						Files.exists(Paths.get(Sprite.values()[i].getImg())));
			}
		}

		if (Files.exists(Paths.get("sounds"))) {
			for (int i = 0; i < SoundEffect.values().length; i++) {
				assertTrue(SoundEffect.values()[i].toString() + " sound file does not exist",
						Files.exists(Paths.get(SoundEffect.values()[i].getSound())));
			}
		}
	}
}