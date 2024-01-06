package items;

import java.awt.Shape;
import java.util.List;
import java.util.Objects;

import enemy.Alien;
import enemy.Dragon;
import enums.Sprite;
import game_engine.SpritePattern;
import util.Intersectable;
import util.LivesAndCounts;
import util.LoadSounds;
import util.Moveable;

public class HealthPack extends SpritePattern implements Moveable, Intersectable {

	public static List<HealthPack> healthpacks;
	
	private static final int SPEED_Y = 5;
	private static final long serialVersionUID = 1L;
	private final int INITIAL_Y = 0;
	private String imageName;

	public HealthPack(int x, int y) {
		super(x, y);
		initHealth();
	}

	public void initHealth() {
		imageName = Objects.requireNonNull(Sprite.HEALTH_INIT.getImg());
		loadImage(imageName);
		getImageDimensions();
	}

	@Override
	public void move() {
		if (y > 1200) {
			y = INITIAL_Y;
		}

		y += SPEED_Y;
	}

	@Override
	public void intersects(Shape entity) {
		if (entity.intersects(this.getBounds()) && (!Alien.aliens.isEmpty()
				|| (Dragon.dragons.isEmpty() && LivesAndCounts.getLifeBunker() < 50) || Gold.goldstack.isEmpty())) {
			this.setVisible(false);
			LoadSounds.GOT_HP.play();
			if (LivesAndCounts.getLifePlayerShip() <= 3) {
				LivesAndCounts.increasePlayerShipLife(1);
			}
		}
	}
	
}