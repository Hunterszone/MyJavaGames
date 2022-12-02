package util;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Constants {
	
	public static final CanvasAssets LOAD_ASSETS = new CanvasAssets(0, 0);
	public static final String VOICENAME = "kevin16";
	public static final Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static final int MYSHIP_X = 40;
	public static final int MYSHIP_Y = 180;
	public static final int MYCROSSHAIR_X = 250;
	public static final int MYCROSSHAIR_Y = 165;
	public static final int EVILHEAD_X = 640;
	public static final int EVILHEAD_Y = 180;
	public static final int VOLBUT_X = (int) DIMENSION.getWidth() - 300;
	public static final int VOLBUT_Y = 10;
	public static final int BUNKER_X = ((int) DIMENSION.getWidth() - 400) / 2;
	public static final int BUNKER_Y = (int) DIMENSION.getHeight() - 260;
	public static final int B_WIDTH = 1310;
	public static final int B_HEIGHT = 1040;
	public static final String HARD = ": HARD";
	public static final String MEDIUM = ": MEDI";
	public static final String EASY = ": EASY";
	public static final String UNLOCKED = ": Unlocked";
	public static final String LOCKED = ": Locked";
	public static final String COLON_WITH_SPACE = ": ";
	public static final String LEVEL = "Level: ";
}
