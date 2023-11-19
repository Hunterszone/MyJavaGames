package suite.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import enemy.Alien;
import enemy.Bunker;
import enemy.Dragon;
import enemy.EvilHead;
import enemy.PlayerShip;
import game_engine.SaveGame;
import game_engine.UpdateObjects;
import items.Gold;
import items.HealthPack;
import util.ActionPerformer;
import util.TextToSpeech;

public class SaveGameTest {

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();
	
    @Test
    public void testSaveGameDataToFile() throws Exception {
    	ActionPerformer.setIngame(true);
        TextToSpeech.voiceInterruptor = false;
        Alien.aliens = new ArrayList<>();
        Dragon.dragons = new ArrayList<>();
        UpdateObjects.setLifeBunker(50);

        // Create a mock file
        File mockFile = temporaryFolder.newFile("testSaveGame.txt");

        // Call the method you want to test
        SaveGame.saveGameDataToFile(mockFile);

        // Read the serialized objects using ObjectInputStream and verify their contents
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(mockFile))) {
            assertNotNull(ois.readObject());
        }
    }
}
