package gameDevelopment;

import java.awt.Rectangle;

public class Crosshair extends Entity {

	private Rectangle crosshair = new Rectangle();
	private Rectangle enemy = new Rectangle();

	public Crosshair(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	@Override
	public boolean collidesWith(Entity other) {
		enemy.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
		crosshair.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
		if (crosshair.intersects(enemy))
			return true;
		return false;
	}

	@Override
	public boolean removedByHero(Entity other) {
		return Game.notifyCrosshairUsed(new Crosshair(sprite, x, y), other);
	}
}
