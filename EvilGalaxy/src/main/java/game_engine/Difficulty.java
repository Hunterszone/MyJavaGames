package game_engine;

import java.awt.Toolkit;

import entities.Bunker;
import entities.Crosshair;
import entities.EvilHead;
import entities.MyShip;
import frames.ConsoleContent;
import frames.GameMenuBar;
import items.VolBtn;
import sound_engine.LoadSounds;

public class Difficulty {

	public static void restart() {

		if (GameMenuBar.savedOnL1 == false && GameMenuBar.savedOnL2 == false) {
			Collisions.alienKilled = 0;
			Collisions.dragonKilled = 0;
		}

		DrawScene.finMusicIsPlayed = false;

		GameMenuBar.autosave.setSelected(false);
		DrawScene.voiceInterruptor = true;

		if (ConsoleContent.god == true) {
			ConsoleContent.god = false;
		}

		InitObjects.ingame = true;
		UpdateObjects.lifeEvilHead = 3;
		UpdateObjects.lifeMyShip = 3;
		UpdateObjects.lifeBunker = 3;

		MyShip.myShip = new MyShip(40, 180);
		MyShip.myShip.isVisible();
		Crosshair.crosshair = new Crosshair(250, 165);
		Crosshair.crosshair.isVisible();

		EvilHead.evilHead = new EvilHead(640, 180);
		EvilHead.evilHead.isVisible();
		EvilHead.evilHead.AIOnEasy();

		Bunker.bunkerObj = new Bunker(450, 680);
		Bunker.bunkerObj.isVisible();

		VolBtn.volButt = new VolBtn(940, 15);
		VolBtn.volButt.isVisible();

		InitObjects.initAliens();
		InitObjects.initGold();
		InitObjects.initDragons();
		InitObjects.initHealth();

		InitObjects.timerHard.stop();
		InitObjects.timerMedium.stop();
		InitObjects.timerEasy.restart();
		
		LoadSounds.gameWon.play();
		LoadSounds.gameLost.stop();
		LoadSounds.bgMusic.loop();
		LoadSounds.roar.stop();
	}

	public static void easy() {

		if (GameMenuBar.savedOnL1 == false && GameMenuBar.savedOnL2 == false) {
			Collisions.alienKilled = 0;
			Collisions.dragonKilled = 0;
		}

		GameMenuBar.autosave.setSelected(false);
		DrawScene.voiceInterruptor = true;

		DrawScene.finMusicIsPlayed = false;

		DrawScene.bg1 = Toolkit.getDefaultToolkit().createImage("images/tenor.gif");
		InitObjects.ingame = true;
		UpdateObjects.lifeEvilHead = 3;
		UpdateObjects.lifeMyShip = 3;
		UpdateObjects.lifeBunker = 3;

		MyShip.myShip = new MyShip(40, 180);
		MyShip.myShip.isVisible();
		Crosshair.crosshair = new Crosshair(250, 180);
		Crosshair.crosshair.isVisible();

		EvilHead.evilHead = new EvilHead(640, 180);
		EvilHead.evilHead.isVisible();
		EvilHead.evilHead.AIOnEasy();

		Bunker.bunkerObj = new Bunker(450, 680);
		Bunker.bunkerObj.isVisible();

		VolBtn.volButt = new VolBtn(940, 15);
		VolBtn.volButt.isVisible();

		InitObjects.initAliens();
		InitObjects.initGold();
		InitObjects.initDragons();
		InitObjects.initHealth();

		InitObjects.timerMedium.stop();
		InitObjects.timerHard.stop();
		InitObjects.timerEasy.restart();
		
		LoadSounds.gameWon.play();
		LoadSounds.gameLost.stop();
		LoadSounds.bgMusic.loop();
		LoadSounds.roar.stop();
	}

	public static void medium() {

		if (GameMenuBar.savedOnL1 == false && GameMenuBar.savedOnL2 == false) {
			Collisions.alienKilled = 0;
			Collisions.dragonKilled = 0;
		}

		GameMenuBar.autosave.setSelected(false);
		DrawScene.voiceInterruptor = true;

		DrawScene.finMusicIsPlayed = false;

		DrawScene.bg1 = Toolkit.getDefaultToolkit().createImage("images/tenor.gif");
		InitObjects.ingame = true;
		UpdateObjects.lifeEvilHead = 3;
		UpdateObjects.lifeMyShip = 3;
		UpdateObjects.lifeBunker = 3;

		MyShip.myShip = new MyShip(40, 180);
		MyShip.myShip.isVisible();
		Crosshair.crosshair = new Crosshair(250, 180);
		Crosshair.crosshair.isVisible();

		EvilHead.evilHead = new EvilHead(640, 180);
		EvilHead.evilHead.isVisible();
		EvilHead.evilHead.AIOnMedium();

		Bunker.bunkerObj = new Bunker(450, 680);
		Bunker.bunkerObj.isVisible();

		VolBtn.volButt = new VolBtn(940, 15);
		VolBtn.volButt.isVisible();

		InitObjects.initAliens();
		InitObjects.initGold();
		InitObjects.initDragons();
		InitObjects.initHealth();

		InitObjects.timerEasy.stop();
		InitObjects.timerHard.stop();
		InitObjects.timerMedium.restart();

		LoadSounds.gameWon.play();
		LoadSounds.gameLost.stop();
		LoadSounds.bgMusic.loop();
		LoadSounds.roar.stop();
	}

	public static void hard() {

		if (GameMenuBar.savedOnL1 == false && GameMenuBar.savedOnL2 == false) {
			Collisions.alienKilled = 0;
			Collisions.dragonKilled = 0;
		}

		GameMenuBar.autosave.setSelected(false);
		DrawScene.voiceInterruptor = true;

		DrawScene.finMusicIsPlayed = false;

		DrawScene.bg1 = Toolkit.getDefaultToolkit().createImage("images/tenor.gif");
		InitObjects.ingame = true;
		UpdateObjects.lifeEvilHead = 3;
		UpdateObjects.lifeMyShip = 3;
		UpdateObjects.lifeBunker = 3;

		MyShip.myShip = new MyShip(40, 180);
		MyShip.myShip.isVisible();
		Crosshair.crosshair = new Crosshair(250, 180);
		Crosshair.crosshair.isVisible();

		EvilHead.evilHead = new EvilHead(640, 180);
		EvilHead.evilHead.isVisible();
		EvilHead.evilHead.AIOnHard();

		Bunker.bunkerObj = new Bunker(450, 680);
		Bunker.bunkerObj.isVisible();

		VolBtn.volButt = new VolBtn(940, 15);
		VolBtn.volButt.isVisible();

		InitObjects.initAliens();
		InitObjects.initGold();
		InitObjects.initDragons();
		InitObjects.initHealth();

		InitObjects.timerEasy.stop();
		InitObjects.timerMedium.stop();
		InitObjects.timerHard.restart();

		LoadSounds.gameWon.play();
		LoadSounds.gameLost.stop();
		LoadSounds.bgMusic.loop();
		LoadSounds.roar.stop();
	}
}