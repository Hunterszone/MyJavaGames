package map;

import java.awt.Graphics;

import javax.swing.JLabel;

import enemy.Alien;
import enemy.Dragon;
import util.Constants;
import util.Drawable;

public class DragonCoordinates extends JLabel implements Drawable {

	private static final long serialVersionUID = 2355593682440642728L;

	@Override
	public void draw(Graphics g) {
		for (int i = 0; i < Dragon.dragons.size(); i++) {			
			g.drawImage(Dragon.dragons.get(i).getImage(), limitX(), limitY(), 40, 40, this);
		}
		repaint();
	}

	private int limitX() {
		if (Alien.aliens.isEmpty() && Dragon.dragons.size() > 0) {
			for (Dragon dragon : Dragon.dragons) {
				if (dragon.getX() > Constants.BUNKER_X + 300) {
					return Constants.BUNKER_X + 700;
				}
				if (dragon.getX() > Constants.BUNKER_X && dragon.getX() < Constants.BUNKER_X + 300) {
					return Constants.BUNKER_X + 625;
				}
				if (dragon.getX() > Constants.BUNKER_X - 300 && dragon.getX() < Constants.BUNKER_X) {
					return Constants.BUNKER_X + 550;
				}
			}
			return Constants.BUNKER_X + 550;
		}
		return -50;
	}

	private int limitY() {
		if (Alien.aliens.isEmpty() && Dragon.dragons.size() > 0) {
			for (Dragon dragon : Dragon.dragons) {
				if (dragon.getY() > Constants.BUNKER_Y - 730
						&& dragon.getY() < Constants.BUNKER_Y - 530) {
					return Constants.BUNKER_Y - 150;
				}
				if (dragon.getY() > Constants.BUNKER_Y - 530
						&& dragon.getY() < Constants.BUNKER_Y - 330) {
					return Constants.BUNKER_Y - 75;
				}
				if (dragon.getY() > Constants.BUNKER_Y - 330) {
					return Constants.BUNKER_Y;
				}				
			}
			return Constants.BUNKER_Y - 380;
		}
		return -50;
	}
}
