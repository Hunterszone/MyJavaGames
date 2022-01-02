package map;

import java.awt.Graphics;

import javax.swing.JPanel;

import util.Constants;

public class MapEntity extends JPanel {
	
	private static final long serialVersionUID = 2735514298436311987L;
	
	public void draw(Graphics g) {
		g.drawRoundRect(Constants.BUNKER_X+550, Constants.BUNKER_Y-150, 200, 200, 5, 5);
		repaint();
	}
}
