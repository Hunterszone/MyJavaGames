package entities;

import java.awt.Image;
import java.awt.Toolkit;

import game_engine.Actor;

public class Water extends Actor {

	private Image image;
	public String img;

	public Water(int x, int y) {
		super(x, y);
		img = "images/terrain.png";
		image = Toolkit.getDefaultToolkit().createImage(img);
		this.setImage(image);
		return;
	}
}
