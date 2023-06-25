package game_engine;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import animation.AsteroidsAnimation;
import animation.TheEndAnimationDown;
import animation.TheEndAnimationUp;
import enemy.Alien;
import enemy.Crosshair;
import enemy.Dragon;
import enemy.PlayerShip;
import items.Gold;
import items.SaveSign;
import items.VolBtn;
import menu_engine.MouseInputHandler;
import util.ConsoleContent;
import util.Flags;
import util.LoadSounds;
import util.TextToSpeech;

public class Controls implements KeyListener {
	
	private Random rand = SecureRandom.getInstanceStrong();
	
	public Controls() throws NoSuchAlgorithmException {
		
	}

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
			Flags.isSPressed = true;
			LoadSounds.BG_MUSIC.stop();
		}

		if (key == KeyEvent.VK_A) {
			Flags.isSPressed = false;
			if (InitObjects.getTimerEasy().isRunning() || 
					InitObjects.getTimerHard().isRunning() || 
					InitObjects.getTimerMedium().isRunning()) {
				LoadSounds.BG_MUSIC.loop();
			}
		}

		if (key == KeyEvent.VK_P) {
			InitObjects.getTimerEasy().stop();
			InitObjects.getTimerMedium().stop();
			InitObjects.getTimerHard().stop();
			LoadSounds.BG_MUSIC.stop();
			LoadSounds.TAUNT.stop();
		}
		
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || 
				key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
			if (InitObjects.isIngame()) {
				ConsoleContent.manualON = false;
				if (!InitObjects.getTimerHard().isRunning() && Flags.isHPressed) {
					InitObjects.getTimerHard().start();
					if(!Flags.isSPressed) {
						LoadSounds.BG_MUSIC.loop();								
					}
				} 
				if (!InitObjects.getTimerMedium().isRunning() && Flags.isMPressed) {
					InitObjects.getTimerMedium().start();
					if(!Flags.isSPressed) {
						LoadSounds.BG_MUSIC.loop();								
					}
				} 
				if ((!InitObjects.getTimerEasy().isRunning() && Flags.isEPressed) && 
						!(InitObjects.getTimerEasy().isRunning() && InitObjects.getTimerMedium().isRunning() && InitObjects.getTimerHard().isRunning()) && 
						!Flags.isMPressed && !Flags.isHPressed) {
					InitObjects.getTimerEasy().start();
					if(!Flags.isSPressed) {
						LoadSounds.BG_MUSIC.loop();								
					}		
				}
				if (Alien.aliens.isEmpty()) {
					LoadSounds.TAUNT.loop();
				}
			}
		}

		if (InitObjects.isIngame() && (InitObjects.getTimerEasy().isRunning() == true || InitObjects.getTimerMedium().isRunning() == true
						|| InitObjects.getTimerHard().isRunning() == true)
				&& key == KeyEvent.VK_CONTROL && (Alien.aliens.isEmpty()
						&& (Dragon.dragons.size() > 0 || UpdateObjects.getLifeBunker() < 50 || Gold.goldstack.isEmpty()))) {
			PlayerShip.playerShip.loadRockets();
		}

		if (InitObjects.isIngame() && (InitObjects.getTimerEasy().isRunning() == true || InitObjects.getTimerMedium().isRunning() == true
						|| InitObjects.getTimerHard().isRunning() == true)
				&& key == KeyEvent.VK_CONTROL && (Alien.aliens.size() > 0
						|| (Dragon.dragons.isEmpty() && UpdateObjects.getLifeBunker() >= 50 && Gold.goldstack.size() > 0))) {
			PlayerShip.playerShip.gunLocked(e);
		}

		if (InitObjects.isIngame() && (InitObjects.getTimerEasy().isRunning() == true || InitObjects.getTimerMedium().isRunning() == true
						|| InitObjects.getTimerHard().isRunning() == true)
				&& key == KeyEvent.VK_SPACE
				&& (Alien.aliens.size() > 0 || (UpdateObjects.getLifeBunker() >= 50 && Gold.goldstack.isEmpty()))) {
			PlayerShip.playerShip.loadMissiles();
		}

		if (InitObjects.isIngame() && (InitObjects.getTimerEasy().isRunning() == true
						|| InitObjects.getTimerMedium().isRunning() == true || InitObjects.getTimerHard().isRunning() == true)
				&& key == KeyEvent.VK_SPACE
				&& ((Alien.aliens.isEmpty() && Dragon.dragons.size() > 0)
						|| (Dragon.dragons.isEmpty() && UpdateObjects.getLifeBunker() < 50)
						|| (Dragon.dragons.isEmpty() && UpdateObjects.getLifeBunker() >= 50 && Gold.goldstack.size() > 0))) {
			PlayerShip.playerShip.gunLocked(e);
		}

		if (key == KeyEvent.VK_1) {
			try {
				Difficulty.restart();
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			TextToSpeech.playVoice("Loading level 1...");
			TextToSpeech.voiceInterruptor = true;
		}

		if (key == KeyEvent.VK_2) {
			try {
				Difficulty.restart();
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			TextToSpeech.playVoice("Loading level 2...");
			TextToSpeech.voiceInterruptor = true;
			Alien.aliens.clear();
		}

		if (key == KeyEvent.VK_3) {
			try {
				Difficulty.restart();
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			TextToSpeech.playVoice("Loading level 3...");
			TextToSpeech.voiceInterruptor = true;
			Alien.aliens.clear();
			Dragon.dragons.clear();
		}

		if (key == KeyEvent.VK_4) {
			try {
				Difficulty.restart();
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			TextToSpeech.playVoice("Loading level 4...");
			TextToSpeech.voiceInterruptor = true;
			Alien.aliens.clear();
			Dragon.dragons.clear();
			UpdateObjects.setLifeBunker(50);
		}

		if (key == KeyEvent.VK_R) {
			Flags.isMPressed = false;
			Flags.isHPressed = false;
			if (!InitObjects.isIngame()) {
				Flags.isEPressed = true;
				TextToSpeech.playVoice("Loading level 1...");
				TextToSpeech.voiceInterruptor = true;
				try {
					Difficulty.restart();
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
			}
		}

		if (key == KeyEvent.VK_E) {
			Flags.isMPressed = false;
			Flags.isHPressed = false;
			Flags.isEPressed = true;
			ConsoleContent.manualON = false;
			InitObjects.getTimerHard().stop();
			InitObjects.getTimerMedium().stop();
			InitObjects.getTimerEasy().start();
			if (!Flags.isSPressed) {
				LoadSounds.BG_MUSIC.loop();
			}
			if (!InitObjects.isIngame()) {
				TextToSpeech.playVoice("Loading level 1...");
				try {
					Difficulty.getEasyDiff();
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
				InitObjects.getTimerEasy().stop();
				LoadSounds.BG_MUSIC.stop();
			} else {
				TextToSpeech.playVoice("Easy diffuculty!");
			}
			TextToSpeech.voiceInterruptor = true;
		}

		if (key == KeyEvent.VK_M) {
			Flags.isEPressed = false;
			Flags.isHPressed = false;
			Flags.isMPressed = true;
			ConsoleContent.manualON = false;
			InitObjects.getTimerEasy().stop();
			InitObjects.getTimerHard().stop();
			InitObjects.getTimerMedium().start();
			if (!Flags.isSPressed) {
				LoadSounds.BG_MUSIC.loop();
			}
			if (!InitObjects.isIngame()) {
				TextToSpeech.playVoice("Loading level 1...");
				try {
					Difficulty.getMediumDiff();
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
				InitObjects.getTimerMedium().stop();
				LoadSounds.BG_MUSIC.stop();
			} else {
				TextToSpeech.playVoice("Medium diffuculty!");
			}
			TextToSpeech.voiceInterruptor = true;
		}

		if (key == KeyEvent.VK_H) {
			Flags.isEPressed = false;
			Flags.isMPressed = false;
			Flags.isHPressed = true;
			ConsoleContent.manualON = false;
			InitObjects.getTimerEasy().stop();
			InitObjects.getTimerMedium().stop();
			InitObjects.getTimerHard().start();
			if (!Flags.isSPressed) {
				LoadSounds.BG_MUSIC.loop();
			}
			if (!InitObjects.isIngame()) {
				TextToSpeech.playVoice("Loading level 1...");
				try {
					Difficulty.getHardDiff();
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
				InitObjects.getTimerHard().stop();
				LoadSounds.BG_MUSIC.stop();
			} else {
				TextToSpeech.playVoice("Hard diffuculty!");
			}
			TextToSpeech.voiceInterruptor = true;
		}

		if (key == KeyEvent.VK_N) {
			if (InitObjects.isIngame()) {
				if (Flags.isMapVisible == false) {
					Flags.isMapVisible = true;
					return;
				} else {
					Flags.isMapVisible = false;
					return;
				}
			}
		}

		if (key == KeyEvent.VK_G) {
			if (InitObjects.isIngame()) {
				if (!ConsoleContent.isGod) {
					ConsoleContent.isGod = true;
					UpdateObjects.setLifePlayerShip(999);
					TextToSpeech.playVoice("GODLIKE!");
					return;
				} else {
					ConsoleContent.isGod = false;
					UpdateObjects.setLifePlayerShip(4);
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
			Flags.isMapVisible = false;
			LoadSounds.BG_MUSIC.stop();
			LoadSounds.HIT.stop();
			LoadSounds.TAUNT.stop();
			InitObjects.getTimerEasy().stop();
			InitObjects.getTimerMedium().stop();
			InitObjects.getTimerHard().stop();
			TextToSpeech.playVoice("Loading main menu...");
			TextToSpeech.voiceInterruptor = true;
			for (AsteroidsAnimation asteroidsAnim : AsteroidsAnimation.ASTEROID_ANIMATION) {
				if (asteroidsAnim != null)
					asteroidsAnim = null;
			}
			AsteroidsAnimation.ASTEROID_ANIMATION.clear();
			for (TheEndAnimationUp elonAnimUp : TheEndAnimationUp.END_ANIMATION_UP) {
				if (elonAnimUp != null)
					elonAnimUp = null;
			}
			TheEndAnimationUp.END_ANIMATION_UP.clear();
			for (TheEndAnimationDown elonAnimDown : TheEndAnimationDown.END_ANIMATION_DOWN) {
				if (elonAnimDown != null)
					elonAnimDown = null;
			}
			TheEndAnimationDown.END_ANIMATION_DOWN.clear();
			InitObjects.setIngame(false);
			if (MouseInputHandler.main != null)
				MouseInputHandler.main.dispose();
			MouseInputHandler.main = null;
			InitObjects.initCanvas();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}