package game_engine;

import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import dbconn.HighScoreToDb;
import entities.Crosshair;
import entities.EnemyEntity;
import entities.Entity;
import entities.HealthEntity;
import entities.HeroEntity;
import entities.TreasureEntity;

public class Logic {

	/** Game title */
	public static final String GAME_TITLE = "GunMan";

	/** Screen size */
	private static final int SCREEN_SIZE_WIDTH = 1280;
	private static final int SCREEN_SIZE_HEIGHT = 800;
	private static final int MAX_LEVELS = 5;
	private static final int AVATAR_START_POS_X = 50;
	private static final int AVATAR_START_POS_Y = 50;
	private static final int TREASURES_ON_LEVEL = 5;
	private static final int MAX_LIVES = 3;
	private static final int MAX_ENEMIES = 5;
	private static final int MAX_JUMP = 70;
	private boolean isJumping = false;
	private static boolean isNotified = false;

	/** Desired frame time */
	private static final int FRAMERATE = 60;

	/** Exit the game */
	private boolean finished;

	private static int currentLevel = 0;
	private static int enemiesKilled = 0;
	private static int treasuresFound = 0;
	private static int lives = 3;

	private LevelTile levels[] = new LevelTile[MAX_LEVELS];
	private static HeroEntity hero;
	private static EnemyEntity enemy;
	private static HealthEntity healthpack;
	private static Crosshair crosshair;
	private static TreasureEntity treasure;
	private static HashMap<Integer, List<TreasureEntity>> treasures;
	private static HashMap<Integer, List<HealthEntity>> healthpacks;
	private static HashMap<Integer, List<EnemyEntity>> enemies;

	// HashMap objectType;
	private static List<HealthEntity> hpOnLevel;
	private static List<TreasureEntity> treasuresOnLevel;
	private static List<EnemyEntity> enemiesOnLevel;
	private Texture levelTexture;

	private TrueTypeFont font;
	private Font awtFont;
	private Sound footsteps;
	private Sound denied;
	private Sound shot;
	private Sound duckhit;
	private Sound bgmusic;
	private Sound quack;

	private static Sound healthy;
	private static Sound collect;
	private static Sound restart;

	public Logic() {
		try {
			init();
			run();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			Sys.alert(GAME_TITLE, "An error occured and the game will exit.");
		} finally {
			cleanup();
		}

		System.exit(0);
	}

	/**
	 * Initialise the game
	 *
	 * @throws Exception if init fails
	 */
	private void init() throws Exception {
		MySprite heroSprite;
		MySprite treasureSprite;
		MySprite mineSprite;
		MySprite crosshairSprite;
		MySprite enemyTexture;

		initGL(SCREEN_SIZE_WIDTH, SCREEN_SIZE_HEIGHT);

		try {
			// Levels
			for (int i = 0; i < MAX_LEVELS; i++) {
				levels[i] = new LevelTile(TextureLoader.getTexture("PNG",
						ResourceLoader.getResourceAsStream("res/images/tile_" + Integer.toString(i + 1) + ".png")));
			}
			// Crosshair
			crosshairSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/pointer.png")));
			initCrosshair(crosshairSprite);
			// Hero
			heroSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/gunman.png")));
			initHero(heroSprite);
			// Treasures
			treasures = new HashMap<Integer, List<TreasureEntity>>();
			treasureSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/chest.png")));
			initTreasures(treasureSprite);
			// Healthpacks
			healthpacks = new HashMap<Integer, List<HealthEntity>>();
			mineSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/health.png")));
			initHealth(mineSprite);
			// Enemies
			enemies = new HashMap<Integer, List<EnemyEntity>>();
			enemyTexture = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/bird.png")));
			initEnemies(enemyTexture);
			// Audio
			footsteps = new Sound("res/sounds/footsteps.wav");
			healthy = new Sound("res/sounds/magic.wav");
			denied = new Sound("res/sounds/denied.wav");
			shot = new Sound("res/sounds/explosion.wav");
			duckhit = new Sound("res/sounds/huh.wav");
			collect = new Sound("res/sounds/collect.wav");
			restart = new Sound("res/sounds/highsc.wav");
			quack = new Sound("res/sounds/quack.wav");
			bgmusic = new Sound("res/sounds/bgmusic.wav");
			bgmusic.loop();
			// Font
			awtFont = new Font("Times New Roman", Font.BOLD, 24);
			font = new TrueTypeFont(awtFont, false);
		} catch (IOException e) {
			e.printStackTrace();
			finished = true;
		}
	}

