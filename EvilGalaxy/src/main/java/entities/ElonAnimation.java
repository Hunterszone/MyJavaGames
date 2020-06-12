package entities;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import game_engine.Images;
import game_engine.InitObjects;
import game_engine.SpritePattern;

public class ElonAnimation extends SpritePattern {
	
	private static final long serialVersionUID = 1L;
	public static List<ElonAnimation> elonAnimationsUp = new ArrayList<ElonAnimation>();
	public static List<ElonAnimation> elonAnimationsDown = new ArrayList<ElonAnimation>();

	public ElonAnimation(int x, int y) {
		super(x, y);
		drawElonsUp();
		drawElonsDown();
	}

	private String imageName;

	public Image drawElonsUp() {
		imageName = Images.ELONSUPINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return loadImage(imageName);
	}
	
	public Image drawElonsDown() {
		imageName = Images.ELONSDOWNINIT.getImg();
		loadImage(imageName);
		getImageDimensions();
		return loadImage(imageName);
	}
	
	public void cycleUp() {

        y -= 3;

        if (y < -400) {
            y = InitObjects.B_HEIGHT;
        }
    }
	
	public void cycleDown() {

      y += 3;

      if (y > InitObjects.B_HEIGHT) {
          y = 0;
      }
  }
}
