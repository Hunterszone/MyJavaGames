package game_engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.List;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import dbconn.HighScoreToDb;
import entities.Alien;
import entities.AsteroidsAnimation;
import entities.Bunker;
import entities.Crosshair;
import entities.Dragon;
import entities.EvilHead;
import entities.MyShip;
import entities.SatelliteAnimation;
import frames.GameMenuBar;
import frames.Main;
import items.BunkerBullet;
import items.CanonBall;
import items.FireBall;
import items.Gold;
import items.HealthPack;
import items.SaveSign;
import items.ShipMissile;
import items.ShipRocket;
import items.VolBtn;
import sound_engine.LoadSounds;

public class DrawScene extends UpdateObjects {

	// Constants
	private static final String VOICENAME = "kevin16";

	public static boolean finMusicIsPlayed;
	public static boolean voiceInterruptor = false;
	static boolean voiceStopped;
	static Voice voice;
	String difficulty = "Difficulty: ";
	transient static Image bg1, bg2, bg3;
	private static final long serialVersionUID = 1L;
//	private String unicode;
//	private String checkMark;

	public static void initVoice(String message) {
		VoiceManager vm = VoiceManager.getInstance();
		voice = vm.getVoice(VOICENAME);
		voice.allocate();
		voice.speak(message);

		if (voiceStopped == false) {
			voiceStopped = true;
		}
	}

