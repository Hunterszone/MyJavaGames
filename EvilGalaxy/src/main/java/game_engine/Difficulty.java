package game_engine;

import java.util.ArrayList;

import items.Angel;
import items.Balloon;
import items.HealthPack;
import util.ConsoleContent;
import util.LoadSounds;
import util.TextToSpeech;

public class Difficulty {

	public static void restart() {
		InitObjects.balloons = new ArrayList<Balloon>();
		InitObjects.angels = new ArrayList<Angel>();
		HealthPack.healthpacks = new ArrayList<>(5);

		if (!InitObjects.ingame) {
			InitObjects.ingame = true;
		}

		initObjects();
	}

	public static void getEasyDiff() {

		InitObjects.balloons = new ArrayList<Balloon>();
		InitObjects.angels = new ArrayList<Angel>();

		if (!InitObjects.ingame) {
			InitObjects.ingame = true;
			InitObjects.timerMedium.stop();
			InitObjects.timerHard.stop();
			InitObjects.timerEasy.restart();
		}

		initObjects();
	}

	public static void getMediumDiff() {

		InitObjects.balloons = new ArrayList<Balloon>();
		InitObjects.angels = new ArrayList<Angel>();

		if (!InitObjects.ingame) {
			InitObjects.ingame = true;
			InitObjects.timerEasy.stop();
			InitObjects.timerHard.stop();
			InitObjects.timerMedium.restart();
		}

		initObjects();
	}

	public static void getHardDiff() {

		InitObjects.balloons = new ArrayList<Balloon>();
		InitObjects.angels = new ArrayList<Angel>();

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