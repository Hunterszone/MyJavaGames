package animation;

import java.util.ArrayList;
import java.util.List;

import enums.Sprite;
import game_engine.SpritePattern;
import util.Constants;
import util.Cyclable;

public class AsteroidAnimation extends SpritePattern implements Cyclable {
	
	private static final long serialVersionUID = 1L;
	private static final int INITIAL_X = (int) Math.ceil(Math.random() * 900);
	private static final int INITIAL_Y = (int) Math.ceil(Math.random());
	public static final List<AsteroidAnimation> ASTEROID_ANIMATION = new ArrayList<AsteroidAnimation>();

	public AsteroidAnimation(int x, int y) {
		super(x, y);
		drawAsteroid();
	}

	public void drawAsteroid() {
		String imageName = Sprite.ASTEROIDS_INIT.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}
	
	@Override
	public void cycle() {

        x += 3;
        y += 3;

        if (y > Constants.B_HEIGHT) {

            y = INITIAL_Y;
            x = INITIAL_X;
        }
    }
}
