package main;

import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

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
import game_engine.Images;
import game_engine.LevelTile;
import game_engine.LoadIcon;
import game_engine.MySprite;

/**
 *
 * This is a skeleton to init a game and run it.
 *
 * start() -> init() -> run() -> cleanup()
 *
 * Gameloop - run() Logic - logic() - Here we make all calculations and collect
 * the user input Render - render() - Here we draw every graphic object to the
 * screen
 *
 */
public class Game {

	/** Game title */
	public static final String GAME_TITLE = "GunMan";

	/** Screen size */
	private static final int SCREEN_SIZE_WIDTH = 1280;
	private static final int SCREEN_SIZE_HEIGHT = 800;
	private static final int MAX_LEVELS = 5;
	private static final int AVATAR_START_POS_X = 50;
	private static final int AVATAR_START_POS_Y = 50;
	private static final int TREASURES_ON_LEVEL = 5;
	private static final int MINES_ON_LEVEL = 3;
	private static final int ENEMIES_ON_LEVEL = 10;
	private static final int MAX_LIVES = 3;
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
	private static int treasuresSum = 0;
	private static int lives = 3;

	private LevelTile levels[] = new LevelTile[MAX_LEVELS];
	private static HeroEntity hero;
	private static EnemyEntity enemy;
	private static HealthEntity healthpack;
	private static Crosshair crosshair;
	private static TreasureEntity treasure;
	private static HashMap<Integer, ArrayList<TreasureEntity>> treasures;
	private static HashMap<Integer, ArrayList<HealthEntity>> healthpacks;
	private static HashMap<Integer, ArrayList<EnemyEntity>> enemies;

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

	/**
	 * Application init
	 *
	 * @param args Commandline args
	 */
	public static void main(String[] args) {
		Game myGame = new Game();
		org.lwjgl.opengl.Display.setIcon(LoadIcon.loadIcon(Images.GUNMAN.getImg(), myGame));
		myGame.start();
	}

	private void start() {
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
			treasures = new HashMap<Integer, ArrayList<TreasureEntity>>();
			treasureSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/chest.png")));
			initTreasures(treasureSprite);
			// Healthpacks
			healthpacks = new HashMap<Integer, ArrayList<HealthEntity>>();
			mineSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/health.png")));
			initHealth(mineSprite);
			// Enemies
			enemies = new HashMap<Integer, ArrayList<EnemyEntity>>();
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
		crosshair = new Crosshair(sprite, SCREEN_SIZE_WIDTH - sprite.getWidth(),
				SCREEN_SIZE_HEIGHT - sprite.getHeight());
		return crosshair;
	}

	public static HeroEntity initHero(MySprite sprite) {
		hero = new HeroEntity(sprite, AVATAR_START_POS_X, AVATAR_START_POS_Y);
		return hero;
	}

	public static TreasureEntity initTreasures(MySprite sprite) {
		Random r = new Random();
		/*for (int i = 0; i < MAX_LEVELS; i++) {
			ArrayList<TreasureEntity> objectsOnALevel = new ArrayList<TreasureEntity>();
			for (int j = 0; j < TREASURES_ON_LEVEL; j++) {
				treasure = new TreasureEntity(sprite, r.nextInt(SCREEN_SIZE_WIDTH - sprite.getWidth()),
						r.nextInt(SCREEN_SIZE_HEIGHT - sprite.getHeight()));
				objectsOnALevel.add(treasure);
			}
			if (treasures != null)
				treasures.put(i, objectsOnALevel);
		}
		return treasure;*/
		ArrayList<TreasureEntity> objectsOnALevel = new ArrayList<TreasureEntity>();
		objectsOnALevel.stream();
		for (int i = 0; i < MAX_LEVELS; i++) {
			objectsOnALevel.range(0, TREASURES_ON_LEVEL).mapToObj(t -> new TreasureEntity(sprite, r.nextInt(SCREEN_SIZE_WIDTH - sprite.getWidth()),
							r.nextInt(SCREEN_SIZE_HEIGHT - sprite.getHeight()))).forEach(treasure -> {
							objectsOnALevel.add(treasure);
							});
		}
		
		objectsOnALevel.removeIf(treasure -> {
			Objects.isNull(treasure);
		});
		
		return treasure;
	}

