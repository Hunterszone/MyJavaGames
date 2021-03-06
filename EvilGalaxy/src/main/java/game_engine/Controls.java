package game_engine;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

import entities.Alien;
import entities.AsteroidsAnimation;
import entities.AstronautAnimation;
import entities.Crosshair;
import entities.Dragon;
import entities.PlayerShip;
import entities.SatelliteAnimation;
import entities.TheEndAnimation;
import items.Gold;
import items.SaveSign;
import items.VolBtn;
import menu_engine.CanvasMenu;
import menu_engine.MouseInputHandler;
import menu_states.MenuState;
import util.ConsoleContent;
import util.LoadSounds;
import util.TextToSpeech;

public class Controls extends JFrame implements KeyListener {

	public static boolean isEPressed, isMPressed, isHPressed;
	private static final long serialVersionUID = 1L;
	private Random rand = new Random();
	
	@Override
	public void keyReleased(KeyEvent e) {
		PlayerShip.playerOne.keyReleased(e);
		Crosshair.crosshair.keyReleased(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		PlayerShip.playerOne.keyPressed(e);
		Crosshair.crosshair.keyPressed(e);
		VolBtn.volButt.keyPressed(e);

		final int key = e.getKeyCode();

		if (key == KeyEvent.VK_S) {
			LoadSounds.BG_MUSIC.stop();
		}

		if (key == KeyEvent.VK_A) {
			if (InitObjects.timerEasy.isRunning() || InitObjects.timerHard.isRunning()
					|| InitObjects.timerMedium.isRunning()) {
				LoadSounds.BG_MUSIC.loop();
			}
		}

		if (key == KeyEvent.VK_P) {
			InitObjects.timerEasy.stop();
			InitObjects.timerMedium.stop();
			InitObjects.timerHard.stop();
			LoadSounds.BG_MUSIC.stop();
			LoadSounds.TAUNT.stop();
		}

		if (InitObjects.ingame == true
				&& (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
						|| InitObjects.timerHard.isRunning() == true)
				&& key == KeyEvent.VK_CONTROL && (Alien.aliens.isEmpty()
						&& (Dragon.dragons.size() > 0 || UpdateObjects.lifeBunker < 50 || Gold.goldstack.isEmpty()))) {
			PlayerShip.playerOne.loadRockets();
		}

		if (InitObjects.ingame == true
				&& (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
						|| InitObjects.timerHard.isRunning() == true)
				&& key == KeyEvent.VK_CONTROL && (Alien.aliens.size() > 0
						|| (Dragon.dragons.isEmpty() && UpdateObjects.lifeBunker >= 50 && Gold.goldstack.size() > 0))) {
			PlayerShip.playerOne.gunLocked(e);
		}

		if (InitObjects.ingame == true
				&& (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
						|| InitObjects.timerHard.isRunning() == true)
				&& key == KeyEvent.VK_SPACE
				&& (Alien.aliens.size() > 0 || (UpdateObjects.lifeBunker >= 50 && Gold.goldstack.isEmpty()))) {
			PlayerShip.playerOne.loadMissiles();
		}

		if (InitObjects.ingame == true
				&& (InitObjects.timerEasy.isRunning() == true
						|| InitObjects.timerMedium.isRunning() == true || InitObjects.timerHard.isRunning() == true)
				&& key == KeyEvent.VK_SPACE
				&& ((Alien.aliens.isEmpty() && Dragon.dragons.size() > 0)
						|| (Dragon.dragons.isEmpty() && UpdateObjects.lifeBunker < 50)
						|| (Dragon.dragons.isEmpty() && UpdateObjects.lifeBunker >= 50 && Gold.goldstack.size() > 0))) {
			PlayerShip.playerOne.gunLocked(e);
		}

		if (key == KeyEvent.VK_1) {
			Difficulty.restart();
			TextToSpeech.playVoice("Loading level 1...");
			TextToSpeech.voiceInterruptor = true;
		}

		if (key == KeyEvent.VK_2) {
			Difficulty.restart();
			TextToSpeech.playVoice("Loading level 2...");
			TextToSpeech.voiceInterruptor = true;
			Alien.aliens.clear();
		}

		if (key == KeyEvent.VK_3) {
			Difficulty.restart();
			TextToSpeech.playVoice("Loading level 3...");
			TextToSpeech.voiceInterruptor = true;
			Alien.aliens.clear();
			Dragon.dragons.clear();
		}

		if (key == KeyEvent.VK_4) {
			Difficulty.restart();
			TextToSpeech.playVoice("Loading level 4...");
			TextToSpeech.voiceInterruptor = true;
			Alien.aliens.clear();
			Dragon.dragons.clear();
			UpdateObjects.lifeBunker = 50;
		}

		if (key == KeyEvent.VK_R) {
			isEPressed = false;
			isMPressed = false;
			isHPressed = false;
			if (InitObjects.ingame == false) {
				TextToSpeech.playVoice("Loading level 1...");
				TextToSpeech.voiceInterruptor = true;
			}
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
			LoadSounds.BG_MUSIC.loop();

			if (Alien.aliens.isEmpty()) {
				LoadSounds.TAUNT.loop();
			}
			if (!InitObjects.ingame) {
				if (InitObjects.ingame == false) {
					TextToSpeech.playVoice("Loading level 1...");
					TextToSpeech.voiceInterruptor = true;
				}
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
			LoadSounds.BG_MUSIC.loop();

			if (Alien.aliens.isEmpty()) {
				LoadSounds.TAUNT.loop();
			}
			if (!InitObjects.ingame) {
				if (InitObjects.ingame == false) {
					TextToSpeech.playVoice("Loading level 1...");
					TextToSpeech.voiceInterruptor = true;
				}
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
			LoadSounds.BG_MUSIC.loop();

			if (Alien.aliens.isEmpty()) {
				LoadSounds.TAUNT.loop();
			}
			if (!InitObjects.ingame) {
				if (InitObjects.ingame == false) {
					TextToSpeech.playVoice("Loading level 1...");
					TextToSpeech.voiceInterruptor = true;
				}
				Difficulty.hard();
			}
		}

		if (!(InitObjects.timerEasy.isRunning() || InitObjects.timerMedium.isRunning()
				|| InitObjects.timerHard.isRunning())
				&& (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP
						|| key == KeyEvent.VK_DOWN)) {
			ConsoleContent.manualON = false;
			InitObjects.timerHard.stop();
			InitObjects.timerMedium.stop();
			InitObjects.timerEasy.start();
			LoadSounds.BG_MUSIC.loop();

			if (Alien.aliens.isEmpty()) {
				LoadSounds.TAUNT.loop();
			}
		}

		if (key == KeyEvent.VK_G) {

			if (!ConsoleContent.god) {
				if (InitObjects.ingame == true) {
					ConsoleContent.god = true;
					UpdateObjects.lifePlayerShip = -999;
					TextToSpeech.playVoice("GODLIKE!");
					return;
				}
			} else {
				if (InitObjects.ingame == true) {
					ConsoleContent.god = false;
					UpdateObjects.lifePlayerShip = 3;
					TextToSpeech.playVoice("Healthy!");
					return;
				}
			}
		}

		if (((key == KeyEvent.VK_Z)
				&& ((e.getModifiers() & InputEvent.ALT_MASK) != 0)) /*
																	 * || ((key == KeyEvent.VK_X) && ((e.getModifiers()
																	 * & InputEvent.ALT_MASK) != 0) &&
																	 * GameMenuBar.autosave.isSelected() == false)
																	 */) {
			if (SaveSign.saveSign != null) {
				SaveSign.saveSign.initSave();
				SaveSign.saveSign.setVisible(true);
				final File file = new File("saves/save" + rand.nextInt() + ".txt");
				try {
					SaveGame.saveGameDataToFile(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} else {
			if (SaveSign.saveSign != null) {
				SaveSign.saveSign.setVisible(false);
			}
		}

		/*
		 * if (key == KeyEvent.VK_V && !ConsoleContent.consoleON) {
		 * ConsoleContent.console = new ConsoleForm(); }
		 */

		if (key == KeyEvent.VK_ESCAPE) {
			MenuState.isOn = false;
			LoadSounds.BG_MUSIC.stop();
			LoadSounds.HIT.stop();
			LoadSounds.TAUNT.stop();
			InitObjects.timerEasy.stop();
			InitObjects.timerMedium.stop();
			InitObjects.timerHard.stop();
			TextToSpeech.playVoice("Loading main menu...");
			TextToSpeech.voiceInterruptor = true;
			if (AstronautAnimation.astronautAnim != null)
				AstronautAnimation.astronautAnim = null;
			if (SatelliteAnimation.starAnim != null)
				SatelliteAnimation.starAnim = null;
			for (AsteroidsAnimation asteroidsAnim : AsteroidsAnimation.asteroidsAnimations) {
				if (asteroidsAnim != null)
					asteroidsAnim = null;
			}
			AsteroidsAnimation.asteroidsAnimations.clear();
			for (TheEndAnimation elonAnimUp : TheEndAnimation.theEndAnimationsUp) {
				if (elonAnimUp != null)
					elonAnimUp = null;
			}
			TheEndAnimation.theEndAnimationsUp.clear();
			for (TheEndAnimation elonAnimDown : TheEndAnimation.theEndAnimationsDown) {
				if (elonAnimDown != null)
					elonAnimDown = null;
			}
			TheEndAnimation.theEndAnimationsDown.clear();
			InitObjects.ingame = false;
			if (MouseInputHandler.main != null)
				MouseInputHandler.main.dispose();
			MouseInputHandler.main = null;
			MenuState.isOn = true;
			final CanvasMenu engine = new CanvasMenu();
			engine.start();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}