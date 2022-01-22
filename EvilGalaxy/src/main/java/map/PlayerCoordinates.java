package map;

import java.awt.Graphics;

import javax.swing.JLabel;

import entities.PlayerShip;
import util.Constants;

public class PlayerCoordinates extends JLabel implements Coordinates {

	private static final long serialVersionUID = 2735514298436311987L;

	@Override
	public void draw(Graphics g) {
		g.drawImage(PlayerShip.playerOne.getImage(), limitX(), limitY(), 50, 50, this);
		repaint();
	}

	@Override
	public int limitX() {
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

	@Override
	public int limitY() {
		if (PlayerShip.playerOne.getY() > Constants.BUNKER_Y - 630
				&& PlayerShip.playerOne.getY() < Constants.BUNKER_Y - 430) {
			return Constants.BUNKER_Y - 150;
		}
		if (PlayerShip.playerOne.getY() > Constants.BUNKER_Y - 430
				&& PlayerShip.playerOne.getY() < Constants.BUNKER_Y - 230) {
			return Constants.BUNKER_Y - 75;
		}
		if (PlayerShip.playerOne.getY() > Constants.BUNKER_Y - 230) {
			return Constants.BUNKER_Y;
		}
		return Constants.BUNKER_Y - 150;
	}
}