	public DrawScene() {
		Main.dim = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) Main.dim.getWidth();
		int height = (int) Main.dim.getHeight();
		bg1 = Toolkit.getDefaultToolkit().createImage("images/tenor.gif");
		bg1 = bg1.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		bg2 = Toolkit.getDefaultToolkit().createImage("images/galaxy2.jpg");
		bg2 = bg2.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		bg3 = Toolkit.getDefaultToolkit().createImage("images/galaxy3.jpg");
		bg3 = bg3.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}

	private void drawStar(Graphics g) {
		g.drawImage(SatelliteAnimation.starAnim.getImage(), SatelliteAnimation.starAnim.getX(),
				SatelliteAnimation.starAnim.getY(), this);
		Toolkit.getDefaultToolkit().sync();
	}

	private void drawAsteroids(Graphics g) {
		for (AsteroidsAnimation asteroidsAnim : AsteroidsAnimation.asteroidsAnimations) {
			g.drawImage(asteroidsAnim.getImage(), asteroidsAnim.getX(), asteroidsAnim.getY(), this);
			Toolkit.getDefaultToolkit().sync();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (ingame && Alien.aliens.size() > 0) {

			drawScene1(g);
			drawObjects(g);
			drawL1Labels(g);
			drawCountGold(g);
			drawLifeMyShip(g);
			drawAsteroids(g);

			if (GameMenuBar.autosave.isSelected() == true && voiceInterruptor == true) {
				GameMenuBar.savedOnL2 = false;
				GameMenuBar.savedOnL3 = false;
				GameMenuBar.savedOnL4 = false;
				GameMenuBar.savedOnL1 = true;
				initVoice("Autosave: ON!");
				voiceInterruptor = false;
			}

		}

		if (ingame && Alien.aliens.isEmpty()) {

			drawScene2(g);
			setFontStyle(g);
			drawAsteroids(g);
			drawL2Labels(g);
			drawObjects(g);
			drawCountGold(g);
			drawLifeMyShip(g);

			if (GameMenuBar.autosave.isSelected() == true) {
				g.drawString("Autosave: ON", 1000, 20);
				GameMenuBar.savedOnL1 = false;
				GameMenuBar.savedOnL3 = false;
				GameMenuBar.savedOnL4 = false;
				GameMenuBar.savedOnL2 = true;
				if (voiceInterruptor == true) {
					initVoice("Autosave: ON!");
					voiceInterruptor = false;
					return;
				}
			}

			if (GameMenuBar.autosave.isSelected() == false) {
				g.drawString("Autosave: OFF", 1000, 20);
				voiceInterruptor = true;
			}

		}

		if (ingame && Dragon.dragons.isEmpty()) {

			drawScene3(g);
			setFontStyle(g);
			drawStar(g);
			drawObjects(g);
			drawCountGold(g);
			drawLifeBunker(g);
			drawLifeMyShip(g);
			
			if (GameMenuBar.autosave.isSelected() == true && lifeBunker < 50) {
				g.drawString("Autosave: ON", 870, 20);
				GameMenuBar.savedOnL1 = false;
				GameMenuBar.savedOnL2 = false;
				GameMenuBar.savedOnL4 = false;
				GameMenuBar.savedOnL3 = true;
				if (voiceInterruptor == true) {
					initVoice("Autosave: ON!");
					voiceInterruptor = false;
				}

			}
			
			if (GameMenuBar.autosave.isSelected() == false && lifeBunker < 50) {
				g.drawString("Autosave: OFF", 870, 20);
				voiceInterruptor = true;
			}
			
			LoadSounds.roar.stop();
		}

		if (!ingame) {
			LoadSounds.bgMusic.stop();
			LoadSounds.fuse.stop();
			LoadSounds.roar.stop();
			timerEasy.stop();
			timerMedium.stop();
			timerHard.stop();
			g.drawImage(bg1, 0, 0, null);

			if (lifeMyShip > 6) {

				if (finMusicIsPlayed == false) {
					LoadSounds.gameLost.play();
					finMusicIsPlayed = true;
				}

				drawGameOver(g);
				drawKilledBy(g);
				g.drawString("Monsters left: " + 0, 5, 20);
				g.drawString("Gold: " + 0, 200, 20);
				g.drawString("Health: 0%", 300, 20);
				
				if (Collisions.killedByAlien == true) {			
					if (InitObjects.ingame == false) {
						DrawScene.initVoice("Killed by an alien!");
						DrawScene.voiceInterruptor = true;
						Collisions.killedByAlien = false;
						return;
					}
				}
				
				if (Collisions.killedByDragon == true) {			
					if (InitObjects.ingame == false) {
						DrawScene.initVoice("Killed by a dragon!");
						DrawScene.voiceInterruptor = true;
						Collisions.killedByDragon = false;
						return;
					}
				}
				
				if (Collisions.killedByBunker == true) {			
					if (InitObjects.ingame == false) {
						DrawScene.initVoice("Killed by the bunker!");
						DrawScene.voiceInterruptor = true;
						Collisions.killedByBunker = false;
						return;
					}
				}
				
				if (Collisions.killedByEvilHead == true) {			
					if (InitObjects.ingame == false) {
						DrawScene.initVoice("Killed by the Evil Head!");
						DrawScene.voiceInterruptor = true;
						Collisions.killedByEvilHead = false;
						return;
					}
				}
				
				try {
					HighScoreToDb.initDbConn();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(SatelliteAnimation.starAnim != null) SatelliteAnimation.starAnim = null;
				for(AsteroidsAnimation asteroidsAnim : AsteroidsAnimation.asteroidsAnimations) {
					if(asteroidsAnim != null) asteroidsAnim = null;				
				}
				AsteroidsAnimation.asteroidsAnimations.clear();
			}

			if (lifeEvilHead == 50) {

				if (finMusicIsPlayed == false) {
					LoadSounds.gameWon.play();
					finMusicIsPlayed = true;
				}

				drawYouWon(g);
				g.drawString("Monsters: Killed!", 5, 20);
				g.drawString("Gold: Collected!", 210, 20);
			}

			return;
		}

		stateWeaponsL1(g);
		stateWeaponsL2(g);
		stateWeaponsL3(g);
		stateWeaponsL4(g);
		stateWeaponsBoss(g);
		evilHeadBehaviour();
		handleLifeEvilHead(g);

		Toolkit.getDefaultToolkit().sync();
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
			LoadSounds.roar.loop();
		}

		if (lifeEvilHead >= 35) {
			LoadSounds.roar.stop();
		}

		if (lifeEvilHead >= 30 && lifeEvilHead < 40) {
			g.drawString("Health: 40%", EvilHead.evilHead.x, EvilHead.evilHead.y);
		}

		if (lifeEvilHead >= 40 && lifeEvilHead < 45) {
			LoadSounds.roar.loop();
		}

		if (lifeEvilHead >= 45) {
			LoadSounds.roar.stop();
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
				&& EvilHead.evilHead.x - MyShip.myShip.x == 400 && timerEasy.isRunning()) {
			EvilHead.evilHead.throwCanons();
			EvilHead.evilHead.strikeHead();
		}

		if (Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && lifeBunker >= 50
				&& EvilHead.evilHead.x - MyShip.myShip.x == 400 && timerMedium.isRunning()) {
			EvilHead.evilHead.throwFireballs();
			EvilHead.evilHead.strikeHead();
		}

		if (Dragon.dragons.isEmpty() && Gold.goldstack.size() >= 0 && lifeBunker >= 50
				&& EvilHead.evilHead.x - MyShip.myShip.x == 400 && timerHard.isRunning()) {
			EvilHead.evilHead.throwFireballs();
			EvilHead.evilHead.strikeHead();
		}

		if (Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && lifeBunker >= 50) {
			if (EvilHead.evilHead.x - MyShip.myShip.x > 810) {
				MyShip.myShip.shipShaked();
				Crosshair.crosshair.crosShaked();
				MyShip.myShip.y = EvilHead.evilHead.y + 70;
				MyShip.myShip.loadImage(Images.MYSHIPPULLED.getImg());
				MyShip.myShip.getImageDimensions();
			}
		}
	}

	private void stateWeaponsL1(Graphics g) {
		if (Alien.aliens.size() > 0 && ingame) {

			setFontStyle(g);

			g.drawString("Aliens left: " + Alien.aliens.size(), 5, 20);
			g.drawString("Level: " + 1, 310, 20);
			g.drawString("Missiles: Unlocked", 410, 20);
			g.drawString("Rockets: Locked", 630, 20);
			g.drawString(difficulty, 820, 20);

			if (timerEasy.isRunning()) {
				g.drawString("Difficulty: Easy", 820, 20);
			}

			if (timerMedium.isRunning()) {
				g.drawString("Difficulty: Med", 820, 20);
			}

			if (timerHard.isRunning()) {
				g.drawString("Difficulty: Hard", 820, 20);
			}

			if (GameMenuBar.autosave.isSelected() == true) {
				g.drawString("Autosave: ON", 1000, 20);

			}

			if (GameMenuBar.autosave.isSelected() == false) {
				g.drawString("Autosave: OFF", 1020, 20);
				voiceInterruptor = true;
			}

		}
	}

	private void stateWeaponsL2(Graphics g) {
		if (Alien.aliens.isEmpty() && !Dragon.dragons.isEmpty() && ingame) {

			if (Dragon.dragons.size() > 0) {
				g.drawString("Dragonzz: " + Dragon.dragons.size(), 5, 20);
			}
			if (Dragon.dragons.isEmpty() && lifeBunker < 50) {
				g.drawString("Dragonzz: Yes", 5, 20);
			}

			g.drawString("Level: " + 2, 310, 20);
			g.drawString("Missiles: Locked", 410, 20);
			g.drawString("Rockets: Unlocked", 600, 20);
			g.drawString(difficulty, 810, 20);

			if (timerEasy.isRunning()) {
				g.drawString("Difficulty: Easy", 810, 20);
			}

			if (timerMedium.isRunning()) {
				g.drawString("Difficulty: Med", 810, 20);
			}

			if (timerHard.isRunning()) {
				g.drawString("Difficulty: Hard", 810, 20);
			}
		}
	}

	private void stateWeaponsL3(Graphics g) {

		if (Dragon.dragons.isEmpty() && lifeBunker < 50 && ingame) {

			drawKillTheBunker(g);
			g.drawString("Missiles: Locked", 270, 20);
			g.drawString("Rockets: Unlocked", 470, 20);
			g.drawString(difficulty, 680, 20);

			if (timerEasy.isRunning()) {

				g.drawString("Difficulty: Easy", 680, 20);
			}

			if (timerMedium.isRunning()) {

				g.drawString("Difficulty: Med", 680, 20);
			}

			if (timerHard.isRunning()) {

				g.drawString("Difficulty: Hard", 680, 20);
			}
		}
	}

	private void stateWeaponsL4(Graphics g) {

		if (Dragon.dragons.isEmpty() && lifeBunker >= 50 && Gold.goldstack.size() > 0 && ingame) {

			drawCollect(g);
			g.drawString("Missiles: Locked", 260, 20);
			g.drawString("Rockets: Locked", 450, 20);
			g.drawString(difficulty, 640, 20);

			if (timerEasy.isRunning()) {
				g.drawString("Difficulty: Easy", 640, 20);
			}

			if (timerMedium.isRunning()) {
				g.drawString("Difficulty: Med", 640, 20);
			}

			if (timerHard.isRunning()) {
				g.drawString("Difficulty: Hard", 640, 20);
			}
		}

	}

	private void stateWeaponsBoss(Graphics g) {
		if (Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && lifeBunker >= 50 && ingame) {

			drawKillTheHead(g);
			g.drawString("Missiles: Unlocked", 240, 20);
			g.drawString("Rockets: Unlocked", 460, 20);
			g.drawString(difficulty, 680, 20);

			if (timerEasy.isRunning()) {
				g.drawString("Difficulty: Easy", 680, 20);
			}
			if (timerMedium.isRunning()) {
				g.drawString("Difficulty: Med", 680, 20);
			}
			if (timerHard.isRunning()) {
				g.drawString("Difficulty: Hard", 680, 20);
			}
		}

	}

	private void drawL1Labels(Graphics g) {

		if (Alien.aliens.size() > 0) {

			setFontStyle(g);

			g.drawString("Aliens left: " + Alien.aliens.size(), 5, 20);
			g.drawString("Level: " + 1, 310, 20);
			g.drawString("Missiles: Unlocked", 410, 20);
			g.drawString("Rockets: Locked", 630, 20);

			if (timerEasy.isRunning()) {
				g.drawString("Difficulty: Easy", 820, 20);
			}

			if (timerMedium.isRunning()) {
				g.drawString("Difficulty: Med", 820, 20);
			}

			if (timerHard.isRunning()) {
				g.drawString("Difficulty: Hard", 820, 20);
			}

			if (GameMenuBar.autosave.isSelected() == true) {
				g.drawString("Autosave: ON", 1020, 20);
			}

			if (GameMenuBar.autosave.isSelected() == false) {
				g.drawString("Autosave: OFF", 1020, 20);
				voiceInterruptor = true;
			}
		}
	}

	private void drawL2Labels(Graphics g) {

		if (Dragon.dragons.size() > 0) {
			g.drawString("Dragonzz: " + Dragon.dragons.size(), 5, 20);
			g.drawString("Level: " + 2, 310, 20);
			g.drawString("Missiles: Locked", 410, 20);
			g.drawString("Rockets: Unlocked", 600, 20);
			
			if (timerEasy.isRunning()) {
				g.drawString("Difficulty: Easy", 810, 20);
			}

			if (timerMedium.isRunning()) {
				g.drawString("Difficulty: Med", 810, 20);
			}

			if (timerHard.isRunning()) {
				g.drawString("Difficulty: Hard", 810, 20);
				drawOuttaControl(g);
				MyShip.myShip.shipShaked();
				Crosshair.crosshair.crosShaked();			
			}
		}
		
		if (timerEasy.isRunning() || 
			timerMedium.isRunning() || 
			timerHard.isRunning() ) {
			UpdateObjects.updateDragons();
			LoadSounds.roar.loop();
		}
	}

	private void drawLifeMyShip(Graphics g) {

		if (lifeMyShip >= 3) {
			MyShip.myShip.renderShip(g);
		}

		if (lifeMyShip < 3) {
			g.drawString("GODMODE!", MyShip.myShip.x, MyShip.myShip.y);
			MyShip.myShip.godMode();
		}

		if (lifeMyShip == 3) {
			g.drawString("Health: 100%", MyShip.myShip.x, MyShip.myShip.y);
		}

		if (lifeMyShip == 4) {
			g.drawString("Health: 75%", MyShip.myShip.x, MyShip.myShip.y);
		}

		if (lifeMyShip == 5) {
			g.drawString("Health: 50%", MyShip.myShip.x, MyShip.myShip.y);
		}

		if (lifeMyShip == 6) {
			g.drawString("Health: 25%", MyShip.myShip.x, MyShip.myShip.y);
		}

		if (lifeMyShip > 6) {
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
			LoadSounds.roar.loop();
		}

		if (lifeBunker >= 35) {
			LoadSounds.roar.stop();
		}

		if (lifeBunker >= 30 && lifeBunker < 40 && Gold.goldstack.size() > 0) {
			g.drawString("Health: 40%", Bunker.bunkerObj.x, Bunker.bunkerObj.y);
		}

		if (lifeBunker >= 40 && lifeBunker < 45) {
			LoadSounds.roar.loop();
		}

		if (lifeBunker >= 45) {
			LoadSounds.roar.stop();
		}

		if (lifeBunker >= 40 && lifeBunker < 50 && Gold.goldstack.size() > 0) {
			g.drawString("Health: 20%", Bunker.bunkerObj.x, Bunker.bunkerObj.y);
		}

		if (lifeBunker < 50) {
//			g.drawString("Dragonzz: Yes", 5, 20);
			g.drawString("Level: " + 3, 160, 20);
		}

		if (lifeBunker == 50) {

			setFontStyle(g);

			if (GameMenuBar.autosave.isSelected() == true) {
				if (!Gold.goldstack.isEmpty())
					g.drawString("Autosave: ON", 830, 20);
				else
					g.drawString("Autosave: ON", 860, 20);
				GameMenuBar.savedOnL1 = false;
				GameMenuBar.savedOnL2 = false;
				GameMenuBar.savedOnL3 = false;
				GameMenuBar.savedOnL4 = true;
			}
			if (GameMenuBar.autosave.isSelected() == false) {
				if (!Gold.goldstack.isEmpty())
					g.drawString("Autosave: OFF", 830, 20);
				else
					g.drawString("Autosave: OFF", 860, 20);
				voiceInterruptor = true;
			}
//			g.drawString("Dragonzz: Yes", 5, 20);
			g.drawString("Level: " + 4, 150, 20);
			g.drawString("Bunker destroyed!", Bunker.bunkerObj.x, Bunker.bunkerObj.y);
			if (Gold.goldstack.size() > 0) {
				EvilHead.evilHead.renderEvilHead(g);
				g.drawString("You're mine!", EvilHead.evilHead.x, EvilHead.evilHead.y);
			}

			Bunker.bunkerObj.drawBunkerHit();
		}
	}

	private void drawCountGold(Graphics g) {
		if (!Alien.aliens.isEmpty() || !Dragon.dragons.isEmpty()) {
			g.drawString("Gold: " + (-(Gold.goldstack.size() - 12)) + "/12", 170, 20);
		}
		if (Dragon.dragons.isEmpty()) {
			g.drawString("Gold: " + (-(Gold.goldstack.size() - 12)) + "/12", 5, 20);
		}
	}

	private void drawObjects(Graphics g) {

		List<ShipMissile> ms = MyShip.myShip.getMissiles();

		for (ShipMissile m : ms) {

			if (m.isVisible()) {
				g.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}
		}

		List<ShipRocket> rs = MyShip.myShip.getRockets();

		for (ShipRocket r : rs) {
			if (r.isVisible()) {
				g.drawImage(r.getImage(), r.getX(), r.getY(), this);
			}
		}

		List<FireBall> guner = EvilHead.evilHead.getEvilMissiles();

		for (FireBall n : guner) {

			if (n.isVisible()) {
				g.drawImage(n.getImage(), n.getX(), n.getY(), this);
			}
		}

		List<CanonBall> can = EvilHead.evilHead.getCanons();

		for (CanonBall n : can) {

			if (n.isVisible()) {
				g.drawImage(n.getImage(), n.getX(), n.getY(), this);
			}
		}

		List<BunkerBullet> bull = Bunker.bunkerObj.getBullets();

		for (BunkerBullet n : bull) {

			if (n.isVisible()) {
				g.drawImage(n.getImage(), n.getX(), n.getY(), this);
			}
		}

		List<BunkerBullet> bull2 = Bunker.bunkerObj.getBullets2();

		for (BunkerBullet n : bull2) {

			if (n.isVisible()) {
				g.drawImage(n.getImage(), n.getX(), n.getY(), this);
			}
		}

		for (Alien alien : Alien.aliens) {
			if (alien.isVisible()) {
				g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
			}
		}

		for (Dragon dragon : Dragon.dragons) {
			if (dragon.isVisible()) {
				g.drawImage(dragon.getImage(), dragon.getX(), dragon.getY(), this);
			}
		}

		if(UpdateObjects.lifeBunker >= 50) {			
			for (Gold gold : Gold.goldstack) {
				if (gold.isVisible()) {
					g.drawImage(gold.getImage(), gold.getX(), gold.getY(), this);
				}
			}
		}

		if (UpdateObjects.lifeBunker >= 50 && Gold.goldstack.isEmpty()) {
			for (HealthPack health : HealthPack.healthpack) {
				if (health.isVisible()) {
					g.drawImage(health.getImage(), health.getX(), health.getY(), this);
				}
			}
		}

		setFontStyle(g);

//		unicode = "2713";
//		checkMark = String.valueOf(Character.toChars(Integer.parseInt(unicode, 16)));

	}

	private void drawScene1(Graphics g) {
		if (EvilHead.evilHead.isVisible() && MyShip.myShip.isVisible() && Crosshair.crosshair.isVisible()
				&& VolBtn.volButt.isVisible() && Bunker.bunkerObj.isVisible() && g.drawImage(bg1, 0, 0, null)) {
			g.drawImage(MyShip.myShip.getImage(), MyShip.myShip.getX(), MyShip.myShip.getY(), this);
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
		if (EvilHead.evilHead.isVisible() && MyShip.myShip.isVisible() && Crosshair.crosshair.isVisible()
				&& VolBtn.volButt.isVisible() && Bunker.bunkerObj.isVisible() && g.drawImage(bg2, 0, 0, null)) {
			g.drawImage(MyShip.myShip.getImage(), MyShip.myShip.getX(), MyShip.myShip.getY(), this);
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

	private void drawScene3(Graphics g) {
		if (EvilHead.evilHead.isVisible() && MyShip.myShip.isVisible() && Crosshair.crosshair.isVisible()
				&& VolBtn.volButt.isVisible() && Bunker.bunkerObj.isVisible() && g.drawImage(bg3, 0, 0, null)) {
			g.drawImage(MyShip.myShip.getImage(), MyShip.myShip.getX(), MyShip.myShip.getY(), this);
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

		String msg = "Game Over!";
		setFontStyle(g);
		g.drawString(msg, InitObjects.BUNKER_X + 30, (B_HEIGHT - 272) / 2);
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
		g.drawString(msg, InitObjects.BUNKER_X, (B_HEIGHT - 222) / 2);
	}

	private void drawCollect(Graphics g) {

		String msg = "Collect all the gold!";
		setFontStyle(g);
		g.drawString(msg, InitObjects.BUNKER_X, (B_HEIGHT - 272) / 2);
	}

	private void drawKillTheBunker(Graphics g) {

		String msg = "Destroy the bunker!";
		setFontStyle(g);
		g.drawString(msg, InitObjects.BUNKER_X, (B_HEIGHT - 272) / 2);

	}

	private void drawKillTheHead(Graphics g) {

		String msg = "Finally..Kill the evil head!";
		setFontStyle(g);
		g.drawString(msg, InitObjects.BUNKER_X, (B_HEIGHT - 272) / 2);
	}

	private void drawYouWon(Graphics g) {

		String msg = "You Won!";
		setFontStyle(g);
		g.drawString(msg, InitObjects.BUNKER_X, (B_HEIGHT - 272) / 2);
	}

	private void drawOuttaControl(Graphics g) {

		String msg = "Dragons invasion brings the ship outta control...";
		setFontStyle(g);
		g.drawString(msg, InitObjects.BUNKER_X - 100, (B_HEIGHT - 272) / 2);
	}

	private void setFontStyle(Graphics g) {
		Font small = new Font("Papyrus", Font.BOLD, 22);
		g.setColor(Color.YELLOW);
		g.setFont(small);
	}

}