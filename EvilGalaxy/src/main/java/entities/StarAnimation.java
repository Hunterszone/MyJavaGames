package entities;

import java.awt.Image;

import game_engine.Images;
import game_engine.InitObjects;
import game_engine.SpritePattern;

public class StarAnimation extends SpritePattern {
	
	private static final long serialVersionUID = 1L;
	private final int INITIAL_X = 0;
	private final int INITIAL_Y = 0;
	public static StarAnimation starAnim;

	public StarAnimation(int x, int y) {
		super(x, y);
		drawStar();
	}

	private String imageName;

	public Image drawStar() {
		imageName = Images.STARINIT.getImg();
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
