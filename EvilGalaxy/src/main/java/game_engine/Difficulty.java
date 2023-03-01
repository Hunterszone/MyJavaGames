package game_engine;

import java.util.ArrayList;

import items.Angel;
import items.HealthPack;
import util.ConsoleContent;
import util.LoadSounds;
import util.TextToSpeech;

public class Difficulty {

	public static void restart() {
		Angel.angels = new ArrayList<Angel>();
		HealthPack.healthpacks = new ArrayList<>(5);

		if (!InitObjects.isIngame()) {
			InitObjects.setIngame(true);
		}

		initObjects();
	}

	public static void getEasyDiff() {

		Angel.angels = new ArrayList<Angel>();

		if (!InitObjects.isIngame()) {
			InitObjects.setIngame(true);
			InitObjects.getTimerMedium().stop();
			InitObjects.getTimerHard().stop();
			InitObjects.getTimerEasy().restart();
		}

		initObjects();
	}

	public static void getMediumDiff() {

		Angel.angels = new ArrayList<Angel>();

		if (!InitObjects.isIngame()) {
			InitObjects.setIngame(true);
			InitObjects.getTimerEasy().stop();
			InitObjects.getTimerHard().stop();
			InitObjects.getTimerMedium().restart();
		}

		initObjects();
	}

	public static void getHardDiff() {

		Angel.angels = new ArrayList<Angel>();

		if (!InitObjects.isIngame()) {
			InitObjects.setIngame(true);
			InitObjects.getTimerEasy().stop();
			InitObjects.getTimerMedium().stop();
			InitObjects.getTimerHard().restart();
		}

		initObjects();
	}

	private static void initObjects() {

		if (!SaveGame.savedOnL1 && !SaveGame.savedOnL2) {
			UpdateObjects.setAlienKilled(0);
			UpdateObjects.setDragonKilled(0);
		}

		TextToSpeech.finMusicIsPlayed = false;
		TextToSpeech.voiceInterruptor = true;
//		GameMenuBar.autosave.setSelected(false);

		if (ConsoleContent.isGod == true) {
			ConsoleContent.isGod = false;
		}

		UpdateObjects.setLifeEvilHead(3);
		UpdateObjects.setLifePlayerShip(4);
		UpdateObjects.setLifeBunker(3);

		InitObjects.initEntities();
		InitObjects.initAnimations();
		InitObjects.initAliens();
		InitObjects.initGold();
		InitObjects.initDragons();

		loadHealthPacks();

		LoadSounds.HIGHSC.play();
	}

	private static void loadHealthPacks() {
		for (int i = 0; i < 5; i++) {
			HealthPack.healthpacks
					.add(new HealthPack((int) Math.ceil(Math.random() * 1200), (int) Math.floor(Math.random() * 1200)));
		}
	}
}