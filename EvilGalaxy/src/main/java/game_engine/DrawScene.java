package game_engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.sql.SQLException;
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
import entities.TheEndAnimation;
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
import main.Main;
import util.Constants;
import util.LoadSounds;
import util.TextToSpeech;

public class DrawScene extends UpdateObjects {

	transient static Image bg1, bg2, bg3;
	private static final long serialVersionUID = 1L;

	public DrawScene() {
		Main.dim = Toolkit.getDefaultToolkit().getScreenSize();
		final int width = (int) Main.dim.getWidth();
		final int height = (int) Main.dim.getHeight();
		bg1 = Toolkit.getDefaultToolkit().createImage(Images.BG1.getImg())
				.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		bg2 = Toolkit.getDefaultToolkit().createImage(Images.BG2.getImg())
				.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		bg3 = Toolkit.getDefaultToolkit().createImage(Images.BG3.getImg())
				.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}

	private void drawExplosion(Graphics g) {
		for (final Explosion explosion : explosions) {
			explosion.draw(g);
		}
	}

	private void drawAstronaut(Graphics g) {
		final AffineTransform backup = ((Graphics2D) g).getTransform();
		AffineTransform a = AffineTransform.getRotateInstance(360, 400, 200);
		a.rotate(Math.toRadians(Math.ceil(Math.random())), (double)AstronautAnimation.astronautAnim.getX() / 2,
				(double)AstronautAnimation.astronautAnim.getY() / 2);
		((Graphics2D) g).setTransform(a);
		g.drawImage(AstronautAnimation.astronautAnim.getImage(), AstronautAnimation.astronautAnim.getX(),
				AstronautAnimation.astronautAnim.getY(), this);
		a = AffineTransform.getRotateInstance(181, 850, 700);
		a.rotate(Math.toRadians(Math.ceil(Math.random())), (double)AstronautAnimation.astronautAnim.getX() / 2,
				(double)AstronautAnimation.astronautAnim.getY() / 2);
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
		a.rotate(Math.toRadians(Math.ceil(Math.random())), (double)SatelliteAnimation.starAnim.getX() / 2,
				(double)SatelliteAnimation.starAnim.getY() / 2);
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
		for (final TheEndAnimation elonAnimUp : TheEndAnimation.theEndAnimationsUp) {
			g.drawImage(elonAnimUp.getImage(), elonAnimUp.getX(), elonAnimUp.getY(), this);
			Toolkit.getDefaultToolkit().sync();
		}
	}

