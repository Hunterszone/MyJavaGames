package items;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import frames.GameMenu;
import game_engine.Images;
import game_engine.SpritePattern;

public class SaveSign extends SpritePattern implements KeyListener {

	public static SaveSign saveSign;
	private static final long serialVersionUID = 1L;
	private String imageName;

	public SaveSign(int x, int y) {
		super(x, y);
	}

	public String initSave() {
		imageName = Images.SAVESIGNINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (((key == KeyEvent.VK_Z) && ((e.getModifiers() & InputEvent.ALT_MASK) != 0)) || ((key == KeyEvent.VK_X)
				&& ((e.getModifiers() & InputEvent.ALT_MASK) != 0) && GameMenu.autosave.isSelected() == false)) {
			if (saveSign != null) {
				saveSign.initSave();
				saveSign.setVisible(true);
			}
		} else {
			if (saveSign != null) {
				saveSign.setVisible(false);
			}
		}
	}
}