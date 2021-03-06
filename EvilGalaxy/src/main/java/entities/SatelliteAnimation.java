package entities;

import java.awt.Image;

import enums.Images;
import game_engine.SpritePattern;
import util.Constants;

public class SatelliteAnimation extends SpritePattern {
	
	private static final long serialVersionUID = 1L;
	private final int INITIAL_X = 0;
	private final int INITIAL_Y = 0;
	public static SatelliteAnimation starAnim;

	public SatelliteAnimation(int x, int y) {
		super(x, y);
		drawSatellite();
	}

	private String imageName;

	public Image drawSatellite() {
		imageName = Images.SATELLITEINIT.getImg();
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
