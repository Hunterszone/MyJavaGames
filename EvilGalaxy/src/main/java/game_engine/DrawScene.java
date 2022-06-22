package game_engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import dbconn.HighScoreToDb;
import entities.Alien;
import entities.AsteroidsAnimation;
import entities.AstronautAnimation;
import entities.Bunker;
import entities.Crosshair;
import entities.Dragon;
import entities.EvilHead;
import entities.PlayerShip;
import entities.SatelliteAnimation;
import entities.TheEndAnimationDown;
import entities.TheEndAnimationUp;
import enums.Entity;
import enums.Images;
import icons.AlienIcon;
import icons.DifficultyIcon;
import icons.DragonIcon;
import icons.GoldIcon;
import icons.LaserIcon;
import icons.RocketIcon;
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
import map.MapEntity;
import map.PlayerCoordinates;
import util.Constants;
import util.LoadSounds;
import util.TextToSpeech;

public class DrawScene extends Collisions {

	transient static Image bg1, bg2, bg3;
	private static final long serialVersionUID = 1L;
	private MapEntity mapEntity;
	private PlayerCoordinates playerSign;
	private AlienCoordinates alienSign;
	private DragonCoordinates dragonSign;

	public DrawScene() throws IOException {
		final int width = (int) Constants.DIMENSION.getWidth();
		final int height = (int) Constants.DIMENSION.getHeight();
		bg1 = Toolkit.getDefaultToolkit().createImage(Images.BG1.getImg()).getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		bg2 = Toolkit.getDefaultToolkit().createImage(Images.BG2.getImg()).getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		bg3 = Toolkit.getDefaultToolkit().createImage(Images.BG3.getImg()).getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		mapEntity = new MapEntity();
		playerSign = new PlayerCoordinates();
		alienSign = new AlienCoordinates();
		dragonSign = new DragonCoordinates();
	}

	private void drawExplosion(Graphics g) {
		for (final Explosion explosion : explosions) {
			explosion.draw(g);
		}
	}

	private void drawAstronaut(Graphics g) {
		final AffineTransform backup = ((Graphics2D) g).getTransform();
		AffineTransform a = AffineTransform.getRotateInstance(360, 400, 200);
		a.rotate(Math.toRadians(Math.ceil(Math.random())), (double) AstronautAnimation.astronautAnim.getX() / 2,
				(double) AstronautAnimation.astronautAnim.getY() / 2);
		((Graphics2D) g).setTransform(a);
		g.drawImage(AstronautAnimation.astronautAnim.getImage(), AstronautAnimation.astronautAnim.getX(),
				AstronautAnimation.astronautAnim.getY(), this);
		a = AffineTransform.getRotateInstance(181, 850, 700);
		a.rotate(Math.toRadians(Math.ceil(Math.random())), (double) AstronautAnimation.astronautAnim.getX() / 2,
				(double) AstronautAnimation.astronautAnim.getY() / 2);
		((Graphics2D) g).setTransform(a);
		g.drawImage(AstronautAnimation.astronautAnim.getImage(), AstronautAnimation.astronautAnim.getX(),
				AstronautAnimation.astronautAnim.getY(), this);
		Toolkit.getDefaultToolkit().sync();
		((Graphics2D) g).setTransform(backup);
	}

	private void drawSatellite(Graphics g) {
		g.drawImage(SatelliteAnimation.starAnim.getImage(), SatelliteAnimation.starAnim.getX(),
				SatelliteAnimation.starAnim.getY(), this);
		final AffineTransform backup = ((Graphics2D) g).getTransform();
		final AffineTransform a = AffineTransform.getRotateInstance(200, 600, 1000);
		a.rotate(Math.toRadians(Math.ceil(Math.random())), (double) SatelliteAnimation.starAnim.getX() / 2,
				(double) SatelliteAnimation.starAnim.getY() / 2);
		((Graphics2D) g).setTransform(a);
		g.drawImage(SatelliteAnimation.starAnim.getImage(), SatelliteAnimation.starAnim.getX(),
				SatelliteAnimation.starAnim.getY(), this);
		Toolkit.getDefaultToolkit().sync();
		((Graphics2D) g).setTransform(backup);
	}

