package suite.tests;

import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import util.LoadSounds;

public class LoadSoundsTest {

	@Test
	public void testReadSoundFiles() {
		assertTrue("Folder 'sounds' does not exist!", Files.exists(Paths.get("sounds")));
		if (Files.exists(Paths.get("sounds"))) {
			LoadSounds.allSoundFiles.stream().forEach(soundName -> {
				assertTrue("File " + soundName + " does not exist!", Files.exists(Paths.get(soundName)));
			});
		}
	}
}
