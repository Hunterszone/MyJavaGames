package game_engine;

import java.awt.Image;
import java.awt.Toolkit;

import enums.Images;

public class Area extends Actor {

	private Image image;
	public String img;

	public Area(int x, int y) {
		super(x, y);
		img = Images.HOLDER.getImg();
		image = Toolkit.getDefaultToolkit().createImage(img);
		this.setImage(image);
	}
}