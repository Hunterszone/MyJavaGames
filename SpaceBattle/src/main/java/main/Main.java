package main;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import game_engine.LoadIcon;
import game_engine.Logic;

public class Main {
	
	static AppGameContainer app;

	public static void main(String[] args) throws SlickException {
//		System.loadLibrary("lwjgl");
		app = new AppGameContainer(new Logic());
		app.setDisplayMode(800, 800, false); // if TRUE, set the optimal
												// resolution for your PC !!
		app.setClearEachFrame(false);
		app.setMinimumLogicUpdateInterval(20);
		app.setShowFPS(false);
		Display.setIcon(LoadIcon.loadIcon("res/gameico.png", app));
		app.start();
	}
}