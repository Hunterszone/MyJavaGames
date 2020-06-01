package entities;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import game_engine.Images;
import game_engine.InitObjects;
import game_engine.SpritePattern;

public class AsteroidsAnimation extends SpritePattern {
	
	private static final long serialVersionUID = 1L;
	private final int INITIAL_X = 0;
	private final int INITIAL_Y = 0;
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

        x += 1;
        y += 1;

        if (y > InitObjects.B_HEIGHT) {

            y = INITIAL_Y;
            x = INITIAL_X;
        }
    }
}
