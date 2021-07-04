package game_engine;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import entities.Alien;
import entities.Dragon;
import entities.PlayerShip;
import items.Gold;
import util.ConsoleContent;
import util.LoadSounds;

public class XboxControls {

	private ControllerManager controllers;

	public XboxControls() {
		controllers = new ControllerManager();
		controllers.initSDLGamepad();
	}

	public void initControls() {

		while (true) {
			ControllerState currState = controllers.getState(0);

			if (!currState.isConnected || currState.b) {
				InitObjects.timerEasy.stop();
				InitObjects.timerMedium.stop();
				InitObjects.timerHard.stop();
				LoadSounds.BG_MUSIC.stop();
				LoadSounds.TAUNT.stop();
//				break;
			}
			if (!(InitObjects.timerEasy.isRunning() || InitObjects.timerMedium.isRunning()
					|| InitObjects.timerHard.isRunning())
					&& (currState.dpadLeft || currState.dpadRight || currState.dpadUp || currState.dpadDown)) {
				ConsoleContent.manualON = false;
				InitObjects.timerHard.stop();
				InitObjects.timerMedium.stop();
				InitObjects.timerEasy.start();
				LoadSounds.BG_MUSIC.loop();

				if (Alien.aliens.isEmpty()) {
					LoadSounds.TAUNT.loop();
				}
			}
			if (currState.dpadLeft || currState.leftStickClick) {
				PlayerShip.speedX = -7.5;
				if (PlayerShip.playerOne.isVisible()) {
					PlayerShip.playerOne.move();
				}
				System.out.println("\"dpadLeft\" on \"" + currState.controllerType + "\" is pressed");
			}
			if (currState.dpadRight || currState.rightStickClick) {
				PlayerShip.speedX = 7.5;
				if (PlayerShip.playerOne.isVisible()) {
					PlayerShip.playerOne.move();
				}
				System.out.println("\"dpadRight\" on \"" + currState.controllerType + "\" is pressed");
			}
			if (currState.dpadUp) {
				PlayerShip.speedY = -7.5;
				if (PlayerShip.playerOne.isVisible()) {
					PlayerShip.playerOne.move();
				}
				System.out.println("\"dpadUp\" on \"" + currState.controllerType + "\" is pressed");
			}
			if (currState.dpadDown) {
				PlayerShip.speedY = 7.5;
				if (PlayerShip.playerOne.isVisible()) {
					PlayerShip.playerOne.move();
				}
				System.out.println("\"dpadDown\" on \"" + currState.controllerType + "\" is pressed");
			}
			if (currState.a) {
				if (InitObjects.ingame == true
						&& (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
								|| InitObjects.timerHard.isRunning() == true)
						&& (Alien.aliens.size() > 0 || (UpdateObjects.lifeBunker >= 50 && Gold.goldstack.isEmpty()))) {
					PlayerShip.playerOne.loadMissiles();
				}
				if (InitObjects.ingame == true
						&& (InitObjects.timerEasy.isRunning() == true || InitObjects.timerMedium.isRunning() == true
								|| InitObjects.timerHard.isRunning() == true)
						&& (Alien.aliens.isEmpty() && (Dragon.dragons.size() > 0 || UpdateObjects.lifeBunker < 50
								|| Gold.goldstack.isEmpty()))) {
					PlayerShip.playerOne.loadRockets();
				}
				System.out.println("\"A\" on \"" + currState.controllerType + "\" is pressed");
			}
//			controllers.quitSDLGamepad();
		}
	}
}
