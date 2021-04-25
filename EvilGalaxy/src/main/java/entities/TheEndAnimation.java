package entities;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import enums.Images;
import game_engine.SpritePattern;
import util.Constants;

public class TheEndAnimation extends SpritePattern {
	
	private static final long serialVersionUID = 1L;
	public static List<TheEndAnimation> theEndAnimationsUp = new ArrayList<TheEndAnimation>();
	public static List<TheEndAnimation> theEndAnimationsDown = new ArrayList<TheEndAnimation>();

	public TheEndAnimation(int x, int y) {
		super(x, y);
		drawTheEndUp();
		drawTheEndDown();
	}

	private String imageName;

	public Image drawTheEndUp() {
		imageName = Images.THEENDUPINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return loadImage(imageName);
	}
	
	public Image drawTheEndDown() {
		imageName = Images.THEENDDOWNINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return loadImage(imageName);
	}
	
	public void cycleUp() {

        y -= 3;

        if (y < -400) {
            y = Constants.B_HEIGHT;
        }
    }
	
	public void cycleDown() {

      y += 3;

      if (y > Constants.B_HEIGHT) {
          y = 0;
      }
  }
}
