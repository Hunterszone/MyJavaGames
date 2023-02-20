package items;

import java.awt.event.KeyEvent;
import java.util.Objects;

import enums.Sprite;
import game_engine.SpritePattern;
import util.Flags;

public class VolBtn extends SpritePattern {

	public static VolBtn volButt;
	private static final long serialVersionUID = 1L;
	private String imageName;

	public VolBtn(int x, int y) {
		super(x, y);
		initMuteOrVol();
	}

	public void keyPressed(KeyEvent e) {

		final int key = e.getKeyCode();

		if (key == KeyEvent.VK_S) {
			initMute();
		}

		if (key == KeyEvent.VK_A) {
			initVol();
		}

	}

	private void initMuteOrVol() {
		if (Flags.isSPressed) {
			initMute();
		} else {
			initVol();
		}
	}

	private void initVol() {
		imageName = Objects.requireNonNull(Sprite.VOLUME_INIT.getImg());
		loadImage(imageName);
		getImageDimensions();
	}

	private void initMute() {
		imageName = Objects.requireNonNull(Sprite.VOLUME_MUTE.getImg());
		loadImage(imageName);
		getImageDimensions();
	}

}