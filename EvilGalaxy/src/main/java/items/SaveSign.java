package items;

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
		imageName = Sprite.SAVESIGN_INIT.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}
}