	private void drawAsteroids(Graphics g) {
		for (final AsteroidsAnimation asteroidsAnim : AsteroidsAnimation.asteroidsAnimations) {
			g.drawImage(asteroidsAnim.getImage(), asteroidsAnim.getX(), asteroidsAnim.getY(), this);
			Toolkit.getDefaultToolkit().sync();
		}
	}

	private void drawElonsUp(Graphics g) {
		for (final TheEndAnimationUp elonAnimUp : TheEndAnimationUp.theEndAnimationUpList) {
			g.drawImage(elonAnimUp.getImage(), elonAnimUp.getX(), elonAnimUp.getY(), this);
			Toolkit.getDefaultToolkit().sync();
		}
	}

	private void drawElonsDown(Graphics g) {
		for (final TheEndAnimationDown elonAnimDown : TheEndAnimationDown.theEndAnimationDownList) {
			g.drawImage(elonAnimDown.getImage(), elonAnimDown.getX(), elonAnimDown.getY(), this);
			Toolkit.getDefaultToolkit().sync();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (ingame) {
			drawGameStates(g);
			evilHeadBehaviour();
			handleLifeEvilHead(g);
		} else {
			stopTimers();
			stopSounds();
			drawGameOverBgs(g);
			handleGameWon(g);
			handleGameOver(g);
		}

		Toolkit.getDefaultToolkit().sync();
	}

	private void drawGameOverBgs(Graphics g) {
		if (Alien.aliens.size() > 0) {
			g.drawImage(bg1, 0, 0, null);
		}

		if (Alien.aliens.isEmpty() && !Dragon.dragons.isEmpty()) {
			g.drawImage(bg2, 0, 0, null);
		}

		if (Dragon.dragons.isEmpty() && lifeBunker < 50) {
			g.drawImage(bg3, 0, 0, null);
		}

		if (Dragon.dragons.isEmpty() && lifeBunker >= 50 && Gold.goldstack.size() > 0) {
			g.drawImage(bg3, 0, 0, null);
		}

		if (Dragon.dragons.isEmpty() && lifeBunker >= 50 && Gold.goldstack.isEmpty()) {
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
		timerEasy.stop();
		timerMedium.stop();
		timerHard.stop();
	}

	private void handleGameWon(Graphics g) {
		if (lifeEvilHead == 50) {

			drawElonsUp(g);
			drawElonsDown(g);

			if (TextToSpeech.finMusicIsPlayed == false) {
				LoadSounds.HIGHSC.play();
				TextToSpeech.finMusicIsPlayed = true;
			}

			g.drawString("Monsters: Killed!", 5, 20);
			g.drawString("Gold: Collected!", 210, 20);
		}
	}

	private void handleGameOver(Graphics g) {
		if (lifePlayerShip <= 0) {
			if (TextToSpeech.finMusicIsPlayed == false) {
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
		}
	}

	private void handleAnimations() {
		if (AstronautAnimation.astronautAnim != null)
			AstronautAnimation.astronautAnim = null;
		
		if (SatelliteAnimation.starAnim != null)
			SatelliteAnimation.starAnim = null;
		
		for (AsteroidsAnimation asteroidsAnim : AsteroidsAnimation.asteroidsAnimations) {
			if (asteroidsAnim != null)
				asteroidsAnim = null;
		}
		AsteroidsAnimation.asteroidsAnimations.clear();
		
		for (TheEndAnimationUp elonAnimUp : TheEndAnimationUp.theEndAnimationUpList) {
			if (elonAnimUp != null)
				elonAnimUp = null;
		}
		TheEndAnimationUp.theEndAnimationUpList.clear();
		
		for (TheEndAnimationDown elonAnimDown : TheEndAnimationDown.theEndAnimationDownList) {
			if (elonAnimDown != null)
				elonAnimDown = null;
		}
		TheEndAnimationDown.theEndAnimationDownList.clear();
	}

	@SuppressWarnings("finally")
	private void handleKilledByEvilHead() {
		if (killedByEvilHead == true) {
			if (InitObjects.ingame == false) {
				TextToSpeech.playVoice("Killed by the Evil Head!");
				TextToSpeech.voiceInterruptor = true;
				killedByEvilHead = false;
				try {
					HighScoreToDb.insertTable();
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
		if (killedByBunker == true) {
			if (InitObjects.ingame == false) {
				TextToSpeech.playVoice("Killed by the bunker!");
				TextToSpeech.voiceInterruptor = true;
				killedByBunker = false;
				try {
					HighScoreToDb.insertTable();
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
		if (killedByDragon == true) {
			if (InitObjects.ingame == false) {
				TextToSpeech.playVoice("Killed by a dragon!");
				TextToSpeech.voiceInterruptor = true;
				killedByDragon = false;
				try {
					HighScoreToDb.insertTable();
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
		if (killedByAlien == true) {
			if (InitObjects.ingame == false) {
				TextToSpeech.playVoice("Killed by an alien!");
				TextToSpeech.voiceInterruptor = true;
				killedByAlien = false;
				try {
					HighScoreToDb.insertTable();
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

		if (Dragon.dragons.isEmpty() && lifeBunker < 50) {
			drawGameStateL3(g);
		}

		if (Dragon.dragons.isEmpty() && lifeBunker >= 50 && Gold.goldstack.size() > 0) {
			drawGameStateL4(g);
		}

		if (Dragon.dragons.isEmpty() && lifeBunker >= 50 && Gold.goldstack.isEmpty()) {
			drawGameStateL5(g);
		}
	}

	private void handleLifeEvilHead(Graphics g) {
		EvilHead.evilHead.renderEvilHead(g);

		if (Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && lifeBunker >= 50 && lifeEvilHead < 10) {
			g.drawString("Health: 100%", EvilHead.evilHead.x, EvilHead.evilHead.y);
		}

		if (lifeEvilHead >= 10 && lifeEvilHead < 20) {
			g.drawString("Health: 80%", EvilHead.evilHead.x, EvilHead.evilHead.y);
		}

		if (lifeEvilHead >= 20 && lifeEvilHead < 30) {
			g.drawString("Health: 60%", EvilHead.evilHead.x, EvilHead.evilHead.y);
		}

		if (lifeEvilHead >= 30 && lifeEvilHead < 35) {
			LoadSounds.TAUNT.loop();
		}

		if (lifeEvilHead >= 35) {
			LoadSounds.TAUNT.stop();
		}

		if (lifeEvilHead >= 30 && lifeEvilHead < 40) {
			g.drawString("Health: 40%", EvilHead.evilHead.x, EvilHead.evilHead.y);
		}

		if (lifeEvilHead >= 40 && lifeEvilHead < 45) {
			LoadSounds.TAUNT.loop();
		}

		if (lifeEvilHead >= 45) {
			LoadSounds.TAUNT.stop();
		}

		if (lifeEvilHead >= 40 && lifeEvilHead < 50) {
			g.drawString("Health: 20%", EvilHead.evilHead.x, EvilHead.evilHead.y);
		}

		if (lifeEvilHead == 50) {
			ingame = false;
		}
	}

	private void evilHeadBehaviour() {

		if (Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && lifeBunker >= 50
				&& (EvilHead.evilHead.x - PlayerShip.playerShip.x == 100
						|| EvilHead.evilHead.x - PlayerShip.playerShip.x == 200
						|| EvilHead.evilHead.x - PlayerShip.playerShip.x == 300
						|| EvilHead.evilHead.x - PlayerShip.playerShip.x == 400)) {

			EvilHead.evilHead.strikeHead();

			if (timerEasy.isRunning()) {
				EvilHead.evilHead.canons();
			}
			if (timerMedium.isRunning() || timerHard.isRunning()) {
				EvilHead.evilHead.plasmaBalls();
			}
		}

		if (Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && lifeBunker >= 50) {
			if (EvilHead.evilHead.x - PlayerShip.playerShip.x > 810) {
				PlayerShip.playerShip.shakeShip();
				Crosshair.crosshair.shakeCrosshair();
				PlayerShip.playerShip.y = EvilHead.evilHead.y + 70;
				PlayerShip.playerShip.loadImage(Images.MYSHIP_PULLED.getImg());
				PlayerShip.playerShip.getImageDimensions();
			}
		}
	}

	private void drawGameStateL1(Graphics g) {
		drawScene1(g);
		drawL1Labels(g);
		drawObjects(g);
		drawLifePlayerShip(g);
		drawAsteroids(g);
		drawGamePausedAndPressAnyKey(g);
	}

	private void drawGameStateL2(Graphics g) {
		drawScene2(g);
		drawL2Labels(g);
		drawObjects(g);
		drawLifePlayerShip(g);
		drawSatellite(g);
		drawGamePausedAndPressAnyKey(g);
	}

	private void drawGameStateL3(Graphics g) {
		drawScene3And4(g);
		drawL3Labels(g);
		drawObjects(g);
		drawLifeBunker(g);
		drawLifePlayerShip(g);
		drawAstronaut(g);
		drawGamePausedAndPressAnyKey(g);

		LoadSounds.TAUNT.stop();
	}

	private void drawGameStateL4(Graphics g) {
		drawScene3And4(g);
		drawL4Labels(g);
		drawObjects(g);
		drawLifePlayerShip(g);
		drawGamePausedAndPressAnyKey(g);
	}

	private void drawGamePausedAndPressAnyKey(Graphics g) {
		if (!(InitObjects.timerEasy.isRunning() || InitObjects.timerMedium.isRunning()
				|| InitObjects.timerHard.isRunning())) {
			drawGamePaused(g);
		}
	}

	private void drawGameStateL5(Graphics g) {
		drawScene3And4(g);
		drawL5Labels(g);
		drawObjects(g);
		drawLifePlayerShip(g);

		if (!(InitObjects.timerEasy.isRunning() || InitObjects.timerMedium.isRunning()
				|| InitObjects.timerHard.isRunning())) {
			drawGamePaused(g);
		}
	}

	private void drawL1Labels(Graphics g) {

		setFontStyle(g);

		g.drawString("Level: " + 1, 5, 20);
		g.drawImage(AlienIcon.alienIcon.getImage(), 100, 5, null);
		g.drawString(": " + Alien.aliens.size(), 140, 20);
		g.drawImage(LaserIcon.laserIcon.getImage(), 200, 5, null);
		g.drawString(Constants.UNLOCKED, 240, 20);
		g.drawImage(RocketIcon.rocketIcon.getImage(), 360, 5, null);
		g.drawString(": Locked", 400, 20);

		if (timerEasy.isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 500, 5, null);
			g.drawString(Constants.EASY, 540, 20);
		}

		if (timerMedium.isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 500, 5, null);
			g.drawString(Constants.MEDIUM, 540, 20);
		}

		if (timerHard.isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 500, 5, null);
			g.drawString(Constants.HARD, 540, 20);
		}

		g.drawString("IP FROM: " + InitObjects.CITY_AND_COUNTRY[0] + "/" + InitObjects.CITY_AND_COUNTRY[1], 650, 20);
		g.drawString("EvilHead", EvilHead.evilHead.x, EvilHead.evilHead.y);
	}

	private void drawL2Labels(Graphics g) {

		setFontStyle(g);

		g.drawString("Level: " + 2, 5, 20);
		g.drawImage(DragonIcon.dragonIcon.getImage(), 100, 5, null);
		g.drawString(": " + Dragon.dragons.size(), 140, 20);
		g.drawImage(LaserIcon.laserIcon.getImage(), 190, 5, null);
		g.drawString(": Locked", 230, 20);
		g.drawImage(RocketIcon.rocketIcon.getImage(), 335, 5, null);
		g.drawString(Constants.UNLOCKED, 375, 20);

		if (timerEasy.isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 500, 5, null);
			g.drawString(Constants.EASY, 540, 20);
		}

		if (timerMedium.isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 500, 5, null);
			g.drawString(Constants.MEDIUM, 540, 20);
		}

		if (timerHard.isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 500, 5, null);
			g.drawString(Constants.HARD, 540, 20);
			drawOuttaControl(g);
			PlayerShip.playerShip.shakeShip();
			Crosshair.crosshair.shakeCrosshair();
		}

		g.drawString("IP FROM: " + InitObjects.CITY_AND_COUNTRY[0] + "/" + InitObjects.CITY_AND_COUNTRY[1], 650, 20);
		g.drawString("EvilHead", EvilHead.evilHead.x, EvilHead.evilHead.y);

		if (timerEasy.isRunning() || timerMedium.isRunning() || timerHard.isRunning()) {
			updateDragons();
			LoadSounds.TAUNT.loop();
		}
	}

	private void drawL3Labels(Graphics g) {

		setFontStyle(g);

		if (InitObjects.timerEasy.isRunning() || InitObjects.timerMedium.isRunning()
				|| InitObjects.timerHard.isRunning()) {
			drawKillTheBunker(g);
		}

		g.drawString("Level: " + 3, 5, 20);
		g.drawImage(LaserIcon.laserIcon.getImage(), 95, 5, null);
		g.drawString(": Locked", 135, 20);
		g.drawImage(RocketIcon.rocketIcon.getImage(), 235, 5, null);
		g.drawString(Constants.UNLOCKED, 275, 20);

		if (timerEasy.isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 400, 5, null);
			g.drawString(Constants.EASY, 440, 20);
		}

		if (timerMedium.isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 400, 5, null);
			g.drawString(Constants.MEDIUM, 440, 20);
		}

		if (timerHard.isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 400, 5, null);
			g.drawString(Constants.HARD, 440, 20);
		}

		g.drawString("IP FROM: " + InitObjects.CITY_AND_COUNTRY[0] + "/" + InitObjects.CITY_AND_COUNTRY[1], 550, 20);
		g.drawString("EvilHead", EvilHead.evilHead.x, EvilHead.evilHead.y);
	}

	private void drawL4Labels(Graphics g) {

		setFontStyle(g);

		g.drawString("Level: " + 4, 5, 20);
		g.drawImage(GoldIcon.goldIcon.getImage(), 100, 5, null);
		g.drawString(": " + (-(Gold.goldstack.size() - Collisions.goldCount)) + "/12", 140, 20);
		g.drawImage(LaserIcon.laserIcon.getImage(), 225, 5, null);
		g.drawString(": Locked", 265, 20);
		g.drawImage(RocketIcon.rocketIcon.getImage(), 370, 5, null);
		g.drawString(": Locked", 405, 20);

		if (timerEasy.isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 505, 5, null);
			g.drawString(Constants.EASY, 540, 20);
		}

		if (timerMedium.isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 505, 5, null);
			g.drawString(Constants.MEDIUM, 540, 20);
		}

		if (timerHard.isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 505, 5, null);
			g.drawString(Constants.HARD, 540, 20);
		}

		if (InitObjects.timerEasy.isRunning() || InitObjects.timerMedium.isRunning()
				|| InitObjects.timerHard.isRunning()) {
			drawCollectGold(g);
		}

		g.drawString("IP FROM: " + InitObjects.CITY_AND_COUNTRY[0] + "/" + InitObjects.CITY_AND_COUNTRY[1], 650, 20);
		g.drawString("Bunker destroyed!", Bunker.bunkerObj.x, Bunker.bunkerObj.y);
		g.drawString("You're mine!", EvilHead.evilHead.x, EvilHead.evilHead.y);
		Bunker.bunkerObj.drawBunkerHit();
		EvilHead.evilHead.renderEvilHead(g);

	}

	private void drawL5Labels(Graphics g) {

		setFontStyle(g);

		g.drawString("Level: " + 4, 5, 20);
		g.drawImage(LaserIcon.laserIcon.getImage(), 95, 5, null);
		g.drawString(Constants.UNLOCKED, 135, 20);
		g.drawImage(RocketIcon.rocketIcon.getImage(), 255, 5, null);
		g.drawString(Constants.UNLOCKED, 295, 20);

		if (timerEasy.isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 420, 5, null);
			g.drawString(Constants.EASY, 460, 20);
		}

		if (timerMedium.isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 420, 5, null);
			g.drawString(Constants.MEDIUM, 460, 20);
		}

		if (timerHard.isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 420, 5, null);
			g.drawString(Constants.HARD, 460, 20);
		}

		if (InitObjects.timerEasy.isRunning() || InitObjects.timerMedium.isRunning()
				|| InitObjects.timerHard.isRunning()) {
			drawKillTheHead(g);
		}
		
		g.drawString("IP FROM: " + InitObjects.CITY_AND_COUNTRY[0] + "/" + InitObjects.CITY_AND_COUNTRY[1], 570, 20);
	}

