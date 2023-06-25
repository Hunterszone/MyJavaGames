package animation;

import java.util.ArrayList;
import java.util.List;

import enums.Sprite;
import game_engine.SpritePattern;
import util.Constants;
import util.Cyclable;

public class TheEndAnimationDown extends SpritePattern implements Cyclable {

	public static final List<TheEndAnimationDown> END_ANIMATION_DOWN = new ArrayList<TheEndAnimationDown>();

	private static final long serialVersionUID = 1L;

	public TheEndAnimationDown(int x, int y) {
		super(x, y);
		drawTheEndDown();
	}

	public void drawTheEndDown() {
		String imageName = Sprite.THEENDDOWN_INIT.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	@Override
	public void cycle() {

		y += 3;

		if (y > Constants.B_HEIGHT) {
			y = 0;
		}
	}
}