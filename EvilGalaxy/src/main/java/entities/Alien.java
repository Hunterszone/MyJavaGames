package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enums.Sprite;
import enums.SoundEffect;
import game_engine.SpritePattern;
import items.CanonBall;
import sound_engine.PlayWave1st;
import util.Moveable;

public class Alien extends SpritePattern implements Moveable {

	public static List<Alien> aliens;
	private static final long serialVersionUID = 1L;
	private final int INITIAL_X = 1024;
	private final int INITIAL_Y = 0;
	private String imageName;

	public Alien(int x, int y) {
		super(x, y);
		drawAlien();
	}

	public void drawAlien() {
		imageName = Sprite.ALIEN.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	@Override
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

	public void loadCanon() {
		CanonBall.canonBalls = new ArrayList<CanonBall>(Arrays.asList(new CanonBall(x + width, y + height / 2)));
		if (!Alien.aliens.isEmpty()) new PlayWave1st(SoundEffect.BOING.getSound()).start();
	}

}