package gameDevelopment;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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

/**
 *
 * This is a <em>very basic</em> skeleton to init a game and run it.
 *
 * start() -> init() -> run() -> cleanup()
 *
 * Gameloop - run() Logic - logic() - Here we make all calculations and collect
 * the user input Render - render() - Here we draw every graphic object to the
 * screen
 *
 * Task: Draw tiles to the screen and draw an avatar, which can move in any
 * direction (right/lef/up/down)
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

	public static int FLOOR_HEIGHT = SCREEN_SIZE_HEIGHT - 695;
	private static final int MAX_JUMP = 70;
	private boolean isJumping = false;

	/** Desired frame time */
	private static final int FRAMERATE = 60;

	/** Exit the game */
	private boolean finished;

	private int currentLevel = 0;
	private int enemiesKilled = 0;
	private int treasuresFound = 0;
	private int lives = 3;

	private LevelTile levels[] = new LevelTile[MAX_LEVELS];
	private HeroEntity hero;
	private EnemyEntity enemy;
	private HealthEntity health;
	private Crosshair crosshair;
	private HashMap<Integer, ArrayList<Entity>> treasures;
	private HashMap<Integer, ArrayList<Entity>> healthpacks;
	private HashMap<Integer, ArrayList<EnemyEntity>> enemies;

	private TrueTypeFont font;
	private Font awtFont;
	private Sound footsteps, healthy, denied, collect, shot, duckhit, restart, bgmusic, quack;

	/**
	 * Application init
	 *
	 * @param args Commandline args
	 */
	public static void main(String[] args) {
		Game myGame = new Game();
		org.lwjgl.opengl.Display.setIcon(LoadIcon.loadIcon("res/gunman.png", myGame));
		myGame.start();
	}

	public void start() {
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
		MySprite avatarTexture;
		MySprite treasureSprite;
		MySprite mineSprite;
		MySprite crosshairSprite;
		MySprite enemyTexture;

		initGL(SCREEN_SIZE_WIDTH, SCREEN_SIZE_HEIGHT);

		try {
			// Levels
			for (int ii = 0; ii < MAX_LEVELS; ii++) {
				levels[ii] = new LevelTile(TextureLoader.getTexture("PNG",
						ResourceLoader.getResourceAsStream("res/tile_" + Integer.toString(ii + 1) + ".png")));
			}

			// Crosshair
			crosshairSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/pointer.png")));
			initCrosshair(this, crosshairSprite);

			// Avatar
			avatarTexture = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/gunman.png")));
			hero = new HeroEntity(this, avatarTexture, AVATAR_START_POS_X, AVATAR_START_POS_Y);

			// Treasures
			treasures = new HashMap<Integer, ArrayList<Entity>>();
			treasureSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/chest.png")));
			initTreasures(treasureSprite);

			// Mines
			healthpacks = new HashMap<Integer, ArrayList<Entity>>();
			mineSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/health.png")));
			initHealth(mineSprite);

			// Enemies
			enemies = new HashMap<Integer, ArrayList<EnemyEntity>>();
			enemyTexture = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/bird.png")));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			finished = true;
		}
	}

	private void initCrosshair(Object object, MySprite sprite) {
		crosshair = new Crosshair(object, sprite, SCREEN_SIZE_WIDTH - sprite.getWidth(),
				SCREEN_SIZE_HEIGHT - sprite.getHeight());
	}

	private void initTreasures(MySprite sprite) {
		TreasureEntity treasure;
		Random r = new Random();

		for (int i = 0; i < MAX_LEVELS; i++) {
			ArrayList<Entity> objectsOnALevel = new ArrayList<Entity>();
			for (int j = 0; j < TREASURES_ON_LEVEL; j++) {
				treasure = new TreasureEntity(sprite, r.nextInt(SCREEN_SIZE_WIDTH - sprite.getWidth()),
						r.nextInt(SCREEN_SIZE_HEIGHT - sprite.getHeight()));
				objectsOnALevel.add(treasure);
			}
			treasures.put(i, objectsOnALevel);
		}
	}

	private void initHealth(MySprite sprite) {
		Random r = new Random();
		for (int i = 0; i < MAX_LEVELS; i++) {
			ArrayList<Entity> objectsOnALevel = new ArrayList<Entity>();
			for (int j = 0; j < MINES_ON_LEVEL; j++) {
				health = new HealthEntity(sprite, r.nextInt(SCREEN_SIZE_WIDTH - sprite.getWidth()),
						r.nextInt(SCREEN_SIZE_HEIGHT - sprite.getHeight()));
				objectsOnALevel.add(health);
			}
			healthpacks.put(i, objectsOnALevel);
		}
	}

	private void initEnemies(MySprite enemyTexture) {
		Random r = new Random();
		for (int i = 0; i < MAX_LEVELS; i++) {
			ArrayList<EnemyEntity> objectsOnALevel = new ArrayList<EnemyEntity>();
			for (int j = 0; j < ENEMIES_ON_LEVEL; j++) {
				enemy = new EnemyEntity(this, enemyTexture, r.nextInt(SCREEN_SIZE_WIDTH - enemyTexture.getWidth()),
						r.nextInt(SCREEN_SIZE_HEIGHT - enemyTexture.getHeight()));
				objectsOnALevel.add(enemy);
			}
			enemies.put(i, objectsOnALevel);
		}
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
		ArrayList<Entity> objectsOnLevel;
		ArrayList<EnemyEntity> enemiesOnLevel;

		int posY = hero.getY();
		int posX = hero.getX();

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			finished = true;

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			objectsOnLevel = healthpacks.get(currentLevel);
			checkForItems(crosshair, objectsOnLevel);
			enemiesOnLevel = enemies.get(currentLevel);
			checkForEnemies(crosshair, enemiesOnLevel);
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
				if ((currentLevel + 1) < MAX_LEVELS && treasures.get(currentLevel).isEmpty()
						&& enemies.get(currentLevel).isEmpty()) {
					currentLevel++;
					treasuresFound = 0;
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
		objectsOnLevel = treasures.get(currentLevel);
		checkForItems(hero, objectsOnLevel);

		objectsOnLevel = healthpacks.get(currentLevel);
		checkForItems(hero, objectsOnLevel);

		enemiesOnLevel = enemies.get(currentLevel);
		checkForHero(hero, enemiesOnLevel);

		// Sound
//		SoundStore.get().poll(0);
	}

	private void footsteps() {
		if (!footsteps.playing())
			footsteps.play(1, 0.2f);
	}

	private void checkForItems(Entity initObject, ArrayList<Entity> objectsOnLevel) {
		Entity object;

		for (int jj = 0; jj < objectsOnLevel.size(); jj++) {
			object = objectsOnLevel.get(jj);
			if (initObject.collidesWith(object)) {
				object.remove(initObject);
				initObject.remove(object);
			}
		}
	}

	private void checkForEnemies(Crosshair crosshair, ArrayList<EnemyEntity> enemiesOnLevel) {
		EnemyEntity enemy;

		for (int jj = 0; jj < enemiesOnLevel.size(); jj++) {
			enemy = enemiesOnLevel.get(jj);
			if (crosshair.collidesWith(enemy)) {
				enemiesOnLevel.remove(enemy);
				enemiesKilled++;
				quack.play();
				shot.play(1, 0.2f);
			}
		}
	}

	private void checkForHero(HeroEntity gunman, ArrayList<EnemyEntity> enemiesOnLevel) {
		EnemyEntity enemy;

		for (int jj = 0; jj < enemiesOnLevel.size(); jj++) {
			enemy = enemiesOnLevel.get(jj);
			if (gunman.collidesWith(enemy)) {
				enemy.collidedWith(gunman);
				enemiesOnLevel.remove(enemy);
				enemiesKilled++;
				duckhit.play(1, 0.2f);
			}
		}
	}

	/**
	 * Render the current frame
	 */
	private void render() {
		ArrayList<Entity> objectsOnLevel;
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
		objectsOnLevel = treasures.get(currentLevel);
		objectsDraw(objectsOnLevel);
		objectsOnLevel = healthpacks.get(currentLevel);
		objectsDraw(objectsOnLevel);
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

	public void objectsDraw(ArrayList<Entity> objectsOnLevel) {
		Entity object;
		for (int j = 0; j < objectsOnLevel.size(); j++) {
			object = objectsOnLevel.get(j);
			if (object.isVisible())
				object.draw();
		}
	}

	public void enemiesDraw(ArrayList<EnemyEntity> enemiesOnLevel) {
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

	public void gameRestart() {

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
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/bird.png")));
			initEnemies(enemiesSprite);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Treasures
		treasures = new HashMap<Integer, ArrayList<Entity>>();
		try {
			treasureSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/chest.png")));
			initTreasures(treasureSprite);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Mines
		healthpacks = new HashMap<Integer, ArrayList<Entity>>();
		try {
			mineSprite = new MySprite(
					TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/health.png")));
			initHealth(mineSprite);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void notifyCrosshair(Entity crossh, Object object) {
		ArrayList<Entity> objectsOnLevel;
		ArrayList<EnemyEntity> enemiesOnLevel;

		if (object instanceof HealthEntity) {
			objectsOnLevel = healthpacks.get(currentLevel);
			health = (HealthEntity) object;
			health.setVisible(false);
			objectsOnLevel.remove(health);
			lives++;
			healthy.play(1, 0.2f);
		}
		if (object instanceof EnemyEntity) {
			enemiesOnLevel = enemies.get(currentLevel);
			enemy.setVisible(false);
			enemiesOnLevel.remove(enemy);
		}
	}

	public void notifyEnemiesHit(Entity heroEntity, Object object) {
		ArrayList<EnemyEntity> objectsOnLevel;
		if (object instanceof HeroEntity) {
			objectsOnLevel = enemies.get(currentLevel);
			enemy.setVisible(false);
			objectsOnLevel.remove(enemy);
			lives--;
			if (lives == 0)
				gameRestart();
		}
	}

	public void notifyItemsCollected(Entity heroEntity, Object object) {
		ArrayList<Entity> objectsOnLevel;

		if (object instanceof TreasureEntity) {
			objectsOnLevel = treasures.get(currentLevel);
			TreasureEntity treasure = (TreasureEntity) object;
			treasure.setVisible(false);
			objectsOnLevel.remove(treasure);
			treasuresFound++;
			collect.play(1, 0.2f);
			int posX = hero.getX();
			if (currentLevel + 1 == MAX_LEVELS && treasures.get(currentLevel).isEmpty()
					&& enemies.get(currentLevel).isEmpty())
				gameRestart();
		} else {
			if (object instanceof HealthEntity) {
				objectsOnLevel = healthpacks.get(currentLevel);
				health = (HealthEntity) object;
				health.setVisible(false);
				objectsOnLevel.remove(health);
				lives++;
				healthy.play(1, 0.2f);
			}
		}
	}
}