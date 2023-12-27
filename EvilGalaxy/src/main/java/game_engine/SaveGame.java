package game_engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import enemy.Alien;
import enemy.Bunker;
import enemy.Dragon;
import enemy.EvilHead;
import enemy.PlayerShip;
import items.Gold;
import items.HealthPack;
import util.ActionPerformer;
import util.LivesAndCounts;
import util.TextToSpeech;

public class SaveGame {
	
	public static Boolean savedOnL1 = false;
	public static Boolean savedOnL2 = false;
	public static Boolean savedOnL3 = false;
	public static Boolean savedOnL4 = false;
	
	public static void saveGameDataToFile(File savefile) throws IOException {

		TextToSpeech.voiceInterruptor = false;

		if (ActionPerformer.isIngame() == true) {
			if (Alien.aliens.size() > 0 && TextToSpeech.voiceInterruptor == false) {
				savedOnL2 = false;
				savedOnL3 = false;
				savedOnL4 = false;
				savedOnL1 = true;
				TextToSpeech.playVoice("Game saved!");
				TextToSpeech.voiceInterruptor = true;
			}

			TextToSpeech.voiceInterruptor = false;
			if (Alien.aliens.isEmpty() && Dragon.dragons.size() > 0 && TextToSpeech.voiceInterruptor == false) {
				savedOnL1 = false;
				savedOnL3 = false;
				savedOnL4 = false;
				savedOnL2 = true;
				TextToSpeech.playVoice("Game saved!");
				TextToSpeech.voiceInterruptor = true;
			}

			TextToSpeech.voiceInterruptor = false;

			if (Dragon.dragons.isEmpty() && LivesAndCounts.getLifeBunker() < 50
					&& TextToSpeech.voiceInterruptor == false) {
				savedOnL2 = false;
				savedOnL1 = false;
				savedOnL4 = false;
				savedOnL3 = true;
				TextToSpeech.playVoice("Game saved!");
				TextToSpeech.voiceInterruptor = true;
			}

			TextToSpeech.voiceInterruptor = false;

			if (LivesAndCounts.getLifeBunker() == 50 && TextToSpeech.voiceInterruptor == false) {
				savedOnL1 = false;
				savedOnL2 = false;
				savedOnL3 = false;
				savedOnL4 = true;
				TextToSpeech.playVoice("Game saved!");
				TextToSpeech.voiceInterruptor = true;
			}

			FileOutputStream fileStream = null;
			ObjectOutputStream objectStream = null;

			try {
				fileStream = new FileOutputStream(savefile);
				objectStream = new ObjectOutputStream(fileStream);
				objectStream.writeObject(PlayerShip.playerShip);
				objectStream.writeObject(EvilHead.evilHead);
				objectStream.writeObject(Bunker.bunkerObj);
				objectStream.writeObject(Alien.aliens);
				objectStream.writeObject(Dragon.dragons);
				objectStream.writeObject(Gold.goldstack);
				objectStream.writeObject(HealthPack.healthpacks);
				objectStream.writeObject(savedOnL1);
				objectStream.writeObject(savedOnL2);
				objectStream.writeObject(savedOnL3);
				objectStream.writeObject(savedOnL4);
//				JOptionPane.showConfirmDialog(frame, "Saved game state successfully.", "Save game",
//						JOptionPane.DEFAULT_OPTION);
			} catch (final Exception e) {
//				JOptionPane.showConfirmDialog(frame, e.toString() + "\nFail to save game state.", "Save game",
//						JOptionPane.DEFAULT_OPTION);
			} finally {
				if (fileStream != null) fileStream.close();
				if (objectStream != null) objectStream.close();
			}

		}

	}
}