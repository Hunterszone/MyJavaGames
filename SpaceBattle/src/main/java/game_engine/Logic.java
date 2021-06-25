package game_engine;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import entities.AdvUfo;
import entities.Points;
import entities.ShotMoon;
import entities.ShotShip;
import entities.ShotUfo;
import entities.Spaceship;
import entities.Ufo;
import game_state.GameEasy;
import game_state.GameEnd;
import game_state.GameHard;
import game_state.GameMedium;
import game_state.GamePause;

public class Logic extends BasicGame {

	private Image background;
	private Image moon;
	private Sound soundBlasterShip;
	private Sound soundBlasterMoon;
	private Sound soundBlasterUfo;
	private Sound soundExplosion;
	private Spaceship spaceship;
	private Effects effects;
	private Points points;
	private Robot r;
	private List<ShotShip> shipbullets = new ArrayList<ShotShip>();
	private List<ShotMoon> moonbullets = new ArrayList<ShotMoon>();
	private List<ShotUfo> ufobullets = new ArrayList<ShotUfo>();
	private Ufo ufo1;
	private AdvUfo ufo2;
	private GameEnd gameOver;
	private GamePause gamePaused;
	private GameEasy gameEasy;
	private GameMedium gameMedium;
	private GameHard gameHard;
	private static boolean isEasy = false;
	private static boolean isMedium = false;
	private static boolean isHard = false;
	int mouseX;
	int mouseY;
	
	public Logic() {
		super("SpaceBattle");
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		background.draw();
		moon.drawCentered(100, 90);
		if (!gameOver.isGameOver()) {
			spaceship.draw(g);
		}
		effects.draw(g);
		for (ShotShip shot : shipbullets) {
			shot.draw(g);
		}
		for (ShotMoon shot : moonbullets) {
			shot.draw(g);
		}
		ufo1.draw(g);
		ufo2.draw(g);
		for (ShotUfo shot : ufobullets) {
			shot.draw(g);
		}
		if (gameOver.isGameOver()) {
			gameOver.draw(g);
			isEasy = false;
			isMedium = false;
			isHard = false;
		}
		if (gamePaused.isGamePaused()) {
			gamePaused.draw(g);
		}
		if (gameEasy.isGameEasy()) {
			gameEasy.draw(g);
		}
		if (gameMedium.isGameMedium()) {
			gameMedium.draw(g);
		}
		if (gameHard.isGameHard()) {
			gameHard.draw(g);
		}
		points.draw(g);
	}

	public void musicON(boolean isOn) throws SlickException {
		Music mus = new Music("res/sounds/bgmusic.wav");
		if (isOn == true) {
			mus.loop();
		} else {
			mus.pause();
		}

	}

