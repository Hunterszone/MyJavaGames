package gameDevelopment;

import java.awt.Rectangle;

public class Crosshair extends Entity {

	private Rectangle crosshair = new Rectangle();
	private Rectangle enemy = new Rectangle();

	public Crosshair(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	@Override
	public boolean remove(Entity other) {
		return Game.notifyCrosshair(this, new EnemyEntity(sprite, x, y));
	}

	@Override
	public boolean collidesWith(Entity other) {
		crosshair.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
		enemy.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
		System.out.println("Collision detected Crosshair");
		if (crosshair.intersects(enemy))
			return true;
		return false;
	}

}
