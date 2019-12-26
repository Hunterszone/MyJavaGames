package game_engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.List;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import dbconn.HighScoreToDb;
import entities.Alien;
import entities.Bunker;
import entities.Crosshair;
import entities.Dragon;
import entities.EvilHead;
import entities.MyShip;
import frames.GameMenu;
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

	private static final long serialVersionUID = 1L;
	private static final String VOICENAME = "kevin16";
	private String unicode;
	private String checkMark;
	static boolean voiceStopped;
	static Voice voice;
	transient static Image bg1, bg2, bg3;
	public static boolean finMusicIsPlayed;
	public static boolean voiceInterruptor = false;

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
		bg1 = Toolkit.getDefaultToolkit().createImage("images/tenor.gif");
		bg2 = Toolkit.getDefaultToolkit().createImage("images/galaxy2.jpg");
		bg3 = Toolkit.getDefaultToolkit().createImage("images/galaxy3.jpg");
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (ingame && Alien.aliens.size() > 0) {

			if (GameMenu.autosave.isSelected() == true && voiceInterruptor == true) {
				GameMenu.savedOnL2 = false;
				GameMenu.savedOnL3 = false;
				GameMenu.savedOnL4 = false;
				GameMenu.savedOnL1 = true;
				initVoice("Autosave: ON!");
				voiceInterruptor = false;
			}

			setScene1(g);
			drawObjects(g);
			drawL1Labels(g);
			drawCountGold(g);
			drawLifeMyShip(g);
		}

		if (Alien.aliens.isEmpty()) {

			setScene2(g);

			Font small = new Font("Helvetica", Font.BOLD, 17);
			g.setColor(Color.white);
			g.setFont(small);

			if (GameMenu.autosave.isSelected() == true) {
				g.drawString("Autosave: ON", 790, 15);
				GameMenu.savedOnL1 = false;
				GameMenu.savedOnL3 = false;
				GameMenu.savedOnL4 = false;
				GameMenu.savedOnL2 = true;
				if (voiceInterruptor == true) {
					initVoice("Autosave: ON!");
					voiceInterruptor = false;
					return;
				}
			}

			if (GameMenu.autosave.isSelected() == false) {
				g.drawString("Autosave: OFF", 790, 15);
				voiceInterruptor = true;
			}

			if (timerEasy.isRunning()) {
				drawObjects(g);
				drawCountGold(g);
				drawL2LabelsEasy(g);
				drawLifeMyShip(g);
			}

			if (timerMedium.isRunning()) {
				drawObjects(g);
				drawCountGold(g);
				drawL2LabelsMedium(g);
				drawLifeMyShip(g);
			}

			if (timerHard.isRunning()) {
				drawObjects(g);
				drawCountGold(g);
				drawL2LabelsHard(g);
				drawLifeMyShip(g);
			}
		}

		if (Dragon.dragons.isEmpty()) {

			setScene3(g);
			Font small = new Font("Helvetica", Font.BOLD, 17);
			g.setColor(Color.white);
			g.setFont(small);

			if (GameMenu.autosave.isSelected() == true && lifeBunker < 50) {
				g.drawString("Autosave: ON", 810, 15);
				GameMenu.savedOnL1 = false;
				GameMenu.savedOnL2 = false;
				GameMenu.savedOnL4 = false;
				GameMenu.savedOnL3 = true;
				if (voiceInterruptor == true) {
					initVoice("Autosave: ON!");
					voiceInterruptor = false;
				}

			}
			if (GameMenu.autosave.isSelected() == false && lifeBunker < 50) {
				g.drawString("Autosave: OFF", 810, 15);
				voiceInterruptor = true;
			}
			drawObjects(g);
			drawCountGold(g);
			drawLifeBunker(g);
			drawLifeMyShip(g);
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
				g.drawString("Monsters left: " + 0, 5, 15);
				g.drawString("Gold: " + 0, 150, 15);
				g.drawString("Health: 0%", 230, 15);
				try {
					HighScoreToDb.main(null);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (lifeEvilHead == 50) {

				if (finMusicIsPlayed == false) {
					LoadSounds.gameWon.play();
					finMusicIsPlayed = true;
				}

				drawYouWon(g);
				g.drawString("Monsters: Killed!", 5, 15);
				g.drawString("Gold: Collected!", 165, 15);
			}

			return;
		}

		stateWeaponsL3(g);
		stateWeaponsL4(g);
		stateWeaponsBoss(g);
		evilHeadBehaviour();
		handleLifeEvilHead(g);

		Toolkit.getDefaultToolkit().sync();
	}

	private void handleLifeEvilHead(Graphics g) {
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
			if (EvilHead.evilHead.x - MyShip.myShip.x > 800) {
				MyShip.myShip.shipShaked();
				Crosshair.crosshair.crosShaked();
				MyShip.myShip.y = EvilHead.evilHead.y + 70;
				MyShip.myShip.loadImage("images/magnetic.png");
				MyShip.myShip.getImageDimensions();
			}
		}
	}

	private void stateWeaponsL3(Graphics g) {

		if (Dragon.dragons.isEmpty() && lifeBunker < 50 && ingame) {

			drawKillTheBunker(g);
			g.drawString("Missiles: Unlocked", 320, 15);
			g.drawString("Rockets: Unlocked", 490, 15);

			if (timerEasy.isRunning()) {

				g.drawString("Difficulty: Easy", 670, 15);
			}

			if (timerMedium.isRunning()) {

				g.drawString("Difficulty: Med", 670, 15);
			}

			if (timerHard.isRunning()) {

				g.drawString("Difficulty: Hard", 670, 15);
			}
		}
	}

	private void stateWeaponsL4(Graphics g) {

		if (Dragon.dragons.isEmpty() && lifeBunker >= 50 && Gold.goldstack.size() > 0 && ingame) {

			drawCollect(g);
			g.drawString("Missiles: Locked", 320, 15);
			g.drawString("Rockets: Locked", 480, 15);

			if (timerEasy.isRunning()) {
				g.drawString("Difficulty: Easy", 640, 15);
			}

			if (timerMedium.isRunning()) {
				g.drawString("Difficulty: Med", 640, 15);
			}

			if (timerHard.isRunning()) {
				g.drawString("Difficulty: Hard", 640, 15);
			}
		}

	}

	private void stateWeaponsBoss(Graphics g) {
		if (Dragon.dragons.isEmpty() && Gold.goldstack.isEmpty() && lifeBunker >= 50 && ingame) {

			drawKillTheHead(g);
			g.drawString("Missiles: Unlocked", 320, 15);
			g.drawString("Rockets: Unlocked", 490, 15);

			if (timerEasy.isRunning()) {
				g.drawString("Difficulty: Easy", 670, 15);
			}
			if (timerMedium.isRunning()) {
				g.drawString("Difficulty: Med", 670, 15);
			}
			if (timerHard.isRunning()) {
				g.drawString("Difficulty: Hard", 670, 15);
			}
		}

	}

	private void drawL1Labels(Graphics g) {

		if (Alien.aliens.size() > 0 && timerEasy.isRunning()) {

			Font small = new Font("Helvetica", Font.BOLD, 17);
			g.setColor(Color.white);
			g.setFont(small);

			if (GameMenu.autosave.isSelected() == true) {
				g.drawString("Autosave: ON", 790, 15);

			}

			if (GameMenu.autosave.isSelected() == false) {
				g.drawString("Autosave: OFF", 790, 15);
				voiceInterruptor = true;
			}

			g.drawString("Alien left: " + Alien.aliens.size(), 5, 15);
			g.drawString("Level: " + 1, 240, 15);
			g.drawString("Missiles: Unlocked", 320, 15);
			g.drawString("Rockets: Locked", 490, 15);
			g.drawString("Difficulty: Easy", 650, 15);
		}

		if (Alien.aliens.size() > 0 && timerMedium.isRunning()) {

			if (GameMenu.autosave.isSelected() == true) {
				g.drawString("Autosave: ON", 790, 15);

			}

			if (GameMenu.autosave.isSelected() == false) {
				g.drawString("Autosave: OFF", 790, 15);
				voiceInterruptor = true;
			}

			g.drawString("Alien left: " + Alien.aliens.size(), 5, 15);
			g.drawString("Level: " + 1, 240, 15);
			g.drawString("Missiles: Unlocked", 320, 15);
			g.drawString("Rockets: Locked", 490, 15);
			g.drawString("Difficulty: Med", 650, 15);

		}

		if (Alien.aliens.size() > 0 && timerHard.isRunning()) {

			if (GameMenu.autosave.isSelected() == true) {
				g.drawString("Autosave: ON", 790, 15);

			}

			if (GameMenu.autosave.isSelected() == false) {
				g.drawString("Autosave: OFF", 790, 15);
				voiceInterruptor = true;
			}

			g.drawString("Alien left: " + Alien.aliens.size(), 5, 15);
			g.drawString("Level: " + 1, 240, 15);
			g.drawString("Missiles: Unlocked", 320, 15);
			g.drawString("Rockets: Locked", 490, 15);
			g.drawString("Difficulty: Hard", 650, 15);

		}

	}

	private void drawL2LabelsEasy(Graphics g) {
		if (Dragon.dragons.size() > 0) {
			g.drawString("Dragonzz: " + Dragon.dragons.size(), 5, 15);
			g.drawString("Level: " + 2, 240, 15);
			g.drawString("Missiles: Locked", 320, 15);
			g.drawString("Rockets: Unlocked", 480, 15);
			g.drawString("Difficulty: Easy", 650, 15);
		}
		UpdateObjects.updateDragons();
		LoadSounds.roar.loop();
		if (Dragon.dragons.isEmpty() && lifeBunker < 50) {
			g.drawString("Dragonzz: " + checkMark, 5, 15);
			g.drawString("Level: " + 3, 240, 15);
			g.drawString("Missiles: Unlocked", 320, 15);
			g.drawString("Rockets: Unlocked", 490, 15);
			g.drawString("Difficulty: Easy", 670, 15);
		}
	}

	private void drawL2LabelsMedium(Graphics g) {
		if (Dragon.dragons.size() > 0) {
			g.drawString("Dragonzz: " + Dragon.dragons.size(), 5, 15);
			g.drawString("Level: " + 2, 240, 15);
			g.drawString("Missiles: Locked", 320, 15);
			g.drawString("Rockets: Unlocked", 480, 15);
			g.drawString("Difficulty: Med", 650, 15);
		}
		UpdateObjects.updateDragons();
		LoadSounds.roar.loop();
		if (Dragon.dragons.isEmpty() && lifeBunker < 50) {
			g.drawString("Dragonzz: " + checkMark, 5, 15);
			g.drawString("Level: " + 3, 240, 15);
			g.drawString("Missiles: Unlocked", 320, 15);
			g.drawString("Rockets: Unlocked", 490, 15);
			g.drawString("Difficulty: Med", 670, 15);
		}
	}

	private void drawL2LabelsHard(Graphics g) {
		if (Dragon.dragons.size() > 0) {
			g.drawString("Dragonzz: " + Dragon.dragons.size(), 5, 15);
			g.drawString("Level: " + 2, 240, 15);
			g.drawString("Missiles: Locked", 320, 15);
			g.drawString("Rockets: Unlocked", 480, 15);
			g.drawString("Difficulty: Hard", 650, 15);
			drawOuttaControl(g);
			MyShip.myShip.shipShaked();
			Crosshair.crosshair.crosShaked();
		}

		UpdateObjects.updateDragons();
		LoadSounds.roar.loop();
		if (Dragon.dragons.isEmpty() && lifeBunker < 50) {
			g.drawString("Dragonzz: " + checkMark, 5, 15);
			g.drawString("Level: " + 3, 240, 15);
			g.drawString("Missiles: Unlocked", 320, 15);
			g.drawString("Rockets: Unlocked", 490, 15);
			g.drawString("Difficulty: Hard", 670, 15);
		}
	}

	private void drawLifeMyShip(Graphics g) {

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
			g.drawString("Dragonzz: " + checkMark, 5, 15);
			g.drawString("Level: " + 3, 240, 15);
		}

		if (lifeBunker == 50) {

			Font small = new Font("Helvetica", Font.BOLD, 17);
			g.setColor(Color.white);
			g.setFont(small);

			if (GameMenu.autosave.isSelected() == true) {
				if (!Gold.goldstack.isEmpty())
					g.drawString("Autosave: ON", 790, 15);
				else
					g.drawString("Autosave: ON", 810, 15);
				GameMenu.savedOnL1 = false;
				GameMenu.savedOnL2 = false;
				GameMenu.savedOnL3 = false;
				GameMenu.savedOnL4 = true;
			}
			if (GameMenu.autosave.isSelected() == false) {
				if (!Gold.goldstack.isEmpty())
					g.drawString("Autosave: OFF", 790, 15);
				else
					g.drawString("Autosave: OFF", 810, 15);
				voiceInterruptor = true;
			}
			g.drawString("Dragonzz: " + checkMark, 5, 15);
			g.drawString("Level: " + 4, 240, 15);
			g.drawString("Bunker destroyed!", Bunker.bunkerObj.x, Bunker.bunkerObj.y);
			if (Gold.goldstack.size() > 0) {
				g.drawString("You're mine!", EvilHead.evilHead.x, EvilHead.evilHead.y);
			}

			Bunker.bunkerObj.drawBunkerHit();
		}
	}

	private void drawCountGold(Graphics g) {
		if (Gold.goldstack.size() > 0) {
			g.drawString("Gold: " + (-(Gold.goldstack.size() - 12)) + "/12", 140, 15);
		}

		if (Gold.goldstack.isEmpty()) {
			g.drawString("Gold: " + checkMark, 140, 15);
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

		for (Gold gold : Gold.goldstack) {
			if (gold.isVisible()) {
				g.drawImage(gold.getImage(), gold.getX(), gold.getY(), this);
			}
		}

		for (HealthPack health : HealthPack.healthpack) {
			if (health.isVisible()) {
				g.drawImage(health.getImage(), health.getX(), health.getY(), this);
			}
		}

		Font small = new Font("Helvetica", Font.BOLD, 17);
		g.setColor(Color.WHITE);
		g.setFont(small);

		unicode = "2713";
		checkMark = String.valueOf(Character.toChars(Integer.parseInt(unicode, 16)));

	}

	private void setScene1(Graphics g) {
		if (EvilHead.evilHead.isVisible() && MyShip.myShip.isVisible() && Crosshair.crosshair.isVisible()
				&& VolBtn.volButt.isVisible() && Bunker.bunkerObj.isVisible() && g.drawImage(bg1, 0, 0, null)) {
			g.drawImage(MyShip.myShip.getImage(), MyShip.myShip.getX(), MyShip.myShip.getY(), this);
			g.drawImage(Crosshair.crosshair.getImage(), Crosshair.crosshair.getX(), Crosshair.crosshair.getY(), this);
			g.drawImage(EvilHead.evilHead.getImage(), EvilHead.evilHead.getX(), EvilHead.evilHead.getY(), this);
			g.drawImage(VolBtn.volButt.getImage(), VolBtn.volButt.getX(), VolBtn.volButt.getY(), this);
			g.drawImage(Bunker.bunkerObj.getImage(), Bunker.bunkerObj.getX(), Bunker.bunkerObj.getY(), this);
			if (SaveSign.saveSign.isVisible())
				g.drawImage(SaveSign.saveSign.getImage(), SaveSign.saveSign.getX(), SaveSign.saveSign.getY(), this);
		}
	}

	private void setScene2(Graphics g) {
		if (EvilHead.evilHead.isVisible() && MyShip.myShip.isVisible() && Crosshair.crosshair.isVisible()
				&& VolBtn.volButt.isVisible() && Bunker.bunkerObj.isVisible() && g.drawImage(bg2, 0, 0, null)) {
			g.drawImage(MyShip.myShip.getImage(), MyShip.myShip.getX(), MyShip.myShip.getY(), this);
			g.drawImage(Crosshair.crosshair.getImage(), Crosshair.crosshair.getX(), Crosshair.crosshair.getY(), this);
			g.drawImage(EvilHead.evilHead.getImage(), EvilHead.evilHead.getX(), EvilHead.evilHead.getY(), this);
			g.drawImage(VolBtn.volButt.getImage(), VolBtn.volButt.getX(), VolBtn.volButt.getY(), this);
			g.drawImage(Bunker.bunkerObj.getImage(), Bunker.bunkerObj.getX(), Bunker.bunkerObj.getY(), this);
			if (SaveSign.saveSign.isVisible())
				g.drawImage(SaveSign.saveSign.getImage(), SaveSign.saveSign.getX(), SaveSign.saveSign.getY(), this);
		}
	}

	private void setScene3(Graphics g) {
		if (EvilHead.evilHead.isVisible() && MyShip.myShip.isVisible() && Crosshair.crosshair.isVisible()
				&& VolBtn.volButt.isVisible() && Bunker.bunkerObj.isVisible() && g.drawImage(bg3, 0, 0, null)) {
			g.drawImage(MyShip.myShip.getImage(), MyShip.myShip.getX(), MyShip.myShip.getY(), this);
			g.drawImage(Crosshair.crosshair.getImage(), Crosshair.crosshair.getX(), Crosshair.crosshair.getY(), this);
			g.drawImage(EvilHead.evilHead.getImage(), EvilHead.evilHead.getX(), EvilHead.evilHead.getY(), this);
			g.drawImage(VolBtn.volButt.getImage(), VolBtn.volButt.getX(), VolBtn.volButt.getY(), this);
			g.drawImage(Bunker.bunkerObj.getImage(), Bunker.bunkerObj.getX(), Bunker.bunkerObj.getY(), this);
			if (SaveSign.saveSign.isVisible())
				g.drawImage(SaveSign.saveSign.getImage(), SaveSign.saveSign.getX(), SaveSign.saveSign.getY(), this);
		}
	}

	private void drawGameOver(Graphics g) {

		String msg = "Game Over!";
		Font small = new Font("Helvetica", Font.BOLD, 17);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - 268 - fm.stringWidth(msg)) / 2, (B_HEIGHT - 272) / 2);
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

		Font small = new Font("Helvetica", Font.BOLD, 17);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - 268 - fm.stringWidth(msg)) / 2, (B_HEIGHT - 222) / 2);
	}

	private void drawCollect(Graphics g) {

		String msg = "Collect all the gold!";
		Font small = new Font("Helvetica", Font.BOLD, 17);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - 268 - fm.stringWidth(msg)) / 2, (B_HEIGHT - 272) / 2);
	}

	private void drawKillTheBunker(Graphics g) {

		String msg = "Destroy the bunker!";
		Font small = new Font("Helvetica", Font.BOLD, 17);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - 268 - fm.stringWidth(msg)) / 2, (B_HEIGHT - 272) / 2);

	}

	private void drawKillTheHead(Graphics g) {

		String msg = "Finally..Kill the evil head!";
		Font small = new Font("Helvetica", Font.BOLD, 17);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - 268 - fm.stringWidth(msg)) / 2, (B_HEIGHT - 272) / 2);
	}

	private void drawYouWon(Graphics g) {

		String msg = "You Won!";
		Font small = new Font("Helvetica", Font.BOLD, 17);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - 268 - fm.stringWidth(msg)) / 2, (B_HEIGHT - 272) / 2);
	}

	private void drawOuttaControl(Graphics g) {

		String msg = "Dragons invasion brings the ship outta control...";
		Font small = new Font("Helvetica", Font.BOLD, 17);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - 268 - fm.stringWidth(msg)) / 2, (B_HEIGHT - 272) / 2);
	}

}