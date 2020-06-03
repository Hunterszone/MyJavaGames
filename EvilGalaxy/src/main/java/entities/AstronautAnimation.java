package entities;

import java.awt.Image;

import game_engine.Images;
import game_engine.InitObjects;
import game_engine.SpritePattern;

public class AstronautAnimation extends SpritePattern {
	
	private static final long serialVersionUID = 1L;
	private final int INITIAL_X = 0;
	private final int INITIAL_Y = 0;
	public static AstronautAnimation astronautAnim;

	public AstronautAnimation(int x, int y) {
		super(x, y);
		drawAstronaut();
	}

	private String imageName;

	public Image drawAstronaut() {
		imageName = Images.ASTRONAUTINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return loadImage(imageName);
	}
	
	public void cycle() {

        x += 4;
        y += 4;

        if (y > InitObjects.B_HEIGHT) {

            y = INITIAL_Y;
            x = INITIAL_X;
        }
    }
}
