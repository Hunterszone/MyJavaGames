package entities;

import enums.Sprite;
import game_engine.SpritePattern;
import util.Constants;
import util.Cyclable;

public class AstronautAnimation extends SpritePattern implements Cyclable {
	
	private static final long serialVersionUID = 1L;
	private final int INITIAL_X = 0;
	private final int INITIAL_Y = 0;
	public static AstronautAnimation astronautAnim;

	public AstronautAnimation(int x, int y) {
		super(x, y);
		drawAstronaut();
	}

	private String imageName;

	public void drawAstronaut() {
		imageName = Sprite.ASTRONAUT_INIT.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}
	
	@Override
	public void cycle() {

        x += 4;
        y += 4;

        if (y > Constants.B_HEIGHT) {

            y = INITIAL_Y;
            x = INITIAL_X;
        }
    }
}
