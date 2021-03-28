package items;

import enums.Images;
import game_engine.SpritePattern;
import util.Constants;

public class CanonBall extends SpritePattern {

	private static final long serialVersionUID = 1L;
	private final int CANON_SPEED = 8;
	private String imageName;

	public CanonBall(int x, int y) {
		super(x, y);
		initCanon();
	}

	public String initCanon() {
		imageName = Images.CANONINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
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