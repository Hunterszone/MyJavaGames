package entities;

import java.awt.Rectangle;

import game_engine.MySprite;
import main.Game;

public class HealthEntity extends Entity {

	private Rectangle hero = new Rectangle();
	private Rectangle health = new Rectangle();

	public HealthEntity(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	@Override
	public boolean removedByHero(Entity other) {
		return Game.notifyHpCollected(new HeroEntity(sprite, x, y), other);
	}

	@Override
	public boolean collidesWith(Entity other) {
		hero.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
		health.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
		if (hero.intersects(health))
			return true;
		return false;
	}
}
