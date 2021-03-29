package game_engine;

import java.awt.Image;
import java.awt.Toolkit;

import entities.Bunker;
import entities.Crosshair;
import entities.EvilHead;
import entities.PlayerShip;
import enums.Images;
import items.VolBtn;
import main.Main;
import util.ConsoleContent;
import util.Constants;
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

		PlayerShip.playerOne = new PlayerShip(Constants.MYSHIP_X, Constants.MYSHIP_Y);
		PlayerShip.playerOne.isVisible();
		Crosshair.crosshair = new Crosshair(Constants.MYCROSSHAIR_X, Constants.MYCROSSHAIR_Y);
		Crosshair.crosshair.isVisible();

		EvilHead.evilHead = new EvilHead(Constants.EVILHEAD_X, Constants.EVILHEAD_Y);
		EvilHead.evilHead.isVisible();
		EvilHead.evilHead.AIOnEasy();

		Bunker.bunkerObj = new Bunker(Constants.BUNKER_X, Constants.BUNKER_Y);
		Bunker.bunkerObj.isVisible();

		VolBtn.volButt = new VolBtn(Constants.VOLBUT_X, Constants.VOLBUT_Y);
		VolBtn.volButt.isVisible();

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
		DrawScene.bg1 = Toolkit.getDefaultToolkit().createImage(Images.BG1.getImg());
		DrawScene.bg1 = DrawScene.bg1.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		InitObjects.ingame = true;
		UpdateObjects.lifeEvilHead = 3;
		UpdateObjects.lifePlayerShip = 3;
		UpdateObjects.lifeBunker = 3;

		PlayerShip.playerOne = new PlayerShip(Constants.MYSHIP_X, Constants.MYSHIP_Y);
		PlayerShip.playerOne.isVisible();
		Crosshair.crosshair = new Crosshair(Constants.MYCROSSHAIR_X, Constants.MYCROSSHAIR_Y);
		Crosshair.crosshair.isVisible();

		EvilHead.evilHead = new EvilHead(Constants.EVILHEAD_X, Constants.EVILHEAD_Y);
		EvilHead.evilHead.isVisible();
		EvilHead.evilHead.AIOnEasy();

		Bunker.bunkerObj = new Bunker(Constants.BUNKER_X, Constants.BUNKER_Y);
		Bunker.bunkerObj.isVisible();

		VolBtn.volButt = new VolBtn(Constants.VOLBUT_X, Constants.VOLBUT_Y);
		VolBtn.volButt.isVisible();

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
		DrawScene.bg1 = Toolkit.getDefaultToolkit().createImage(Images.BG1.getImg());
		DrawScene.bg1 = DrawScene.bg1.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		InitObjects.ingame = true;
		UpdateObjects.lifeEvilHead = 3;
		UpdateObjects.lifePlayerShip = 3;
		UpdateObjects.lifeBunker = 3;

		PlayerShip.playerOne = new PlayerShip(Constants.MYSHIP_X, Constants.MYSHIP_Y);
		PlayerShip.playerOne.isVisible();
		Crosshair.crosshair = new Crosshair(Constants.MYCROSSHAIR_X, Constants.MYCROSSHAIR_Y);
		Crosshair.crosshair.isVisible();

		EvilHead.evilHead = new EvilHead(Constants.EVILHEAD_X, Constants.EVILHEAD_Y);
		EvilHead.evilHead.isVisible();
		EvilHead.evilHead.AIOnMedium();

		Bunker.bunkerObj = new Bunker(Constants.BUNKER_X, Constants.BUNKER_Y);
		Bunker.bunkerObj.isVisible();

		VolBtn.volButt = new VolBtn(Constants.VOLBUT_X, Constants.VOLBUT_Y);
		VolBtn.volButt.isVisible();

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
		DrawScene.bg1 = Toolkit.getDefaultToolkit().createImage(Images.BG1.getImg());
		DrawScene.bg1 = DrawScene.bg1.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		InitObjects.ingame = true;
		UpdateObjects.lifeEvilHead = 3;
		UpdateObjects.lifePlayerShip = 3;
		UpdateObjects.lifeBunker = 3;

		PlayerShip.playerOne = new PlayerShip(Constants.MYSHIP_X, Constants.MYSHIP_Y);
		PlayerShip.playerOne.isVisible();
		Crosshair.crosshair = new Crosshair(Constants.MYCROSSHAIR_X, Constants.MYCROSSHAIR_Y);
		Crosshair.crosshair.isVisible();

		EvilHead.evilHead = new EvilHead(Constants.EVILHEAD_X, Constants.EVILHEAD_Y);
		EvilHead.evilHead.isVisible();
		EvilHead.evilHead.AIOnHard();

		Bunker.bunkerObj = new Bunker(Constants.BUNKER_X, Constants.BUNKER_Y);
		Bunker.bunkerObj.isVisible();

		VolBtn.volButt = new VolBtn(Constants.VOLBUT_X, Constants.VOLBUT_Y);
		VolBtn.volButt.isVisible();

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