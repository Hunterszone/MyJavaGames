package entities;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import enums.Images;
import game_engine.SpritePattern;
import util.Constants;

public class AsteroidsAnimation extends SpritePattern {
	
	private static final long serialVersionUID = 1L;
	private final int INITIAL_X = (int) Math.ceil(Math.random() * 900);
	private final int INITIAL_Y = (int) Math.ceil(Math.random());
	public static List<AsteroidsAnimation> asteroidsAnimations = new ArrayList<AsteroidsAnimation>();

	public AsteroidsAnimation(int x, int y) {
		super(x, y);
		drawAsteroids();
	}

	private String imageName;

	public Image drawAsteroids() {
		imageName = Images.ASTEROIDSINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return loadImage(imageName);
	}
	
	public void cycle() {

        x += 3;
        y += 3;

        if (y > Constants.B_HEIGHT) {

            y = INITIAL_Y;
            x = INITIAL_X;
        }
    }
}
