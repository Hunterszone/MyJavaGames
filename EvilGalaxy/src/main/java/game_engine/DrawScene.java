package game_engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import animation.AsteroidAnimation;
import animation.AstronautAnimation;
import animation.SatelliteAnimation;
import animation.TheEndAnimationDown;
import animation.TheEndAnimationUp;
import dbconn.HighScoreToDb;
import enemy.Alien;
import enemy.Bunker;
import enemy.Crosshair;
import enemy.Dragon;
import enemy.EvilHead;
import enemy.PlayerShip;
import enums.Entity;
import enums.SoundEffect;
import enums.Sprite;
import icons.AlienIcon;
import icons.DifficultyIcon;
import icons.DragonIcon;
import icons.GoldIcon;
import icons.LaserIcon;
import icons.LocationIcon;
import icons.RocketIcon;
import items.Angel;
import items.BunkerBullet;
import items.CanonBall;
import items.Explosion;
import items.Gold;
import items.HealthPack;
import items.PlasmaBall;
import items.SaveSign;
import items.ShipMissile;
import items.ShipRocket;
import items.VolBtn;
import map.AlienCoordinates;
import map.DragonCoordinates;
import map.EvilHeadCoordinates;
import map.MapEntity;
import map.PlayerCoordinates;
import sound_engine.PlayWave1st;
import util.Constants;
import util.Flags;
import util.LoadSounds;
import util.OpenAiApiImpl;
import util.TextToSpeech;

public class DrawScene extends UpdateObjects {

	private static final String COLLECT_ALL_THE_GOLD = "Collect all the gold!";
	private static final String DESTROY_THE_BUNKER = "Destroy the bunker!";
	private static final String KILL_THE_EVIL_HEAD = "Finally..Kill the evil head!";
	private static final String SHIP_OUTTA_CONTROL = "Dragons invasion brings the ship outta control...";
	private static final String FONT_PAPYRUS = "Papyrus";
	private static final long serialVersionUID = 160473770719678686L;
	private static final String PRESS_AN_ARROW_TO_CONTINUE = "Press an arrow to continue...";
	private static final String KILLED_BY_THE_EVIL_HEAD = "Killed by the EvilHead!";
	private static final String KILLED_BY_THE_BUNKER = "Killed by the bunker!";
	private static final String KILLED_BY_A_DRAGON = "Killed by a dragon!";
	private static final String KILLED_BY_AN_ALIEN = "Killed by an alien!";

	private MapEntity mapEntity;
	private PlayerCoordinates playerSign;
	private EvilHeadCoordinates evilHeadSign;
	private AlienCoordinates alienSign;
	private DragonCoordinates dragonSign;

	private transient Image bg1, bg2, bg3;