	public static Crosshair initCrosshair(MySprite sprite) {
		crosshair = new Crosshair(sprite, AVATAR_START_POS_X + 200, AVATAR_START_POS_Y);
		return crosshair;
	}

	public static HeroEntity initHero(MySprite sprite) {
		hero = new HeroEntity(sprite, AVATAR_START_POS_X, AVATAR_START_POS_Y);
		return hero;
	}

	@SuppressWarnings("unchecked")
	private static <T> List<T> initEntitiesHelper(MySprite sprite, Entity entity) {
		Random r = new Random();
		if (entity instanceof TreasureEntity) {
			treasuresOnLevel = new ArrayList<TreasureEntity>();
			for (int i = 0; i < TREASURES_ON_LEVEL; i++) {
				treasuresOnLevel.add(new TreasureEntity(sprite, r.nextInt(SCREEN_SIZE_WIDTH - sprite.getWidth()),
						r.nextInt(SCREEN_SIZE_HEIGHT - sprite.getHeight())));
			}
			return (List<T>) treasuresOnLevel;
		}
		if (entity instanceof HealthEntity) {
			hpOnLevel = new ArrayList<HealthEntity>();
			for (int i = 0; i < MAX_LIVES; i++) {
				hpOnLevel.add(new HealthEntity(sprite, r.nextInt(SCREEN_SIZE_WIDTH - sprite.getWidth()),
						r.nextInt(SCREEN_SIZE_HEIGHT - sprite.getHeight())));
			}
			return (List<T>) hpOnLevel;
		}
		if (entity instanceof EnemyEntity) {
			enemiesOnLevel = new ArrayList<EnemyEntity>();
			for (int i = 0; i < MAX_ENEMIES; i++) {
				enemiesOnLevel.add(new EnemyEntity(sprite, r.nextInt(SCREEN_SIZE_WIDTH - sprite.getWidth()),
						r.nextInt(SCREEN_SIZE_HEIGHT - sprite.getHeight())));
			}
			return (List<T>) enemiesOnLevel;
		}
		return new ArrayList<T>();
	}

	public static List<TreasureEntity> initTreasures(MySprite sprite) {
		for (int i = 0; i < MAX_LEVELS; i++) {
			if (initEntitiesHelper(sprite, new TreasureEntity()) != null
					&& !initEntitiesHelper(sprite, new TreasureEntity()).isEmpty()) {
				List<TreasureEntity> trsOnLevel = initEntitiesHelper(sprite, new TreasureEntity());
				Supplier<Stream<TreasureEntity>> treasuresStream = () -> Stream.of(trsOnLevel.get(0), trsOnLevel.get(1),
						trsOnLevel.get(2), trsOnLevel.get(3), trsOnLevel.get(4));
				treasuresOnLevel = treasuresStream.get().collect(Collectors.toList());
			}
			treasuresOnLevel.removeIf(t -> t == null);
			if (treasures != null) {
				treasures.put(i, treasuresOnLevel);
			}
		}
		return treasuresOnLevel;
	}

	public static List<HealthEntity> initHealth(MySprite sprite) {
		for (int i = 0; i < MAX_LEVELS; i++) {
			if (initEntitiesHelper(sprite, new HealthEntity()) != null
					&& !initEntitiesHelper(sprite, new HealthEntity()).isEmpty()) {
				List<HealthEntity> hpsOnLevel = initEntitiesHelper(sprite, new HealthEntity());
				Supplier<Stream<HealthEntity>> hpStream = () -> Stream.of(hpsOnLevel.get(0), hpsOnLevel.get(1),
						hpsOnLevel.get(2));
				hpOnLevel = hpStream.get().collect(Collectors.toList());
			}
			hpOnLevel.removeIf(h -> h == null);
			if (healthpacks != null)
				healthpacks.put(i, hpOnLevel);
		}
		return hpOnLevel;
	}

