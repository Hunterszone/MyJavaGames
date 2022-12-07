package animation;

import enums.Sprite;
import game_engine.SpritePattern;
import util.Constants;
import util.Cyclable;

public class SatelliteAnimation extends SpritePattern implements Cyclable {
	
	private static final long serialVersionUID = 1L;
	private final int INITIAL_X = 0;
	private final int INITIAL_Y = 0;
	public static SatelliteAnimation starAnim;

	public SatelliteAnimation(int x, int y) {
		super(x, y);
		drawSatellite();
	}

	private String imageName;

	public void drawSatellite() {
		imageName = Sprite.SATELLITE_INIT.getImg();
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