	private void drawLifePlayerShip(Graphics g) {

		if (lifePlayerShip > 0) {
			PlayerShip.playerShip.renderShip(g);
		} else {
			ingame = false;
		}

		if (lifePlayerShip > 4) {
			g.drawString("GODMODE", PlayerShip.playerShip.x, PlayerShip.playerShip.y);
			PlayerShip.playerShip.godMode();
			drawNaviMap(g);
		}

		if (lifePlayerShip == 4) {
			g.drawString("Health: 100%", PlayerShip.playerShip.x, PlayerShip.playerShip.y);
			drawNaviMap(g);
		}

		if (lifePlayerShip == 3) {
			g.drawString("Health: 75%", PlayerShip.playerShip.x, PlayerShip.playerShip.y);
			drawExplosion(g);
			updateExplosions();
			drawNaviMap(g);
		}

		if (lifePlayerShip == 2) {
			g.drawString("Health: 50%", PlayerShip.playerShip.x, PlayerShip.playerShip.y);
			drawExplosion(g);
			updateExplosions();
			drawNaviMap(g);
		}

		if (lifePlayerShip == 1) {
			g.drawString("Health: 25%", PlayerShip.playerShip.x, PlayerShip.playerShip.y);
			drawExplosion(g);
			updateExplosions();
			drawNaviMap(g);
		}
	}