	@Override
	public void init(GameContainer container) throws SlickException {
		container.setMouseCursor("res/crosshair.png", 31, 31);
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		background = new Image("res/bg.jpg");
		moon = new Image("res/moon.jpg");
		effects = new Effects();
		spaceship = new Spaceship(100, 100, new Image("res/spaceship.png"), container.getInput(),
				effects.getRocketSmokeEmitter());
		ufo1 = new Ufo(400, 200, new Image("res/ufo.png"));
		ufo2 = new AdvUfo(200, 500, new Image("res/advufo.png"));
		Font fontPunkte = new AngelCodeFont("res/fonts/score_numer_font.fnt",
				new Image("res/fonts/score_numer_mine.png"));
		points = new Points(container.getWidth() - 180, 10, fontPunkte);
		soundExplosion = new Sound("res/sounds/explosion.wav");
		soundBlasterShip = new Sound("res/sounds/shotship.wav");
		soundBlasterMoon = new Sound("res/sounds/laser.wav");
		soundBlasterUfo = new Sound("res/sounds/laser.wav");
		Font fontGameOver = new AngelCodeFont("res/fonts/game_over_font.fnt",
				new Image("res/fonts/game_over_mine_2.png")),
				fontGamePaused = new AngelCodeFont("res/fonts/game_over_font.fnt",
						new Image("res/fonts/game_over_mine_2.png")),
				fontGameEasy = new AngelCodeFont("res/fonts/game_over_font.fnt",
						new Image("res/fonts/game_over_mine_2.png")),
				fontGameMedium = new AngelCodeFont("res/fonts/game_over_font.fnt",
						new Image("res/fonts/game_over_mine_2.png")),
				fontGameHard = new AngelCodeFont("res/fonts/game_over_font.fnt",
						new Image("res/fonts/game_over_mine_2.png"));
		gameOver = new GameEnd(container.getHeight(), container.getWidth(), fontGameOver);
		gamePaused = new GamePause(container.getHeight(), container.getWidth(), fontGamePaused);
		gameEasy = new GameEasy(container.getHeight(), container.getWidth(), fontGameEasy);
		gameMedium = new GameMedium(container.getHeight(), container.getWidth(), fontGameMedium);
		gameHard = new GameHard(container.getHeight(), container.getWidth(), fontGameHard);
		musicON(true);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		Input input = container.getInput();
		if (!gameOver.isGameOver() && !container.isPaused()) {

			gameHard.setGameHard(false);
			gameMedium.setGameMedium(false);
			gameEasy.setGameEasy(true);

			mouseX = input.getMouseX();
			mouseY = input.getMouseY();

			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				newShotShip(mouseX + 15, mouseY + 250);
			}

			if (isEasy == true) {
				gameHard.setGameHard(false);
				gameMedium.setGameMedium(false);
				gameEasy.setGameEasy(true);
			}

			if (isMedium == true) {
				gameEasy.setGameEasy(false);
				gameHard.setGameHard(false);
				gameMedium.setGameMedium(true);
				if (spaceship.getX() - moon.getHeight() == 100 || spaceship.getX() - moon.getHeight() == 150
						|| spaceship.getX() - moon.getHeight() == 200 || spaceship.getX() - moon.getHeight() == 250
						|| spaceship.getY() - moon.getHeight() == 100 || spaceship.getY() - moon.getHeight() == 150
						|| spaceship.getY() - moon.getHeight() == 200 || spaceship.getY() - moon.getHeight() == 250) {
					newShotMoon(spaceship.getX(), spaceship.getY());
				}
			}

			if (isHard == true) {
				gameEasy.setGameEasy(false);
				gameMedium.setGameMedium(false);
				gameHard.setGameHard(true);
				if (spaceship.getX() - moon.getHeight() == 100 || spaceship.getX() - moon.getHeight() == 150
						|| spaceship.getX() - moon.getHeight() == 200 || spaceship.getX() - moon.getHeight() == 250
						|| spaceship.getY() - moon.getHeight() == 100 || spaceship.getY() - moon.getHeight() == 150
						|| spaceship.getY() - moon.getHeight() == 200 || spaceship.getY() - moon.getHeight() == 250) {
					newShotMoon(spaceship.getX(), spaceship.getY());
				}

				if (spaceship.getY() - ufo1.getY() == 300 || spaceship.getY() - ufo1.getY() == 400
						|| spaceship.getY() - ufo1.getY() == 500) {
					newShotUfo(ufo1.getX(), ufo1.getY());
				}

				if (spaceship.getY() - ufo2.getY() == 300 || spaceship.getY() - ufo1.getY() == 400
						|| spaceship.getY() - ufo2.getY() == 400 || spaceship.getY() - ufo1.getY() == 500) {
					newShotUfo(ufo2.getX(), ufo2.getY());
				}

			}
			
			if (ufo1.checkCollision(spaceship) || ufo2.checkCollision(spaceship)) {
				deadShipByUfo(container);
			}

			for (int i = 0; i < shipbullets.size(); i++) {
				ShotShip shot = shipbullets.get(i);
				shot.update(delta);
				if (ufo1.checkCollision(shot)) {
					newUfo(container, shot);
				}
				if (ufo2.checkCollision(shot)) {
					newAdvUfo(container, shot);
				}

			}

			for (int i = 0; i < moonbullets.size(); i++) {
				ShotMoon shot = moonbullets.get(i);
				shot.update(delta);
				if (spaceship.checkCollision(shot)) {
					deadShipByMoon(container, shot);
				}
			}

			for (int i = 0; i < ufobullets.size(); i++) {
				ShotUfo shot = ufobullets.get(i);
				shot.update(delta);
				if (spaceship.checkCollision(shot)) {
					deadShipByUfoShot(container, shot);
				}
			}

			if (r == null)
				return;

			// Get global current cursor location
			Point p = MouseInfo.getPointerInfo().getLocation();

			if (input.isKeyDown(Input.KEY_LEFT)) {
				container.setMouseGrabbed(false);
				r.mouseMove(p.x -= 15, p.y);
				Spaceship.speedX += -5.5;
			}

			if (input.isKeyDown(Input.KEY_RIGHT)) {
				container.setMouseGrabbed(false);
				r.mouseMove(p.x += 15, p.y);
				Spaceship.speedX += 5.5;
			}

			if (input.isKeyDown(Input.KEY_UP)) {
				container.setMouseGrabbed(false);
				r.mouseMove(p.x, p.y -= 11);
				Spaceship.speedY += -5.5;
			}

			if (input.isKeyDown(Input.KEY_DOWN)) {
				container.setMouseGrabbed(false);
				r.mouseMove(p.x, p.y += 11);
				Spaceship.speedY += 5.5;
			}

			ufo1.update(delta);
			ufo2.update(delta);
			spaceship.update(delta);
			effects.update(delta);
			moon.rotate(0.5f);
		}

