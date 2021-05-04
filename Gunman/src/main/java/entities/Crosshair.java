package entities;

import java.awt.Rectangle;

import game_engine.MySprite;
import main.Game;

public class Crosshair extends Entity {

	private Rectangle crosshair = new Rectangle();
	private Rectangle enemy = new Rectangle();

	public Crosshair(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	@Override
	public boolean collidesWith(Entity other) {
		enemy.setBounds(x, y, sprite.getWidth(), sprite.getHeight());
		crosshair.setBounds(other.x, other.y, other.sprite.getWidth(), other.sprite.getHeight());
		if (crosshair.intersects(enemy))
			return true;
		return false;
	}

	@Override
	public boolean removedByHero(Entity entity) {
		return Game.notifyCrosshairUsed(entity);
	}
}
