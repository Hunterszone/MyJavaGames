package map;

import java.awt.Graphics;

import javax.swing.JLabel;

import enemy.PlayerShip;
import util.Constants;
import util.Drawable;

public class PlayerCoordinates extends JLabel implements Drawable {

	private static final long serialVersionUID = 2735514298436311987L;

	@Override
	public void draw(Graphics g) {
		g.drawImage(PlayerShip.playerShip.getImage(), limitX(), limitY(), 50, 50, this);
		repaint();
	}

	private int limitX() {
		if (PlayerShip.playerShip.getX() > Constants.BUNKER_X + 300) {
			return Constants.BUNKER_X + 700;
		}
		if (PlayerShip.playerShip.getX() > Constants.BUNKER_X
				&& PlayerShip.playerShip.getX() < Constants.BUNKER_X + 300) {
			return Constants.BUNKER_X + 625;
		}
		if (PlayerShip.playerShip.getX() > Constants.BUNKER_X - 300
				&& PlayerShip.playerShip.getX() < Constants.BUNKER_X) {
			return Constants.BUNKER_X + 550;
		}
		return Constants.BUNKER_X + 550;
	}

	private int limitY() {
		if (PlayerShip.playerShip.getY() > Constants.BUNKER_Y - 630
				&& PlayerShip.playerShip.getY() < Constants.BUNKER_Y - 430) {
			return Constants.BUNKER_Y - 150;
		}
		if (PlayerShip.playerShip.getY() > Constants.BUNKER_Y - 430
				&& PlayerShip.playerShip.getY() < Constants.BUNKER_Y - 230) {
			return Constants.BUNKER_Y - 75;
		}
		if (PlayerShip.playerShip.getY() > Constants.BUNKER_Y - 230) {
			return Constants.BUNKER_Y;
		}
		return Constants.BUNKER_Y - 150;
	}
}
