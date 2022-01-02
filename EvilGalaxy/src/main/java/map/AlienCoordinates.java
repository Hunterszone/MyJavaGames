package map;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

import entities.Alien;
import util.Constants;

public class AlienCoordinates extends JLabel {

	private static final long serialVersionUID = -3143464978804920679L;

	public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 255, 128));
		for (int i = 0; i < Alien.aliens.size(); i++) {			
			g.drawImage(Alien.aliens.get(i).getImage(), limitX(), limitY(), 20, 20, this);
		}
		repaint();
	}

	private int limitX() {
		if (Alien.aliens.size() > 0) {
			for (Alien alien : Alien.aliens) {
				if (alien.getX() > Constants.B_WIDTH - 300) {
					return Constants.B_WIDTH + 350;
				}
				if (alien.getX() > Constants.B_WIDTH - 600 && alien.getX() < Constants.B_WIDTH - 300) {
					return Constants.B_WIDTH + 275;
				}
				if (alien.getX() > Constants.B_WIDTH - 900 && alien.getX() < Constants.B_WIDTH - 600) {
					return Constants.B_WIDTH + 200;
				}
			}
		}
		return Constants.B_WIDTH + 350;
	}

	private int limitY() {
		if (Alien.aliens.size() > 0) {
			for (Alien alien : Alien.aliens) {
				if (alien.getY() > Constants.B_HEIGHT - 930
						&& alien.getY() < Constants.B_HEIGHT - 730) {
					return Constants.B_HEIGHT - 380;
				}
				if (alien.getY() > Constants.B_HEIGHT - 730
						&& alien.getY() < Constants.B_HEIGHT - 530) {
					return Constants.B_HEIGHT - 305;
				}
				if (alien.getY() > Constants.B_HEIGHT - 530) {
					return Constants.B_HEIGHT - 230;
				}				
			}
		}
		return Constants.B_HEIGHT - 380;
	}
}
