package main;

import game_engine.Images;
import game_engine.LoadIcon;
import game_engine.Logic;

/**
 *
 * This is a skeleton to init a game and run it.
 *
 * start() -> init() -> run() -> cleanup()
 *
 * Gameloop - run() Logic - logic() - Here we make all calculations and collect
 * the user input Render - render() - Here we draw every graphic object to the
 * screen
 *
 */
public class Game {

	/**
	 * Application init
	 *
	 * @param args Commandline args
	 */
	public static void main(String[] args) {
		Logic newGame = new Logic();
		org.lwjgl.opengl.Display.setIcon(LoadIcon.loadIcon(Images.GUNMAN.getImg(), newGame));
	}
}