	public static List<EnemyEntity> initEnemies(MySprite sprite) {
		Supplier<Stream<EnemyEntity>> enemyStream = null;
		for (int i = 0; i < MAX_LEVELS; i++) {
			if (initEntitiesHelper(sprite, new EnemyEntity()) != null
					&& !initEntitiesHelper(sprite, new EnemyEntity()).isEmpty()) {
				List<EnemyEntity> enemiezOnLevel = initEntitiesHelper(sprite, new EnemyEntity());
				enemyStream = () -> Stream.of(enemiezOnLevel.get(0), enemiezOnLevel.get(1), enemiezOnLevel.get(2),
						enemiezOnLevel.get(3), enemiezOnLevel.get(4));
				enemiesOnLevel = enemyStream.get().collect(Collectors.toList());
			}
			enemiesOnLevel.removeIf(e -> e == null);
			if (enemies != null)
				enemies.put(i, enemiesOnLevel);
		}
		return enemiesOnLevel;
	}

	private void initGL(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(GAME_TITLE);
			Display.setFullscreen(false);
			Display.create();

			// Enable vsync if we can
			Display.setVSyncEnabled(true);

			// Start up the sound system
			// AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// enable alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glViewport(0, 0, width, height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	/**
	 * Runs the game (the "main loop")
	 */
	private void run() {
		while (!finished) {
			// Always call Window.update(), all the time
			Display.update();

			if (Display.isCloseRequested()) {
				// Check for O/S close requests
				finished = true;
			} else if (Display.isActive()) {
				// The window is in the foreground, so we should play the game
				logic();
				render();
				Display.sync(FRAMERATE);
			} else {
				// The window is not in the foreground, so we can allow other
				// stuff to run and
				// infrequently update
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				logic();
				if (Display.isVisible() || Display.isDirty()) {
					// Only bother rendering if the window is visible or dirty
					render();
				}
			}
		}
	}

	/**
	 * Do any game-specific cleanup
	 */
	private void cleanup() {
		// TODO: save anything you want to disk here

		// Stop the sound
		AL.destroy();

		// Close the window
		Display.destroy();
	}

	private void jump() {

		int posY = hero.getY();

		if (isJumping == false) {

			hero.setY(posY -= MAX_JUMP);
			crosshair.setY(posY -= MAX_JUMP);
			isJumping = true;
		}

		else {
			posY += MAX_JUMP;
			if (posY > posY + MAX_JUMP) {
				posY = posY + MAX_JUMP;
			}
			hero.setY(posY);
			crosshair.setY(posY);
			isJumping = false;
		}

	}

	/**
	 * Do all calculations, handle input, etc.
	 */
	private void logic() {

		int posY = hero.getY();
		int posX = hero.getX();

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			finished = true;

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			enemiesOnLevel = enemies.get(currentLevel);
			crosshairCheckForEnemies(enemy, crosshair, enemiesOnLevel);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A) && !bgmusic.playing())
			bgmusic.loop();

		if (Keyboard.isKeyDown(Keyboard.KEY_S))
			bgmusic.stop();

		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {

			jump();

			if (posX > SCREEN_SIZE_WIDTH) {
				if ((currentLevel + 1) < MAX_LEVELS && treasures.get(currentLevel).isEmpty()
						&& enemies.get(currentLevel).isEmpty()) {
					currentLevel++;
					healthy.play(1, 0.2f);
					hero.setX(0);
					hero.setY(700);
				} else {
					posX = 10;
					hero.setX(posX);
				}
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if ((posX + hero.getWidth() + 10) < SCREEN_SIZE_WIDTH) {
				hero.setX(posX += 10);
				crosshair.setY(posY);
				crosshair.setX(posX + 200);
			} else {
				denied.play(1, 0.2f);
				if (currentLevel + 1 < MAX_LEVELS && treasures.get(currentLevel).isEmpty()
						&& enemies.get(currentLevel).isEmpty()) {
					treasuresFound = 0;
					currentLevel++;
					hero.setX(0);
					hero.setY(700);
				}
			}
			footsteps();
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if ((posX - 10) > 0) {
				hero.setX(posX -= 10);
				crosshair.setY(posY);
				crosshair.setX(posX - 200);
			} else {
				int prevLev = currentLevel - 1;
				if ((prevLev) >= -1) {
					prevLev++;
					hero.setX(SCREEN_SIZE_WIDTH - hero.getWidth());
				}
			}
			footsteps();
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if ((posY - 10) > 0) {
				hero.setY(posY -= 10);
				crosshair.setX(posX);
				crosshair.setY(posY - 200);
			}
			footsteps();
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			if ((posY + hero.getHeight() + 10) < SCREEN_SIZE_HEIGHT) {
				hero.setY(posY += 10);
				crosshair.setX(posX);
				crosshair.setY(posY + 200);
			}
			footsteps();
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_R))
			gameRestart();

