package game_engine;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import items.Angel;
import items.HealthPack;
import util.LoadSounds;
import util.TextToSpeech;
import util.ActionPerformer;

public class Difficulty {

	public static void restart() throws NoSuchAlgorithmException {
		Angel.angels = new ArrayList<Angel>();
		HealthPack.healthpacks = new ArrayList<>(5);

		if (!ActionPerformer.isIngame()) {
			ActionPerformer.setIngame(true);
		}

		InitAndUpdate();
	}

	public static void getEasyDiff() throws NoSuchAlgorithmException {

		Angel.angels = new ArrayList<Angel>();

		if (!ActionPerformer.isIngame()) {
			ActionPerformer.setIngame(true);
			ActionPerformer.getTimerMedium().stop();
			ActionPerformer.getTimerHard().stop();
			ActionPerformer.getTimerEasy().restart();
		}

		InitAndUpdate();
	}

	public static void getMediumDiff() throws NoSuchAlgorithmException {

		Angel.angels = new ArrayList<Angel>();

		if (!ActionPerformer.isIngame()) {
			ActionPerformer.setIngame(true);
			ActionPerformer.getTimerEasy().stop();
			ActionPerformer.getTimerHard().stop();
			ActionPerformer.getTimerMedium().restart();
		}

		InitAndUpdate();
	}

	public static void getHardDiff() throws NoSuchAlgorithmException {

		Angel.angels = new ArrayList<Angel>();

		if (!ActionPerformer.isIngame()) {
			ActionPerformer.setIngame(true);
			ActionPerformer.getTimerEasy().stop();
			ActionPerformer.getTimerMedium().stop();
			ActionPerformer.getTimerHard().restart();
		}

		InitAndUpdate();
	}

	private static void InitAndUpdate() throws NoSuchAlgorithmException {

		if (!SaveGame.savedOnL1 && !SaveGame.savedOnL2) {
			UpdateObjects.setAlienKilled(0);
			UpdateObjects.setDragonKilled(0);
		}

		TextToSpeech.finMusicIsPlayed = false;
		TextToSpeech.voiceInterruptor = true;
//		GameMenuBar.autosave.setSelected(false);

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