	private void drawNaviMap(Graphics g) {
		if (Constants.isMapVisible) {
			setFontStyle(g);
			mapEntity.draw(g);
			playerSign.draw(g);
			alienSign.draw(g);
			dragonSign.draw(g);
			g.drawString("Date/Time: " + DateFormat.getDateTimeInstance().format(new Date()), 1010, 23);
		}
	}

	private void drawLifeBunker(Graphics g) {
		if (lifeBunker < 10 && Gold.goldstack.size() > 0) {
			g.drawString("Health: 100%", Bunker.bunkerObj.x, Bunker.bunkerObj.y);
		}

		if (lifeBunker >= 10 && lifeBunker < 20 && Gold.goldstack.size() > 0) {
			g.drawString("Health: 80%", Bunker.bunkerObj.x, Bunker.bunkerObj.y);

		}

		if (lifeBunker >= 20 && lifeBunker < 30 && Gold.goldstack.size() > 0) {
			g.drawString("Health: 60%", Bunker.bunkerObj.x, Bunker.bunkerObj.y);
		}

		if (lifeBunker >= 30 && lifeBunker < 35) {
			LoadSounds.TAUNT.loop();
		}

		if (lifeBunker >= 35) {
			LoadSounds.TAUNT.stop();
		}

		if (lifeBunker >= 30 && lifeBunker < 40 && Gold.goldstack.size() > 0) {
			g.drawString("Health: 40%", Bunker.bunkerObj.x, Bunker.bunkerObj.y);
		}

		if (lifeBunker >= 40 && lifeBunker < 45) {
			LoadSounds.TAUNT.loop();
		}

		if (lifeBunker >= 45) {
			LoadSounds.TAUNT.stop();
		}

		if (lifeBunker >= 40 && lifeBunker < 50 && Gold.goldstack.size() > 0) {
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
			if (n.isVisible()) {
				g.drawImage(n.getImage(), n.getX(), n.getY(), this);
			}
		}

		final List<CanonBall> canons = EvilHead.evilHead.getCanons();

		for (final CanonBall n : canons) {
			if (n.isVisible()) {
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
			}
		}

		for (final Dragon dragon : Dragon.dragons) {
			if (dragon.isVisible()) {
				g.drawImage(dragon.getImage(), dragon.getX(), dragon.getY(), this);
			}
		}

		if (UpdateObjects.lifeBunker >= 50) {
			for (final Gold gold : Gold.goldstack) {
				if (gold.isVisible()) {
					g.drawImage(gold.getImage(), gold.getX(), gold.getY(), this);
				}
			}
		}

		if (Dragon.dragons.isEmpty() && (UpdateObjects.lifeBunker < 50 || Gold.goldstack.isEmpty())) {
			for (final HealthPack health : HealthPack.healthpacks) {
				if (health.isVisible()) {
					g.drawImage(health.getImage(), health.getX(), health.getY(), this);
				}
			}
		}
	}

