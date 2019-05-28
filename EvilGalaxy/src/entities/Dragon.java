package entities;

import java.util.List;

import game_engine.SpritePattern;

public class Dragon extends SpritePattern {

	private static final long serialVersionUID = 1L;
	public static List<Dragon> dragons;
	private final int INITIAL_X = 600;

	public Dragon(int x, int y) {
		super(x, y);

		initBoss();
	}

	private void initBoss() {

		loadImage("images/boss.png");
		getImageDimensions();
	}

	public void move() {

		if (x < 0) {
			x = INITIAL_X;
		}

		x -= 3;

	}
}