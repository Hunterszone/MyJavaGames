package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import dbconn.HighScoreToDb;
import entities.Bomb;
import entities.Gift;
import entities.Lepricon;
import entities.Lives;
import entities.Points;
import entities.Timer;
import resources.Effects;
import states.GameOver;
import states.GamePause;
import states.YouWon;

public class Main extends BasicGame {

	private Image background;
	private Image lepriHead;
	private Image collectedGifts;
	private Image timerImg;
	private Points points;
	private Timer timer;
	private Sound soundCollected;
	private Sound soundBoom;
	private Lepricon lepricon;
	private Effects effects;
	private Lives lives;
	private Gift gift1, gift2;
	private GameOver gameOver;
	private YouWon youWonLabel;
	private GamePause gamePausedLabel;
	private static AppGameContainer app;
	private List<Bomb> bombs;
	private Music bgMusic;
	private Music musicFailure;
	private Music musicVictory;

	final static int SCREEN_WIDTH = 1000;
	final static int SCREEN_HEIGTH = 700;

	public Main() {
		super("Pot-O-Gold");
	}

	public static void main(String[] args) throws SlickException {
//		System.loadLibrary("lwjgl64");
		app = new AppGameContainer(new Main());
		app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGTH, false); // if TRUE, set the optimal
		// resolution for your PC !!
		app.setClearEachFrame(false);
		app.setMinimumLogicUpdateInterval(20);
		app.setShowFPS(false);
		org.lwjgl.opengl.Display.setIcon(LoadIcon.loadIcon("res/gameico.png", app));
		app.start();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {

		background.draw();

		if (!gameOver.isGameOver()) {
			lepricon.draw(g);
		}

		lepriHead.draw(850, 10);
		collectedGifts.draw(10, 10);
		timerImg.draw(430, 10);
		effects.draw(g);

		gift1.draw(g);
		gift2.draw(g);

		if (gameOver.isGameOver()) {
			gameOver.draw(g);
		}

		if (youWonLabel.isGameWon()) {
			youWonLabel.draw(g);
		}

		if (gamePausedLabel.isGamePaused()) {
			gamePausedLabel.draw(g);
		}

		points.draw(g);

		if (!gamePausedLabel.isGamePaused()) {
			timer.decrementTime();
		}

		for (Bomb bomb : bombs)
			bomb.draw(g);

		timer.draw(g);
		lives.draw(g);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		background = new Image("res/background.png");
		lepriHead = new Image("res/santa-head.png");
		collectedGifts = new Image("res/gift1.png");
		timerImg = new Image("res/timer.png");
		Font fontPoints = new AngelCodeFont("res/fonts/score_numer_font.fnt",
				new Image("res/fonts/score_numer_mine.png"));
		points = new Points(container.getWidth() - 900, 10, fontPoints);
		timer = new Timer(container.getWidth() - 470, 10, fontPoints);
		timer.running = true;
		lives = new Lives(container.getWidth() - 80, 10, fontPoints);
		effects = new Effects();
		lepricon = new Lepricon(500, 630, new Image("res/santa.png"), container.getInput(),
				effects.getRocketSmokeEmitter());
		gift1 = new Gift(400, 200, new Image("res/gift1.png"));
		gift2 = new Gift(200, 500, new Image("res/gift2.png"));
		bombs = new ArrayList<>();
		bombs.add(new Bomb(650, 10, new Image("res/mine.png")));
		bombs.add(new Bomb(550, 30, new Image("res/mine.png")));
		bombs.add(new Bomb(850, 10, new Image("res/mine.png")));
		bombs.add(new Bomb(200, 30, new Image("res/mine.png")));
		bombs.add(new Bomb(350, 10, new Image("res/mine.png")));
		soundCollected = new Sound("res/sounds/collect.wav");
		soundBoom = new Sound("res/sounds/explosion.wav");
		Font fontGameOver = new AngelCodeFont("res/fonts/game_over_font.fnt",
				new Image("res/fonts/game_over_mine_2.png")),
				fontGamePaused = new AngelCodeFont("res/fonts/game_over_font.fnt",
						new Image("res/fonts/game_over_mine_2.png")),
				fontYouWon = new AngelCodeFont("res/fonts/game_over_font.fnt",
						new Image("res/fonts/game_over_mine_2.png"));
		gameOver = new GameOver(container.getHeight(), container.getWidth(), fontGameOver);
		gamePausedLabel = new GamePause(container.getHeight(), container.getWidth(), fontGamePaused);
		youWonLabel = new YouWon(container.getHeight(), container.getWidth(), fontYouWon);
		bgMusic = new Music("res/sounds/bgmusic.wav");
		bgMusic.loop();
		musicFailure = new Music("res/sounds/failure.wav");
		musicVictory = new Music("res/sounds/victory.wav");
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {

		Input input = container.getInput();

		if (!gameOver.isGameOver() && !container.isPaused() && !youWonLabel.isGameWon()) {
			if (input.isKeyDown(Input.KEY_LEFT) && lepricon.getX() >= 70) {
				Lepricon.speedX += -10.5;
				if (lepricon.getX() < 70) {
					lepricon.setX(70);
				}
			}

			if (input.isKeyDown(Input.KEY_RIGHT) && lepricon.getX() <= SCREEN_WIDTH - 70) {
				Lepricon.speedX += 10.5;
				if (lepricon.getX() > SCREEN_WIDTH - 70) {
					lepricon.setX(SCREEN_WIDTH - 70);
				}
			}

			if (input.isKeyDown(Input.KEY_UP) && lepricon.getY() >= 70) {
				Lepricon.speedY += -10.5;
				if (lepricon.getY() < 70) {
					lepricon.setY(70);
				}
			}

			if (input.isKeyDown(Input.KEY_DOWN) && lepricon.getY() <= SCREEN_HEIGTH - 75) {
				Lepricon.speedY += 10.5;
				if (lepricon.getY() > SCREEN_HEIGTH - 75) {
					lepricon.setY(SCREEN_HEIGTH - 75);
				}
			}

			gift1.update(delta);
			gift2.update(delta);
			lepricon.update(delta);
			effects.update(delta);
			for (Bomb bomb : bombs)
				bomb.update(delta);
		}

		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			container.exit();
		}

		if (input.isKeyPressed(Input.KEY_A)) {
			bgMusic.loop();
		}

		if (input.isKeyPressed(Input.KEY_S)) {
			bgMusic.stop();
		}

		if (input.isKeyPressed(Input.KEY_P) && !gameOver.isGameOver()) {
			bgMusic.stop();
			gamePausedLabel.setGamePaused(true);
			container.pause();
		}

		if (input.isKeyPressed(Input.KEY_R)) {

			if (container.isPaused()) {
				bgMusic.loop();
				gamePausedLabel.setGamePaused(false);
				container.resume();
			} else {
				bgMusic.stop();
				init(container);
				Points.points = 0;
				Lives.lives = 3;
			}

		}

		if (gift1.checkCollision(lepricon) || gift2.checkCollision(lepricon)) {
			newGift(container, lepricon);
		}

		for (Bomb bomb : bombs) {
			if (lepricon.checkCollision(bomb)) {
				lepriLifeMinus(container, bomb);
			}
		}

		if ((60 - Timer.elapsedMillis / 1000) == 0) {
			if (Points.points < 30) {
				gameOver.setGameOver(true);
				bgMusic.stop();
				musicFailure.play();
				try {
					HighScoreToDb.init();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void newGift(GameContainer container, Lepricon lepricon) {
		if (Points.points < 30) {
			Random random = new Random();
			if (gift1.checkCollision(lepricon)) {
				effects.objColliding(gift1.getX(), gift1.getY());
				gift1.setX(random.nextInt(container.getWidth()));
				gift1.setY(random.nextInt((int) (container.getHeight() * 0.7)));
			}
			if (gift2.checkCollision(lepricon)) {
				effects.objColliding(gift2.getX(), gift2.getY());
				gift2.setX(random.nextInt(container.getWidth()));
				gift2.setY(random.nextInt((int) (container.getHeight() * 0.7)));
			}
			soundCollected.play();
			points.incrementPoints(new Integer(Points.points));
			if (Points.points == 30) {
				timer.stopTime();
				bgMusic.stop();
				youWonLabel.setYouWon(true);
				musicVictory.play(1, 0.3f);
				try {
					HighScoreToDb.init();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void lepriLifeMinus(GameContainer container, Bomb bomb) throws SlickException {
		effects.objColliding(bomb.getX(), bomb.getY());
		Random random = new Random();
		bomb.setX(random.nextInt(container.getWidth()));
		bomb.setY(random.nextInt((int) (container.getHeight() * 0.7)));
		soundBoom.play();
		lives.decrementLives(new Integer(Lives.lives));
		if (Lives.lives <= 0) {
			Lives.lives = 0;
			gameOver.setGameOver(true);
			bgMusic.stop();
			musicFailure.play();
			try {
				HighScoreToDb.init();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}