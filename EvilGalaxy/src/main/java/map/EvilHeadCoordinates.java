package map;

import java.awt.Graphics;

import javax.swing.JLabel;

import enemy.EvilHead;
import game_engine.UpdateObjects;
import util.Constants;
import util.Drawable;

public class EvilHeadCoordinates extends JLabel implements Drawable {

	private static final long serialVersionUID = 2735514298436311987L;

	@Override
	public void draw(Graphics g) {
		if (UpdateObjects.getLifeBunker() >= 50) {
			g.drawImage(EvilHead.evilHead.getImage(), limitX(), limitY(), 50, 50, this);
		}
		repaint();
	}

	private int limitX() {
		if (EvilHead.evilHead.getX() > Constants.BUNKER_X + 300) {
			return Constants.BUNKER_X + 700;
		}
		if (EvilHead.evilHead.getX() > Constants.BUNKER_X && EvilHead.evilHead.getX() < Constants.BUNKER_X + 300) {
			return Constants.BUNKER_X + 625;
		}
		if (EvilHead.evilHead.getX() > Constants.BUNKER_X - 300 && EvilHead.evilHead.getX() < Constants.BUNKER_X) {
			return Constants.BUNKER_X + 550;
		}
		return Constants.BUNKER_X + 550;
	}

	private int limitY() {
		if (EvilHead.evilHead.getY() > Constants.BUNKER_Y - 630
				&& EvilHead.evilHead.getY() < Constants.BUNKER_Y - 430) {
			return Constants.BUNKER_Y - 150;
		}
		if (EvilHead.evilHead.getY() > Constants.BUNKER_Y - 430
				&& EvilHead.evilHead.getY() < Constants.BUNKER_Y - 230) {
			return Constants.BUNKER_Y - 75;
		}
		if (EvilHead.evilHead.getY() > Constants.BUNKER_Y - 230) {
			return Constants.BUNKER_Y;
		}
		return Constants.BUNKER_Y - 150;
	}
}
