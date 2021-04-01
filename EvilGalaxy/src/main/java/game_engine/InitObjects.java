package game_engine;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import entities.Alien;
import entities.AsteroidsAnimation;
import entities.AstronautAnimation;
import entities.Bunker;
import entities.Crosshair;
import entities.Dragon;
import entities.TheEndAnimation;
import icons.AlienIcon;
import icons.DifficultyIcon;
import icons.DragonIcon;
import icons.GoldIcon;
import icons.LaserIcon;
import icons.RocketIcon;
import entities.EvilHead;
import entities.PlayerShip;
import entities.SatelliteAnimation;
import items.Explosion;
import items.Gold;
import items.HealthPack;
import items.SaveSign;
import items.VolBtn;
import util.Constants;
import util.LoadSounds;
import util.TextToSpeech;

public class InitObjects extends JPanel implements ActionListener, Runnable {

	private final Thread animator = new Thread(this);

	public static List<Explosion> explosions;

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

		setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
		
		explosions = new ArrayList<Explosion>();
		
		// Icons
		AlienIcon.alienIcon = new AlienIcon(0, 0);
		LaserIcon.laserIcon = new LaserIcon(0, 0);
		RocketIcon.rocketIcon = new RocketIcon(0, 0);
		DifficultyIcon.difficultyIcon = new DifficultyIcon(0, 0);
		DragonIcon.dragonIcon = new DragonIcon(0, 0);
		GoldIcon.goldIcon = new GoldIcon(0, 0);
		
		// Animations
		AstronautAnimation.astronautAnim = new AstronautAnimation(0, 0);
		AstronautAnimation.astronautAnim.drawAstronaut();
		AstronautAnimation.astronautAnim.setVisible(true);

		SatelliteAnimation.starAnim = new SatelliteAnimation(0, 0);
		SatelliteAnimation.starAnim.drawSatellite();
		SatelliteAnimation.starAnim.setVisible(true);

		AsteroidsAnimation.asteroidsAnimations.add(new AsteroidsAnimation(0, 0));
		AsteroidsAnimation.asteroidsAnimations.add(new AsteroidsAnimation(0, 200));
		AsteroidsAnimation.asteroidsAnimations.add(new AsteroidsAnimation(300, 300));
		AsteroidsAnimation.asteroidsAnimations.add(new AsteroidsAnimation(600, 500));
		AsteroidsAnimation.asteroidsAnimations.add(new AsteroidsAnimation(800, 320));

		for (final AsteroidsAnimation asteroidsAnim : AsteroidsAnimation.asteroidsAnimations) {
			asteroidsAnim.drawAsteroids();
			asteroidsAnim.setVisible(true);
		}

		TheEndAnimation.theEndAnimationsUp.add(new TheEndAnimation(100, Constants.B_HEIGHT));
		TheEndAnimation.theEndAnimationsDown.add(new TheEndAnimation(300, 0));
		TheEndAnimation.theEndAnimationsUp.add(new TheEndAnimation(500, Constants.B_HEIGHT));
		TheEndAnimation.theEndAnimationsDown.add(new TheEndAnimation(700, 0));
		TheEndAnimation.theEndAnimationsUp.add(new TheEndAnimation(900, Constants.B_HEIGHT));

		for (final TheEndAnimation elonAnim : TheEndAnimation.theEndAnimationsUp) {
			elonAnim.drawTheEndUp();
			elonAnim.setVisible(true);
		}

		for (final TheEndAnimation elonAnim : TheEndAnimation.theEndAnimationsDown) {
			elonAnim.drawTheEndDown();
			elonAnim.setVisible(true);
		}

		PlayerShip.playerOne = new PlayerShip(Constants.MYSHIP_X, Constants.MYSHIP_Y);
		PlayerShip.playerOne.setVisible(true);

		Crosshair.crosshair = new Crosshair(Constants.MYCROSSHAIR_X, Constants.MYCROSSHAIR_Y);
		Crosshair.crosshair.setVisible(true);

		EvilHead.evilHead = new EvilHead(Constants.EVILHEAD_X, Constants.EVILHEAD_Y);
		EvilHead.evilHead.AIOnEasy();
		EvilHead.evilHead.setVisible(true);

		VolBtn.volButt = new VolBtn(Constants.VOLBUT_X, Constants.VOLBUT_Y);
		VolBtn.volButt.setVisible(true);

		Bunker.bunkerObj = new Bunker(Constants.BUNKER_X, Constants.BUNKER_Y);
		Bunker.bunkerObj.setVisible(true);

		SaveSign.saveSign = new SaveSign(Constants.BUNKER_X + 30, (int) Constants.getCoordinates().getHeight() - 700);
		SaveSign.saveSign.setVisible(false);

		initAliens();
		initGold();
		initDragons();
		initHealth();

//		GameMenuBar.autosave.setSelected(false);
		TextToSpeech.voiceInterruptor = true;

		timerEasy = new Timer(DELAY, this);
		timerMedium = new Timer(DELAY, this);
		timerHard = new Timer(DELAY, this);
		timerEasy.start();
		LoadSounds.HIGHSC.play();
		LoadSounds.BG_MUSIC.loop();
	}

	public static List<Alien> initAliens() {
		Alien.aliens = new ArrayList<Alien>();
		IntStream.range(0, 40)
				.mapToObj(i -> new Alien((int) Math.ceil(Math.random() * 7000), (int) Math.ceil(Math.random() * 800)))
				.forEach(alien -> {
					Alien.aliens.add(alien);
				});

		return Alien.aliens;
	}

	public static List<Dragon> initDragons() {
		Dragon.dragons = new ArrayList<Dragon>();
		IntStream.range(0, 30).mapToObj(i -> new Dragon(posDragon[i][0], posDragon[i][1]))
				.forEach(dragon -> {
					Dragon.dragons.add(dragon);
					dragon.setVisible(false);
				});
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
		final List<String> enemyNames = new ArrayList<String>();
		enemyNames.add(Alien.class.getName());
		enemyNames.add(Dragon.class.getName());
		return enemyNames;
	}

	@Override
	public void run() {

		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (true) {

			if (SatelliteAnimation.starAnim != null) {
				SatelliteAnimation.starAnim.cycle();
			}

			if (AstronautAnimation.astronautAnim != null) {
				AstronautAnimation.astronautAnim.cycle();
			}

			for (final AsteroidsAnimation asteroidsAnim : AsteroidsAnimation.asteroidsAnimations) {
				asteroidsAnim.cycle();
			}

			for (final TheEndAnimation elonsAnim : TheEndAnimation.theEndAnimationsUp) {
				elonsAnim.cycleUp();
			}

			for (final TheEndAnimation elonsAnim : TheEndAnimation.theEndAnimationsDown) {
				elonsAnim.cycleDown();
			}

			repaint();

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			if (sleep < 0) {
				sleep = 2;
			}

			try {
				Thread.sleep(sleep);
			} catch (final InterruptedException e) {

				final String msg = String.format("Thread interrupted: %s", e.getMessage());

				JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
			}

			beforeTime = System.currentTimeMillis();
		}
	}

	@Override
	public void addNotify() {
		super.addNotify();

		animator.start();
	}

	@Override
	public void removeNotify() {
		super.removeNotify();

		animator.interrupt();
	}
}