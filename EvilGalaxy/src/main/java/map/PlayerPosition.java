package map;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import util.Constants;

public class PlayerPosition extends JPanel {

	private static final long serialVersionUID = 2735514298436311987L;

	public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 255, 128));
		g.drawRect(Constants.B_WIDTH+200, Constants.B_HEIGHT-380, 200, 200);
		repaint();
	}
}
