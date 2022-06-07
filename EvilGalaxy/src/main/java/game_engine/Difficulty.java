package game_engine;

import util.ConsoleContent;
import util.LoadSounds;
import util.TextToSpeech;

public class Difficulty {

	public static void restart() {

		if (!InitObjects.ingame) {
			InitObjects.ingame = true;
		}

		initObjects();
	}

	public static void easy() {

		if (!InitObjects.ingame) {
			InitObjects.ingame = true;
			InitObjects.timerMedium.stop();
			InitObjects.timerHard.stop();
			InitObjects.timerEasy.restart();
		}

		initObjects();
	}

	public static void medium() {

		if (!InitObjects.ingame) {
			InitObjects.ingame = true;
			InitObjects.timerEasy.stop();
			InitObjects.timerHard.stop();
			InitObjects.timerMedium.restart();
		}

		initObjects();
	}

	public static void hard() {

		if (!InitObjects.ingame) {
			InitObjects.ingame = true;
			InitObjects.timerEasy.stop();
			InitObjects.timerMedium.stop();
			InitObjects.timerHard.restart();
		}

		initObjects();
	}

	private static void initObjects() {

		if (!SaveGame.savedOnL1 && !SaveGame.savedOnL2) {
			Collisions.alienKilled = 0;
			Collisions.dragonKilled = 0;
		}

		TextToSpeech.finMusicIsPlayed = false;
		TextToSpeech.voiceInterruptor = true;
//		GameMenuBar.autosave.setSelected(false);

		if (ConsoleContent.isGod == true) {
			ConsoleContent.isGod = false;
		}

		UpdateObjects.lifeEvilHead = 3;
		UpdateObjects.lifePlayerShip = 4;
		UpdateObjects.lifeBunker = 3;

		InitObjects.initEntities();
		InitObjects.initAnimations();
		InitObjects.initAliens();
		InitObjects.initGold();
		InitObjects.initDragons();
		InitObjects.initHealth();

		LoadSounds.HIGHSC.play();
		LoadSounds.FIN.stop();
		LoadSounds.BG_MUSIC.loop();
		LoadSounds.TAUNT.stop();
	}
}