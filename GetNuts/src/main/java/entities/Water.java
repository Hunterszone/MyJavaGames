package entities;

import java.awt.Image;
import java.awt.Toolkit;

import enums.Images;
import game_engine.Actor;

public class Water extends Actor {

	private Image image;
	public String img;

	public Water(int x, int y) {
		super(x, y);
		img = Images.TERRAIN.getImg();
		image = Toolkit.getDefaultToolkit().createImage(img);
		this.setImage(image.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		return;
	}
}
