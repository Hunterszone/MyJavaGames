package sokoban;

import java.awt.Image;
import java.awt.Toolkit;

public class Area extends Actor {

	private Image image;
	public String img;

	public Area(int x, int y) {
		super(x, y);
		img = "images/holder.png";
		image = Toolkit.getDefaultToolkit().createImage(img);
		this.setImage(image);
	}
}