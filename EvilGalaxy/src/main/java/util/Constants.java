package util;

import java.awt.Dimension;
import java.awt.Toolkit;

import main.Main;

public class Constants {
	
	public static final CanvasAssets LOAD_ASSETS = new CanvasAssets(0, 0);
	public static final String VOICENAME = "kevin16";
	public final static int MYSHIP_X = 40;
	public final static int MYSHIP_Y = 180;
	public final static int MYCROSSHAIR_X = 250;
	public final static int MYCROSSHAIR_Y = 165;
	public final static int EVILHEAD_X = 640;
	public final static int EVILHEAD_Y = 180;
	public final static int VOLBUT_X = (int) getCoordinates().getWidth() - 300;
	public final static int VOLBUT_Y = 10;
	public final static int BUNKER_X = ((int) getCoordinates().getWidth() - 400) / 2;
	public final static int BUNKER_Y = (int) getCoordinates().getHeight() - 260;
	public final static int B_WIDTH = 1310;
	public final static int B_HEIGHT = 1040;
	
	public static Dimension getCoordinates() {
		return Main.dim = Toolkit.getDefaultToolkit().getScreenSize();
	}
}
