package game_engine;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import entities.Alien;
import entities.Bunker;
import entities.Crosshair;
import entities.Dragon;
import entities.EvilHead;
import entities.MyShip;
import entities.StarAnimation;
import frames.GameMenuBar;
import frames.Main;
import items.Gold;
import items.HealthPack;
import items.SaveSign;
import items.VolBtn;
import sound_engine.LoadSounds;

public class InitObjects extends JPanel implements ActionListener, Runnable {

	private Thread animator;

	public static Dimension getCoordinates() {
		return Main.dim = Toolkit.getDefaultToolkit().getScreenSize();
	}

	// Constants
	final static int MYSHIP_X = 40;
	final static int MYSHIP_Y = 180;
	final static int MYCROSSHAIR_X = 250;
	final static int MYCROSSHAIR_Y = 165;
	final static int EVILHEAD_X = 640;
	final static int EVILHEAD_Y = 180;
	final static int VOLBUT_X = (int) getCoordinates().getWidth() - 365;
	final static int VOLBUT_Y = (int) getCoordinates().getHeight() - 1050;
	final static int BUNKER_X = ((int) getCoordinates().getWidth() - 400) / 2;
	final static int BUNKER_Y = 688;
	final static int B_WIDTH = 1310;
	public final static int B_HEIGHT = 1040;

	public static boolean ingame;
	public static Timer timerEasy, timerMedium, timerHard;
	private static final long serialVersionUID = 1L;
	private final int DELAY = 15;

	/*
	 * private final static int[][] posGold = { { 700, 1029 }, { 690, 1180 }, { 730,
	 * 60 }, { 510, 1839 }, { 820, 1600 }, { 680, 1359 }, { 760, 1150 }, { 640, 90
	 * }, { 630, 1420 }, { 760, 1520 }, { 655, 1228 }, { 700, 1130 } };
	 */

	private final static int[][] posDragon = { { 1380, 550 }, { 1580, 370 }, { 1680, 239 }, { 1390, 450 },
			{ 1460, 580 }, { 1790, 590 }, { 1400, 359 }, { 1460, 290 }, { 1540, 250 }, { 1410, 220 }, { 1560, 250 },
			{ 1740, 280 }, { 1420, 290 }, { 1590, 690 }, { 1700, 470 }, { 1380, 650 }, { 1580, 270 }, { 1680, 439 },
			{ 1390, 350 }, { 1460, 280 }, { 1790, 490 }, { 1400, 259 }, { 1460, 690 }, { 1540, 450 }, { 1410, 420 },
			{ 1560, 350 }, { 1740, 280 }, { 1420, 250 }, { 1590, 290 }, { 1700, 470 } };

	/*
	 * private final static int[][] posHealthPack = { { 540, 869 }, { 709, 1060 }, {
	 * 650, 240 }, { 600, 500 }, { 500, 600 } };
	 */

	public InitObjects() {
		initBoard();
	}

	private void initBoard() {

		addKeyListener(new Controls());
		setFocusable(true);
		ingame = true;

		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		
		StarAnimation.starAnim = new StarAnimation(0, 0);
		StarAnimation.starAnim.drawStar();
		StarAnimation.starAnim.setVisible(true);

		MyShip.myShip = new MyShip(MYSHIP_X, MYSHIP_Y);
		MyShip.myShip.setVisible(true);

		Crosshair.crosshair = new Crosshair(MYCROSSHAIR_X, MYCROSSHAIR_Y);
		Crosshair.crosshair.setVisible(true);
		
		EvilHead.evilHead = new EvilHead(EVILHEAD_X, EVILHEAD_Y);
		EvilHead.evilHead.AIOnEasy();
		EvilHead.evilHead.setVisible(true);

		VolBtn.volButt = new VolBtn(VOLBUT_X, VOLBUT_Y);
		VolBtn.volButt.setVisible(true);

		Bunker.bunkerObj = new Bunker(BUNKER_X, BUNKER_Y);
		Bunker.bunkerObj.setVisible(true);

		SaveSign.saveSign = new SaveSign(BUNKER_X, (B_HEIGHT - 350) / 2);
		SaveSign.saveSign.setVisible(false);

		initAliens();
		initGold();
		initDragons();
		initHealth();
		HealthPack.healthpack.clear();

		GameMenuBar.autosave.setSelected(false);
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

		for (int i = 0; i < 12; i++) {
			Gold.goldstack.add(new Gold((int) Math.ceil(Math.random() * 1200), (int) Math.floor(Math.random() * 1200)));
		}

		return Gold.goldstack;
	}

	public static List<HealthPack> initHealth() {
		HealthPack.healthpack = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			HealthPack.healthpack
					.add(new HealthPack((int) Math.ceil(Math.random() * 1200), (int) Math.floor(Math.random() * 1200)));
		}
		return HealthPack.healthpack;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public static List<String> getEnemyNames() {
		List<String> enemyNames = new ArrayList<String>();
		enemyNames.add(Alien.class.getName());
		enemyNames.add(Dragon.class.getName());
		return enemyNames;
	}

	 @Override
	    public void run() {

	        long beforeTime, timeDiff, sleep;

	        beforeTime = System.currentTimeMillis();

	        while (true) {

	        	StarAnimation.starAnim.cycle();
	            repaint();

	            timeDiff = System.currentTimeMillis() - beforeTime;
	            sleep = DELAY - timeDiff;

	            if (sleep < 0) {
	                sleep = 2;
	            }

	            try {
	                Thread.sleep(sleep);
	            } catch (InterruptedException e) {
	                
	                String msg = String.format("Thread interrupted: %s", e.getMessage());
	                
	                JOptionPane.showMessageDialog(this, msg, "Error", 
	                    JOptionPane.ERROR_MESSAGE);
	            }

	            beforeTime = System.currentTimeMillis();
	        }
	    }

	@Override
	public void addNotify() {
		super.addNotify();

		animator = new Thread(this);
		animator.start();
	}
}