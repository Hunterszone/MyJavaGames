package game_engine;

import java.awt.Image;
import java.awt.Toolkit;

import enums.Images;
import main.Main;
import util.ConsoleContent;
import util.LoadSounds;
import util.TextToSpeech;

public class Difficulty {

	public static void restart() {

		if (SaveGame.savedOnL1 == false && SaveGame.savedOnL2 == false) {
			Collisions.alienKilled = 0;
			Collisions.dragonKilled = 0;
		}

		TextToSpeech.finMusicIsPlayed = false;

//		GameMenuBar.autosave.setSelected(false);
		TextToSpeech.voiceInterruptor = true;

		if (ConsoleContent.god == true) {
			ConsoleContent.god = false;
		}

		InitObjects.ingame = true;
		UpdateObjects.lifeEvilHead = 3;
		UpdateObjects.lifePlayerShip = 3;
		UpdateObjects.lifeBunker = 3;

		InitObjects.initEntities();
		InitObjects.initAnimations();
		
		InitObjects.initAliens();
		InitObjects.initGold();
		InitObjects.initDragons();
		InitObjects.initHealth();

		InitObjects.timerHard.stop();
		InitObjects.timerMedium.stop();
		InitObjects.timerEasy.restart();

		LoadSounds.HIGHSC.play();
		LoadSounds.FIN.stop();
		LoadSounds.BG_MUSIC.loop();
		LoadSounds.TAUNT.stop();
	}
	
	public static void easy() {

		if (SaveGame.savedOnL1 == false && SaveGame.savedOnL2 == false) {
			Collisions.alienKilled = 0;
			Collisions.dragonKilled = 0;
		}

//		GameMenuBar.autosave.setSelected(false);
		TextToSpeech.voiceInterruptor = true;

		TextToSpeech.finMusicIsPlayed = false;

		Main.dim = Toolkit.getDefaultToolkit().getScreenSize();
		final int width = (int) Main.dim.getWidth();
		final int height = (int) Main.dim.getHeight();
		DrawScene.bg1 = Toolkit.getDefaultToolkit().createImage(Images.BG1.getImg())
				.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		InitObjects.ingame = true;
		UpdateObjects.lifeEvilHead = 3;
		UpdateObjects.lifePlayerShip = 3;
		UpdateObjects.lifeBunker = 3;

		InitObjects.initEntities();

		InitObjects.initAliens();
		InitObjects.initGold();
		InitObjects.initDragons();
		InitObjects.initHealth();

		InitObjects.timerMedium.stop();
		InitObjects.timerHard.stop();
		InitObjects.timerEasy.restart();

		LoadSounds.HIGHSC.play();
		LoadSounds.FIN.stop();
		LoadSounds.BG_MUSIC.loop();
		LoadSounds.TAUNT.stop();
	}

	public static void medium() {

		if (SaveGame.savedOnL1 == false && SaveGame.savedOnL2 == false) {
			Collisions.alienKilled = 0;
			Collisions.dragonKilled = 0;
		}

//		GameMenuBar.autosave.setSelected(false);
		TextToSpeech.voiceInterruptor = true;

		TextToSpeech.finMusicIsPlayed = false;

		Main.dim = Toolkit.getDefaultToolkit().getScreenSize();
		final int width = (int) Main.dim.getWidth();
		final int height = (int) Main.dim.getHeight();
		DrawScene.bg1 = Toolkit.getDefaultToolkit().createImage(Images.BG1.getImg())
				.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		InitObjects.ingame = true;
		UpdateObjects.lifeEvilHead = 3;
		UpdateObjects.lifePlayerShip = 3;
		UpdateObjects.lifeBunker = 3;

		InitObjects.initEntities();

		InitObjects.initAliens();
		InitObjects.initGold();
		InitObjects.initDragons();
		InitObjects.initHealth();

		InitObjects.timerEasy.stop();
		InitObjects.timerHard.stop();
		InitObjects.timerMedium.restart();

		LoadSounds.HIGHSC.play();
		LoadSounds.FIN.stop();
		LoadSounds.BG_MUSIC.loop();
		LoadSounds.TAUNT.stop();
	}

	public static void hard() {
		if (SaveGame.savedOnL1 == false && SaveGame.savedOnL2 == false) {
			Collisions.alienKilled = 0;
			Collisions.dragonKilled = 0;
		}

//		GameMenuBar.autosave.setSelected(false);
		TextToSpeech.voiceInterruptor = true;

		TextToSpeech.finMusicIsPlayed = false;

		Main.dim = Toolkit.getDefaultToolkit().getScreenSize();
		final int width = (int) Main.dim.getWidth();
		final int height = (int) Main.dim.getHeight();
		DrawScene.bg1 = Toolkit.getDefaultToolkit().createImage(Images.BG1.getImg())
				.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		InitObjects.ingame = true;
		UpdateObjects.lifeEvilHead = 3;
		UpdateObjects.lifePlayerShip = 3;
		UpdateObjects.lifeBunker = 3;

		InitObjects.initEntities();

		InitObjects.initAliens();
		InitObjects.initGold();
		InitObjects.initDragons();
		InitObjects.initHealth();

		InitObjects.timerEasy.stop();
		InitObjects.timerMedium.stop();
		InitObjects.timerHard.restart();

		LoadSounds.HIGHSC.play();
		LoadSounds.FIN.stop();
		LoadSounds.BG_MUSIC.loop();
		LoadSounds.TAUNT.stop();
	}
}