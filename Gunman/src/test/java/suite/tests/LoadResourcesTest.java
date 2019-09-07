package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import gameDevelopment.Images;
import gameDevelopment.SoundEffects;

public class LoadResourcesTest {

	@Test
	public void testReadFile() {
		assertTrue("Folder 'images' does not exist", Files.exists(Paths.get("res/images")));
		assertTrue("Folder 'sounds' does not exist", Files.exists(Paths.get("res/sounds")));

		if (Files.exists(Paths.get("res/images"))) {
			for (int i = 0; i < Images.values().length; i++) {
				assertFalse(Images.values()[i].toString() + " image file does not exist",
						!Files.exists(Paths.get(Images.values()[i].getImg())));
				assertFalse(Images.values()[i].toString() + " image value is NULL",
						Images.values()[i].getImg().equals(null));
				assertNotEquals(Images.values()[i].toString() + " image value is empty",
						Images.values()[i].getImg().isEmpty());
			}
		}

		if (Files.exists(Paths.get("res/sounds"))) {
			for (int i = 0; i < SoundEffects.values().length; i++) {
				assertFalse(SoundEffects.values()[i].toString() + " sound file does not exist",
						!Files.exists(Paths.get(SoundEffects.values()[i].getSound())));
				assertNotEquals(SoundEffects.values()[i].toString() + " sound value is NULL",
						SoundEffects.values()[i].getSound().equals(null));
				assertNotEquals(SoundEffects.values()[i].toString() + " sound value is empty",
						SoundEffects.values()[i].getSound().isEmpty());
			}
		}
	}
}