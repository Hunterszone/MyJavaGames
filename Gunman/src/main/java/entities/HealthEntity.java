package entities;

import java.awt.Rectangle;

import game_engine.Logic;
import game_engine.MySprite;

public class HealthEntity extends Entity {

	private Rectangle hero = new Rectangle();
	private Rectangle health = new Rectangle();

	public HealthEntity(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	public HealthEntity() {
		super(null, 0, 0);
	}

	@Override
	public boolean removedByHero(Entity entity) {
		return Logic.notifyHpCollected(entity);
	}

	@Override
	public boolean collidesWith(Entity other) {
		hero.setBounds(x, y, sprite.getWidth(), sprite.getHeight());
		health.setBounds(other.x, other.y, other.sprite.getWidth(), other.sprite.getHeight());
		if (hero.intersects(health))
			return true;
		return false;
	}
}