	public static HealthEntity initHealth(MySprite sprite) {
		Random r = new Random();
		/*for (int i = 0; i < MAX_LEVELS; i++) {
			ArrayList<HealthEntity> objectsOnALevel = new ArrayList<HealthEntity>();
			for (int j = 0; j < MINES_ON_LEVEL; j++) {
				healthpack = new HealthEntity(sprite, r.nextInt(SCREEN_SIZE_WIDTH - sprite.getWidth()),
						r.nextInt(SCREEN_SIZE_HEIGHT - sprite.getHeight()));
				objectsOnALevel.add(healthpack);
			}
			if (healthpacks != null)
				healthpacks.put(i, objectsOnALevel);
		}*/
		ArrayList<HealthEntity> objectsOnALevel = new ArrayList<HealthEntity>();
		for(int i = 0; i < MAX_LEVELS; i++) {
			IntStream.range(0, MINES_ON_LEVEL).mapToObj(h -> new HealthEntity(sprite, r.nextInt(SCREEN_SIZE_WIDTH - sprite.getWidth()),
						r.nextInt(SCREEN_SIZE_HEIGHT - sprite.getHeight()))).forEach(health -> {
							objectsOnALevel.add(health);
						});
		}
		return healthpack;
	}

	public static EnemyEntity initEnemies(MySprite enemyTexture) {
		Random r = new Random();
		/*for (int i = 0; i < MAX_LEVELS; i++) {
			ArrayList<EnemyEntity> objectsOnALevel = new ArrayList<EnemyEntity>();
			for (int j = 0; j < ENEMIES_ON_LEVEL; j++) {
				enemy = new EnemyEntity(enemyTexture, r.nextInt(SCREEN_SIZE_WIDTH - enemyTexture.getWidth()),
						r.nextInt(SCREEN_SIZE_HEIGHT - enemyTexture.getHeight()));
				objectsOnALevel.add(enemy);
			}
			if (enemies != null)
				enemies.put(i, objectsOnALevel);
		}*/
		for(int i = 0; i < MAX_LEVELS; i++) {
			ArrayList<EnemyEntity> objectsOnALevel = new ArrayList<EnemyEntity>();
			IntStream.range(0, ENEMIES_ON_LEVEL).mapToObj(e -> new EnemyEntity(enemyTexture, r.nextInt(SCREEN_SIZE_WIDTH - enemyTexture.getWidth()),
					r.nextInt(SCREEN_SIZE_HEIGHT - enemyTexture.getHeight()))).forEach(enemy -> {
						objectsOnALevel.add(enemy);
					});
		}
		return enemy;
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

		// HashMap objectType;
		ArrayList<HealthEntity> hpOnLevel;
		ArrayList<TreasureEntity> treasuresOnLevel;
		ArrayList<EnemyEntity> enemiesOnLevel;

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
					treasuresSum += treasuresFound;
					treasuresSum /= 2;
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

	private void checkForHp(HealthEntity item, HeroEntity gunman, ArrayList<HealthEntity> objectsOnLevel) {
		for (int jj = 0; jj < objectsOnLevel.size(); jj++) {
			item = objectsOnLevel.get(jj);
			if (gunman.collidesWith(item)) {
				gunman.removedByHero(item);
				lives++;
				healthy.play(1, 0.2f);
			}
		}
	}

	private void checkForTreasures(TreasureEntity item, HeroEntity gunman, ArrayList<TreasureEntity> objectsOnLevel) {
		for (int jj = 0; jj < objectsOnLevel.size(); jj++) {
			item = objectsOnLevel.get(jj);
			if (gunman.collidesWith(item)) {
				gunman.removedByHero(item);
				treasuresFound++;
				treasuresSum++;
				collect.play(1, 0.2f);
			}
		}
	}

	private void crosshairCheckForEnemies(EnemyEntity enemy, Crosshair crosshair,
			ArrayList<EnemyEntity> enemiesOnLevel) {
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

	private void heroCheckForEnemies(EnemyEntity enemy, HeroEntity gunman, ArrayList<EnemyEntity> enemiesOnLevel) {
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
		ArrayList<HealthEntity> hpOnLevel;
		ArrayList<TreasureEntity> treasuresOnLevel;
		ArrayList<EnemyEntity> enemiesOnLevel;
		Texture levelTexture;

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
		font.drawString(455, 10, String.format("Killed: %d", enemiesKilled, Color.white));
		font.drawString(825, 10, String.format("Level: %d", currentLevel + 1, Color.white));
		font.drawString(1150, 10, String.format("Lives: %d", lives, MAX_LIVES), Color.white);
//		font.drawString( SCREEN_SIZE_WIDTH-(int)(awtFont.getStringBounds(livesText, frc).getWidth())-10, 10, livesText, Color.white);

	}

	private void treasuresDraw(ArrayList<TreasureEntity> objectsOnLevel) {
		TreasureEntity object;
		for (int j = 0; j < objectsOnLevel.size(); j++) {
			object = objectsOnLevel.get(j);
			if (object.isVisible())
				object.draw();
		}
	}

	private void hpDraw(ArrayList<HealthEntity> objectsOnLevel) {
		HealthEntity object;
		for (int j = 0; j < objectsOnLevel.size(); j++) {
			object = objectsOnLevel.get(j);
			if (object.isVisible())
				object.draw();
		}
	}

	private void enemiesDraw(ArrayList<EnemyEntity> enemiesOnLevel) {
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

		System.out.println("Num of treasures: " + treasuresSum);

		try {
			HighScoreToDb.init();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lives = 3;
		currentLevel = 0;
		treasuresFound = 0;
		enemiesKilled = 0;

		restart.play();

		MySprite treasureSprite;
		MySprite mineSprite;
		MySprite enemiesSprite;

		// Enemies
		enemies = new HashMap<Integer, ArrayList<EnemyEntity>>();
		try {
			enemiesSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/bird.png")));
			initEnemies(enemiesSprite);
		} catch (IOException e) {

			e.printStackTrace();
		}

		// Treasures
		treasures = new HashMap<Integer, ArrayList<TreasureEntity>>();
		try {
			treasureSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/chest.png")));
			initTreasures(treasureSprite);
		} catch (IOException e) {

			e.printStackTrace();
		}

		// Mines
		healthpacks = new HashMap<Integer, ArrayList<HealthEntity>>();
		try {
			mineSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/health.png")));
			initHealth(mineSprite);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static boolean notifyCrosshairUsed(Entity crossh, Object object) {
		ArrayList<EnemyEntity> enemiesOnLevel;
		if (object instanceof EnemyEntity) {
			enemiesOnLevel = null;
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

	public static boolean notifyEnemyHit(Entity heroEntity, Object object) {
		ArrayList<EnemyEntity> objectsOnLevel;
		if (object instanceof HeroEntity) {
			isNotified = true;
			objectsOnLevel = null;
			if (enemies != null) {
				objectsOnLevel = enemies.get(currentLevel);
				enemy.setVisible(false);
				objectsOnLevel.remove(enemy);
			}
			lives--;
			if (lives == 0)
				gameRestart();
		}
		return isNotified;
	}

	public static boolean notifyTreasuresCollected(Entity heroEntity, Object object) {
		ArrayList<TreasureEntity> treasuresOnLevel;
		if (object instanceof TreasureEntity) {
			isNotified = true;
			if (treasures != null) {
				treasuresOnLevel = treasures.get(currentLevel);
				TreasureEntity treasure = (TreasureEntity) object;
				treasure.setVisible(false);
				treasuresOnLevel.remove(treasure);
			}
			if (currentLevel + 1 == MAX_LEVELS && treasures.get(currentLevel).isEmpty()
					&& enemies.get(currentLevel).isEmpty())
				gameRestart();
		}
		return isNotified;
	}

	public static boolean notifyHpCollected(Entity heroEntity, Object object) {
		ArrayList<HealthEntity> hpOnLevel;
		if (object instanceof HealthEntity) {
			isNotified = true;
			if (healthpacks != null) {
				hpOnLevel = healthpacks.get(currentLevel);
				healthpack = (HealthEntity) object;
				healthpack.setVisible(false);
				hpOnLevel.remove(healthpack);
			}
		}
		return isNotified;
	}

	public static String[] getTreasuresAndKilledCount() {
		return new String[] { Integer.toString(treasuresSum), Integer.toString(enemiesKilled) };
	}
}