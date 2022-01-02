package map;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

import entities.Alien;
import entities.Dragon;
import util.Constants;

public class DragonCoordinates extends JLabel {

	private static final long serialVersionUID = 2355593682440642728L;

	public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 255, 128));
		for (int i = 0; i < Dragon.dragons.size(); i++) {			
			g.drawImage(Dragon.dragons.get(i).getImage(), limitX(), limitY(), 30, 30, this);
		}
		repaint();
	}

	private int limitX() {
		if (Alien.aliens.isEmpty() && Dragon.dragons.size() > 0) {
			for (Dragon dragon : Dragon.dragons) {
				if (dragon.getX() > Constants.B_WIDTH - 300) {
					return Constants.B_WIDTH + 350;
				}
				if (dragon.getX() > Constants.B_WIDTH - 600 && dragon.getX() < Constants.B_WIDTH - 300) {
					return Constants.B_WIDTH + 275;
				}
				if (dragon.getX() > Constants.B_WIDTH - 900 && dragon.getX() < Constants.B_WIDTH - 600) {
					return Constants.B_WIDTH + 200;
				}
			}
			return Constants.B_WIDTH + 350;
		}
		return 0;
	}

	private int limitY() {
		if (Alien.aliens.isEmpty() && Dragon.dragons.size() > 0) {
			for (Dragon dragon : Dragon.dragons) {
				if (dragon.getY() > Constants.B_HEIGHT - 930
						&& dragon.getY() < Constants.B_HEIGHT - 730) {
					return Constants.B_HEIGHT - 380;
				}
				if (dragon.getY() > Constants.B_HEIGHT - 730
						&& dragon.getY() < Constants.B_HEIGHT - 530) {
					return Constants.B_HEIGHT - 305;
				}
				if (dragon.getY() > Constants.B_HEIGHT - 530) {
					return Constants.B_HEIGHT - 230;
				}				
			}
			return Constants.B_HEIGHT - 380;
		}
		return 0;
	}
}
