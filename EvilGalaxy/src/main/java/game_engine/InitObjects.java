package game_engine;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

import javax.swing.JPanel;
import javax.swing.Timer;

import animation.AsteroidsAnimation;
import animation.AstronautAnimation;
import animation.SatelliteAnimation;
import animation.TheEndAnimationDown;
import animation.TheEndAnimationUp;
import enemy.Alien;
import enemy.Bunker;
import enemy.Crosshair;
import enemy.Dragon;
import enemy.EvilHead;
import enemy.PlayerShip;
import icons.AlienIcon;
import icons.DifficultyIcon;
import icons.DragonIcon;
import icons.GoldIcon;
import icons.LaserIcon;
import icons.LocationIcon;
import icons.RocketIcon;
import items.Angel;
import items.BunkerBullet;
import items.Explosion;
import items.Gold;
import items.HealthPack;
import items.SaveSign;
import items.VolBtn;
import menu_engine.CanvasMenu;
import util.Constants;
import util.Flags;
import util.LoadSounds;
import util.LocationJsonExtractor;
import util.TextToSpeech;

public abstract class InitObjects extends JPanel implements ActionListener, Runnable {

	protected static final String[] CITY_AND_COUNTRY = getCityAndCountry();
	
	private static boolean isIngame;
	private static Timer timerEasy, timerMedium, timerHard;
	private static Thread animator;
	private static final long serialVersionUID = 4553098009656808450L;
	private static final int DELAY = 15;

	/*
	 * private final static int[][] posGold = { { 700, 1029 }, { 690, 1180 }, { 730,
	 * 60 }, { 510, 1839 }, { 820, 1600 }, { 680, 1359 }, { 760, 1150 }, { 640, 90
	 * }, { 630, 1420 }, { 760, 1520 }, { 655, 1228 }, { 700, 1130 } };
	 */

	private final static int[][] posDragon = { { 1380, 350 }, { 1580, 270 }, { 1680, 139 }, { 1390, 350 },
			{ 1560, 480 }, { 1290, 490 }, { 1100, 359 }, { 1460, 390 }, { 1540, 150 }, { 1410, 220 }, { 1560, 150 },
			{ 1640, 280 }, { 1420, 190 }, { 1390, 490 }, { 1700, 470 }, { 1380, 450 }, { 1580, 270 }, { 1680, 339 },
			{ 1690, 350 }, { 1760, 180 }, { 1290, 290 }, { 1400, 259 }, { 1460, 390 }, { 1540, 450 }, { 1410, 320 },
			{ 1360, 350 }, { 1540, 280 }, { 1420, 150 }, { 1790, 290 }, { 1300, 370 } };

	/*
	 * private final static int[][] posHealthPack = { { 540, 869 }, { 709, 1060 }, {
	 * 650, 240 }, { 600, 500 }, { 500, 600 } };
	 */

	public InitObjects() {
		initBoard();
//		new XboxControls().initControls();
	}