	private void drawScene1(Graphics g) {
		if (EvilHead.evilHead.isVisible() && PlayerShip.playerShip.isVisible() && Crosshair.crosshair.isVisible()
				&& VolBtn.volButt.isVisible() && Bunker.bunkerObj.isVisible() && g.drawImage(bg1, 0, 0, null)) {
			g.drawImage(PlayerShip.playerShip.getImage(), PlayerShip.playerShip.getX(), PlayerShip.playerShip.getY(),
					this);
			g.drawImage(Crosshair.crosshair.getImage(), Crosshair.crosshair.getX(), Crosshair.crosshair.getY(), this);
			g.drawImage(EvilHead.evilHead.getImage(), EvilHead.evilHead.getX(), EvilHead.evilHead.getY(), this);
			EvilHead.evilHead.renderEvilHead(g);
			g.drawImage(VolBtn.volButt.getImage(), VolBtn.volButt.getX(), VolBtn.volButt.getY(), this);
			g.drawImage(Bunker.bunkerObj.getImage(), Bunker.bunkerObj.getX(), Bunker.bunkerObj.getY(), this);
		}
		if (SaveSign.saveSign.isVisible()) {
			g.drawImage(SaveSign.saveSign.getImage(), SaveSign.saveSign.getX(), SaveSign.saveSign.getY(), this);
		}
	}

