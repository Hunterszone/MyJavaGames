package items;

import java.util.List;

import enums.Sprite;
import game_engine.SpritePattern;
import util.Constants;

public class CanonBall extends SpritePattern {
	
	public static List<CanonBall> canonBalls;

	private static final long serialVersionUID = 1L;
	private final int CANON_SPEED = 8;
	private String imageName;

	public CanonBall(int x, int y) {
		super(x, y);
		initCanon();
	}

	public void initCanon() {
		imageName = Sprite.CANON_INIT.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	public void moveCanonLeft() {
		x -= CANON_SPEED;
		if(x < Constants.B_WIDTH - 1280) {
			this.setVisible(false);
		}
	}
	
	public void moveCanonRight() {
		x += CANON_SPEED;
		if(x > Constants.B_WIDTH + 130) {
			this.setVisible(false);
		}
	}
}