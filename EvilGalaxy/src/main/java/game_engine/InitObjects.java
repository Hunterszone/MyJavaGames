package game_engine;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import entities.Alien;
import entities.Bunker;
import entities.Crosshair;
import entities.Dragon;
import entities.EvilHead;
import entities.MyShip;
import frames.ConsoleForm;
import frames.GameMenu;
import frames.Manual;
import items.Gold;
import items.HealthPack;
import items.SaveSign;
import items.VolBtn;
import sound_engine.LoadSounds;

public class InitObjects extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	public static boolean consoleON, manualON, ingame, god;
	public static ConsoleForm console;
	public static Manual manual;
	public static Timer timerEasy, timerMedium, timerHard;
	int MYSHIP_X = 40;
	int MYSHIP_Y = 180;
	int MYCROSSHAIR_X = 250;
	int MYCROSSHAIR_Y = 165;
	int EVILHEAD_X = 640;
	int EVILHEAD_Y = 180;
	final static int VOLBUT_X = 940;
	final static int VOLBUT_Y = 15;
	final static int BUNKER_X = 450;
	final static int BUNKER_Y = 680;
	final static int B_WIDTH = 1310;
	final static int B_HEIGHT = 1040;
	private final int DELAY = 15;

	private final static int[][] posGold = { { 500, 1029 }, { 290, 1180 }, { 330, 60 }, { 510, 1839 }, { 620, 1600 },
			{ 480, 1359 }, { 360, 1150 }, { 640, 90 }, { 430, 1420 }, { 560, 1520 }, { 455, 1228 }, { 600, 1130 } };

	private final static int[][] posDragon = { { 1380, 550 }, { 1580, 370 }, { 1680, 239 }, { 1390, 450 },
			{ 1460, 580 }, { 1790, 590 }, { 1400, 359 }, { 1460, 290 }, { 1540, 250 }, { 1410, 220 }, { 1560, 250 },
			{ 1740, 280 }, { 1420, 290 }, { 1590, 690 }, { 1700, 470 }, { 1380, 650 }, { 1580, 270 }, { 1680, 439 },
			{ 1390, 350 }, { 1460, 280 }, { 1790, 490 }, { 1400, 259 }, { 1460, 690 }, { 1540, 450 }, { 1410, 420 },
			{ 1560, 350 }, { 1740, 280 }, { 1420, 250 }, { 1590, 290 }, { 1700, 470 } };

	private final static int[][] posHealthPack = { { 540, 869 }, { 709, 1060 }, { 650, 240 }, { 600, 500 },
			{ 500, 600 } };

	public InitObjects() {

		initBoard();

	}

	private void initBoard() {

		addKeyListener(new Controls());
		setFocusable(true);
		ingame = true;

		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

		MyShip.myShip = new MyShip(MYSHIP_X, MYSHIP_Y);
		MyShip.myShip.isVisible();

		Crosshair.crosshair = new Crosshair(MYCROSSHAIR_X, MYCROSSHAIR_Y);
		Crosshair.crosshair.isVisible();

		EvilHead.evilHead = new EvilHead(EVILHEAD_X, EVILHEAD_Y);
		EvilHead.evilHead.isVisible();
		EvilHead.evilHead.AIOnEasy();

		VolBtn.volButt = new VolBtn(VOLBUT_X, VOLBUT_Y);
		VolBtn.volButt.isVisible();

		SaveSign.saveSign = new SaveSign((B_WIDTH - 350) / 2, (B_HEIGHT - 350) / 2);
		SaveSign.saveSign.setVisible(false);

		Bunker.bunkerObj = new Bunker(BUNKER_X, BUNKER_Y);
		Bunker.bunkerObj.isVisible();

		initAliens();
		initGold();
		initDragons();
		initHealth();

		GameMenu.autosave.setSelected(false);
		DrawScene.voiceInterruptor = true;

		timerEasy = new Timer(DELAY, this);
		timerMedium = new Timer(DELAY, this);
		timerHard = new Timer(DELAY, this);
		timerEasy.start();
		LoadSounds.gameWon.play();
		LoadSounds.bgMusic.loop();
	}

	public static List<Alien> initAliens() {
		Alien.aliens = new ArrayList<>();
		for (int i = 0; i < 40; i++) {
			Alien born = new Alien((int) Math.ceil(Math.random() * 7000), (int) Math.ceil(Math.random() * 800));
			Alien.aliens.add(born);
		}
		return Alien.aliens;
	}

	public static List<Dragon> initDragons() {
		Dragon.dragons = new ArrayList<>();
		for (int[] p : posDragon) {
			Dragon born = new Dragon(p[0], p[1]);
			Dragon.dragons.add(born);
			born.setVisible(false);
		}
		return Dragon.dragons;
	}

	public static List<Gold> initGold() {
		Gold.goldstack = new ArrayList<>();

		for (int[] p : posGold) {
			Gold.goldstack.add(new Gold(p[0], p[1]));
		}

		return Gold.goldstack;
	}

	public static List<HealthPack> initHealth() {
		HealthPack.healthpack = new ArrayList<>();

		for (int[] p : posHealthPack) {
			HealthPack.healthpack.add(new HealthPack(p[0], p[1]));
		}
		return HealthPack.healthpack;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	static List<String> getEnemyNames() {
		List<String> enemyNames = new ArrayList<String>();
		enemyNames.add(Alien.class.getName());
		enemyNames.add(Dragon.class.getName());
		return enemyNames;
	}
}