package util;

import java.awt.Dimension;

import main.Main;

public class Constants {
	
	public static final CanvasAssets LOAD_ASSETS = new CanvasAssets(0, 0);
	public static final String VOICENAME = "kevin16";
	public static final int VOLBUT_X = (int) getCoordinates().getWidth() - 300;
	public static final int VOLBUT_Y = 10;
	public static final int B_WIDTH = 1310;
	public static final int B_HEIGHT = 1040;
	
	public static Dimension getCoordinates() {
		return Main.DIM;
	}
}
