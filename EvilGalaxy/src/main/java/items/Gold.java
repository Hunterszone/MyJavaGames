package items;

import java.awt.Shape;
import java.util.List;
import java.util.Objects;

import enums.Sprite;
import game_engine.SpritePattern;
import util.Intersectable;
import util.LivesAndCounts;
import util.LoadSounds;
import util.Moveable;

public class Gold extends SpritePattern implements Moveable, Intersectable {

	public static List<Gold> goldstack;
	private static final long serialVersionUID = 1L;
	private final int INITIAL_Y = 1200;
	private String imageName;

	public Gold(int x, int y) {
		super(x, y);
		initGifts();
	}

	public void initGifts() {
		imageName = Objects.requireNonNull(Sprite.GOLD_INIT.getImg());
		loadImage(imageName);
		getImageDimensions();
	}

	@Override
	public void move() {

		if (y < 0) {
			y = INITIAL_Y;
		}

		y -= 1;

	}

	@Override
	public void intersects(Shape entity) {
		if (entity.intersects(this.getBounds()) 
				&& LivesAndCounts.getLifeBunker() >= 50) {
			this.setVisible(false);
			LoadSounds.GOT_GOLD.play();
		}
	}
}