	private void drawScene2(Graphics g) {
		if (EvilHead.evilHead.isVisible() && PlayerShip.playerShip.isVisible() && Crosshair.crosshair.isVisible()
				&& VolBtn.volButt.isVisible() && Bunker.bunkerObj.isVisible() && g.drawImage(bg2, 0, 0, null)) {
			g.drawImage(PlayerShip.playerShip.getImage(), PlayerShip.playerShip.getX(), PlayerShip.playerShip.getY(),
					this);
			g.drawImage(Crosshair.crosshair.getImage(), Crosshair.crosshair.getX(), Crosshair.crosshair.getY(), this);
			g.drawImage(EvilHead.evilHead.getImage(), EvilHead.evilHead.getX(), EvilHead.evilHead.getY(), this);
			EvilHead.evilHead.renderEvilHead(g);
			g.drawImage(VolBtn.volButt.getImage(), VolBtn.volButt.getX(), VolBtn.volButt.getY(), this);
			g.drawImage(Bunker.bunkerObj.getImage(), Bunker.bunkerObj.getX(), Bunker.bunkerObj.getY(), this);
		}
		if (SaveSign.saveSign.isVisible()) {
			g.drawImage(SaveSign.saveSign.getImage(), SaveSign.saveSign.getX(), SaveSign.saveSign.getY(), this);
		}
	}

