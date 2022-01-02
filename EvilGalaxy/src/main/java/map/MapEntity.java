package map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import util.Constants;

public class MapEntity extends JPanel {
	
	public MapEntity() throws IOException {
		
	}

	private static final long serialVersionUID = 2735514298436311987L;
	private BufferedImage mapImg = ImageIO.read(new File("images/compass.png"));
	private Image scaledMap = mapImg.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	
	public void draw(Graphics g) {
		g.drawImage(scaledMap, Constants.BUNKER_X + 550, Constants.BUNKER_Y - 150, 200, 200, this);
		repaint();
	}
}
