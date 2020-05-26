package menu_engine;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import frames.Main;
import game_engine.InitObjects;
import game_engine.UpdateObjects;
import menu_states.MenuState;
import menu_states.SettingsState;
import menu_states.StateManager;
import sound_engine.LoadSounds;

public class MouseInputHandler implements MouseListener{
	
	public static Main main;

	@Override
	public void mousePressed(MouseEvent e) {

		int mouseX = e.getX();
		int mouseY = e.getY();

		if(MenuState.isOn) {
			if(mouseX >= 430 && mouseX <= 770) {
				if(mouseY >= 150 && mouseY <= 200) {
					MenuState.isOn = false;
					UpdateObjects.lifeMyShip = 3;
					UpdateObjects.lifeEvilHead = 3;
					UpdateObjects.lifeBunker = 3;
					InitObjects.ingame = true;
					EventQueue.invokeLater(() -> {
							main = new Main();
							main.setVisible(true);
					});
					MenuState.isOn = true;
				} else if (mouseY >= 350 && mouseY <= 400 && MenuState.isOn) {
					MenuState.isOn = false;
					LoadSounds.bgMusic.stop();
					LoadSounds.fuse.stop();
					LoadSounds.roar.stop();
					InitObjects.ingame = false;
					main.dispose();
					MenuState.isOn = true;
				} else if (mouseY >= 250 && mouseY <= 300 && MenuState.isOn) {
					CanvasMenu.State.setState(StateManager.STATES.SETTINGS);
					MenuState.isOn = false;
					SettingsState.isOn = true;
				}
			}
		}
		if(SettingsState.isOn) {
			if(mouseX >= 430 && mouseX <= 770) {
				if (mouseY >= 150 && mouseY <= 200) {
					SettingsState.isOn = false;
					MenuState.isOn = true;
					CanvasMenu.State.setState(StateManager.STATES.MENU);
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {    }

	@Override
	public void mouseEntered(MouseEvent e) {	}

	@Override
	public void mouseExited(MouseEvent e) {    }

	@Override
	public void mouseClicked(MouseEvent e) {

	}

}
