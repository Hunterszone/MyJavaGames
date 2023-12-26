package game_engine;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import animation.AsteroidAnimation;
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
import util.ActionPerformer;
import util.Constants;
import util.Flags;
import util.LoadSounds;
import util.TextToSpeech;

public class InitObjects {

	private static Random rand;

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
	
	public InitObjects() throws NoSuchAlgorithmException {
		initBoard();
//		new XboxControls().initControls();
	}

	private void initBoard() throws NoSuchAlgorithmException {

		// Constants
		ActionPerformer.setIngame(true);
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

		LoadSounds.HIGHSC.play();
		LoadSounds.BG_MUSIC.loop();
	}

	public static void initEntities() {
		PlayerShip.playerShip = new PlayerShip(Constants.MYSHIP_X, Constants.MYSHIP_Y);
		PlayerShip.playerShip.setVisible(true);

		Crosshair.crosshair = new Crosshair(Constants.MYCROSSHAIR_X, Constants.MYCROSSHAIR_Y);
		Crosshair.crosshair.setVisible(true);

		EvilHead.evilHead = new EvilHead(Constants.EVILHEAD_X, Constants.EVILHEAD_Y);
		EvilHead.evilHead.setVisible(true);

		AIEngine.aiEngine = new AIEngine(Constants.EVILHEAD_X, Constants.EVILHEAD_Y);
		AIEngine.aiEngine.evilHeadOnEasy();

		VolBtn.volButt = new VolBtn(Constants.VOLBUT_X, Constants.VOLBUT_Y);
		VolBtn.volButt.setVisible(true);

		Bunker.bunkerObj = new Bunker(Constants.BUNKER_X, Constants.BUNKER_Y);
		Bunker.bunkerObj.setVisible(true);
	}

	public static void initAnimations() {
		
		// Animations
		while (AsteroidAnimation.ASTEROID_ANIMATION.size() < 5) {
			try {
				rand = SecureRandom.getInstanceStrong();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			final AsteroidAnimation asteroidsAnim = new AsteroidAnimation(rand.nextInt(Constants.B_WIDTH), 
					rand.nextInt(Constants.B_HEIGHT));
			AsteroidAnimation.ASTEROID_ANIMATION.add(asteroidsAnim);
			asteroidsAnim.setVisible(true);
		}
		
		while (TheEndAnimationDown.END_ANIMATION_DOWN.size() < 2) {
			TheEndAnimationDown elonAnim = new TheEndAnimationDown(400, Constants.B_HEIGHT);
			TheEndAnimationDown.END_ANIMATION_DOWN.add(elonAnim);
			elonAnim.drawTheEndDown();
			elonAnim.setVisible(true);
		}

		while (TheEndAnimationUp.END_ANIMATION_UP.size() < 3) {
			TheEndAnimationUp elonAnim = new TheEndAnimationUp(800, Constants.B_HEIGHT);
			TheEndAnimationUp.END_ANIMATION_UP.add(elonAnim);
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
		IntStream.range(0, 30)
			.mapToObj(i -> new Dragon(posDragon[i][0], posDragon[i][1]))
			.forEach(dragon -> {
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
		new CanvasMenu().start();
	}
	
	public static List<String> initEnemyNames() {
		return Arrays.asList(Alien.class.getName(), Dragon.class.getName());
	}

}