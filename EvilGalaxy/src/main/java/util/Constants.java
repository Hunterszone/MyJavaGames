package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.Timer;

public class Constants {
	
	public static final CanvasAssets LOAD_ASSETS = new CanvasAssets(0, 0);
	public static final String VOICENAME = "kevin16";
	public static final Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static final int FONT_SIZE = 22;
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
	public static final int MIN_LIFE_PLAYER_SHIP = 0;
	public static final int MIN_LIFE_BUNKER = 0;
	public static final int MIN_LIFE_EVIL_HEAD = 0;
	public static final int MIN_ALIEN_KILLED = 0;
	public static final int MIN_DRAGON_KILLED = 0;

	public static final String HARD = ": HARD";
	public static final String MEDIUM = ": MEDI";
	public static final String EASY = ": EASY";
	public static final String UNLOCKED = ": Unlocked";
	public static final String LOCKED = ": Locked";
	public static final String COLON_WITH_SPACE = ": ";
	public static final String LEVEL = "Level: ";
	public static final String FONT_PAPYRUS = "Papyrus";
	public static final String COLLECT_ALL_THE_GOLD = "Collect all the gold!";
	public static final String DESTROY_THE_BUNKER = "Destroy the bunker!";
	public static final String KILL_THE_EVIL_HEAD = "Finally..Kill the evil head!";
	public static final String SHIP_OUTTA_CONTROL = "Dragons invasion brings the ship outta control...";
	public static final Color FONT_COLOR = Color.ORANGE;
	
	public static boolean isIngame, isOnPause;
	public static Timer timerEasy, timerMedium, timerHard;
	public static Thread animator;
	public static String[] CITY_AND_COUNTRY;
}
