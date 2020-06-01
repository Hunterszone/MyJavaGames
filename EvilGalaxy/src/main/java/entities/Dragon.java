package entities;

import java.util.List;

import game_engine.Images;
import game_engine.SpritePattern;

public class Dragon extends SpritePattern {

	private static final long serialVersionUID = 1L;
	public static List<Dragon> dragons;
	private final int INITIAL_X = (int) Math.ceil(Math.random() * 7000);
	private String imageName;

	public Dragon(int x, int y) {
		super(x, y);

		drawDragon();
	}

	public String drawDragon() {
		imageName = Images.DRAGON.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public void move() {

		if (x < 0) {
			x = INITIAL_X;
		}

		x -= 3;
	}
}