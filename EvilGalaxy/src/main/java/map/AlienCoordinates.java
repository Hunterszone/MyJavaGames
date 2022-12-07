package map;

import java.awt.Graphics;

import javax.swing.JLabel;

import enemy.Alien;
import util.Constants;

public class AlienCoordinates extends JLabel implements Coordinates {

	private static final long serialVersionUID = -3143464978804920679L;

	@Override
	public void draw(Graphics g) {
		for (int i = 0; i < Alien.aliens.size(); i++) {			
			g.drawImage(Alien.aliens.get(i).getImage(), limitX(), limitY(), 20, 20, this);
		}
		repaint();
	}

	@Override
	public int limitX() {
		if (Alien.aliens.size() > 0) {
			for (Alien alien : Alien.aliens) {
				if (alien.getX() > Constants.BUNKER_X + 300) {
					return Constants.BUNKER_X + 700;
				}
				if (alien.getX() > Constants.BUNKER_X && alien.getX() < Constants.B_WIDTH + 300) {
					return Constants.BUNKER_X + 625;
				}
				if (alien.getX() > Constants.BUNKER_X - 300 && alien.getX() < Constants.BUNKER_X) {
					return Constants.BUNKER_X + 550;
				}
			}
		}
		return Constants.BUNKER_X + 700;
	}

	@Override
	public int limitY() {
		if (Alien.aliens.size() > 0) {
			for (Alien alien : Alien.aliens) {
				if (alien.getY() > Constants.BUNKER_Y - 730
						&& alien.getY() < Constants.BUNKER_Y - 530) {
					return Constants.BUNKER_Y - 150;
				}
				if (alien.getY() > Constants.BUNKER_Y - 530
						&& alien.getY() < Constants.BUNKER_Y - 330) {
					return Constants.BUNKER_Y - 75;
				}
				if (alien.getY() > Constants.BUNKER_Y - 330) {
					return Constants.BUNKER_Y;
				}				
			}
		}
		return Constants.BUNKER_Y - 180;
	}
}