	private void drawScene3And4(Graphics g) {
		if (EvilHead.evilHead.isVisible() && PlayerShip.playerShip.isVisible() && Crosshair.crosshair.isVisible()
				&& VolBtn.volButt.isVisible() && Bunker.bunkerObj.isVisible() && g.drawImage(bg3, 0, 0, null)) {
			g.drawImage(PlayerShip.playerShip.getImage(), PlayerShip.playerShip.getX(), PlayerShip.playerShip.getY(),
					this);
			g.drawImage(Crosshair.crosshair.getImage(), Crosshair.crosshair.getX(), Crosshair.crosshair.getY(), this);
			g.drawImage(EvilHead.evilHead.getImage(), EvilHead.evilHead.getX(), EvilHead.evilHead.getY(), this);
			EvilHead.evilHead.renderEvilHead(g);
			g.drawImage(VolBtn.volButt.getImage(), VolBtn.volButt.getX(), VolBtn.volButt.getY(), this);
			g.drawImage(Bunker.bunkerObj.getImage(), Bunker.bunkerObj.getX(), Bunker.bunkerObj.getY(), this);
		}
		if (SaveSign.saveSign.isVisible()) {
			g.drawImage(SaveSign.saveSign.getImage(), SaveSign.saveSign.getX(), SaveSign.saveSign.getY(), this);
		}
	}

