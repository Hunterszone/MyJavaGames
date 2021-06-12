package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import game_engine.Logic;
import resources.Images;
import utils.Constants;

public class Main {
	
	private static AppGameContainer app;

	public static void main(String[] args) throws SlickException {
//		System.loadLibrary("lwjgl64");
		app = new AppGameContainer(new Logic());
		app.setDisplayMode(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGTH, false); // if TRUE, set the optimal
		// resolution for your PC !!
		app.setClearEachFrame(false);
		app.setMinimumLogicUpdateInterval(20);
		app.setShowFPS(false);
		org.lwjgl.opengl.Display.setIcon(LoadIcon.loadIcon(Images.GAME_ICON.getImg(), app));
		app.start();
	}
}