package game_engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import entities.Alien;
import entities.Bunker;
import entities.Dragon;
import entities.EvilHead;
import entities.MyShip;
import items.Gold;
import items.HealthPack;
import util.TextToSpeech;

public class SaveGame {
	
	public static Boolean savedOnL1 = false;
	public static Boolean savedOnL2 = false;
	public static Boolean savedOnL3 = false;
	public static Boolean savedOnL4 = false;
	
	public static void saveGameDataToFile(File savefile) {

		TextToSpeech.voiceInterruptor = false;

		if (InitObjects.ingame == true) {

			if (Alien.aliens.size() > 0 && TextToSpeech.voiceInterruptor == false) {

				savedOnL2 = false;
				savedOnL3 = false;
				savedOnL4 = false;
				savedOnL1 = true;
				TextToSpeech.initVoice("Game saved!");
				TextToSpeech.voiceInterruptor = true;
			}

			TextToSpeech.voiceInterruptor = false;

			if (Alien.aliens.isEmpty() && Dragon.dragons.size() > 0 && TextToSpeech.voiceInterruptor == false) {

				savedOnL1 = false;
				savedOnL3 = false;
				savedOnL4 = false;
				savedOnL2 = true;
				TextToSpeech.initVoice("Game saved!");
				TextToSpeech.voiceInterruptor = true;
			}

			TextToSpeech.voiceInterruptor = false;

			if (Dragon.dragons.isEmpty() && UpdateObjects.lifeBunker < 50
					&& TextToSpeech.voiceInterruptor == false) {

				savedOnL2 = false;
				savedOnL1 = false;
				savedOnL4 = false;
				savedOnL3 = true;
				TextToSpeech.initVoice("Game saved!");
				TextToSpeech.voiceInterruptor = true;
			}

			TextToSpeech.voiceInterruptor = false;

			if (UpdateObjects.lifeBunker == 50 && TextToSpeech.voiceInterruptor == false) {

				savedOnL1 = false;
				savedOnL2 = false;
				savedOnL3 = false;
				savedOnL4 = true;
				TextToSpeech.initVoice("Game saved!");
				TextToSpeech.voiceInterruptor = true;
			}

			try {
				FileOutputStream fileStream = new FileOutputStream(savefile);
				ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

				objectStream.writeObject(MyShip.myShip);
				objectStream.writeObject(EvilHead.evilHead);
				objectStream.writeObject(Bunker.bunkerObj);
				objectStream.writeObject(Alien.aliens);
				objectStream.writeObject(Dragon.dragons);
				objectStream.writeObject(Gold.goldstack);
				objectStream.writeObject(HealthPack.healthpack);
				objectStream.writeObject(savedOnL1);
				objectStream.writeObject(savedOnL2);
				objectStream.writeObject(savedOnL3);
				objectStream.writeObject(savedOnL4);

				objectStream.close();
				fileStream.close();

//				JOptionPane.showConfirmDialog(frame, "Saved game state successfully.", "Save game",
//						JOptionPane.DEFAULT_OPTION);
			} catch (Exception e) {
//				JOptionPane.showConfirmDialog(frame, e.toString() + "\nFail to save game state.", "Save game",
//						JOptionPane.DEFAULT_OPTION);
			}

		}

	}
}