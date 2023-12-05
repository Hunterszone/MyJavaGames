package game_engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.commons.lang.StringUtils;

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
import util.ActionPerformer;
import util.Constants;
import util.Drawable;
import util.Flags;
import util.LoadSounds;
import util.OpenAiApiImpl;
import util.TextToSpeech;

public class DrawScene extends JPanel implements ActionListener, Runnable {

	private static final long serialVersionUID = 160473770719678686L;
	private static final String PRESS_AN_ARROW_TO_CONTINUE = "Press an arrow to continue...";
	private static final String KILLED_BY_THE_EVIL_HEAD = "Killed by the EvilHead!";
	private static final String KILLED_BY_THE_BUNKER = "Killed by the bunker!";
	private static final String KILLED_BY_A_DRAGON = "Killed by a dragon!";
	private static final String KILLED_BY_AN_ALIEN = "Killed by an alien!";
	private static final int DELAY = 15;

	private MapEntity mapEntity;
	private PlayerCoordinates playerSign;
	private EvilHeadCoordinates evilHeadSign;
	private AlienCoordinates alienSign;
	private DragonCoordinates dragonSign;

	private transient Image bg1, bg2, bg3;

	public DrawScene() throws IOException, NoSuchAlgorithmException {
		final int width = (int) Constants.DIMENSION.getWidth();
		final int height = (int) Constants.DIMENSION.getHeight();
		bg1 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(Sprite.BG1.getImg()))
				.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		bg2 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(Sprite.BG2.getImg()))
				.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		bg3 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(Sprite.BG3.getImg()))
				.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		mapEntity = new MapEntity();
		playerSign = new PlayerCoordinates();
		evilHeadSign = new EvilHeadCoordinates();
		alienSign = new AlienCoordinates();
		dragonSign = new DragonCoordinates();

		new InitObjects();
		new UpdateObjects();

		addKeyListener(new Controls());
		setFocusable(true);
		setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));

		ActionPerformer.setAnimator(new Thread(this));
		ActionPerformer.setTimerEasy(new Timer(DELAY, this));
		ActionPerformer.setTimerMedium(new Timer(DELAY, this));
		ActionPerformer.setTimerHard(new Timer(DELAY, this));
		ActionPerformer.getTimerEasy().start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (ActionPerformer.isIngame()) {
			startAnimations();
			drawGameStates(g);
			evilHeadBehaviour();
			handleLifeEvilHead(g);
			drawNaviMap(g);
			drawDateTime(g);
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

	private void startAnimations() {
		if (ActionPerformer.getAnimator() == null) {
			ActionPerformer.setAnimator(new Thread(this));
			ActionPerformer.getAnimator().start();
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
		ActionPerformer.getTimerEasy().stop();
		ActionPerformer.getTimerMedium().stop();
		ActionPerformer.getTimerHard().stop();
	}

	private void drawGameWon(Graphics g) {
		if (UpdateObjects.getLifeEvilHead() == 50) {

			DrawAnimations.drawElonsUp(g, this);
			DrawAnimations.drawElonsDown(g, this);

			if (!TextToSpeech.finMusicIsPlayed) {
				new PlayWave1st(SoundEffect.VICTORY.getSound()).start();
				TextToSpeech.finMusicIsPlayed = true;
			}

			Drawable.setFontStyle(g);
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
			DrawAnimations.handleAnimations();
			ActionPerformer.setAnimator(null);
		}
	}

	@SuppressWarnings("finally")
	private void handleKilledByEvilHead() {
		if (Flags.killedByEvilHead) {
			if (!ActionPerformer.isIngame()) {
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
			if (!ActionPerformer.isIngame()) {
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
			if (!ActionPerformer.isIngame()) {
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
			if (!ActionPerformer.isIngame()) {
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
			if (ActionPerformer.getTimerHard().isRunning() && !Constants.isOnPause) {
				g.drawString(formatMsg(g, Constants.SHIP_OUTTA_CONTROL), Constants.BUNKER_X,
						((int) Constants.DIMENSION.getHeight() / 2) - 150);
			}
		}

		if (Dragon.dragons.isEmpty() && UpdateObjects.getLifeBunker() < 50) {
			drawGameStateL3(g);
			if (!Constants.isOnPause) {
				g.drawString(formatMsg(g, Constants.DESTROY_THE_BUNKER), Constants.BUNKER_X,
						((int) Constants.DIMENSION.getHeight() / 2) - 150);
			}
		}

		if (Dragon.dragons.isEmpty() && UpdateObjects.getLifeBunker() >= 50 && Gold.goldstack.size() > 0) {
			drawGameStateL4(g);
			if (!Constants.isOnPause) {
				g.drawString(formatMsg(g, Constants.COLLECT_ALL_THE_GOLD), Constants.BUNKER_X,
						((int) Constants.DIMENSION.getHeight() / 2) - 150);
			}
		}

		if (Dragon.dragons.isEmpty() && UpdateObjects.getLifeBunker() >= 50 && Gold.goldstack.isEmpty()) {
			drawGameStateL5(g);
			if (!Constants.isOnPause) {
				g.drawString(formatMsg(g, Constants.KILL_THE_EVIL_HEAD), Constants.BUNKER_X,
						((int) Constants.DIMENSION.getHeight() / 2) - 150);
			}
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
			ActionPerformer.setIngame(false);
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
		DrawLabels.drawL1Labels(g);
		drawObjects(g);
		drawLifePlayerShip(g);
		DrawAnimations.drawAsteroids(g, this);
		drawPressArrowKey(g);
	}

	private void drawGameStateL2(Graphics g) {
		drawScene2(g);
		DrawLabels.drawL2Labels(g);
		drawObjects(g);
		drawLifePlayerShip(g);
		DrawAnimations.drawSatellite(g, this);
		drawPressArrowKey(g);
	}

	private void drawGameStateL3(Graphics g) {
		drawScene3(g);
		DrawLabels.drawL3Labels(g);
		drawObjects(g);
		drawLifeBunker(g);
		drawLifePlayerShip(g);
		DrawAnimations.drawAstronaut(g, this);
		drawPressArrowKey(g);

		LoadSounds.TAUNT.stop();
	}

	private void drawGameStateL4(Graphics g) {
		drawScene4(g);
		DrawLabels.drawL4Labels(g);
		drawObjects(g);
		drawLifePlayerShip(g);
		drawPressArrowKey(g);
	}

	private void drawGameStateL5(Graphics g) {
		drawScene5(g);
		DrawLabels.drawL5Labels(g);
		drawObjects(g);
		drawLifePlayerShip(g);
		drawPressArrowKey(g);
	}

	private void drawPressArrowKey(Graphics g) {
		if (!(ActionPerformer.getTimerEasy().isRunning() || ActionPerformer.getTimerMedium().isRunning()
				|| ActionPerformer.getTimerHard().isRunning()) && Constants.isOnPause) {
			drawGamePaused(g);
		}
	}

	private void drawLifePlayerShip(Graphics g) {

		if (UpdateObjects.getLifePlayerShip() > 0 && PlayerShip.playerShip != null) {
			PlayerShip.playerShip.renderShip(g);
		} else {
			ActionPerformer.setIngame(false);
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
			mapEntity.draw(g);
			playerSign.draw(g);
			alienSign.draw(g);
			dragonSign.draw(g);
			evilHeadSign.draw(g);
		}
	}

	private void drawDateTime(Graphics g) {
		if (Flags.isClockVisible) {
			Drawable.setFontStyle(g);
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
		Drawable.setFontStyle(g);
		g.drawString("Monsters left: " + 0, 5, 20);
		g.drawString("Health: 0%", 200, 20);
		if (UpdateObjects.getLifeBunker() >= 50) {
			g.drawString("Gold: " + 0, 340, 20);
		}
	}

	private void drawKilledBy(Graphics g) {
		String msg = null;

		if (Flags.killedByAlien) {
			msg = retrieveOpenAiMessage(Entity.ALIEN, KILLED_BY_AN_ALIEN);
		} else if (Flags.killedByDragon) {
			msg = retrieveOpenAiMessage(Entity.DRAGON, KILLED_BY_A_DRAGON);
		} else if (Flags.killedByBunker) {
			msg = retrieveOpenAiMessage(Entity.BUNKER, KILLED_BY_THE_BUNKER);
		} else if (Flags.killedByEvilHead) {
			msg = retrieveOpenAiMessage(Entity.EVILHEAD, KILLED_BY_THE_EVIL_HEAD);
		}

		formatMsg(g, msg);
	}

	private String retrieveOpenAiMessage(Entity entity, String defaultMsg) {
		String openAiMsg = OpenAiApiImpl.createCompletion(entity.getMsg());
		return !StringUtils.isEmpty(openAiMsg) ? openAiMsg : defaultMsg;
	}

	private void drawGamePaused(Graphics g) {
		final String msg = PRESS_AN_ARROW_TO_CONTINUE;
		formatMsg(g, msg);
	}

	private String formatMsg(Graphics g, final String msg) {
		int width = msg != null ? g.getFontMetrics().stringWidth(msg) : 0;
		String[] tokens = null;
		g.setColor(Color.BLACK);
		g.fillRect(Constants.BUNKER_X, ((int) Constants.DIMENSION.getHeight() / 2) - 180, width, 50);
		Drawable.setFontStyle(g);
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
		return "";
	}

	@Override
	public void run() {

		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (ActionPerformer.isIngame() || ActionPerformer.getAnimator() != null) {

			if (SatelliteAnimation.SATELLITE != null) {
				SatelliteAnimation.SATELLITE.cycle();
			}

			if (AstronautAnimation.ASTRONAUT != null) {
				AstronautAnimation.ASTRONAUT.cycle();
			}

			if (AsteroidAnimation.ASTEROID_ANIMATION != null && !AsteroidAnimation.ASTEROID_ANIMATION.isEmpty()) {
				for (final AsteroidAnimation asteroidsAnim : AsteroidAnimation.ASTEROID_ANIMATION) {
					asteroidsAnim.cycle();
				}
			}

			if (TheEndAnimationUp.END_ANIMATION_UP != null && !TheEndAnimationUp.END_ANIMATION_UP.isEmpty()) {
				for (final TheEndAnimationUp endAnimationUp : TheEndAnimationUp.END_ANIMATION_UP) {
					endAnimationUp.cycle();
				}
			}

			if (TheEndAnimationDown.END_ANIMATION_DOWN != null && !TheEndAnimationDown.END_ANIMATION_DOWN.isEmpty()) {
				for (final TheEndAnimationDown endAnimationDown : TheEndAnimationDown.END_ANIMATION_DOWN) {
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

		if (ActionPerformer.getAnimator() != null) {
			ActionPerformer.getAnimator().start();
		}
	}

	@Override
	public void removeNotify() {
		super.removeNotify();

		if (ActionPerformer.getAnimator() != null) {
			ActionPerformer.getAnimator().interrupt();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		UpdateObjects.inGame();

		UpdateObjects.updateMyShip();
		UpdateObjects.updateMyCrosshair();
		UpdateObjects.updateMyShipMissiles();
		UpdateObjects.updateEHPlasmaBalls();
		UpdateObjects.updateEHCanons();
		UpdateObjects.updateRockets();
		UpdateObjects.updateAliens();
		UpdateObjects.updateEvilHead();
		UpdateObjects.updateGold();
		UpdateObjects.updateAngels();
		UpdateObjects.updateHealth();
		UpdateObjects.updateBullets();

		Collisions.checkAlienCollision(PlayerShip.playerShip.getBounds());
		Collisions.checkEvilHeadCollision(PlayerShip.playerShip.getBounds(), EvilHead.evilHead.getBounds());
		Collisions.checkBunkerCollision(PlayerShip.playerShip.getBounds(), Bunker.bunkerObj.getBounds());
		Collisions.checkGoldCollision(PlayerShip.playerShip.getBounds());
		Collisions.checkHealthCollision(PlayerShip.playerShip.getBounds());
		Collisions.checkMissileCollision(EvilHead.evilHead.getBounds());
		Collisions.checkRocketCollision(EvilHead.evilHead.getBounds(), Bunker.bunkerObj.getBounds());
		Collisions.checkPlasmaCollision(PlayerShip.playerShip.getBounds());
		Collisions.checkCanonCollision(PlayerShip.playerShip.getBounds());
		Collisions.checkBulletCollision(BunkerBullet.bulletsLeft);
		Collisions.checkBulletCollision(BunkerBullet.bulletsRight);

		repaint();
	}
}