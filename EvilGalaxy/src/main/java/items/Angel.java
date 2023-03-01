package items;

import java.util.List;
import java.util.Objects;

import enums.Sprite;
import game_engine.SpritePattern;
import util.Moveable;

public class Angel extends SpritePattern implements Moveable {

	public static List<Angel> angels;
	private static final long serialVersionUID = 1L;
	private String imageName;

	public Angel(int x, int y) {
		super(x, y);
		initAngel();
	}

	public void initAngel() {
		imageName = Objects.requireNonNull(Sprite.ANGEL.getImg());
		loadImage(imageName);
		getImageDimensions();
	}

	@Override
	public void move() {
		y -= 0.01;
	}
}