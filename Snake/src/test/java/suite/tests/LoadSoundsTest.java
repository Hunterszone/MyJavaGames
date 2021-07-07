package suite.tests;

import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Test;

import enums.SoundEffects;

public class LoadSoundsTest {

	@Test
	public void testReadSoundFiles() {
		assertTrue("Folder 'sounds' does not exist!", Files.exists(Paths.get("sounds")));
		if (Files.exists(Paths.get("sounds"))) {
			Arrays.asList(SoundEffects.values()).stream().forEach(soundName -> {
				assertTrue("File " + soundName + " does not exist!", Files.exists(Paths.get(soundName.getSound())));
			});
		}
	}
}
