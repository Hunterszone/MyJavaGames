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

import animation.AsteroidAnimation;
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
import util.Flags;
import util.LivesAndCounts;
import util.ActionPerformer;
import util.Constants;
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
			if (ActionPerformer.getTimerEasy().isRunning() || ActionPerformer.getTimerHard().isRunning()
					|| ActionPerformer.getTimerMedium().isRunning()) {
				LoadSounds.BG_MUSIC.loop();
			}
		}

		if (key == KeyEvent.VK_P) {
			ActionPerformer.getTimerEasy().stop();
			ActionPerformer.getTimerMedium().stop();
			ActionPerformer.getTimerHard().stop();
			LoadSounds.BG_MUSIC.stop();
			LoadSounds.TAUNT.stop();
			Constants.isOnPause = true;
		}

		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
			if (ActionPerformer.isIngame()) {
				Constants.isOnPause = false;
				if (!ActionPerformer.getTimerHard().isRunning() && Flags.isHPressed) {
					ActionPerformer.getTimerHard().start();
					if (!Flags.isSPressed) {
						LoadSounds.BG_MUSIC.loop();
					}
				}
				if (!ActionPerformer.getTimerMedium().isRunning() && Flags.isMPressed) {
					ActionPerformer.getTimerMedium().start();
					if (!Flags.isSPressed) {
						LoadSounds.BG_MUSIC.loop();
					}
				}
				if ((!ActionPerformer.getTimerEasy().isRunning() && Flags.isEPressed)
						&& !(ActionPerformer.getTimerEasy().isRunning() && ActionPerformer.getTimerMedium().isRunning()
								&& ActionPerformer.getTimerHard().isRunning())
						&& !Flags.isMPressed && !Flags.isHPressed) {
					ActionPerformer.getTimerEasy().start();
					if (!Flags.isSPressed) {
						LoadSounds.BG_MUSIC.loop();
					}
				}
				if (Alien.aliens.isEmpty()) {
					LoadSounds.TAUNT.loop();
				}
			}
		}

		if (ActionPerformer.isIngame()
				&& (ActionPerformer.getTimerEasy().isRunning() == true || ActionPerformer.getTimerMedium().isRunning() == true
						|| ActionPerformer.getTimerHard().isRunning() == true)
				&& key == KeyEvent.VK_CONTROL && (Alien.aliens.isEmpty() && (Dragon.dragons.size() > 0
						|| LivesAndCounts.getLifeBunker() < 50 || Gold.goldstack.isEmpty()))) {
			PlayerShip.playerShip.loadRockets();
		}

		if (ActionPerformer.isIngame()
				&& (ActionPerformer.getTimerEasy().isRunning() == true || ActionPerformer.getTimerMedium().isRunning() == true
						|| ActionPerformer.getTimerHard().isRunning() == true)
				&& key == KeyEvent.VK_CONTROL && (Alien.aliens.size() > 0 || (Dragon.dragons.isEmpty()
						&& LivesAndCounts.getLifeBunker() >= 50 && Gold.goldstack.size() > 0))) {
			PlayerShip.playerShip.gunLocked(e);
		}

		if (ActionPerformer.isIngame()
				&& (ActionPerformer.getTimerEasy().isRunning() == true || ActionPerformer.getTimerMedium().isRunning() == true
						|| ActionPerformer.getTimerHard().isRunning() == true)
				&& key == KeyEvent.VK_SPACE
				&& (Alien.aliens.size() > 0 || (LivesAndCounts.getLifeBunker() >= 50 && Gold.goldstack.isEmpty()))) {
			PlayerShip.playerShip.loadMissiles();
		}

		if (ActionPerformer.isIngame()
				&& (ActionPerformer.getTimerEasy().isRunning() == true || ActionPerformer.getTimerMedium().isRunning() == true
						|| ActionPerformer.getTimerHard().isRunning() == true)
				&& key == KeyEvent.VK_SPACE
				&& ((Alien.aliens.isEmpty() && Dragon.dragons.size() > 0)
						|| (Dragon.dragons.isEmpty() && LivesAndCounts.getLifeBunker() < 50) || (Dragon.dragons.isEmpty()
								&& LivesAndCounts.getLifeBunker() >= 50 && Gold.goldstack.size() > 0))) {
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
			Constants.isOnPause = true;
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
			Constants.isOnPause = true;
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
			Constants.isOnPause = true;
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
			LivesAndCounts.setLifeBunker(50);
			Constants.isOnPause = true;
		}

		if (key == KeyEvent.VK_R) {
			Flags.isMPressed = false;
			Flags.isHPressed = false;
			Flags.isEPressed = true;
			if (!ActionPerformer.isIngame()) {
				TextToSpeech.playVoice("Loading level 1...");
				TextToSpeech.voiceInterruptor = true;
				try {
					Difficulty.restart();
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
			} else {
				Constants.isOnPause = true;
			}
		}

		if (key == KeyEvent.VK_E) {
			Flags.isMPressed = false;
			Flags.isHPressed = false;
			Flags.isEPressed = true;
			ActionPerformer.getTimerHard().stop();
			ActionPerformer.getTimerMedium().stop();
			ActionPerformer.getTimerEasy().start();
			if (!Flags.isSPressed) {
				LoadSounds.BG_MUSIC.loop();
			}
			if (!ActionPerformer.isIngame()) {
				TextToSpeech.playVoice("Loading level 1...");
				try {
					Difficulty.getEasyDiff();
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
				ActionPerformer.getTimerEasy().stop();
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
			ActionPerformer.getTimerEasy().stop();
			ActionPerformer.getTimerHard().stop();
			ActionPerformer.getTimerMedium().start();
			if (!Flags.isSPressed) {
				LoadSounds.BG_MUSIC.loop();
			}
			if (!ActionPerformer.isIngame()) {
				TextToSpeech.playVoice("Loading level 1...");
				try {
					Difficulty.getMediumDiff();
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
				ActionPerformer.getTimerMedium().stop();
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
			ActionPerformer.getTimerEasy().stop();
			ActionPerformer.getTimerMedium().stop();
			ActionPerformer.getTimerHard().start();
			if (!Flags.isSPressed) {
				LoadSounds.BG_MUSIC.loop();
			}
			if (!ActionPerformer.isIngame()) {
				TextToSpeech.playVoice("Loading level 1...");
				try {
					Difficulty.getHardDiff();
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
				ActionPerformer.getTimerHard().stop();
				LoadSounds.BG_MUSIC.stop();
			} else {
				TextToSpeech.playVoice("Hard diffuculty!");
			}
			TextToSpeech.voiceInterruptor = true;
		}

		if (key == KeyEvent.VK_N) {
			if (ActionPerformer.isIngame()) {
				if (Flags.isMapVisible == false) {
					Flags.isMapVisible = true;
					return;
				} else {
					Flags.isMapVisible = false;
					return;
				}
			}
		}
		
		if (key == KeyEvent.VK_T) {
			if (ActionPerformer.isIngame()) {
				if (Flags.isClockVisible == false) {
					Flags.isClockVisible = true;
					return;
				} else {
					Flags.isClockVisible = false;
					return;
				}
			}
		}

		if (key == KeyEvent.VK_G) {
			if (!Flags.isGod) {
				Flags.isGod = true;
				LivesAndCounts.setLifePlayerShip(999);
				TextToSpeech.playVoice("GODLIKE!");
				return;
			} else {
				Flags.isGod = false;
				LivesAndCounts.setLifePlayerShip(4);
				TextToSpeech.playVoice("Healthy!");
				return;
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
			ActionPerformer.getTimerEasy().stop();
			ActionPerformer.getTimerMedium().stop();
			ActionPerformer.getTimerHard().stop();
			TextToSpeech.playVoice("Loading main menu...");
			TextToSpeech.voiceInterruptor = true;
			for (AsteroidAnimation asteroidsAnim : AsteroidAnimation.ASTEROID_ANIMATION) {
				if (asteroidsAnim != null)
					asteroidsAnim = null;
			}
			AsteroidAnimation.ASTEROID_ANIMATION.clear();
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
			ActionPerformer.setIngame(false);
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