package menu_engine;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import frames.Main;
import game_engine.InitObjects;
import game_engine.LoadGame;
import game_engine.UpdateObjects;
import menu_states.ControlsState;
import menu_states.ManualState;
import menu_states.MenuState;
import menu_states.SettingsState;
import menu_states.StateManager;
import sound_engine.LoadSounds;
import sound_engine.PlayWave1st;
import sound_engine.SoundEffects;

public class MouseInputHandler implements MouseListener {

	public static Main main;
	private String soundName = SoundEffects.CLICK.getSound();
	private String forbidden = SoundEffects.FORBIDDEN.getSound();

	@Override
	public void mousePressed(MouseEvent e) {

		int mouseX = e.getX();
		int mouseY = e.getY();

		if (MenuState.isOn) {
			if (mouseX >= 430 && mouseX <= 770) {
				if (mouseY >= 150 && mouseY <= 200) {
					LoadSounds.menuMusic.stop();
					new PlayWave1st(soundName).start();
					MenuState.isOn = false;
					UpdateObjects.lifeMyShip = 3;
					UpdateObjects.lifeEvilHead = 3;
					UpdateObjects.lifeBunker = 3;
					InitObjects.ingame = true;
					if (main == null) {
						Display.frame.remove(Display.canvas);
						Display.frame.dispose();
						EventQueue.invokeLater(() -> {
							main = new Main();
							main.setVisible(true);
						});
					}
					MenuState.isOn = true;
				} if (mouseY >= 250 && mouseY <= 300) {
					new PlayWave1st(soundName).start();
					new LoadGame().openFileChooser();
				} if (mouseY >= 350 && mouseY <= 400 && MenuState.isOn) {
					new PlayWave1st(soundName).start();
					CanvasMenu.State.setState(StateManager.STATES.CONTROLS);
					MenuState.isOn = false;
					ControlsState.isOn = true;
				} if (mouseY >= 450 && mouseY <= 500 && MenuState.isOn) {
					new PlayWave1st(soundName).start();
					CanvasMenu.State.setState(StateManager.STATES.SETTINGS);
					MenuState.isOn = false;
					SettingsState.isOn = true;
				} if (mouseY >= 550 && mouseY <= 600 && MenuState.isOn) {
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
				if((mouseX < 800 || mouseX > 800 + Constants.LOAD_ASSETS.myShip.getWidth(null)
				|| mouseX > 800 + Constants.LOAD_ASSETS.evilHead.getWidth(null) 
				|| mouseX > 790 + Constants.LOAD_ASSETS.manual.getWidth(null)) && 
						!(mouseY >= 150 && mouseY <= 200)) {
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
		Point point = e.getPoint();
		if ((CanvasMenu.State.getState() == StateManager.STATES.SETTINGS)
				&& (point.getX() >= 800 && point.getX() <= 800 + Constants.LOAD_ASSETS.myShip.getWidth(null))) {
			if (point.getY() >= 255 && point.getY() <= 255 + Constants.LOAD_ASSETS.myShip.getHeight(null)) {
				new PlayWave1st(soundName).start();
				CanvasMenu.color.nextColor(CanvasMenu.color.getColor());
			}
		}
		if ((CanvasMenu.State.getState() == StateManager.STATES.SETTINGS)
				&& (point.getX() >= 800 && point.getX() <= 800 + Constants.LOAD_ASSETS.evilHead.getWidth(null))) {
			if (point.getY() >= 355 && point.getY() <= 355 + Constants.LOAD_ASSETS.evilHead.getHeight(null)) {
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
		if ((CanvasMenu.State.getState() == StateManager.STATES.MENU || 
				CanvasMenu.State.getState() == StateManager.STATES.CONTROLS || 
				CanvasMenu.State.getState() == StateManager.STATES.SETTINGS || 
				CanvasMenu.State.getState() == StateManager.STATES.MANUAL)
				&& (point.getX() >= 1100 && point.getX() <= 1100 + Constants.LOAD_ASSETS.volume.getWidth(null))) {
			if ((point.getY() >= 32 && point.getY() <= 32 + Constants.LOAD_ASSETS.volume.getHeight(null))) {
				new PlayWave1st(soundName).start();
				LoadSounds.menuMusic.stop();
			}
		} else {
			if(   	   !(point.getY() >= 150 && point.getY() <= 200)
					&& !(point.getY() >= 250 && point.getY() <= 300)
					&& !(point.getY() >= 350 && point.getY() <= 400)
					&& !(point.getY() >= 450 && point.getY() <= 500)
					&& !(SettingsState.isOn && point.getY() >= 255 && point.getY() <= 255 + Constants.LOAD_ASSETS.myShip.getHeight(null))
					&& !(SettingsState.isOn && point.getY() >= 355 && point.getY() <= 355 + Constants.LOAD_ASSETS.evilHead.getHeight(null))
					&& !(SettingsState.isOn && point.getY() >= 475 && point.getY() <= 475 + Constants.LOAD_ASSETS.manual.getHeight(null)))
			LoadSounds.menuMusic.loop();
		}
	}

}
