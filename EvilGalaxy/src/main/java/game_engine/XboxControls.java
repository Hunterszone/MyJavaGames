package game_engine;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import enemy.Alien;
import enemy.Dragon;
import enemy.PlayerShip;
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
				InitObjects.getTimerEasy().stop();
				InitObjects.getTimerMedium().stop();
				InitObjects.getTimerHard().stop();
				LoadSounds.BG_MUSIC.stop();
				LoadSounds.TAUNT.stop();
//				break;
			}
			if (!(InitObjects.getTimerEasy().isRunning() || InitObjects.getTimerMedium().isRunning()
					|| InitObjects.getTimerHard().isRunning())
					&& (currState.dpadLeft || currState.dpadRight || currState.dpadUp || currState.dpadDown)) {
				ConsoleContent.manualON = false;
				InitObjects.getTimerHard().stop();
				InitObjects.getTimerMedium().stop();
				InitObjects.getTimerEasy().start();
				LoadSounds.BG_MUSIC.loop();

				if (Alien.aliens.isEmpty()) {
					LoadSounds.TAUNT.loop();
				}
			}
			if (currState.dpadLeft || currState.leftStickClick) {
				PlayerShip.speedX = -7.5;
				if (PlayerShip.playerShip.isVisible()) {
					PlayerShip.playerShip.move();
				}
				System.out.println("\"dpadLeft\" on \"" + currState.controllerType + "\" is pressed");
			}
			if (currState.dpadRight || currState.rightStickClick) {
				PlayerShip.speedX = 7.5;
				if (PlayerShip.playerShip.isVisible()) {
					PlayerShip.playerShip.move();
				}
				System.out.println("\"dpadRight\" on \"" + currState.controllerType + "\" is pressed");
			}
			if (currState.dpadUp) {
				PlayerShip.speedY = -7.5;
				if (PlayerShip.playerShip.isVisible()) {
					PlayerShip.playerShip.move();
				}
				System.out.println("\"dpadUp\" on \"" + currState.controllerType + "\" is pressed");
			}
			if (currState.dpadDown) {
				PlayerShip.speedY = 7.5;
				if (PlayerShip.playerShip.isVisible()) {
					PlayerShip.playerShip.move();
				}
				System.out.println("\"dpadDown\" on \"" + currState.controllerType + "\" is pressed");
			}
			if (currState.a) {
				if (InitObjects.isIngame() == true
						&& (InitObjects.getTimerEasy().isRunning() == true || InitObjects.getTimerMedium().isRunning() == true
								|| InitObjects.getTimerHard().isRunning() == true)
						&& (Alien.aliens.size() > 0 || (UpdateObjects.getLifeBunker() >= 50 && Gold.goldstack.isEmpty()))) {
					PlayerShip.playerShip.loadMissiles();
				}
				if (InitObjects.isIngame() == true
						&& (InitObjects.getTimerEasy().isRunning() == true || InitObjects.getTimerMedium().isRunning() == true
								|| InitObjects.getTimerHard().isRunning() == true)
						&& (Alien.aliens.isEmpty() && (Dragon.dragons.size() > 0 || UpdateObjects.getLifeBunker() < 50
								|| Gold.goldstack.isEmpty()))) {
					PlayerShip.playerShip.loadRockets();
				}
				System.out.println("\"A\" on \"" + currState.controllerType + "\" is pressed");
			}
//			controllers.quitSDLGamepad();
		}
	}
}