		// Collision
		treasuresOnLevel = treasures.get(currentLevel);
		checkForTreasures(treasure, hero, treasuresOnLevel);

		hpOnLevel = healthpacks.get(currentLevel);
		checkForHp(healthpack, hero, hpOnLevel);

		enemiesOnLevel = enemies.get(currentLevel);
		heroCheckForEnemies(enemy, hero, enemiesOnLevel);
	}

	private void footsteps() {
		if (!footsteps.playing())
			footsteps.play(1, 0.2f);
	}

	private void checkForHp(HealthEntity item, HeroEntity gunman, List<HealthEntity> hpOnLevel) {
		for (int jj = 0; jj < hpOnLevel.size(); jj++) {
			item = hpOnLevel.get(jj);
			if (gunman.collidesWith(item) && lives < MAX_LIVES) {
				gunman.removedByHero(item);
				lives++;
				healthy.play(1, 0.2f);
			}
		}
	}

	private void checkForTreasures(TreasureEntity item, HeroEntity gunman, List<TreasureEntity> treasuresOnLevel) {
		for (int jj = 0; jj < treasuresOnLevel.size(); jj++) {
			item = treasuresOnLevel.get(jj);
			if (gunman.collidesWith(item)) {
				gunman.removedByHero(item);
				treasuresFound++;
				collect.play(1, 0.2f);
			}
		}
	}

	private void crosshairCheckForEnemies(EnemyEntity enemy, Crosshair crosshair, List<EnemyEntity> enemiesOnLevel) {
		for (int jj = 0; jj < enemiesOnLevel.size(); jj++) {
			enemy = enemiesOnLevel.get(jj);
			if (crosshair.collidesWith(enemy)) {
				enemy.removedByHero(crosshair);
				enemiesOnLevel.remove(enemy);
				enemiesKilled++;
				quack.play();
				shot.play(1, 0.2f);
			}
		}
	}

	private void heroCheckForEnemies(EnemyEntity enemy, HeroEntity gunman, List<EnemyEntity> enemiesOnLevel) {
		for (int jj = 0; jj < enemiesOnLevel.size(); jj++) {
			enemy = enemiesOnLevel.get(jj);
			if (gunman.collidesWith(enemy)) {
				enemy.removedByHero(gunman);
				enemiesOnLevel.remove(enemy);
				enemiesKilled++;
				quack.play();
				duckhit.play(1, 0.2f);
			}
		}
	}

	/**
	 * Render the current frame
	 */
	private void render() {

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		Color.white.bind();

		// Texture draw
		levelTexture = levels[currentLevel].getTexture();
		for (int ii = 0; ii < SCREEN_SIZE_WIDTH; ii += levelTexture.getImageWidth()) {
			for (int jj = 0; jj < SCREEN_SIZE_HEIGHT; jj += levelTexture.getImageHeight()) {
				levels[currentLevel].draw(ii, jj);
			}
		}

		// Avatar + Crosshair draw
		hero.draw();
		crosshair.draw();

		// Objects draw
		treasuresOnLevel = treasures.get(currentLevel);
		treasuresDraw(treasuresOnLevel);
		hpOnLevel = healthpacks.get(currentLevel);
		hpDraw(hpOnLevel);
		enemiesOnLevel = enemies.get(currentLevel);
		enemiesDraw(enemiesOnLevel);

		// Font draw
//		AffineTransform affinetransform = new AffineTransform();
//		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		font.drawString(10, 10, String.format("Treasures found: %d/%d", treasuresFound, TREASURES_ON_LEVEL),
				Color.white);
		font.drawString(455, 10, String.format("Killed: %d", enemiesKilled), Color.white);
		font.drawString(825, 10, String.format("Level: %d/%d", currentLevel + 1, MAX_LEVELS), Color.white);
		font.drawString(1150, 10, String.format("Lives: %d/%d", lives, MAX_LIVES), Color.white);
