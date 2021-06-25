package entities;

import java.awt.Image;
import java.awt.Toolkit;

import enums.Images;
import game_engine.Actor;

public class Nut extends Actor {

	Image image;
	public String img;

	public Nut(int x, int y) {
		super(x, y);
		img = Images.BAGGAGE.getImg();
		image = Toolkit.getDefaultToolkit().createImage(img);
		this.setImage(image.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
	}

	public void move(int x, int y) {
		int nx = this.x() + x;
		int ny = this.y() + y;
		this.setX(nx);
		this.setY(ny);
	}
}