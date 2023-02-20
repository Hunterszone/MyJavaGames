package items;

import java.util.Objects;

import enums.Sprite;
import game_engine.SpritePattern;

public class SaveSign extends SpritePattern {

	public static SaveSign saveSign;
	private static final long serialVersionUID = 1L;
	private String imageName;

	public SaveSign(int x, int y) {
		super(x, y);
	}

	public void initSave() {
		imageName = Objects.requireNonNull(Sprite.SAVESIGN_INIT.getImg());
		loadImage(imageName);
		getImageDimensions();
	}
}