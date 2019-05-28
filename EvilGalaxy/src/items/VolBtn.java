package items;

import java.awt.event.KeyEvent;

import game_engine.SpritePattern;

public class VolBtn extends SpritePattern {

	private static final long serialVersionUID = 1L;
	public static VolBtn volButt;

	public VolBtn(int x, int y) {
		super(x, y);

		initVol();
	}

	private void initVol() {

		loadImage("images/volbutt.png");
		getImageDimensions();
	}

	private void initMute() {

		loadImage("images/mute.png");
		getImageDimensions();
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