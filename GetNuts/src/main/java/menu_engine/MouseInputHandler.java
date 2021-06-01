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

import main.Launcher;
// import game packages:
import main.Main;
import menu_states.ControlsState;
import menu_states.ExtrasState;
import menu_states.ManualState;
import menu_states.MenuState;
import menu_states.SettingsState;
import menu_states.StateManager;
import sound_engine.LoadSounds;
import util.Constants;

public class MouseInputHandler implements MouseListener {

	public static Main main;

	@Override
	public void mousePressed(MouseEvent e) {

		final int mouseX = e.getX();
		final int mouseY = e.getY();

		if (MenuState.isOn) {
			if (mouseX >= 430 && mouseX <= 770) { // Play game button width
				if (mouseY >= 135 && mouseY <= 185) { // Play game button height
					LoadSounds.CLICK.play();
					MenuState.isOn = false;
					if (main == null) {
						DisplayCanvas.frame.remove(DisplayCanvas.canvas);
						DisplayCanvas.frame.dispose();
						EventQueue.invokeLater(() -> {
							main = new Main();
							main.setVisible(true);
						});
					}
					MenuState.isOn = true;
				}
				if (mouseY >= 235 && mouseY <= 285 && MenuState.isOn) {
					LoadSounds.CLICK.play();
					CanvasMenu.state.setState(StateManager.STATES.CONTROLS);
					MenuState.isOn = false;
					ControlsState.isOn = true;
				}
				if (mouseY >= 335 && mouseY <= 385 && MenuState.isOn) {
					LoadSounds.CLICK.play();
					CanvasMenu.state.setState(StateManager.STATES.SETTINGS);
					MenuState.isOn = false;
					SettingsState.isOn = true;
				}
				if (mouseY >= 440 && mouseY <= 485 && MenuState.isOn) {
					LoadSounds.CLICK.play();
					CanvasMenu.state.setState(StateManager.STATES.EXTRAS);
					MenuState.isOn = false;
					ExtrasState.isOn = true;
				}
				if (mouseY >= 535 && mouseY <= 585 && MenuState.isOn) { // Exit button
					LoadSounds.CLICK.play();
					System.exit(0);
				}
			} else {
				LoadSounds.NEGATIVE.play();
			}
		}

		if (ControlsState.isOn) {
			if (mouseX >= 430 && mouseX <= 770) {
				if (mouseY >= 150 && mouseY <= 200) {
					LoadSounds.CLICK.play();
					ControlsState.isOn = false;
					MenuState.isOn = true;
					CanvasMenu.state.setState(StateManager.STATES.MENU);
				}
			} else {
				LoadSounds.NEGATIVE.play();
			}
		}

		if (SettingsState.isOn) {
			if (mouseX >= 430 && mouseX <= 770) {
				if (mouseY >= 150 && mouseY <= 200) {
					LoadSounds.CLICK.play();
					SettingsState.isOn = false;
					MenuState.isOn = true;
					CanvasMenu.state.setState(StateManager.STATES.MENU);
				} else {
					if ((mouseX < 800 || mouseX > 790 + Constants.LOAD_ASSETS.manual.getWidth(null))
							&& !(mouseY >= 150 && mouseY <= 200)) {
						LoadSounds.NEGATIVE.play();
					}
				}
			}
		}

		if (SettingsState.isOn && (mouseX >= 810 && mouseX <= 660 + Constants.LOAD_ASSETS.manual.getWidth(null))) {
			if (mouseY >= 300 && mouseY <= 150 + Constants.LOAD_ASSETS.manual.getHeight(null)) {
				LoadSounds.CLICK.play();
				SettingsState.isOn = false;
				ManualState.isOn = true;
				CanvasMenu.state.setState(StateManager.STATES.MANUAL);
			} else {
				LoadSounds.NEGATIVE.play();
			}
		}

		if (ManualState.isOn) {
			if (mouseX >= 430 && mouseX <= 770) {
				if (mouseY >= 150 && mouseY <= 200) {
					LoadSounds.CLICK.play();
					ManualState.isOn = false;
					SettingsState.isOn = true;
					CanvasMenu.state.setState(StateManager.STATES.SETTINGS);
				} else {
					LoadSounds.NEGATIVE.play();
				}
			}
		}

		if (ExtrasState.isOn) {
			if (mouseX >= 430 && mouseX <= 770) {
				if (mouseY >= 150 && mouseY <= 200) {
					LoadSounds.CLICK.play();
					MenuState.isOn = false;
					DisplayCanvas.frame.remove(DisplayCanvas.canvas);
					DisplayCanvas.frame.dispose();
					Launcher.main(null);
					MenuState.isOn = true;
				}
				if (mouseY >= 250 && mouseY <= 300) {
					LoadSounds.CLICK.play();
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
					LoadSounds.CLICK.play();
					try {
						Desktop.getDesktop().browse(new URI("http://me4gaming.com/index.php/en/gamedev/6-articles"));
					} catch (final IOException e1) {
						e1.printStackTrace();
					} catch (final URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
				if (mouseY >= 450 && mouseY <= 500) {
					LoadSounds.CLICK.play();
					ExtrasState.isOn = false;
					MenuState.isOn = true;
					CanvasMenu.state.setState(StateManager.STATES.MENU);
				}
			} else {
				if (!(mouseY >= 150 && mouseY <= 200) || !(mouseY >= 250 && mouseY <= 300)
						|| !(mouseY >= 350 && mouseY <= 400) || !(mouseY >= 450 && mouseY <= 500)) {
					LoadSounds.NEGATIVE.play();
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
		if ((CanvasMenu.state.getState() == StateManager.STATES.MENU
				|| CanvasMenu.state.getState() == StateManager.STATES.CONTROLS
				|| CanvasMenu.state.getState() == StateManager.STATES.SETTINGS
				|| CanvasMenu.state.getState() == StateManager.STATES.MANUAL
				|| CanvasMenu.state.getState() == StateManager.STATES.EXTRAS)
				&& (point.getX() >= 1100 && point.getX() <= 1100 /* + Constants.LOAD_ASSETS.volume.getWidth(null) */)) {
			if ((point.getY() >= 32 && point.getY() <= 32 /* + Constants.LOAD_ASSETS.volume.getHeight(null) */)) {
				LoadSounds.CLICK.play();
			}
		} else {
			if (!(point.getY() >= 115 && point.getY() <= 165) && !(point.getY() >= 215 && point.getY() <= 265)
					&& !(point.getY() >= 315 && point.getY() <= 365) && !(point.getY() >= 415 && point.getY() <= 465)
					&& !(point.getY() >= 515 && point.getY() <= 565)
					&& !(SettingsState.isOn && point.getY() >= 280
							&& point.getY() <= 280 /* + Constants.LOAD_ASSETS.myShip.getHeight(null) */)
					&& !(SettingsState.isOn && point.getY() >= 380
							&& point.getY() <= 380 /* + Constants.LOAD_ASSETS.evilHead.getHeight(null) */)
					&& !(ManualState.isOn && point.getY() >= 475
							&& point.getY() <= 475 /* + Constants.LOAD_ASSETS.manual.getHeight(null) */));
		}
	}
}
