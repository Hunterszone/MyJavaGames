package game_engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import entities.Alien;
import entities.Crosshair;
import entities.Dragon;
import entities.MyShip;
import frames.ConsoleContent;
import frames.ConsoleForm;
import frames.Manual;
import items.Gold;
import items.SaveSign;
import items.VolBtn;
import menu_engine.MouseInputHandler;
import menu_states.MenuState;
import sound_engine.LoadSounds;

public class Controls extends JFrame implements KeyListener {

	public static boolean isEPressed, isMPressed, isHPressed;
	private static final long serialVersionUID = 1L;

	@Override
	public void keyReleased(KeyEvent e) {
		MyShip.myShip.keyReleased(e);
		Crosshair.crosshair.keyReleased(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		MyShip.myShip.keyPressed(e);
		Crosshair.crosshair.keyPressed(e);
		VolBtn.volButt.keyPressed(e);
		SaveSign.saveSign.keyPressed(e);

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_S) {
			LoadSounds.bgMusic.stop();
		}

		if (key == KeyEvent.VK_A) {
			if (InitObjects.timerEasy.isRunning() || InitObjects.timerHard.isRunning()
					|| InitObjects.timerMedium.isRunning()) {
				LoadSounds.bgMusic.loop();
			}
		}

		if (key == KeyEvent.VK_P) {
			InitObjects.timerEasy.stop();
			InitObjects.timerMedium.stop();
			InitObjects.timerHard.stop();
			LoadSounds.bgMusic.stop();
			LoadSounds.roar.stop();
		}

		if (InitObjects.ingame == true
				&& (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
						|| InitObjects.timerHard.isRunning() == true)
				&& key == KeyEvent.VK_CONTROL && (Alien.aliens.isEmpty()
						&& (Dragon.dragons.size() > 0 || UpdateObjects.lifeBunker < 50 || Gold.goldstack.isEmpty()))) {
			MyShip.myShip.loadRockets();
		}

		if (InitObjects.ingame == true
				&& (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
						|| InitObjects.timerHard.isRunning() == true)
				&& key == KeyEvent.VK_CONTROL && (Alien.aliens.size() > 0
						|| (Dragon.dragons.isEmpty() && UpdateObjects.lifeBunker >= 50 && Gold.goldstack.size() > 0))) {
			MyShip.myShip.gunLocked();
		}

		if (InitObjects.ingame == true
				&& (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
						|| InitObjects.timerHard.isRunning() == true)
				&& key == KeyEvent.VK_SPACE
				&& (Alien.aliens.size() > 0 || (Dragon.dragons.isEmpty() && UpdateObjects.lifeBunker < 50)
						|| (UpdateObjects.lifeBunker >= 50 && Gold.goldstack.isEmpty()))) {
			MyShip.myShip.loadMissiles();
		}

		if (InitObjects.ingame == true
				&& (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
						|| InitObjects.timerHard.isRunning() == true)
				&& key == KeyEvent.VK_SPACE && (Alien.aliens.isEmpty() && Dragon.dragons.size() > 0
						|| (Dragon.dragons.isEmpty() && UpdateObjects.lifeBunker >= 50 && Gold.goldstack.size() > 0))) {
			MyShip.myShip.gunLocked();
		}

		if (key == KeyEvent.VK_1) {

			Difficulty.restart();
			if (DrawScene.voiceInterruptor == false) {
				DrawScene.initVoice("Level 1!");
				DrawScene.voiceInterruptor = true;
				return;
			}
		}

		if (key == KeyEvent.VK_2) {

			Difficulty.restart();
			Alien.aliens.clear();
			if (DrawScene.voiceInterruptor == false && !Dragon.dragons.isEmpty()) {
				DrawScene.initVoice("Level 2!");
				DrawScene.voiceInterruptor = true;
				return;
			}
		}

		if (key == KeyEvent.VK_3) {

			Difficulty.restart();
			Alien.aliens.clear();
			Dragon.dragons.clear();
			if (DrawScene.voiceInterruptor == false && Dragon.dragons.isEmpty()) {
				DrawScene.initVoice("Level 3!");
				DrawScene.voiceInterruptor = true;
				return;
			}
		}

		if (key == KeyEvent.VK_4) {

			Difficulty.restart();
			Alien.aliens.clear();
			Dragon.dragons.clear();
			UpdateObjects.lifeBunker = 50;
			if (DrawScene.voiceInterruptor == false) {
				DrawScene.initVoice("Level 4!");
				DrawScene.voiceInterruptor = true;
				return;
			}
		}

		if (key == KeyEvent.VK_R) {
			isEPressed = false;
			isMPressed = false;
			isHPressed = false;
			Difficulty.restart();
		}

		if (key == KeyEvent.VK_E) {
			isMPressed = false;
			isHPressed = false;
			isEPressed = true;
			ConsoleContent.manualON = false;
			InitObjects.timerHard.stop();
			InitObjects.timerMedium.stop();
			InitObjects.timerEasy.start();
			LoadSounds.bgMusic.loop();

			if (Alien.aliens.isEmpty()) {
				LoadSounds.roar.loop();
			}
			if (!InitObjects.ingame) {

				Difficulty.easy();
			}

		}

		if (key == KeyEvent.VK_M) {
			isEPressed = false;
			isHPressed = false;
			isMPressed = true;
			ConsoleContent.manualON = false;
			InitObjects.timerEasy.stop();
			InitObjects.timerHard.stop();
			InitObjects.timerMedium.start();
			LoadSounds.bgMusic.loop();

			if (Alien.aliens.isEmpty()) {
				LoadSounds.roar.loop();
			}
			if (!InitObjects.ingame) {

				Difficulty.medium();
			}

		}

		if (key == KeyEvent.VK_H) {
			isEPressed = false;
			isMPressed = false;
			isHPressed = true;
			ConsoleContent.manualON = false;
			InitObjects.timerEasy.stop();
			InitObjects.timerMedium.stop();
			InitObjects.timerHard.start();
			LoadSounds.bgMusic.loop();

			if (Alien.aliens.isEmpty()) {
				LoadSounds.roar.loop();
			}
			if (!InitObjects.ingame) {

				Difficulty.hard();
			}
		}

		if (key == KeyEvent.VK_G) {

			if (!ConsoleContent.god) {

				if (InitObjects.ingame == true) {
					ConsoleContent.god = true;
					UpdateObjects.lifeMyShip = -999;
					DrawScene.initVoice("GODLIKE!");
					return;
				}
			}

			else {

				if (InitObjects.ingame == true) {
					ConsoleContent.god = false;
					UpdateObjects.lifeMyShip = 3;
					DrawScene.initVoice("Healthy!");
					return;
				}

			}

		}

		if (key == KeyEvent.VK_O && !ConsoleContent.manualON) {
			ConsoleContent.manual = new Manual();
			ConsoleContent.manual.setVisible(true);
		}

		if (key == KeyEvent.VK_V && !ConsoleContent.consoleON) {
			ConsoleContent.console = new ConsoleForm();
		}

		if (key == KeyEvent.VK_ESCAPE) {
//			System.exit(0);
			MenuState.isOn = false;
			LoadSounds.bgMusic.stop();
			LoadSounds.fuse.stop();
			LoadSounds.roar.stop();
			InitObjects.ingame = false;
			MouseInputHandler.main.dispose();
			MenuState.isOn = true;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}