		if (input.isKeyDown(Input.KEY_E)) {
			isMedium = false;
			isHard = false;
			isEasy = true;
		}

		if (input.isKeyDown(Input.KEY_M)) {
			isEasy = false;
			isHard = false;
			isMedium = true;
		}

		if (input.isKeyDown(Input.KEY_H)) {
			isEasy = false;
			isMedium = false;
			isHard = true;
		}

		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			container.exit();
		}

		if (input.isKeyPressed(Input.KEY_A)) {
			musicON(true);
		}

		if (input.isKeyPressed(Input.KEY_S)) {
			musicON(false);
		}

		if (input.isKeyPressed(Input.KEY_SPACE)) {
			if (!gameOver.isGameOver() && !gamePaused.isGamePaused()) {
				newShotShip(mouseX + 30, mouseY + 250);
			}
		}

		if (input.isKeyPressed(Input.KEY_P) && !gameOver.isGameOver()) {
			gamePaused.setGamePaused(true);
			container.pause();
		}

		if (input.isKeyPressed(Input.KEY_R) || input.isKeyPressed(Input.KEY_M) || input.isKeyPressed(Input.KEY_H)
				|| input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			container.setMouseGrabbed(false);
			if (container.isPaused()) {
				gamePaused.setGamePaused(false);
				container.resume();
			}
			if (gameOver.isGameOver()) {
				shipbullets.removeAll(shipbullets);
				moonbullets.removeAll(moonbullets);
				ufobullets.removeAll(ufobullets);
				Points.points = 0;
				init(container);
			}
		}

		/* Restart if in a play mode - optional */
		if (input.isKeyPressed(Input.KEY_N)) {
			if (!gameOver.isGameOver()) {
				shipbullets.removeAll(shipbullets);
				moonbullets.removeAll(moonbullets);
				ufobullets.removeAll(ufobullets);
				init(container);
			}
		}

		if (ufo1.getY() > container.getHeight()) {
			container.setPaused(true);
			gameOver.setGameOver(true);
		}

		if (ufo2.getY() > container.getHeight()) {
			container.setPaused(true);
			gameOver.setGameOver(true);
		}
	}

	private void newShotShip(int mouseX, int mouseY) {
		ShotShip shot = new ShotShip(mouseX, mouseY - 20, soundBlasterShip, effects.getShotEmitterShip());
		shipbullets.add(shot);
	}

	private void newShotMoon(int moonX, int moonY) {
		ShotMoon shot = new ShotMoon(200, 150, soundBlasterMoon, effects.getShotEmitterMoon());
		moonbullets.add(shot);
	}

	private void newShotUfo(int ufoX, int ufoY) {
		ShotUfo shot = new ShotUfo(ufoX, ufoY, soundBlasterUfo, effects.getShotEmitterShip());
		ufobullets.add(shot);
	}

	private void newUfo(GameContainer container, ShotShip shot) {
		shipbullets.remove(shot);
		shot.disappear();
		effects.ufoExplosion(ufo1.getX(), ufo1.getY());
		Random random = new Random();
		ufo1.setX(random.nextInt(container.getWidth()));
		ufo1.setY(random.nextInt((int) (container.getHeight() * 0.7)));
		soundExplosion.play();
		points.incrementPoints();
	}

	private void newAdvUfo(GameContainer container, ShotShip shot) {
		shipbullets.remove(shot);
		shot.disappear();
		effects.ufoExplosion(ufo2.getX(), ufo2.getY());
		Random random = new Random();
		ufo2.setX(random.nextInt(container.getWidth()));
		ufo2.setY(random.nextInt((int) (container.getHeight() * 0.7)));
		soundExplosion.play();
		points.incrementPoints();
	}

	private void deadShipByMoon(GameContainer container, ShotMoon shot) throws SlickException {
		moonbullets.remove(shot);
		shot.disappear();
		soundExplosion.play();
		gameOver.setGameOver(true);
	}

	private void deadShipByUfoShot(GameContainer container, ShotUfo shot) throws SlickException {
		ufobullets.remove(shot);
		shot.disappear();
		soundExplosion.play();
		gameOver.setGameOver(true);
	}
	
	private void deadShipByUfo(GameContainer container) throws SlickException {
		soundExplosion.play();
		gameOver.setGameOver(true);
	}
}
