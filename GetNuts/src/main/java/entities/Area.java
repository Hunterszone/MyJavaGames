package entities;

import java.awt.Image;
import java.awt.Toolkit;

import enums.Images;
import game_engine.Actor;

public class Area extends Actor {

	private Image image;
	public String img;

	public Area(int x, int y) {
		super(x, y);
		img = Images.HOLDER.getImg();
		image = Toolkit.getDefaultToolkit().createImage(img);
		this.setImage(image.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
	}
}