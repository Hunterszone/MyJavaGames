package items;

import java.awt.event.KeyEvent;

import enums.Images;
import game_engine.SpritePattern;

public class VolBtn extends SpritePattern {

	public static VolBtn volButt;
	private static final long serialVersionUID = 1L;
	private String imageName;

	public VolBtn(int x, int y) {
		super(x, y);
		initVol();
	}

	public String initVol() {
		imageName = Images.VOLUMEINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public String initMute() {
		imageName = Images.VOLUMEMUTE.getImg();
		loadImage(imageName);
		getImageDimensions();
		return imageName;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_S) {
			initMute();
		}

		if (key == KeyEvent.VK_A || key == KeyEvent.VK_E || key == KeyEvent.VK_M || key == KeyEvent.VK_H) {
			initVol();
		}

	}
}