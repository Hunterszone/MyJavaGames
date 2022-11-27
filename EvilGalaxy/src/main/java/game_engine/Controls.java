package game_engine;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import entities.Alien;
import entities.AsteroidsAnimation;
import entities.AstronautAnimation;
import entities.Crosshair;
import entities.Dragon;
import entities.PlayerShip;
import entities.SatelliteAnimation;
import entities.TheEndAnimationDown;
import entities.TheEndAnimationUp;
import items.Gold;
import items.SaveSign;
import items.VolBtn;
import menu_engine.MouseInputHandler;
import util.ConsoleContent;
import util.Constants;
import util.LoadSounds;
import util.TextToSpeech;

public class Controls implements KeyListener {

	@Override
	public void keyReleased(KeyEvent e) {
		PlayerShip.playerShip.keyReleased(e);
		Crosshair.crosshair.keyReleased(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		PlayerShip.playerShip.keyPressed(e);
		Crosshair.crosshair.keyPressed(e);
		VolBtn.volButt.keyPressed(e);

		final int key = e.getKeyCode();

		if (key == KeyEvent.VK_S) {
			Constants.isSPressed = true;
			LoadSounds.BG_MUSIC.stop();
		}

		if (key == KeyEvent.VK_A) {
			Constants.isSPressed = false;
			if (InitObjects.timerEasy.isRunning() || 
					InitObjects.timerHard.isRunning() || 
					InitObjects.timerMedium.isRunning()) {
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
		
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || 
				key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
			if (InitObjects.ingame) {
				ConsoleContent.manualON = false;
				if (!InitObjects.timerHard.isRunning() && Constants.isHPressed) {
					InitObjects.timerHard.start();
					if(!Constants.isSPressed) {
						LoadSounds.BG_MUSIC.loop();								
					}
				} 
				if (!InitObjects.timerMedium.isRunning() && Constants.isMPressed) {
					InitObjects.timerMedium.start();
					if(!Constants.isSPressed) {
						LoadSounds.BG_MUSIC.loop();								
					}
				} 
				if ((!InitObjects.timerEasy.isRunning() && Constants.isEPressed) && 
						!(InitObjects.timerEasy.isRunning() && InitObjects.timerMedium.isRunning() && InitObjects.timerHard.isRunning()) && 
						!Constants.isMPressed && !Constants.isHPressed) {
					InitObjects.timerEasy.start();
					if(!Constants.isSPressed) {
						LoadSounds.BG_MUSIC.loop();								
					}		
				}
				if (Alien.aliens.isEmpty()) {
					LoadSounds.TAUNT.loop();
				}
			}
		}

		if (InitObjects.ingame && (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
						|| InitObjects.timerHard.isRunning() == true)
				&& key == KeyEvent.VK_CONTROL && (Alien.aliens.isEmpty()
						&& (Dragon.dragons.size() > 0 || UpdateObjects.lifeBunker < 50 || Gold.goldstack.isEmpty()))) {
			PlayerShip.playerShip.loadRockets();
		}

		if (InitObjects.ingame && (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
						|| InitObjects.timerHard.isRunning() == true)
				&& key == KeyEvent.VK_CONTROL && (Alien.aliens.size() > 0
						|| (Dragon.dragons.isEmpty() && UpdateObjects.lifeBunker >= 50 && Gold.goldstack.size() > 0))) {
			PlayerShip.playerShip.gunLocked(e);
		}

		if (InitObjects.ingame && (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
						|| InitObjects.timerHard.isRunning() == true)
				&& key == KeyEvent.VK_SPACE
				&& (Alien.aliens.size() > 0 || (UpdateObjects.lifeBunker >= 50 && Gold.goldstack.isEmpty()))) {
			PlayerShip.playerShip.loadMissiles();
		}

		if (InitObjects.ingame && (InitObjects.timerEasy.isRunning() == true
						|| InitObjects.timerMedium.isRunning() == true || InitObjects.timerHard.isRunning() == true)
				&& key == KeyEvent.VK_SPACE
				&& ((Alien.aliens.isEmpty() && Dragon.dragons.size() > 0)
						|| (Dragon.dragons.isEmpty() && UpdateObjects.lifeBunker < 50)
						|| (Dragon.dragons.isEmpty() && UpdateObjects.lifeBunker >= 50 && Gold.goldstack.size() > 0))) {
			PlayerShip.playerShip.gunLocked(e);
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
			Constants.isMPressed = false;
			Constants.isHPressed = false;
			if (!InitObjects.ingame) {
				Constants.isEPressed = true;
				TextToSpeech.playVoice("Loading level 1...");
				TextToSpeech.voiceInterruptor = true;
				Difficulty.restart();
			}
		}

		if (key == KeyEvent.VK_E) {
			Constants.isMPressed = false;
			Constants.isHPressed = false;
			Constants.isEPressed = true;
			ConsoleContent.manualON = false;
			InitObjects.timerHard.stop();
			InitObjects.timerMedium.stop();
			InitObjects.timerEasy.start();
			if (!Constants.isSPressed) {
				LoadSounds.BG_MUSIC.loop();
			}
			if (!InitObjects.ingame) {
				TextToSpeech.playVoice("Loading level 1...");
				TextToSpeech.voiceInterruptor = true;
				Difficulty.getEasyDiff();
			} else {
				TextToSpeech.playVoice("Easy diffuculty!");
				TextToSpeech.voiceInterruptor = true;
			}
		}

		if (key == KeyEvent.VK_M) {
			Constants.isEPressed = false;
			Constants.isHPressed = false;
			Constants.isMPressed = true;
			ConsoleContent.manualON = false;
			InitObjects.timerEasy.stop();
			InitObjects.timerHard.stop();
			InitObjects.timerMedium.start();
			if (!Constants.isSPressed) {
				LoadSounds.BG_MUSIC.loop();
			}
			if (!InitObjects.ingame) {
				TextToSpeech.playVoice("Loading level 1...");
				TextToSpeech.voiceInterruptor = true;
				Difficulty.getMediumDiff();
			} else {
				TextToSpeech.playVoice("Medium diffuculty!");
				TextToSpeech.voiceInterruptor = true;
			}
		}

		if (key == KeyEvent.VK_H) {
			Constants.isEPressed = false;
			Constants.isMPressed = false;
			Constants.isHPressed = true;
			ConsoleContent.manualON = false;
			InitObjects.timerEasy.stop();
			InitObjects.timerMedium.stop();
			InitObjects.timerHard.start();
			if (!Constants.isSPressed) {
				LoadSounds.BG_MUSIC.loop();
			}
			if (!InitObjects.ingame) {
				TextToSpeech.playVoice("Loading level 1...");
				TextToSpeech.voiceInterruptor = true;
				Difficulty.getHardDiff();
			} else {
				TextToSpeech.playVoice("Hard diffuculty!");
				TextToSpeech.voiceInterruptor = true;
			}
		}

		if (key == KeyEvent.VK_N) {
			if (InitObjects.ingame) {
				if (Constants.isMapVisible == false) {
					Constants.isMapVisible = true;
					return;
				} else {
					Constants.isMapVisible = false;
					return;
				}
			}
		}

		if (key == KeyEvent.VK_G) {
			if (InitObjects.ingame) {
				if (!ConsoleContent.isGod) {
					ConsoleContent.isGod = true;
					UpdateObjects.lifePlayerShip = 999;
					TextToSpeech.playVoice("GODLIKE!");
					return;
				} else {
					ConsoleContent.isGod = false;
					UpdateObjects.lifePlayerShip = 4;
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
				Path path = Paths.get("saves");
				Random rand = new Random();
				try {
					Files.createDirectories(path);
					final File file = new File("saves/save" + rand.nextInt() + ".txt");
					SaveGame.saveGameDataToFile(file);
				} catch (IOException ioe) {
					ioe.getMessage();
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
			Constants.isMapVisible = false;
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
			for (TheEndAnimationUp elonAnimUp : TheEndAnimationUp.theEndAnimationUpList) {
				if (elonAnimUp != null)
					elonAnimUp = null;
			}
			TheEndAnimationUp.theEndAnimationUpList.clear();
			for (TheEndAnimationDown elonAnimDown : TheEndAnimationDown.theEndAnimationDownList) {
				if (elonAnimDown != null)
					elonAnimDown = null;
			}
			TheEndAnimationDown.theEndAnimationDownList.clear();
			InitObjects.ingame = false;
			if (MouseInputHandler.main != null)
				MouseInputHandler.main.dispose();
			MouseInputHandler.main = null;
			Constants.initCanvas();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}