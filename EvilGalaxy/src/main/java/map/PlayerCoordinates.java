package map;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

import entities.PlayerShip;
import util.Constants;

public class PlayerCoordinates extends JLabel {

	private static final long serialVersionUID = 2735514298436311987L;

	public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 255, 128));
		g.drawImage(PlayerShip.playerOne.getImage(), limitX(), limitY(), 50, 50, this);
		repaint();
	}

	private int limitX() {
		if (PlayerShip.playerOne.getX() > Constants.BUNKER_X + 300) {
			return Constants.BUNKER_X + 700;
		}
		if (PlayerShip.playerOne.getX() > Constants.BUNKER_X
				&& PlayerShip.playerOne.getX() < Constants.BUNKER_X + 300) {
			return Constants.BUNKER_X + 625;
		}
		if (PlayerShip.playerOne.getX() > Constants.BUNKER_X - 300
				&& PlayerShip.playerOne.getX() < Constants.BUNKER_X) {
			return Constants.BUNKER_X + 550;
		}
		return Constants.BUNKER_X + 550;
	}

	private int limitY() {
		if (PlayerShip.playerOne.getY() > Constants.BUNKER_Y - 730
				&& PlayerShip.playerOne.getY() < Constants.BUNKER_Y - 530) {
			return Constants.BUNKER_Y - 150;
		}
		if (PlayerShip.playerOne.getY() > Constants.BUNKER_Y - 530
				&& PlayerShip.playerOne.getY() < Constants.BUNKER_Y - 330) {
			return Constants.BUNKER_Y - 75;
		}
		if (PlayerShip.playerOne.getY() > Constants.BUNKER_Y - 330) {
			return Constants.BUNKER_Y;
		}
		return Constants.BUNKER_Y;
	}
}