//		font.drawString( SCREEN_SIZE_WIDTH-(int)(awtFont.getStringBounds(livesText, frc).getWidth())-10, 10, livesText, Color.white);

	}

	private void treasuresDraw(List<TreasureEntity> treasuresOnLevel) {
		TreasureEntity object;
		for (int j = 0; j < treasuresOnLevel.size(); j++) {
			object = treasuresOnLevel.get(j);
			if (object.isVisible())
				object.draw();
		}
	}

	private void hpDraw(List<HealthEntity> hpOnLevel) {
		HealthEntity object;
		for (int j = 0; j < hpOnLevel.size(); j++) {
			object = hpOnLevel.get(j);
			if (object.isVisible())
				object.draw();
		}
	}

	private void enemiesDraw(List<EnemyEntity> enemiesOnLevel) {
		for (int jj = 0; jj < enemiesOnLevel.size(); jj++) {
			enemy = enemiesOnLevel.get(jj);

			int enemX = enemy.getX();

			if ((enemX - 10) > 0)
				enemy.setX(enemX -= 10);

			if (enemX - 10 <= 0)
				enemy.setX(SCREEN_SIZE_WIDTH - enemy.getWidth());

			if (enemy.isVisible())
				enemy.draw();
		}
	}

	private static void gameRestart() {

		lives = 3;
		currentLevel = 0;
		treasuresFound = 0;
		enemiesKilled = 0;

		restart.play();

		// Enemies
		MySprite enemiesSprite;
		enemies = new HashMap<Integer, List<EnemyEntity>>();
		try {
			enemiesSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/bird.png")));
			initEnemies(enemiesSprite);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Treasures
		MySprite treasureSprite;
		treasures = new HashMap<Integer, List<TreasureEntity>>();
		try {
			treasureSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/chest.png")));
			initTreasures(treasureSprite);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// HP
		MySprite healthPack;
		healthpacks = new HashMap<Integer, List<HealthEntity>>();
		try {
			healthPack = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/health.png")));
			initHealth(healthPack);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			HighScoreToDb.init();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean notifyCrosshairUsed(Entity entity) {
		if (entity instanceof EnemyEntity) {
			isNotified = true;
			if (enemies != null) {
				enemiesOnLevel = enemies.get(currentLevel);
				enemy.setVisible(false);
				enemiesOnLevel.remove(enemy);
			}
			return isNotified;
		}
		return isNotified;
	}

	public static boolean notifyEnemyHit(Entity entity) {
		if (entity instanceof HeroEntity) {
			isNotified = true;
			if (enemies != null) {
				enemiesOnLevel = enemies.get(currentLevel);
				enemy.setVisible(false);
				enemiesOnLevel.remove(enemy);
			}
			lives--;
			if (lives == 0)
				gameRestart();
		}
		return isNotified;
	}

	public static boolean notifyTreasuresCollected(Entity entity) {
		if (entity instanceof TreasureEntity) {
			isNotified = true;
			if (treasures != null) {
				treasuresOnLevel = treasures.get(currentLevel);
				TreasureEntity treasure = (TreasureEntity) entity;
				treasure.setVisible(false);
				treasuresOnLevel.remove(treasure);
			}
			if (currentLevel + 1 == MAX_LEVELS && treasures.get(currentLevel).isEmpty()
					&& enemies.get(currentLevel).isEmpty())
				gameRestart();
		}
		return isNotified;
	}

	public static boolean notifyHpCollected(Entity entity) {
		if (entity instanceof HealthEntity) {
			isNotified = true;
			if (healthpacks != null) {
				hpOnLevel = healthpacks.get(currentLevel);
				healthpack = (HealthEntity) entity;
				healthpack.setVisible(false);
				hpOnLevel.remove(healthpack);
			}
		}
		return isNotified;
	}

	public static String[] getTreasuresAndKilledCount() {
		return new String[] { Integer.toString(treasuresFound), Integer.toString(enemiesKilled) };
	}

}
