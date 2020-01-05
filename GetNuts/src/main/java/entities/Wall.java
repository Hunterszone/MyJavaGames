package entities;

import java.awt.Image;
import java.awt.Toolkit;

import game_engine.Actor;

public class Wall extends Actor {

	private Image image;
	public String img;

	public Wall(int x, int y) {
		super(x, y);
		img = "images/wall.png";
		image = Toolkit.getDefaultToolkit().createImage(img);
		this.setImage(image);
	}
}