	private void initBoard() {

		addKeyListener(new Controls());
		setFocusable(true);
		setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));

		// Constants
		setIngame(true);
		Flags.isEPressed = true;
		
		// Collections
		Explosion.explosions = new ArrayList<Explosion>();
		Angel.angels = new ArrayList<Angel>();
		
		// Icons
		AlienIcon.alienIcon = new AlienIcon(0, 0);
		LaserIcon.laserIcon = new LaserIcon(0, 0);
		RocketIcon.rocketIcon = new RocketIcon(0, 0);
		DifficultyIcon.difficultyIcon = new DifficultyIcon(0, 0);
		LocationIcon.locationIcon = new LocationIcon(0, 0);
		DragonIcon.dragonIcon = new DragonIcon(0, 0);
		GoldIcon.goldIcon = new GoldIcon(0, 0);

		initAnimations();
		initEntities();

		BunkerBullet.bulletsLeft = Bunker.bunkerObj.getBulletsLeft();
		BunkerBullet.bulletsRight = Bunker.bunkerObj.getBulletsRight();

		SaveSign.saveSign = new SaveSign(Constants.BUNKER_X + 30, (int) Constants.DIMENSION.getHeight() - 700);
		SaveSign.saveSign.setVisible(false);

		initAliens();
		initGold();
		initDragons();
		initHealth();

		TextToSpeech.voiceInterruptor = true;

		setAnimator(new Thread(this));
		setTimerEasy(new Timer(DELAY, this));
		setTimerMedium(new Timer(DELAY, this));
		setTimerHard(new Timer(DELAY, this));
		getTimerEasy().start();
		LoadSounds.HIGHSC.play();
		LoadSounds.BG_MUSIC.loop();
	}

	public static void initEntities() {
		PlayerShip.playerShip = new PlayerShip(Constants.MYSHIP_X, Constants.MYSHIP_Y);
		PlayerShip.playerShip.setVisible(true);

		Crosshair.crosshair = new Crosshair(Constants.MYCROSSHAIR_X, Constants.MYCROSSHAIR_Y);
		Crosshair.crosshair.setVisible(true);

		EvilHead.evilHead = new EvilHead(Constants.EVILHEAD_X, Constants.EVILHEAD_Y);
		EvilHead.evilHead.AIOnEasy();
		EvilHead.evilHead.setVisible(true);

		VolBtn.volButt = new VolBtn(Constants.VOLBUT_X, Constants.VOLBUT_Y);
		VolBtn.volButt.setVisible(true);

		Bunker.bunkerObj = new Bunker(Constants.BUNKER_X, Constants.BUNKER_Y);
		Bunker.bunkerObj.setVisible(true);
	}

	public static void initAnimations() {
		
		Random rand = new Random();

		// Animations
		if (AstronautAnimation.astronautAnim == null) {
			AstronautAnimation.astronautAnim = new AstronautAnimation(0, 0);
			AstronautAnimation.astronautAnim.setVisible(true);
		}

		if (SatelliteAnimation.starAnim == null) {
			SatelliteAnimation.starAnim = new SatelliteAnimation(0, 0);
			SatelliteAnimation.starAnim.setVisible(true);
		}

		while (AsteroidsAnimation.asteroidsAnimations.size() < 5) {
			final AsteroidsAnimation asteroidsAnim = new AsteroidsAnimation(rand.nextInt(Constants.B_WIDTH), 
					rand.nextInt(Constants.B_HEIGHT));
			AsteroidsAnimation.asteroidsAnimations.add(asteroidsAnim);
			asteroidsAnim.setVisible(true);
		}
		
		while (TheEndAnimationDown.theEndAnimationDownList.size() < 2) {
			TheEndAnimationDown elonAnim = new TheEndAnimationDown(400, Constants.B_HEIGHT);
			TheEndAnimationDown.theEndAnimationDownList.add(elonAnim);
			elonAnim.drawTheEndDown();
			elonAnim.setVisible(true);
		}

		while (TheEndAnimationUp.theEndAnimationUpList.size() < 3) {
			TheEndAnimationUp elonAnim = new TheEndAnimationUp(800, Constants.B_HEIGHT);
			TheEndAnimationUp.theEndAnimationUpList.add(elonAnim);
			elonAnim.drawTheEndUp();
			elonAnim.setVisible(true);
		}
	}

	public static void initAliens() {
		Alien.aliens = new ArrayList<Alien>();
		IntStream.range(0, 40)
				.mapToObj(i -> new Alien((int) Math.ceil(Math.random() * 13000), (int) Math.ceil(Math.random() * 800)))
				.forEach(alien -> {
					Alien.aliens.add(alien);
				});
	}

	public static void initDragons() {
		Dragon.dragons = new ArrayList<Dragon>();
		IntStream.range(0, 30).mapToObj(i -> new Dragon(posDragon[i][0], posDragon[i][1])).forEach(dragon -> {
			Dragon.dragons.add(dragon);
			dragon.setVisible(false);
		});
	}

	public static void initGold() {
		Gold.goldstack = new ArrayList<>();

		for (int i = 0; i < UpdateObjects.getGoldCount(); i++) {
			Gold.goldstack.add(new Gold((int) Math.ceil(Math.random() * 1200), (int) Math.floor(Math.random() * 1200)));
		}
	}

	public static void initHealth() {
		HealthPack.healthpacks = new ArrayList<>(5);
	}
	
	public static void initCanvas() {
		final CanvasMenu engine = new CanvasMenu();
		engine.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public static List<String> getEnemyNames() {
		return Arrays.asList(Alien.class.getName(), Dragon.class.getName());
	}

	@Override
	public void run() {

		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();
		
		while (isIngame() || getAnimator() != null) {

			if (SatelliteAnimation.starAnim != null) {
				SatelliteAnimation.starAnim.cycle();
			}

			if (AstronautAnimation.astronautAnim != null) {
				AstronautAnimation.astronautAnim.cycle();
			}

			if (AsteroidsAnimation.asteroidsAnimations != null && !AsteroidsAnimation.asteroidsAnimations.isEmpty()) {				
				for (final AsteroidsAnimation asteroidsAnim : AsteroidsAnimation.asteroidsAnimations) {
					asteroidsAnim.cycle();
				}
			}

			if (TheEndAnimationUp.theEndAnimationUpList != null && !TheEndAnimationUp.theEndAnimationUpList.isEmpty()) {
				for (final TheEndAnimationUp endAnimationUp : TheEndAnimationUp.theEndAnimationUpList) {
					endAnimationUp.cycle();
				}	
			}

			if (TheEndAnimationDown.theEndAnimationDownList != null && !TheEndAnimationDown.theEndAnimationDownList.isEmpty()) {
				for (final TheEndAnimationDown endAnimationDown : TheEndAnimationDown.theEndAnimationDownList) {
					endAnimationDown.cycle();
				}
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
//				final String msg = String.format("Thread interrupted: %s", e.getMessage());
//				JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
				Thread.currentThread().interrupt();
			}

			beforeTime = System.currentTimeMillis();
		}
	}

	@Override
	public void addNotify() {
		super.addNotify();

		if (getAnimator() != null) {			
			getAnimator().start();
		}
	}

	@Override
	public void removeNotify() {
		super.removeNotify();

		if (getAnimator() != null) {
			getAnimator().interrupt();	
		}
	}

	public static void setAnimator(Thread animator) {
		InitObjects.animator = animator;
	}

	protected static Thread getAnimator() {
		return animator;
	}

	
	private static String[] getCityAndCountry() {
		return LocationJsonExtractor.extractCityAndCountry();
	}

	public static Timer getTimerMedium() {
		return timerMedium;
	}

	public static void setTimerMedium(Timer timerMedium) {
		InitObjects.timerMedium = Objects.requireNonNull(timerMedium);
	}

	public static Timer getTimerHard() {
		return timerHard;
	}

	public static void setTimerHard(Timer timerHard) {
		InitObjects.timerHard = Objects.requireNonNull(timerHard);
	}

	public static Timer getTimerEasy() {
		return timerEasy;
	}

	public static void setTimerEasy(Timer timerEasy) {
		InitObjects.timerEasy = Objects.requireNonNull(timerEasy);
	}

	public static boolean isIngame() {
		return isIngame;
	}

	public static void setIngame(boolean isIngame) {
		InitObjects.isIngame = isIngame;
	}

}