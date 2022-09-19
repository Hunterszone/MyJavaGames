package icons;

import java.awt.Image;

import enums.Images;
import game_engine.SpritePattern;

public class LocationIcon extends SpritePattern {

	public static LocationIcon locationIcon;
	private static final long serialVersionUID = 1L;
	
	public LocationIcon(int x, int y) {
		super(x, y);
		drawIcon();
	}

	public Image drawIcon() {
		String imageName = Images.LOCATION_ICON.getImg();
		Image img = loadImage(imageName);
		getImageDimensions();
		return img;
	}
}