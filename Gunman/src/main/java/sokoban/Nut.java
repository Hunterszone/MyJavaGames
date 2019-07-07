package sokoban;

import java.awt.Image;
import java.awt.Toolkit;

public class Nut extends Actor {

	Image image;
	public String img;

	public Nut(int x, int y) {
		super(x, y);
		img = "images/baggage.png";
		image = Toolkit.getDefaultToolkit().createImage(img);
		this.setImage(image);
	}

	public void move(int x, int y) {
		int nx = this.x() + x;
		int ny = this.y() + y;
		this.setX(nx);
		this.setY(ny);
	}
}