	private void drawElonsDown(Graphics g) {
		for (final TheEndAnimation elonAnimDown : TheEndAnimation.theEndAnimationsDown) {
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
			drawGameOverBgs(g);
			handleGameOver(g);
			handleGameWon(g);
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

	private void stopTimers() {
		LoadSounds.BG_MUSIC.stop();
		LoadSounds.HIT.stop();
		LoadSounds.TAUNT.stop();
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

			drawYouWon(g);
			g.drawString("Monsters: Killed!", 5, 20);
			g.drawString("Gold: Collected!", 210, 20);
		}
	}

	private void handleGameOver(Graphics g) {
		if (lifePlayerShip > 6) {
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
		for (TheEndAnimation elonAnimUp : TheEndAnimation.theEndAnimationsUp) {
			if (elonAnimUp != null)
				elonAnimUp = null;
		}
		TheEndAnimation.theEndAnimationsUp.clear();
		for (TheEndAnimation elonAnimDown : TheEndAnimation.theEndAnimationsDown) {
			if (elonAnimDown != null)
				elonAnimDown = null;
		}
		TheEndAnimation.theEndAnimationsDown.clear();
	}

	@SuppressWarnings("finally")
	private void handleKilledByEvilHead() {
		if (Collisions.killedByEvilHead == true) {
			if (InitObjects.ingame == false) {
				TextToSpeech.playVoice("Killed by the Evil Head!");
				TextToSpeech.voiceInterruptor = true;
				Collisions.killedByEvilHead = false;
				try {
					HighScoreToDb.initDbConn();
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
		if (Collisions.killedByBunker == true) {
			if (InitObjects.ingame == false) {
				TextToSpeech.playVoice("Killed by the bunker!");
				TextToSpeech.voiceInterruptor = true;
				Collisions.killedByBunker = false;
				try {
					HighScoreToDb.initDbConn();
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
		if (Collisions.killedByDragon == true) {
			if (InitObjects.ingame == false) {
				TextToSpeech.playVoice("Killed by a dragon!");
				TextToSpeech.voiceInterruptor = true;
				Collisions.killedByDragon = false;
				try {
					HighScoreToDb.initDbConn();
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
		if (Collisions.killedByAlien == true) {
			if (InitObjects.ingame == false) {
				TextToSpeech.playVoice("Killed by an alien!");
				TextToSpeech.voiceInterruptor = true;
				Collisions.killedByAlien = false;
				try {
					HighScoreToDb.initDbConn();
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
				&& (EvilHead.evilHead.x - PlayerShip.playerOne.x == 100
						|| EvilHead.evilHead.x - PlayerShip.playerOne.x == 200
						|| EvilHead.evilHead.x - PlayerShip.playerOne.x == 300
						|| EvilHead.evilHead.x - PlayerShip.playerOne.x == 400)) {

			EvilHead.evilHead.strikeHead();

			if (timerEasy.isRunning()) {
				EvilHead.evilHead.throwCanons();
			}
			if (timerMedium.isRunning() || timerHard.isRunning()) {
				EvilHead.evilHead.throwPlasmaBalls();
			}
		}

		if (Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && lifeBunker >= 50) {
			if (EvilHead.evilHead.x - PlayerShip.playerOne.x > 810) {
				PlayerShip.playerOne.shipShaked();
				Crosshair.crosshair.crosShaked();
				PlayerShip.playerOne.y = EvilHead.evilHead.y + 70;
				PlayerShip.playerOne.loadImage(Images.MYSHIPPULLED.getImg());
				PlayerShip.playerOne.getImageDimensions();
			}
		}
	}

	private void drawGameStateL1(Graphics g) {
		drawScene1(g);
		drawL1Labels(g);
		drawObjects(g);
		drawlifePlayerShip(g);
		drawAsteroids(g);

		if (!(InitObjects.timerEasy.isRunning() || InitObjects.timerMedium.isRunning()
				|| InitObjects.timerHard.isRunning())) {
			drawGamePaused(g);
		}
	}

	private void drawGameStateL2(Graphics g) {
		drawScene2(g);
		drawL2Labels(g);
		drawObjects(g);
		drawlifePlayerShip(g);
		drawSatellite(g);

		if (!(InitObjects.timerEasy.isRunning() || InitObjects.timerMedium.isRunning()
				|| InitObjects.timerHard.isRunning())) {
			drawGamePaused(g);
		}
	}

	private void drawGameStateL3(Graphics g) {
		drawScene3And4(g);
		drawL3Labels(g);
		drawObjects(g);
		drawLifeBunker(g);
		drawlifePlayerShip(g);
		drawAstronaut(g);

		if (!(InitObjects.timerEasy.isRunning() || InitObjects.timerMedium.isRunning()
				|| InitObjects.timerHard.isRunning())) {
			drawGamePaused(g);
		}

		LoadSounds.TAUNT.stop();
	}

	private void drawGameStateL4(Graphics g) {
		drawScene3And4(g);
		drawL4Labels(g);
		drawObjects(g);
		drawlifePlayerShip(g);
		
		if (!(InitObjects.timerEasy.isRunning() || InitObjects.timerMedium.isRunning()
				|| InitObjects.timerHard.isRunning())) {
			drawGamePaused(g);
		}
	}

	private void drawGameStateL5(Graphics g) {
		drawScene3And4(g);
		drawL5Labels(g);
		drawObjects(g);
		drawlifePlayerShip(g);
		
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
			PlayerShip.playerOne.shipShaked();
			Crosshair.crosshair.crosShaked();
		}

		g.drawString("EvilHead", EvilHead.evilHead.x, EvilHead.evilHead.y);

		if (timerEasy.isRunning() || timerMedium.isRunning() || timerHard.isRunning()) {
			UpdateObjects.updateDragons();
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

		g.drawString("EvilHead", EvilHead.evilHead.x, EvilHead.evilHead.y);
	}

	private void drawL4Labels(Graphics g) {
		
		setFontStyle(g);

		g.drawString("Level: " + 4, 5, 20);
		g.drawImage(GoldIcon.goldIcon.getImage(), 100, 5, null);
		g.drawString(": " + (-(Gold.goldstack.size() - 12)) + "/12", 140, 20);
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
	}

	private void drawlifePlayerShip(Graphics g) {

		if (lifePlayerShip >= 3) {
			PlayerShip.playerOne.renderShip(g);
		}

		if (lifePlayerShip < 3) {
			g.drawString("GODMODE", PlayerShip.playerOne.x, PlayerShip.playerOne.y);
			PlayerShip.playerOne.godMode();
			drawExplosion(g);
			updateExplosions();
		}

		if (lifePlayerShip == 3) {
			g.drawString("Health: 100%", PlayerShip.playerOne.x, PlayerShip.playerOne.y);
		}

		if (lifePlayerShip == 4) {
			g.drawString("Health: 75%", PlayerShip.playerOne.x, PlayerShip.playerOne.y);
			drawExplosion(g);
			updateExplosions();
		}

		if (lifePlayerShip == 5) {
			g.drawString("Health: 50%", PlayerShip.playerOne.x, PlayerShip.playerOne.y);
			drawExplosion(g);
			updateExplosions();
		}

		if (lifePlayerShip == 6) {
			g.drawString("Health: 25%", PlayerShip.playerOne.x, PlayerShip.playerOne.y);
			drawExplosion(g);
			updateExplosions();
		}

		if (lifePlayerShip > 6) {
			ingame = false;
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

		final List<ShipMissile> missiles = PlayerShip.playerOne.getMissiles();

		for (final ShipMissile m : missiles) {

			if (m.isVisible()) {
				g.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}
		}

		final List<ShipRocket> rs = PlayerShip.playerOne.getRockets();

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

		Bunker.bullets = Bunker.bunkerObj.getBulletsLeft();

		for (final BunkerBullet n : Bunker.bullets) {

			if (n.isVisible()) {
				g.drawImage(n.getImage(), n.getX(), n.getY(), this);
			}
		}

		Bunker.bullets2 = Bunker.bunkerObj.getBulletsRight();

		for (final BunkerBullet n : Bunker.bullets2) {

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
			for (final HealthPack health : HealthPack.healthpack) {
				if (health.isVisible()) {
					g.drawImage(health.getImage(), health.getX(), health.getY(), this);
				}
			}
		}
	}

	private void drawScene1(Graphics g) {
		if (EvilHead.evilHead.isVisible() && PlayerShip.playerOne.isVisible() && Crosshair.crosshair.isVisible()
				&& VolBtn.volButt.isVisible() && Bunker.bunkerObj.isVisible() && g.drawImage(bg1, 0, 0, null)) {
			g.drawImage(PlayerShip.playerOne.getImage(), PlayerShip.playerOne.getX(), PlayerShip.playerOne.getY(),
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
		if (EvilHead.evilHead.isVisible() && PlayerShip.playerOne.isVisible() && Crosshair.crosshair.isVisible()
				&& VolBtn.volButt.isVisible() && Bunker.bunkerObj.isVisible() && g.drawImage(bg2, 0, 0, null)) {
			g.drawImage(PlayerShip.playerOne.getImage(), PlayerShip.playerOne.getX(), PlayerShip.playerOne.getY(),
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
		if (EvilHead.evilHead.isVisible() && PlayerShip.playerOne.isVisible() && Crosshair.crosshair.isVisible()
				&& VolBtn.volButt.isVisible() && Bunker.bunkerObj.isVisible() && g.drawImage(bg3, 0, 0, null)) {
			g.drawImage(PlayerShip.playerOne.getImage(), PlayerShip.playerOne.getX(), PlayerShip.playerOne.getY(),
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
		final String msg = "Game Over!";
		setFontStyle(g);
		g.drawString(msg, Constants.BUNKER_X + 30, ((int) Constants.getCoordinates().getHeight() / 2) - 150);
		g.drawString("Monsters left: " + 0, 5, 20);
		g.drawString("Health: 0%", 200, 20);
		if (lifeBunker >= 50) {
			g.drawString("Gold: " + 0, 340, 20);
		}
	}

	private void drawKilledBy(Graphics g) {
		
		String msg = null;
		
		if (Collisions.killedByAlien == true)
			msg = "Killed by an alien!";
		if (Collisions.killedByDragon == true)
			msg = "Killed by a dragon!";
		if (Collisions.killedByBunker == true)
			msg = "Killed by the bunker!";
		if (Collisions.killedByEvilHead == true)
			msg = "Killed by the Evil Head!";

		setFontStyle(g);
		g.drawString(msg, Constants.BUNKER_X, ((int) Constants.getCoordinates().getHeight() / 2) - 125);
	}

	private void drawCollectGold(Graphics g) {
		final String msg = "Collect all the gold!";
		setFontStyle(g);
		g.drawString(msg, Constants.BUNKER_X, ((int) Constants.getCoordinates().getHeight() / 2) - 150);
	}

	private void drawKillTheBunker(Graphics g) {
		final String msg = "Destroy the bunker!";
		setFontStyle(g);
		g.drawString(msg, Constants.BUNKER_X, ((int) Constants.getCoordinates().getHeight() / 2) - 150);

	}

	private void drawKillTheHead(Graphics g) {
		final String msg = "Finally..Kill the evil head!";
		setFontStyle(g);
		g.drawString(msg, Constants.BUNKER_X, ((int) Constants.getCoordinates().getHeight() / 2) - 150);
	}

	private void drawYouWon(Graphics g) {
		final String msg = "You Won!";
		setFontStyle(g);
		g.drawString(msg, Constants.BUNKER_X, ((int) Constants.getCoordinates().getHeight() / 2) - 150);
	}

	private void drawOuttaControl(Graphics g) {
		final String msg = "Dragons invasion brings the ship outta control...";
		setFontStyle(g);
		g.drawString(msg, Constants.BUNKER_X - 100, ((int) Constants.getCoordinates().getHeight() / 2) - 150);
	}

	private void drawGamePaused(Graphics g) {
		final String msg = "Game paused";
		setFontStyle(g);
		g.drawString(msg, Constants.BUNKER_X, ((int) Constants.getCoordinates().getHeight() / 2) - 150);
	}
	
	private void setFontStyle(Graphics g) {
		final Font small = new Font("Papyrus", Font.BOLD, 22);
		g.setColor(Color.ORANGE);
		g.setFont(small);
	}
}