// MouseInputHandler.java
//
// Creator: Konstantin
//

package menu_engine;

// import java libraries:
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

// import game packages:
import enums.SoundEffects;
import launcher.Launcher;
import menu_states.ControlsState;
import menu_states.ExtrasState;
import menu_states.MenuState;
import menu_states.SettingsState;
import menu_states.StateManager;
import snake.Main;
import sound_engine.PlayWave1st;
import util.Constants;
import util.LoadSounds;
import util.TextToSpeech;

public class MouseInputHandler extends MouseInputAdapter implements MouseListener {

	public static Main main;
	private final String hoverSound = SoundEffects.HOVER.getSound();
	private final String clickSound = SoundEffects.CLICK.getSound();
	private final String forbidden = SoundEffects.FORBIDDEN.getSound();
	private PlayWave1st playWave1st;

	@Override
	public void mousePressed(MouseEvent e) {

		final int mouseX = e.getX();
		final int mouseY = e.getY();

		if (MenuState.isOn) {
			playWave1st = new PlayWave1st(clickSound);
			if (mouseX >= 430 && mouseX <= 770) { // Play game button width
				if (mouseY >= 115 && mouseY <= 165) { // Play game button height
					LoadSounds.BG_SOUND.stop();
					playWave1st.start();
					MenuState.isOn = false;
					if (main == null) {
						DisplayCanvas.frame.remove(DisplayCanvas.canvas);
						DisplayCanvas.frame.dispose();
						SwingUtilities.invokeLater(() -> {
							try {
								main = new Main();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							main.setVisible(true);
						});
					}
					MenuState.isOn = true;
					TextToSpeech.voiceInterruptor = true;
					TextToSpeech.playVoice("Loading level 1...");
				}
				if (mouseY >= 215 && mouseY <= 265 && MenuState.isOn) {
					playWave1st.start();
					CanvasMenu.State.setState(StateManager.STATES.CONTROLS);
					MenuState.isOn = false;
					ControlsState.isOn = true;
				}
				if (mouseY >= 315 && mouseY <= 365 && MenuState.isOn) {
					playWave1st.start();
					CanvasMenu.State.setState(StateManager.STATES.EXTRAS);
					MenuState.isOn = false;
					ExtrasState.isOn = true;
				}
				if (mouseY >= 415 && mouseY <= 465 && MenuState.isOn) {
					playWave1st.start();
					System.exit(0);
				}
			} else {
				playWave1st = new PlayWave1st(forbidden);
				playWave1st.start();
			}
		}

		if (ControlsState.isOn) {
			if (mouseX >= 430 && mouseX <= 770) {
				if (mouseY >= 150 && mouseY <= 200) {
					playWave1st = new PlayWave1st(clickSound);
					playWave1st.start();
					ControlsState.isOn = false;
					MenuState.isOn = true;
					CanvasMenu.State.setState(StateManager.STATES.MENU);
				}
			} else {
				playWave1st = new PlayWave1st(forbidden);
				playWave1st.start();
			}
		}

		if (ExtrasState.isOn) {
			playWave1st = new PlayWave1st(clickSound);
			if (mouseX >= 430 && mouseX <= 770) {
				if (mouseY >= 150 && mouseY <= 200) {
					LoadSounds.BG_SOUND.stop();
					playWave1st.start();
					MenuState.isOn = false;
					DisplayCanvas.frame.remove(DisplayCanvas.canvas);
					DisplayCanvas.frame.dispose();
					try {
						Launcher.main(null);
					} catch (FileNotFoundException | UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					MenuState.isOn = true;
				}
				if (mouseY >= 250 && mouseY <= 300) {
					playWave1st.start();
					try {
						Desktop.getDesktop()
								.browse(new URI("https://github.com/Hunterszone/MyJavaGames/tree/master/EvilGalaxy"));
					} catch (final IOException e1) {
						e1.printStackTrace();
					} catch (final URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
				if (mouseY >= 350 && mouseY <= 400) {
					playWave1st.start();
					try {
						Desktop.getDesktop().browse(new URI("http://me4gaming.com/index.php/en/gamedev/6-articles"));
					} catch (final IOException e1) {
						e1.printStackTrace();
					} catch (final URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
				if (mouseY >= 450 && mouseY <= 500) {
					playWave1st.start();
					ExtrasState.isOn = false;
					MenuState.isOn = true;
					CanvasMenu.State.setState(StateManager.STATES.MENU);
				}
			} else {
				if (!(mouseY >= 150 && mouseY <= 200) || !(mouseY >= 250 && mouseY <= 300)
						|| !(mouseY >= 350 && mouseY <= 400) || !(mouseY >= 450 && mouseY <= 500)) {
					playWave1st = new PlayWave1st(forbidden);
					playWave1st.start();
				}
			}
		}
		if ((MenuState.isOn || ControlsState.isOn || SettingsState.isOn || ExtrasState.isOn)
				&& (mouseX >= 1100 && mouseX <= 1100 + Constants.LOAD_ASSETS.volume.getWidth(null))) {
			if ((mouseY >= 32 && mouseY <= 32 + Constants.LOAD_ASSETS.volume.getHeight(null))) {
				playWave1st = new PlayWave1st(clickSound);
				playWave1st.start();
				LoadSounds.BG_SOUND.stop();
			}
		} else {
			if (!(mouseY >= 115 && mouseY <= 165) && !(mouseY >= 215 && mouseY <= 265)
					&& !(mouseY >= 315 && mouseY <= 365) && !(mouseY >= 415 && mouseY <= 465)
					&& !(mouseY >= 515 && mouseY <= 565)
					&& !(SettingsState.isOn && mouseY >= 280
							&& mouseY <= 280 + Constants.LOAD_ASSETS.myShip.getHeight(null))
					&& !(SettingsState.isOn && mouseY >= 380
							&& mouseY <= 380 + Constants.LOAD_ASSETS.evilHead.getHeight(null)))
				LoadSounds.BG_SOUND.loop();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		playWave1st = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		playWave1st = new PlayWave1st(hoverSound);
		playWave1st.start();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		playWave1st = null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
