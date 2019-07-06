package suite.tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import sound_engine.SoundEffects;

public class LoadResourcesTest {

	@Test
	public void testReadFile() {
		assertTrue("Folder 'images' does not exist", Files.exists(Paths.get("images")));
		assertTrue("Folder 'sounds' does not exist", Files.exists(Paths.get("sounds")));

		assertTrue("BLOOP sound file does not exist", Files.exists(Paths.get(SoundEffects.BLOOP.getSound())));
		assertTrue("BURNED sound file does not exist", Files.exists(Paths.get(SoundEffects.BURNED.getSound())));
		assertTrue("EXPLOSION sound file does not exist", Files.exists(Paths.get(SoundEffects.EXPLOSION.getSound())));
		assertTrue("SCREAM sound file does not exist", Files.exists(Paths.get(SoundEffects.SCREAM.getSound())));

		assertNotEquals("BLOOP sound value is NULL", null, SoundEffects.BLOOP.getSound());
		assertNotEquals("BURNED sound value is NULL", null, SoundEffects.BURNED.getSound());
		assertNotEquals("EXPLOSION sound value is NULL", null, SoundEffects.EXPLOSION.getSound());
		assertNotEquals("SCREAM sound value is NULL", null, SoundEffects.SCREAM.getSound());
		assertNotEquals("BLOOP sound value is empty", "", SoundEffects.BLOOP.getSound());
		assertNotEquals("BURNED sound value is empty", "", SoundEffects.BURNED.getSound());
		assertNotEquals("EXPLOSION sound value is empty", "", SoundEffects.EXPLOSION.getSound());
		assertNotEquals("SCREAM sound value is empty", "", SoundEffects.SCREAM.getSound());
	}
}