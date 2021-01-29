package entities;

import java.awt.Image;
import java.awt.Toolkit;

import enums.Images;
import game_engine.Actor;

public class Player extends Actor {

	Image sokoimage;
	public String img;

	public Player(int x, int y) {
		super(x, y);
		img = Images.SOKOBAN.getImg();
		sokoimage = Toolkit.getDefaultToolkit().createImage(img);
		this.setImage(sokoimage);
	}

	public void move(int x, int y) {
		int nx = this.x() + x;
		int ny = this.y() + y;
		this.setX(nx);
		this.setY(ny);
	}
}
