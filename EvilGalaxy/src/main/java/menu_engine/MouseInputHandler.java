// MouseInputHandler.java
//
// Creator: Konstantin
//

package menu_engine;

// import java libraries:
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

// import game packages:
import enums.SoundEffects;
import game_engine.InitObjects;
import game_engine.LoadGame;
import game_engine.UpdateObjects;
import launcher.Launcher;
import main.Main;
import menu_states.ControlsState;
import menu_states.ExtrasState;
import menu_states.ManualState;
import menu_states.MenuState;
import menu_states.SettingsState;
import menu_states.StateManager;
import sound_engine.PlayWave1st;
import util.Constants;
import util.LoadSounds;
import util.TextToSpeech;

public class MouseInputHandler implements MouseListener {

	public static Main main;
	private final String soundName = SoundEffects.CLICK.getSound();
	private final String forbidden = SoundEffects.FORBIDDEN.getSound();

	@Override
	public void mousePressed(MouseEvent e) {

		final int mouseX = e.getX();
		final int mouseY = e.getY();

		if (MenuState.isOn) {
			if (mouseX >= 430 && mouseX <= 770) { // Play game button width
				if (mouseY >= 115 && mouseY <= 165) { // Play game button height
					LoadSounds.MENU_MUSIC.stop();
					new PlayWave1st(soundName).start();
					MenuState.isOn = false;
					UpdateObjects.lifePlayerShip = 3;
					UpdateObjects.lifeEvilHead = 3;
					UpdateObjects.lifeBunker = 3;
					InitObjects.ingame = true;
					if (main == null) {
						DisplayCanvas.frame.remove(DisplayCanvas.canvas);
						DisplayCanvas.frame.dispose();
						EventQueue.invokeLater(() -> {
							main = new Main();
							main.setVisible(true);
						});
					}
					MenuState.isOn = true;
					TextToSpeech.voiceInterruptor = true;
					TextToSpeech.playVoice("Loading level 1...");
				}
				if (mouseY >= 215 && mouseY <= 265) {
					new PlayWave1st(soundName).start();
					new LoadGame().openFileChooser();
				}
				if (mouseY >= 315 && mouseY <= 365 && MenuState.isOn) {
					new PlayWave1st(soundName).start();
					CanvasMenu.State.setState(StateManager.STATES.CONTROLS);
					MenuState.isOn = false;
					ControlsState.isOn = true;
				}
				if (mouseY >= 415 && mouseY <= 465 && MenuState.isOn) {
					new PlayWave1st(soundName).start();
					CanvasMenu.State.setState(StateManager.STATES.SETTINGS);
					MenuState.isOn = false;
					SettingsState.isOn = true;
				}
				if (mouseY >= 515 && mouseY <= 565 && MenuState.isOn) {
					new PlayWave1st(soundName).start();
					CanvasMenu.State.setState(StateManager.STATES.EXTRAS);
					MenuState.isOn = false;
					ExtrasState.isOn = true;
				}
				if (mouseY >= 615 && mouseY <= 665 && MenuState.isOn) {
					new PlayWave1st(soundName).start();
					System.exit(0);
				}
			} else {
				new PlayWave1st(forbidden).start();
			}
		}

		if (ControlsState.isOn) {
			if (mouseX >= 430 && mouseX <= 770) {
				if (mouseY >= 150 && mouseY <= 200) {
					new PlayWave1st(soundName).start();
					ControlsState.isOn = false;
					MenuState.isOn = true;
					CanvasMenu.State.setState(StateManager.STATES.MENU);
				}
			} else {
				new PlayWave1st(forbidden).start();
			}
		}

		if (SettingsState.isOn) {
			if (mouseX >= 430 && mouseX <= 770) {
				if (mouseY >= 150 && mouseY <= 200) {
					new PlayWave1st(soundName).start();
					SettingsState.isOn = false;
					MenuState.isOn = true;
					CanvasMenu.State.setState(StateManager.STATES.MENU);
				}
			} else {
				if ((mouseX < 800 || mouseX > 800 + Constants.LOAD_ASSETS.myShip.getWidth(null)
						|| mouseX > 800 + Constants.LOAD_ASSETS.evilHead.getWidth(null)
						|| mouseX > 790 + Constants.LOAD_ASSETS.manual.getWidth(null))
						&& !(mouseY >= 150 && mouseY <= 200)) {
					new PlayWave1st(forbidden).start();
				}
			}
		}

		if (ManualState.isOn) {
			if (mouseX >= 430 && mouseX <= 770) {
				if (mouseY >= 150 && mouseY <= 200) {
					new PlayWave1st(soundName).start();
					ManualState.isOn = false;
					SettingsState.isOn = true;
					CanvasMenu.State.setState(StateManager.STATES.SETTINGS);
				}
			} else {
				new PlayWave1st(forbidden).start();
			}
		}

		if (ExtrasState.isOn) {
			if (mouseX >= 430 && mouseX <= 770) {
				if (mouseY >= 150 && mouseY <= 200) {
					LoadSounds.MENU_MUSIC.stop();
					new PlayWave1st(soundName).start();
					MenuState.isOn = false;
					DisplayCanvas.frame.remove(DisplayCanvas.canvas);
					DisplayCanvas.frame.dispose();
					Launcher.main(null);
					MenuState.isOn = true;
				}
				if (mouseY >= 250 && mouseY <= 300) {
					new PlayWave1st(soundName).start();
					try {
						Desktop.getDesktop().browse(new URI("https://github.com/Hunterszone/MyJavaGames/tree/master/EvilGalaxy"));
					} catch (final IOException e1) {
						e1.printStackTrace();
					} catch (final URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
				if (mouseY >= 350 && mouseY <= 400) {
					new PlayWave1st(soundName).start();
					try {
						Desktop.getDesktop().browse(new URI("http://me4gaming.com/index.php/en/gamedev/6-articles"));
					} catch (final IOException e1) {
						e1.printStackTrace();
					} catch (final URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
				if (mouseY >= 450 && mouseY <= 500) {
					new PlayWave1st(soundName).start();
					ExtrasState.isOn = false;
					MenuState.isOn = true;
					CanvasMenu.State.setState(StateManager.STATES.MENU);
				}
			} else {
				if (!(mouseY >= 150 && mouseY <= 200) ||
					!(mouseY >= 250 && mouseY <= 300) ||
					!(mouseY >= 350 && mouseY <= 400) ||
					!(mouseY >= 450 && mouseY <= 500)) {
					new PlayWave1st(forbidden).start();
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		final Point point = e.getPoint();
		if ((CanvasMenu.State.getState() == StateManager.STATES.SETTINGS)
				&& (point.getX() >= 790 && point.getX() <= 790 + Constants.LOAD_ASSETS.myShip.getWidth(null))) {
			if (point.getY() >= 280 && point.getY() <= 280 + Constants.LOAD_ASSETS.myShip.getHeight(null)) {
				new PlayWave1st(soundName).start();
				CanvasMenu.color.nextColor(CanvasMenu.color.getColor());
			}
		}
		if ((CanvasMenu.State.getState() == StateManager.STATES.SETTINGS)
				&& (point.getX() >= 790 && point.getX() <= 790 + Constants.LOAD_ASSETS.evilHead.getWidth(null))) {
			if (point.getY() >= 380 && point.getY() <= 380 + Constants.LOAD_ASSETS.evilHead.getHeight(null)) {
				new PlayWave1st(soundName).start();
				CanvasMenu.color2.nextColor(CanvasMenu.color2.getColor());
			}
		}
		if ((CanvasMenu.State.getState() == StateManager.STATES.SETTINGS)
				&& (point.getX() >= 810 && point.getX() <= 660 + Constants.LOAD_ASSETS.manual.getWidth(null))) {
			if (point.getY() >= 490 && point.getY() <= 370 + Constants.LOAD_ASSETS.manual.getHeight(null)) {
				new PlayWave1st(soundName).start();
				SettingsState.isOn = false;
				ManualState.isOn = true;
				CanvasMenu.State.setState(StateManager.STATES.MANUAL);
			}
		}
		if ((CanvasMenu.State.getState() == StateManager.STATES.MENU
				|| CanvasMenu.State.getState() == StateManager.STATES.CONTROLS
				|| CanvasMenu.State.getState() == StateManager.STATES.SETTINGS
				|| CanvasMenu.State.getState() == StateManager.STATES.MANUAL
				|| CanvasMenu.State.getState() == StateManager.STATES.EXTRAS)
				&& (point.getX() >= 1100 && point.getX() <= 1100 + Constants.LOAD_ASSETS.volume.getWidth(null))) {
			if ((point.getY() >= 32 && point.getY() <= 32 + Constants.LOAD_ASSETS.volume.getHeight(null))) {
				new PlayWave1st(soundName).start();
				LoadSounds.MENU_MUSIC.stop();
			}
		} else {
			if (!(point.getY() >= 115 && point.getY() <= 165) && 
				!(point.getY() >= 215 && point.getY() <= 265) && 
				!(point.getY() >= 315 && point.getY() <= 365) && 
				!(point.getY() >= 415 && point.getY() <= 465) &&
				!(point.getY() >= 515 && point.getY() <= 565)
					&& !(SettingsState.isOn && point.getY() >= 280
							&& point.getY() <= 280 + Constants.LOAD_ASSETS.myShip.getHeight(null))
					&& !(SettingsState.isOn && point.getY() >= 380
							&& point.getY() <= 380 + Constants.LOAD_ASSETS.evilHead.getHeight(null))
					&& !(ManualState.isOn && point.getY() >= 475
							&& point.getY() <= 475 + Constants.LOAD_ASSETS.manual.getHeight(null)))
				LoadSounds.MENU_MUSIC.loop();
		}
	}

}
