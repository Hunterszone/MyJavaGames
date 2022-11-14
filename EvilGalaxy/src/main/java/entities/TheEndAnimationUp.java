package entities;

import java.util.ArrayList;
import java.util.List;

import enums.Sprite;
import game_engine.SpritePattern;
import util.Constants;
import util.Cyclable;

public class TheEndAnimationUp extends SpritePattern implements Cyclable {

	public static List<TheEndAnimationUp> theEndAnimationUpList = new ArrayList<TheEndAnimationUp>();

	private static final long serialVersionUID = 1L;

	public TheEndAnimationUp(int x, int y) {
		super(x, y);
		drawTheEndUp();
	}

	private String imageName;

	public void drawTheEndUp() {
		imageName = Sprite.THEENDUP_INIT.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	@Override
	public void cycle() {

		y -= 3;

		if (y < -400) {
			y = Constants.B_HEIGHT;
		}
	}

}