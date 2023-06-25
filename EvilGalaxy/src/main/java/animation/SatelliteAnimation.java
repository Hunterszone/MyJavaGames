package animation;

import enums.Sprite;
import game_engine.SpritePattern;
import util.Constants;
import util.Cyclable;

public class SatelliteAnimation extends SpritePattern implements Cyclable {
	
	private static final long serialVersionUID = 1L;
	private final int INITIAL_X = 0;
	private final int INITIAL_Y = 0;
	public static final SatelliteAnimation SATELLITE = new SatelliteAnimation(0, 0);

	public SatelliteAnimation(int x, int y) {
		super(x, y);
		drawSatellite();
	}

	public void drawSatellite() {
		String imageName = Sprite.SATELLITE_INIT.getImg();
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