	public DrawScene() throws IOException, NoSuchAlgorithmException {
		final int width = (int) Constants.DIMENSION.getWidth();
		final int height = (int) Constants.DIMENSION.getHeight();
		bg1 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(Sprite.BG1.getImg())).getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		bg2 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(Sprite.BG2.getImg())).getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		bg3 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(Sprite.BG3.getImg())).getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		mapEntity = new MapEntity();
		playerSign = new PlayerCoordinates();
		evilHeadSign = new EvilHeadCoordinates();
		alienSign = new AlienCoordinates();
		dragonSign = new DragonCoordinates();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (UpdateObjects.isIngame()) {
			startAnimations();
			drawGameStates(g);
			evilHeadBehaviour();
			handleLifeEvilHead(g);
			drawNaviMap(g);
		} else {
			stopTimers();
			stopSounds();
			drawGameOverBgs(g);
			drawGameWon(g);
			handleGameOver(g);
		}

		Toolkit.getDefaultToolkit().sync();
	}

	private void drawExplosion(Graphics g) {
		for (final Explosion explosion : Explosion.explosions) {
			explosion.draw(g);
		}
	}

	private void drawAstronaut(Graphics g) {
		final AffineTransform backup = ((Graphics2D) g).getTransform();
		AffineTransform a = AffineTransform.getRotateInstance(360, 400, 200);
		a.rotate(Math.toRadians(Math.ceil(Math.random())), (double) AstronautAnimation.ASTRONAUT.getX() / 2,
				(double) AstronautAnimation.ASTRONAUT.getY() / 2);
		((Graphics2D) g).setTransform(a);
		g.drawImage(AstronautAnimation.ASTRONAUT.getImage(), AstronautAnimation.ASTRONAUT.getX(),
				AstronautAnimation.ASTRONAUT.getY(), this);
		a = AffineTransform.getRotateInstance(181, 850, 700);
		a.rotate(Math.toRadians(Math.ceil(Math.random())), (double) AstronautAnimation.ASTRONAUT.getX() / 2,
				(double) AstronautAnimation.ASTRONAUT.getY() / 2);
		((Graphics2D) g).setTransform(a);
		g.drawImage(AstronautAnimation.ASTRONAUT.getImage(), AstronautAnimation.ASTRONAUT.getX(),
				AstronautAnimation.ASTRONAUT.getY(), this);
		Toolkit.getDefaultToolkit().sync();
		((Graphics2D) g).setTransform(backup);
	}
	
	private void drawAsteroids(Graphics g) {
		for (final AsteroidAnimation asteroidsAnim : AsteroidAnimation.ASTEROID_ANIMATION) {
			g.drawImage(asteroidsAnim.getImage(), asteroidsAnim.getX(), asteroidsAnim.getY(), this);
			Toolkit.getDefaultToolkit().sync();
		}
	}

	private void drawSatellite(Graphics g) {
		g.drawImage(SatelliteAnimation.SATELLITE.getImage(), SatelliteAnimation.SATELLITE.getX(),
				SatelliteAnimation.SATELLITE.getY(), this);
		final AffineTransform backup = ((Graphics2D) g).getTransform();
		final AffineTransform a = AffineTransform.getRotateInstance(200, 600, 1000);
		a.rotate(Math.toRadians(Math.ceil(Math.random())), (double) SatelliteAnimation.SATELLITE.getX() / 2,
				(double) SatelliteAnimation.SATELLITE.getY() / 2);
		((Graphics2D) g).setTransform(a);
		g.drawImage(SatelliteAnimation.SATELLITE.getImage(), SatelliteAnimation.SATELLITE.getX(),
				SatelliteAnimation.SATELLITE.getY(), this);
		Toolkit.getDefaultToolkit().sync();
		((Graphics2D) g).setTransform(backup);
	}

	private void drawElonsUp(Graphics g) {
		for (final TheEndAnimationUp elonAnimUp : TheEndAnimationUp.END_ANIMATION_UP) {
			g.drawImage(elonAnimUp.getImage(), elonAnimUp.getX(), elonAnimUp.getY(), this);
			Toolkit.getDefaultToolkit().sync();
		}
	}

	private void drawElonsDown(Graphics g) {
		for (final TheEndAnimationDown elonAnimDown : TheEndAnimationDown.END_ANIMATION_DOWN) {
			g.drawImage(elonAnimDown.getImage(), elonAnimDown.getX(), elonAnimDown.getY(), this);
			Toolkit.getDefaultToolkit().sync();
		}
	}

	private void startAnimations() {
		if (UpdateObjects.getAnimator() == null) {
			UpdateObjects.setAnimator(new Thread(this));
			UpdateObjects.getAnimator().start();
		}
	}

	private void drawGameOverBgs(Graphics g) {
		if (Alien.aliens.size() > 0) {
			g.drawImage(bg1, 0, 0, null);
		}

		if (Alien.aliens.isEmpty() && !Dragon.dragons.isEmpty()) {
			g.drawImage(bg2, 0, 0, null);
		}

		if (Dragon.dragons.isEmpty() && UpdateObjects.getLifeBunker() < 50) {
			g.drawImage(bg3, 0, 0, null);
		}

		if (Dragon.dragons.isEmpty() && UpdateObjects.getLifeBunker() >= 50 && Gold.goldstack.size() > 0) {
			g.drawImage(bg3, 0, 0, null);
		}

		if (Dragon.dragons.isEmpty() && UpdateObjects.getLifeBunker() >= 50 && Gold.goldstack.isEmpty()) {
			g.drawImage(bg3, 0, 0, null);
		}
	}

	private void stopSounds() {
		LoadSounds.BG_MUSIC.stop();
		LoadSounds.HIT.stop();
		LoadSounds.FIN.stop();
		LoadSounds.TAUNT.stop();
	}

	private void stopTimers() {
		getTimerEasy().stop();
		getTimerMedium().stop();
		getTimerHard().stop();
	}

	private void drawGameWon(Graphics g) {
		if (UpdateObjects.getLifeEvilHead() == 50) {

			drawElonsUp(g);
			drawElonsDown(g);
			
			if (!TextToSpeech.finMusicIsPlayed) {
				new PlayWave1st(SoundEffect.VICTORY.getSound()).start();
				TextToSpeech.finMusicIsPlayed = true;
			}
			
			setFontStyle(g);
			g.drawString("Monsters: Killed!", 5, 20);
			g.drawString("Gold: Collected!", 210, 20);
		}
	}

	private void handleGameOver(Graphics g) {
		if (UpdateObjects.getLifePlayerShip() <= 0) {
			if (!TextToSpeech.finMusicIsPlayed) {
				LoadSounds.FIN.play();
				TextToSpeech.finMusicIsPlayed = true;
			}
			drawGameOver(g);
			drawKilledBy(g);
			handleKilledByAlien();
			handleKilledByDragon();
			handleKilledByBunker();
			handleKilledByEvilHead();
			handleAnimations();
			setAnimator(null);
		}
	}

	private void handleAnimations() {

		for (AsteroidAnimation asteroidsAnim : AsteroidAnimation.ASTEROID_ANIMATION) {
			if (asteroidsAnim != null)
				asteroidsAnim = null;
		}
		AsteroidAnimation.ASTEROID_ANIMATION.clear();

		for (TheEndAnimationUp elonAnimUp : TheEndAnimationUp.END_ANIMATION_UP) {
			if (elonAnimUp != null)
				elonAnimUp = null;
		}
		TheEndAnimationUp.END_ANIMATION_UP.clear();

		for (TheEndAnimationDown elonAnimDown : TheEndAnimationDown.END_ANIMATION_DOWN) {
			if (elonAnimDown != null)
				elonAnimDown = null;
		}
		TheEndAnimationDown.END_ANIMATION_DOWN.clear();
	}

	@SuppressWarnings("finally")
	private void handleKilledByEvilHead() {
		if (Flags.killedByEvilHead) {
			if (!InitObjects.isIngame()) {
				TextToSpeech.playVoice(KILLED_BY_THE_EVIL_HEAD);
				TextToSpeech.voiceInterruptor = true;
				Flags.killedByEvilHead = false;
				try {
					HighScoreToDb.isInserted();
				} catch (final SQLException e) {
					e.printStackTrace();
				} finally {
					return;
				}
			}
		}
	}

	@SuppressWarnings("finally")
	private void handleKilledByBunker() {
		if (Flags.killedByBunker) {
			if (!InitObjects.isIngame()) {
				TextToSpeech.playVoice(KILLED_BY_THE_BUNKER);
				TextToSpeech.voiceInterruptor = true;
				Flags.killedByBunker = false;
				try {
					HighScoreToDb.isInserted();
				} catch (final SQLException e) {
					e.printStackTrace();
				} finally {
					return;
				}
			}
		}
	}

	@SuppressWarnings("finally")
	private void handleKilledByDragon() {
		if (Flags.killedByDragon) {
			if (!InitObjects.isIngame()) {
				TextToSpeech.playVoice(KILLED_BY_A_DRAGON);
				TextToSpeech.voiceInterruptor = true;
				Flags.killedByDragon = false;
				try {
					HighScoreToDb.isInserted();
				} catch (final SQLException e) {
					e.printStackTrace();
				} finally {
					return;
				}
			}
		}
	}

	@SuppressWarnings("finally")
	private void handleKilledByAlien() {
		if (Flags.killedByAlien) {
			if (!InitObjects.isIngame()) {
				TextToSpeech.playVoice(KILLED_BY_AN_ALIEN);
				TextToSpeech.voiceInterruptor = true;
				Flags.killedByAlien = false;
				try {
					HighScoreToDb.isInserted();
				} catch (final SQLException e) {
					e.printStackTrace();
				} finally {
					return;
				}
			}
		}
	}

	private void drawGameStates(Graphics g) {
		if (Alien.aliens.size() > 0) {
			drawGameStateL1(g);
		}

		if (Alien.aliens.isEmpty() && !Dragon.dragons.isEmpty()) {
			drawGameStateL2(g);
		}

		if (Dragon.dragons.isEmpty() && UpdateObjects.getLifeBunker() < 50) {
			drawGameStateL3(g);
		}

		if (Dragon.dragons.isEmpty() && UpdateObjects.getLifeBunker() >= 50 && Gold.goldstack.size() > 0) {
			drawGameStateL4(g);
		}

		if (Dragon.dragons.isEmpty() && UpdateObjects.getLifeBunker() >= 50 && Gold.goldstack.isEmpty()) {
			drawGameStateL5(g);
		}
	}

	private void handleLifeEvilHead(Graphics g) {

		if (Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && UpdateObjects.getLifeBunker() >= 50
				&& UpdateObjects.getLifeEvilHead() < 10) {
			g.drawString("Health: 100%", EvilHead.evilHead.x, EvilHead.evilHead.y);
		}

		if (UpdateObjects.getLifeEvilHead() >= 10 && UpdateObjects.getLifeEvilHead() < 20) {
			g.drawString("Health: 80%", EvilHead.evilHead.x, EvilHead.evilHead.y);
		}

		if (UpdateObjects.getLifeEvilHead() >= 20 && UpdateObjects.getLifeEvilHead() < 30) {
			g.drawString("Health: 60%", EvilHead.evilHead.x, EvilHead.evilHead.y);
		}

		if (UpdateObjects.getLifeEvilHead() >= 30 && UpdateObjects.getLifeEvilHead() < 35) {
			LoadSounds.TAUNT.loop();
		}

		if (UpdateObjects.getLifeEvilHead() >= 35) {
			LoadSounds.TAUNT.stop();
		}

		if (UpdateObjects.getLifeEvilHead() >= 30 && UpdateObjects.getLifeEvilHead() < 40) {
			g.drawString("Health: 40%", EvilHead.evilHead.x, EvilHead.evilHead.y);
		}

		if (UpdateObjects.getLifeEvilHead() >= 40 && UpdateObjects.getLifeEvilHead() < 45) {
			LoadSounds.TAUNT.loop();
		}

		if (UpdateObjects.getLifeEvilHead() >= 45) {
			LoadSounds.TAUNT.stop();
		}

		if (UpdateObjects.getLifeEvilHead() >= 40 && UpdateObjects.getLifeEvilHead() < 50) {
			g.drawString("Health: 20%", EvilHead.evilHead.x, EvilHead.evilHead.y);
		}

		if (UpdateObjects.getLifeEvilHead() == 50) {
			setIngame(false);
		}
	}

	private void evilHeadBehaviour() {
		if (Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && UpdateObjects.getLifeBunker() >= 50) {
			if (EvilHead.evilHead.x - PlayerShip.playerShip.x > 810) {
				PlayerShip.playerShip.shakeShip();
				Crosshair.crosshair.shakeCrosshair();
				PlayerShip.playerShip.y = EvilHead.evilHead.y + 70;
				PlayerShip.playerShip.loadImage(Sprite.MYSHIP_PULLED.getImg());
				PlayerShip.playerShip.getImageDimensions();
			}
			if (EvilHead.evilHead.x - PlayerShip.playerShip.x == 100
					|| EvilHead.evilHead.x - PlayerShip.playerShip.x == 200
					|| EvilHead.evilHead.x - PlayerShip.playerShip.x == 300
					|| EvilHead.evilHead.x - PlayerShip.playerShip.x == 400) {
				EvilHead.evilHead.strikeHead();
			}
		}
	}
	
	private void drawGameStateL1(Graphics g) {
		drawScene1(g);
		drawL1Labels(g);
		drawObjects(g);
		drawLifePlayerShip(g);
		drawAsteroids(g);
		drawPressArrowKey(g);
	}

	private void drawGameStateL2(Graphics g) {
		drawScene2(g);
		drawL2Labels(g);
		drawObjects(g);
		drawLifePlayerShip(g);
		drawSatellite(g);
		drawPressArrowKey(g);
	}

	private void drawGameStateL3(Graphics g) {
		drawScene3(g);
		drawL3Labels(g);
		drawObjects(g);
		drawLifeBunker(g);
		drawLifePlayerShip(g);
		drawAstronaut(g);
		drawPressArrowKey(g);

		LoadSounds.TAUNT.stop();
	}

	private void drawGameStateL4(Graphics g) {
		drawScene4(g);
		drawL4Labels(g);
		drawObjects(g);
		drawLifePlayerShip(g);
		drawPressArrowKey(g);
	}

	private void drawPressArrowKey(Graphics g) {
		if (!(InitObjects.getTimerEasy().isRunning() || InitObjects.getTimerMedium().isRunning()
				|| InitObjects.getTimerHard().isRunning())) {
			drawGamePaused(g);
		}
	}

	private void drawGameStateL5(Graphics g) {
		drawScene5(g);
		drawL5Labels(g);
		drawObjects(g);
		drawLifePlayerShip(g);

		if (!(InitObjects.getTimerEasy().isRunning() || InitObjects.getTimerMedium().isRunning()
				|| InitObjects.getTimerHard().isRunning())) {
			drawGamePaused(g);
		}
	}
	
	private void drawL1Labels(Graphics g) {

		setFontStyle(g);

		g.drawString(Constants.LEVEL + 1, 5, 20);
		g.drawImage(AlienIcon.alienIcon.getImage(), 100, 5, null);
		g.drawString(Constants.COLON_WITH_SPACE + Alien.aliens.size(), 140, 20);
		g.drawImage(LaserIcon.laserIcon.getImage(), 200, 5, null);
		g.drawString(Constants.UNLOCKED, 240, 20);
		g.drawImage(RocketIcon.rocketIcon.getImage(), 370, 5, null);
		g.drawString(Constants.LOCKED, 410, 20);

		if (getTimerEasy().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 510, 5, null);
			g.drawString(Constants.EASY, 545, 20);
		}

		if (getTimerMedium().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 510, 5, null);
			g.drawString(Constants.MEDIUM, 545, 20);
		}

		if (getTimerHard().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 510, 5, null);
			g.drawString(Constants.HARD, 545, 20);
		}

		drawLocation(g);
	}
	
	private void drawL2Labels(Graphics g) {

		setFontStyle(g);

		g.drawString(Constants.LEVEL + 2, 5, 20);
		g.drawImage(DragonIcon.dragonIcon.getImage(), 100, 5, null);
		g.drawString(Constants.COLON_WITH_SPACE + Dragon.dragons.size(), 140, 20);
		g.drawImage(LaserIcon.laserIcon.getImage(), 190, 5, null);
		g.drawString(Constants.LOCKED, 230, 20);
		g.drawImage(RocketIcon.rocketIcon.getImage(), 340, 5, null);
		g.drawString(Constants.UNLOCKED, 375, 20);

		if (getTimerEasy().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 500, 5, null);
			g.drawString(Constants.EASY, 540, 20);
		}

		if (getTimerMedium().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 500, 5, null);
			g.drawString(Constants.MEDIUM, 540, 20);
		}

		if (getTimerHard().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 500, 5, null);
			g.drawString(Constants.HARD, 540, 20);
			drawOuttaControl(g);
		}

		drawLocation(g);

		if (getTimerEasy().isRunning() || getTimerMedium().isRunning() || getTimerHard().isRunning()) {
			UpdateObjects.updateDragons();
			LoadSounds.TAUNT.loop();
		}
	}

	private void drawL3Labels(Graphics g) {

		setFontStyle(g);

		if (InitObjects.getTimerEasy().isRunning() || InitObjects.getTimerMedium().isRunning()
				|| InitObjects.getTimerHard().isRunning()) {
			drawKillTheBunker(g);
		}

		g.drawString(Constants.LEVEL + 3, 5, 20);
		g.drawImage(LaserIcon.laserIcon.getImage(), 95, 5, null);
		g.drawString(Constants.LOCKED, 135, 20);
		g.drawImage(RocketIcon.rocketIcon.getImage(), 235, 5, null);
		g.drawString(Constants.UNLOCKED, 275, 20);

		if (getTimerEasy().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 400, 5, null);
			g.drawString(Constants.EASY, 440, 20);
		}

		if (getTimerMedium().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 400, 5, null);
			g.drawString(Constants.MEDIUM, 440, 20);
		}

		if (getTimerHard().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 400, 5, null);
			g.drawString(Constants.HARD, 440, 20);
		}

		g.drawImage(LocationIcon.locationIcon.getImage(), 550, 0, null);
		g.drawString(" : " + CITY_AND_COUNTRY[0] + "/" + CITY_AND_COUNTRY[1], 570, 20);
	}

	private void drawL4Labels(Graphics g) {

		setFontStyle(g);

		g.drawString(Constants.LEVEL + 4, 5, 20);
		g.drawImage(GoldIcon.goldIcon.getImage(), 100, 5, null);
		g.drawString(Constants.COLON_WITH_SPACE + (-(Gold.goldstack.size() - UpdateObjects.getGoldCount())) + "/12", 140,
				20);
		g.drawImage(LaserIcon.laserIcon.getImage(), 225, 5, null);
		g.drawString(Constants.LOCKED, 265, 20);
		g.drawImage(RocketIcon.rocketIcon.getImage(), 370, 5, null);
		g.drawString(Constants.LOCKED, 405, 20);

		if (getTimerEasy().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 505, 5, null);
			g.drawString(Constants.EASY, 540, 20);
		}

		if (getTimerMedium().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 505, 5, null);
			g.drawString(Constants.MEDIUM, 540, 20);
		}

		if (getTimerHard().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 505, 5, null);
			g.drawString(Constants.HARD, 540, 20);
		}

		if (InitObjects.getTimerEasy().isRunning() || InitObjects.getTimerMedium().isRunning()
				|| InitObjects.getTimerHard().isRunning()) {
			drawCollectGold(g);
		}

		drawLocation(g);
		g.drawString("Bunker destroyed!", Bunker.bunkerObj.x, Bunker.bunkerObj.y);
		g.drawString("You're mine!", EvilHead.evilHead.x, EvilHead.evilHead.y);
		Bunker.bunkerObj.drawBunkerHit();
		EvilHead.evilHead.renderEvilHead(g);
	}

	private void drawLocation(Graphics g) {
		g.drawImage(LocationIcon.locationIcon.getImage(), 650, 0, null);
		g.drawString(" : " + CITY_AND_COUNTRY[0] + "/" + CITY_AND_COUNTRY[1], 670, 20);
	}

	private void drawL5Labels(Graphics g) {

		setFontStyle(g);

		g.drawString(Constants.LEVEL + 4, 5, 20);
		g.drawImage(LaserIcon.laserIcon.getImage(), 95, 5, null);
		g.drawString(Constants.UNLOCKED, 135, 20);
		g.drawImage(RocketIcon.rocketIcon.getImage(), 265, 5, null);
		g.drawString(Constants.UNLOCKED, 295, 20);

		if (getTimerEasy().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 420, 5, null);
			g.drawString(Constants.EASY, 460, 20);
		}

		if (getTimerMedium().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 420, 5, null);
			g.drawString(Constants.MEDIUM, 460, 20);
		}

		if (getTimerHard().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 420, 5, null);
			g.drawString(Constants.HARD, 460, 20);
		}

		if (InitObjects.getTimerEasy().isRunning() || InitObjects.getTimerMedium().isRunning()
				|| InitObjects.getTimerHard().isRunning()) {
			drawKillTheHead(g);
		}

		g.drawImage(LocationIcon.locationIcon.getImage(), 570, 0, null);
		g.drawString(" : " + CITY_AND_COUNTRY[0] + "/" + CITY_AND_COUNTRY[1], 590, 20);
	}

	private void drawLifePlayerShip(Graphics g) {

		if (UpdateObjects.getLifePlayerShip() > 0 && PlayerShip.playerShip != null) {
			PlayerShip.playerShip.renderShip(g);
		} else {
			setIngame(false);
		}

		if (UpdateObjects.getLifePlayerShip() > 4) {
			g.drawString("GODMODE", PlayerShip.playerShip.x, PlayerShip.playerShip.y);
			PlayerShip.playerShip.godMode();
		}

		if (UpdateObjects.getLifePlayerShip() == 4) {
			g.drawString("Health: 100%", PlayerShip.playerShip.x, PlayerShip.playerShip.y);
		}

		if (UpdateObjects.getLifePlayerShip() == 3) {
			g.drawString("Health: 75%", PlayerShip.playerShip.x, PlayerShip.playerShip.y);
			drawExplosion(g);
			UpdateObjects.updateExplosions();
		}

		if (UpdateObjects.getLifePlayerShip() == 2) {
			g.drawString("Health: 50%", PlayerShip.playerShip.x, PlayerShip.playerShip.y);
			drawExplosion(g);
			UpdateObjects.updateExplosions();
		}

		if (UpdateObjects.getLifePlayerShip() == 1) {
			g.drawString("Health: 25%", PlayerShip.playerShip.x, PlayerShip.playerShip.y);
			drawExplosion(g);
			UpdateObjects.updateExplosions();
		}
	}

	private void drawNaviMap(Graphics g) {
		if (Flags.isMapVisible) {
			setFontStyle(g);
			mapEntity.draw(g);
			playerSign.draw(g);
			alienSign.draw(g);
			dragonSign.draw(g);
			if (getLifeBunker() >= 50) {
				evilHeadSign.draw(g);
			}
			g.drawString("Date/Time: " + DateFormat.getDateTimeInstance().format(new Date()), 1010, 23);
		}
	}

	private void drawLifeBunker(Graphics g) {
		if (UpdateObjects.getLifeBunker() < 10 && Gold.goldstack.size() > 0) {
			g.drawString("Health: 100%", Bunker.bunkerObj.x, Bunker.bunkerObj.y);
		}

		if (UpdateObjects.getLifeBunker() >= 10 && UpdateObjects.getLifeBunker() < 20 && Gold.goldstack.size() > 0) {
			g.drawString("Health: 80%", Bunker.bunkerObj.x, Bunker.bunkerObj.y);

		}

		if (UpdateObjects.getLifeBunker() >= 20 && UpdateObjects.getLifeBunker() < 30 && Gold.goldstack.size() > 0) {
			g.drawString("Health: 60%", Bunker.bunkerObj.x, Bunker.bunkerObj.y);
		}

		if (UpdateObjects.getLifeBunker() >= 30 && UpdateObjects.getLifeBunker() < 35) {
			LoadSounds.TAUNT.loop();
		}

		if (UpdateObjects.getLifeBunker() >= 35) {
			LoadSounds.TAUNT.stop();
		}

		if (UpdateObjects.getLifeBunker() >= 30 && UpdateObjects.getLifeBunker() < 40 && Gold.goldstack.size() > 0) {
			g.drawString("Health: 40%", Bunker.bunkerObj.x, Bunker.bunkerObj.y);
		}

		if (UpdateObjects.getLifeBunker() >= 40 && UpdateObjects.getLifeBunker() < 45) {
			LoadSounds.TAUNT.loop();
		}

		if (UpdateObjects.getLifeBunker() >= 45) {
			LoadSounds.TAUNT.stop();
		}

		if (UpdateObjects.getLifeBunker() >= 40 && UpdateObjects.getLifeBunker() < 50 && Gold.goldstack.size() > 0) {
			g.drawString("Health: 20%", Bunker.bunkerObj.x, Bunker.bunkerObj.y);
		}

	}

	private void drawObjects(Graphics g) {

		final List<ShipMissile> missiles = PlayerShip.playerShip.getMissiles();

		for (final ShipMissile m : missiles) {
			if (m.isVisible()) {
				g.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}
		}

		final List<ShipRocket> rs = PlayerShip.playerShip.getRockets();

		for (final ShipRocket r : rs) {
			if (r.isVisible()) {
				g.drawImage(r.getImage(), r.getX(), r.getY(), this);
			}
		}

		final List<PlasmaBall> plasmaBalls = EvilHead.evilHead.getEvilPlasmaBalls();

		for (final PlasmaBall n : plasmaBalls) {
			if (n.isVisible() && UpdateObjects.getLifeBunker() >= 50) {
				g.drawImage(n.getImage(), n.getX(), n.getY(), this);
			}
		}

		final List<CanonBall> canons = EvilHead.evilHead.getCanons();

		for (final CanonBall n : canons) {
			if (n.isVisible() && UpdateObjects.getLifeBunker() >= 50) {
				g.drawImage(n.getImage(), n.getX(), n.getY(), this);
			}
		}

		final List<CanonBall> alienCanons = CanonBall.canonBalls;

		for (final CanonBall n : alienCanons) {
			if (n.isVisible() && !Alien.aliens.isEmpty()) {
				g.drawImage(n.getImage(), n.getX(), n.getY(), this);
			}
		}

		BunkerBullet.bulletsLeft = Bunker.bunkerObj.getBulletsLeft();

		for (final BunkerBullet n : BunkerBullet.bulletsLeft) {
			if (n.isVisible()) {
				g.drawImage(n.getImage(), n.getX(), n.getY(), this);
			}
		}

		BunkerBullet.bulletsRight = Bunker.bunkerObj.getBulletsRight();

		for (final BunkerBullet n : BunkerBullet.bulletsRight) {
			if (n.isVisible()) {
				g.drawImage(n.getImage(), n.getX(), n.getY(), this);
			}
		}

		for (final Alien alien : Alien.aliens) {
			if (alien.isVisible()) {
				g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
			} else {
				HealthPack.healthpacks.add(new HealthPack(alien.getX(), alien.getY()));
			}
		}

		if (!Alien.aliens.isEmpty()) {
			if (Alien.aliens.size() >= 40) {
				HealthPack.healthpacks = new ArrayList<HealthPack>(5);
			} else {
				for (HealthPack hp : HealthPack.healthpacks) {
					if (hp.getY() <= 0) {
						hp.setVisible(false);
					}

					g.drawImage(hp.getImage(), hp.getX(), hp.getY(), this);

					hp.moveUp();
				}
			}
		}

		for (final Dragon dragon : Dragon.dragons) {
			if (dragon.isVisible()) {
				g.drawImage(dragon.getImage(), dragon.getX(), dragon.getY(), this);
			}
		}

		for (Angel angel : Angel.angels) {
			if (angel.getY() <= 0) {
				angel.setVisible(false);
			}

			g.drawImage(angel.getImage(), angel.getX(), angel.getY(), this);

			angel.move();
		}

		if (UpdateObjects.getLifeBunker() >= 50) {
			for (final Gold gold : Gold.goldstack) {
				if (gold.isVisible()) {
					g.drawImage(gold.getImage(), gold.getX(), gold.getY(), this);
				}
			}
		}

		if (Dragon.dragons.isEmpty() && (UpdateObjects.getLifeBunker() < 50 || Gold.goldstack.isEmpty())) {
			for (final HealthPack health : HealthPack.healthpacks) {
				if (health.isVisible()) {
					g.drawImage(health.getImage(), health.getX(), health.getY(), this);
				}
			}
		}
	}
	
	private void drawScene1(Graphics g) {
		g.drawImage(bg1, 0, 0, null);
		if (PlayerShip.playerShip.isVisible() && Crosshair.crosshair.isVisible() && VolBtn.volButt.isVisible()
				&& Bunker.bunkerObj.isVisible()) {
			drawEntities(g);
		}
		drawSaveSign(g);
	}

	private void drawScene2(Graphics g) {
		g.drawImage(bg2, 0, 0, null);
		if (PlayerShip.playerShip.isVisible() && Crosshair.crosshair.isVisible() && VolBtn.volButt.isVisible()
				&& Bunker.bunkerObj.isVisible()) {
			drawEntities(g);
		}
		drawSaveSign(g);
	}

	private void drawScene3(Graphics g) {
		g.drawImage(bg3, 0, 0, null);
		if (PlayerShip.playerShip.isVisible() && Crosshair.crosshair.isVisible() && VolBtn.volButt.isVisible()
				&& Bunker.bunkerObj.isVisible()) {
			drawEntities(g);
		}
		drawSaveSign(g);
	}

	private void drawScene4(Graphics g) {
		g.drawImage(bg3, 0, 0, null);
		if (EvilHead.evilHead.isVisible() && PlayerShip.playerShip.isVisible() && Crosshair.crosshair.isVisible()
				&& VolBtn.volButt.isVisible() && Bunker.bunkerObj.isVisible()) {
			g.drawImage(Bunker.bunkerObj.getImage(), Bunker.bunkerObj.getX(), Bunker.bunkerObj.getY(), this);
			g.drawImage(EvilHead.evilHead.getImage(), EvilHead.evilHead.getX(), EvilHead.evilHead.getY(), this);
			EvilHead.evilHead.renderEvilHead(g);
		}
		drawSaveSign(g);
	}

	private void drawScene5(Graphics g) {
		g.drawImage(bg3, 0, 0, null);
		if (EvilHead.evilHead.isVisible() && PlayerShip.playerShip.isVisible() && Crosshair.crosshair.isVisible()
				&& VolBtn.volButt.isVisible() && Bunker.bunkerObj.isVisible()) {
			g.drawImage(EvilHead.evilHead.getImage(), EvilHead.evilHead.getX(), EvilHead.evilHead.getY(), this);
			EvilHead.evilHead.renderEvilHead(g);
		}
		drawSaveSign(g);
	}

	private void drawEntities(Graphics g) {
		g.drawImage(PlayerShip.playerShip.getImage(), PlayerShip.playerShip.getX(), PlayerShip.playerShip.getY(), this);
		g.drawImage(Crosshair.crosshair.getImage(), Crosshair.crosshair.getX(), Crosshair.crosshair.getY(), this);
		g.drawImage(VolBtn.volButt.getImage(), VolBtn.volButt.getX(), VolBtn.volButt.getY(), this);
		g.drawImage(Bunker.bunkerObj.getImage(), Bunker.bunkerObj.getX(), Bunker.bunkerObj.getY(), this);
	}

	private void drawSaveSign(Graphics g) {
		if (SaveSign.saveSign.isVisible()) {
			g.drawImage(SaveSign.saveSign.getImage(), SaveSign.saveSign.getX(), SaveSign.saveSign.getY(), this);
		}
	}

	private void drawGameOver(Graphics g) {
		setFontStyle(g);
		g.drawString("Monsters left: " + 0, 5, 20);
		g.drawString("Health: 0%", 200, 20);
		if (UpdateObjects.getLifeBunker() >= 50) {
			g.drawString("Gold: " + 0, 340, 20);
		}
	}

	private void drawKilledBy(Graphics g) {

		String msg = null;

		if (Flags.killedByAlien) {
			String openAiMsgAlien = OpenAiApiImpl.createCompletion(Entity.ALIEN.getMsg()); 
			msg = openAiMsgAlien == null || Entity.ALIEN.getMsg().equals(openAiMsgAlien) 
					? KILLED_BY_AN_ALIEN : openAiMsgAlien;
		} else if (Flags.killedByDragon) {			
			String openAiMsgDragon = OpenAiApiImpl.createCompletion(Entity.DRAGON.getMsg());
			msg = openAiMsgDragon == null || Entity.DRAGON.getMsg().equals(openAiMsgDragon) 
					? KILLED_BY_A_DRAGON : openAiMsgDragon;
		} else if (Flags.killedByBunker) {			
			String openAiMsgBunker = OpenAiApiImpl.createCompletion(Entity.BUNKER.getMsg());
			msg = openAiMsgBunker == null || Entity.BUNKER.getMsg().equals(openAiMsgBunker) 
					? KILLED_BY_THE_BUNKER : openAiMsgBunker;
		} else if (Flags.killedByEvilHead) {			
			String openAiMsgEvilHead = OpenAiApiImpl.createCompletion(Entity.EVILHEAD.getMsg());
			msg = openAiMsgEvilHead == null || Entity.EVILHEAD.getMsg().equals(openAiMsgEvilHead) 
					? KILLED_BY_THE_EVIL_HEAD : openAiMsgEvilHead;
		}

		formatMsg(g, msg);
	}

	private void drawCollectGold(Graphics g) {
		final String msg = COLLECT_ALL_THE_GOLD;
		formatMsg(g, msg);
	}

	private void drawKillTheBunker(Graphics g) {
		final String msg = DESTROY_THE_BUNKER;
		formatMsg(g, msg);
	}

	private void drawKillTheHead(Graphics g) {
		final String msg = KILL_THE_EVIL_HEAD;
		formatMsg(g, msg);
	}

	private void drawOuttaControl(Graphics g) {
		final String msg = SHIP_OUTTA_CONTROL;
		formatMsg(g, msg);
	}

	private void drawGamePaused(Graphics g) {
		final String msg = PRESS_AN_ARROW_TO_CONTINUE;
		formatMsg(g, msg);
	}

	private void formatMsg(Graphics g, final String msg) {
		int width = msg != null ? g.getFontMetrics().stringWidth(msg) : 0;
		String[] tokens = null;
		g.setColor(Color.BLACK);
		g.fillRect(Constants.BUNKER_X, ((int) Constants.DIMENSION.getHeight() / 2) - 180, width, 50);
		setFontStyle(g);
		if (msg != null && msg.contains("What")) {
			tokens = msg.split("[?!.]");
			if (tokens.length > 1) {
				g.drawString("- " + tokens[0], Constants.BUNKER_X, ((int) Constants.DIMENSION.getHeight() / 2) - 170);
				g.drawString("- " + tokens[1], Constants.BUNKER_X, ((int) Constants.DIMENSION.getHeight() / 2) - 140);
			} else {
				g.drawString(msg, Constants.BUNKER_X, ((int) Constants.DIMENSION.getHeight() / 2) - 150);
			}
		} else {
			g.drawString(msg, Constants.BUNKER_X, ((int) Constants.DIMENSION.getHeight() / 2) - 150);
		}
	}

	private void setFontStyle(Graphics g) {
		final Font small = new Font(FONT_PAPYRUS, Font.BOLD, 22);
		g.setColor(Color.ORANGE);
		g.setFont(small);
	}
}