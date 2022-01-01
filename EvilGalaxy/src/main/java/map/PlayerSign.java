package map;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

import entities.PlayerShip;
import util.Constants;

public class PlayerSign extends JLabel {

	private static final long serialVersionUID = 2735514298436311987L;

	public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 255, 128));
		g.drawRect(limitX(), limitY(), 50, 50);
		repaint();
	}
	
	private int limitX() {
		if (PlayerShip.playerOne.getX() > Constants.B_WIDTH-300) {
			return Constants.B_WIDTH+350;
		}
		if (PlayerShip.playerOne.getX() > Constants.B_WIDTH-600 
				&& PlayerShip.playerOne.getX() < Constants.B_WIDTH-300) {
			return Constants.B_WIDTH+275;
		}
		if (PlayerShip.playerOne.getX() > Constants.B_WIDTH-900 
				&& PlayerShip.playerOne.getX() < Constants.B_WIDTH-600) {
			return Constants.B_WIDTH+200;
		}
		return Constants.B_WIDTH+200;
	}
	
	private int limitY() {
		if (PlayerShip.playerOne.getY() > Constants.B_HEIGHT-930
				&& PlayerShip.playerOne.getY() < Constants.B_HEIGHT-730) {
			return Constants.B_HEIGHT-380;
		}
		if (PlayerShip.playerOne.getY() > Constants.B_HEIGHT-730
				&& PlayerShip.playerOne.getY() < Constants.B_HEIGHT-530) {
			return Constants.B_HEIGHT-305;
		}
		if (PlayerShip.playerOne.getY() > Constants.B_HEIGHT-530) {
			return Constants.B_HEIGHT-230;
		}
		return Constants.B_HEIGHT-380;
	}
}
