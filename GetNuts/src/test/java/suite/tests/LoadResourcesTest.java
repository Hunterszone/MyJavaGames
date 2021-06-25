package suite.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class LoadResourcesTest {

	@Test
	public void testReadFile() {
		String path = null;
		File sourceImage = null;
		assertTrue("Folder 'levels' does not exist", Files.exists(Paths.get("levels")));
		assertTrue("Folder 'backgrounds' does not exist", Files.exists(Paths.get("backgrounds")));
		if (Files.exists(Paths.get("levels"))) {
			path = Paths.get("levels").toString();
			assertTrue("File " + path + " does not exist", Files.exists(Paths.get(path)));
		}
		if (Files.exists(Paths.get("backgrounds"))) {
			sourceImage = new File(Paths.get("backgrounds").toString());
			assertTrue("File " + sourceImage.getAbsoluteFile().toString() + " does not exist",
					Files.exists(Paths.get(sourceImage.getAbsoluteFile().toString())));
		}
	}
}