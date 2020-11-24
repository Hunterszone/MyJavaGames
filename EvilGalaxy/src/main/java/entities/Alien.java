package entities;

import java.util.List;

import enums.Images;
import game_engine.SpritePattern;

public class Alien extends SpritePattern {

	public static List<Alien> aliens;
	private static final long serialVersionUID = 1L;
	private final int INITIAL_X = 1024;
	private final int INITIAL_Y = 0;
	private String imageName;

	public Alien(int x, int y) {
		super(x, y);
		drawAlien();
	}

	public String drawAlien() {
		imageName = Images.ALIEN.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public void move() {

		if (x < 0) {
			x = INITIAL_X;
		}

		if (y > 1200) {
			y = INITIAL_Y;
		}

		x -= 3.1;
		y += 1.1;
	}

	public void moveFaster() {

		if (x < 0) {
			x = INITIAL_X;
		}

		x -= 0.1;
	}
}