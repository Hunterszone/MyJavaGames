package game_engine;

import java.security.NoSuchAlgorithmException;

import items.Angel;
import items.HealthPack;
import util.LoadSounds;
import util.TextToSpeech;
import util.ActionPerformer;
import util.LivesAndCounts;

public class Difficulty {

	public static void restart() throws NoSuchAlgorithmException {
		
		clearItems();
		
		if (!ActionPerformer.isIngame()) {
			ActionPerformer.setIngame(true);
		}

		initAndUpdate();
	}

	private static void clearItems() {
		HealthPack.healthpacks.clear();
		Angel.angels.clear();
	}

	public static void getEasyDiff() throws NoSuchAlgorithmException {
		
		clearItems();

		if (!ActionPerformer.isIngame()) {
			ActionPerformer.setIngame(true);
			ActionPerformer.getTimerMedium().stop();
			ActionPerformer.getTimerHard().stop();
			ActionPerformer.getTimerEasy().restart();
		}

		initAndUpdate();
	}

	public static void getMediumDiff() throws NoSuchAlgorithmException {
		
		clearItems();

		if (!ActionPerformer.isIngame()) {
			ActionPerformer.setIngame(true);
			ActionPerformer.getTimerEasy().stop();
			ActionPerformer.getTimerHard().stop();
			ActionPerformer.getTimerMedium().restart();
		}

		initAndUpdate();
	}

	public static void getHardDiff() throws NoSuchAlgorithmException {
		
		clearItems();

		if (!ActionPerformer.isIngame()) {
			ActionPerformer.setIngame(true);
			ActionPerformer.getTimerEasy().stop();
			ActionPerformer.getTimerMedium().stop();
			ActionPerformer.getTimerHard().restart();
		}

		initAndUpdate();
	}

	private static void initAndUpdate() throws NoSuchAlgorithmException {

		if (!SaveGame.savedOnL1 && !SaveGame.savedOnL2) {
			LivesAndCounts.setAlienKilled(0);
			LivesAndCounts.setDragonKilled(0);
		}

		TextToSpeech.finMusicIsPlayed = false;
		TextToSpeech.voiceInterruptor = true;
//		GameMenuBar.autosave.setSelected(false);

		LivesAndCounts.setLifeEvilHead(3);
		LivesAndCounts.setLifePlayerShip(4);
		LivesAndCounts.setLifeBunker(3);

		InitObjects.initEntities();
		InitObjects.initAnimations();
		InitObjects.initAliens();
		InitObjects.initGold();
		InitObjects.initDragons();

		LoadSounds.HIGHSC.play();
	}

}