	private void drawGameOver(Graphics g) {
		setFontStyle(g);
		g.drawString("Monsters left: " + 0, 5, 20);
		g.drawString("Health: 0%", 200, 20);
		if (lifeBunker >= 50) {
			g.drawString("Gold: " + 0, 340, 20);
		}
	}

	private void drawKilledBy(Graphics g) {

		String msg = null;

		if (killedByAlien == true)
			msg = Entity.ALIEN.getMsg();
		if (killedByDragon == true)
			msg = Entity.DRAGON.getMsg();
		if (killedByBunker == true)
			msg = Entity.BUNKER.getMsg();
		if (killedByEvilHead == true)
			msg = Entity.EVILHEAD.getMsg();

		formatMsg(g, msg);
	}
		
	private void drawCollectGold(Graphics g) {
		final String msg = "Collect all the gold!";
		formatMsg(g, msg);
	}

	private void drawKillTheBunker(Graphics g) {
		final String msg = "Destroy the bunker!";
		formatMsg(g, msg);
	}

	private void drawKillTheHead(Graphics g) {
		final String msg = "Finally..Kill the evil head!";
		formatMsg(g, msg);
	}

	private void drawOuttaControl(Graphics g) {
		final String msg = "Dragons invasion brings the ship outta control...";
		formatMsg(g, msg);
	}

	private void drawGamePaused(Graphics g) {
		final String msg = "Game paused. Press an arrow.";
		formatMsg(g, msg);
	}

	private void formatMsg(Graphics g, final String msg) {
		Color bgColor = Color.BLACK;
		int width = msg != null ? g.getFontMetrics().stringWidth(msg) : 0;
		g.setColor(bgColor);
		g.fillRect(Constants.BUNKER_X, ((int) Constants.DIMENSION.getHeight() / 2) - 180, width, 50);
		setFontStyle(g);
		g.drawString(msg, Constants.BUNKER_X, ((int) Constants.DIMENSION.getHeight() / 2) - 150);
	}
	
	private void setFontStyle(Graphics g) {
		final Font small = new Font("Papyrus", Font.BOLD, 22);
		g.setColor(Color.ORANGE);
		g.setFont(small);
	}
}