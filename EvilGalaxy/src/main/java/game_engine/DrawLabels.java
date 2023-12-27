package game_engine;

import java.awt.Graphics;

import enemy.Alien;
import enemy.Bunker;
import enemy.Dragon;
import enemy.EvilHead;
import icons.AlienIcon;
import icons.DifficultyIcon;
import icons.DragonIcon;
import icons.GoldIcon;
import icons.LaserIcon;
import icons.LocationIcon;
import icons.RocketIcon;
import items.Gold;
import util.ActionPerformer;
import util.Constants;
import util.Drawable;
import util.LivesAndCounts;

public class DrawLabels {
	
	private static final String CITY = Constants.CITY_AND_COUNTRY[0];
	private static final String COUNTRY = Constants.CITY_AND_COUNTRY[1];

	static void drawL1Labels(Graphics g) {

		Drawable.setFontStyle(g);

		g.drawString(Constants.LEVEL + 1, 5, 20);
		g.drawImage(AlienIcon.alienIcon.getImage(), 100, 5, null);
		g.drawString(Constants.COLON_WITH_SPACE + Alien.aliens.size(), 140, 20);
		g.drawImage(LaserIcon.laserIcon.getImage(), 200, 5, null);
		g.drawString(Constants.UNLOCKED, 240, 20);
		g.drawImage(RocketIcon.rocketIcon.getImage(), 370, 5, null);
		g.drawString(Constants.LOCKED, 410, 20);

		if (ActionPerformer.getTimerEasy().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 510, 5, null);
			g.drawString(Constants.EASY, 545, 20);
		}

		if (ActionPerformer.getTimerMedium().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 510, 5, null);
			g.drawString(Constants.MEDIUM, 545, 20);
		}

		if (ActionPerformer.getTimerHard().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 510, 5, null);
			g.drawString(Constants.HARD, 545, 20);
		}

		drawLocation(g, 650);
	}
	
	static void drawL2Labels(Graphics g) {

		Drawable.setFontStyle(g);

		g.drawString(Constants.LEVEL + 2, 5, 20);
		g.drawImage(DragonIcon.dragonIcon.getImage(), 100, 5, null);
		g.drawString(Constants.COLON_WITH_SPACE + Dragon.dragons.size(), 140, 20);
		g.drawImage(LaserIcon.laserIcon.getImage(), 190, 5, null);
		g.drawString(Constants.LOCKED, 230, 20);
		g.drawImage(RocketIcon.rocketIcon.getImage(), 340, 5, null);
		g.drawString(Constants.UNLOCKED, 375, 20);

		if (ActionPerformer.getTimerEasy().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 500, 5, null);
			g.drawString(Constants.EASY, 540, 20);
		}

		if (ActionPerformer.getTimerMedium().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 500, 5, null);
			g.drawString(Constants.MEDIUM, 540, 20);
		}

		if (ActionPerformer.getTimerHard().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 500, 5, null);
			g.drawString(Constants.HARD, 540, 20);
		}

		drawLocation(g, 650);
	}

	static void drawL3Labels(Graphics g) {

		Drawable.setFontStyle(g);

		g.drawString(Constants.LEVEL + 3, 5, 20);
		g.drawImage(LaserIcon.laserIcon.getImage(), 95, 5, null);
		g.drawString(Constants.LOCKED, 135, 20);
		g.drawImage(RocketIcon.rocketIcon.getImage(), 235, 5, null);
		g.drawString(Constants.UNLOCKED, 275, 20);

		if (ActionPerformer.getTimerEasy().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 400, 5, null);
			g.drawString(Constants.EASY, 440, 20);
		}

		if (ActionPerformer.getTimerMedium().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 400, 5, null);
			g.drawString(Constants.MEDIUM, 440, 20);
		}

		if (ActionPerformer.getTimerHard().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 400, 5, null);
			g.drawString(Constants.HARD, 440, 20);
		}
		
		drawLocation(g, 550);
	}

	static void drawL4Labels(Graphics g) {

		Drawable.setFontStyle(g);

		g.drawString(Constants.LEVEL + 4, 5, 20);
		g.drawImage(GoldIcon.goldIcon.getImage(), 100, 5, null);
		g.drawString(Constants.COLON_WITH_SPACE + (-(Gold.goldstack.size() - LivesAndCounts.getGoldCount())) + "/12", 140,
				20);
		g.drawImage(LaserIcon.laserIcon.getImage(), 225, 5, null);
		g.drawString(Constants.LOCKED, 265, 20);
		g.drawImage(RocketIcon.rocketIcon.getImage(), 370, 5, null);
		g.drawString(Constants.LOCKED, 405, 20);

		if (ActionPerformer.getTimerEasy().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 505, 5, null);
			g.drawString(Constants.EASY, 540, 20);
		}

		if (ActionPerformer.getTimerMedium().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 505, 5, null);
			g.drawString(Constants.MEDIUM, 540, 20);
		}

		if (ActionPerformer.getTimerHard().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 505, 5, null);
			g.drawString(Constants.HARD, 540, 20);
		}

		drawLocation(g, 650);

		g.drawString("Bunker destroyed!", Bunker.bunkerObj.x, Bunker.bunkerObj.y);
		g.drawString("You're mine!", EvilHead.evilHead.x, EvilHead.evilHead.y);
		Bunker.bunkerObj.drawBunkerHit();
		EvilHead.evilHead.renderEvilHead(g);
	}

	static void drawL5Labels(Graphics g) {

		Drawable.setFontStyle(g);

		g.drawString(Constants.LEVEL + 4, 5, 20);
		g.drawImage(LaserIcon.laserIcon.getImage(), 95, 5, null);
		g.drawString(Constants.UNLOCKED, 135, 20);
		g.drawImage(RocketIcon.rocketIcon.getImage(), 265, 5, null);
		g.drawString(Constants.UNLOCKED, 295, 20);

		if (ActionPerformer.getTimerEasy().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 420, 5, null);
			g.drawString(Constants.EASY, 460, 20);
		}

		if (ActionPerformer.getTimerMedium().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 420, 5, null);
			g.drawString(Constants.MEDIUM, 460, 20);
		}

		if (ActionPerformer.getTimerHard().isRunning()) {
			g.drawImage(DifficultyIcon.difficultyIcon.getImage(), 420, 5, null);
			g.drawString(Constants.HARD, 460, 20);
		}
		
		drawLocation(g, 570);
	}
	
	private static void drawLocation(Graphics g, int xAxis) {
		g.drawImage(LocationIcon.locationIcon.getImage(), xAxis, 0, null);
		g.drawString(" : " + CITY + "/" + COUNTRY, xAxis+20, 20);
	}
}
