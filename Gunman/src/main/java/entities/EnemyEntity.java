package entities;

import java.awt.Rectangle;

import game_engine.Logic;
import game_engine.MySprite;

public class EnemyEntity extends Entity {

	private Rectangle hero = new Rectangle();
	private Rectangle crosshair = new Rectangle();
	private Rectangle enemy = new Rectangle();

	public EnemyEntity(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	public EnemyEntity() {
		super(null, 0, 0);
	}

	@Override
	public boolean collidesWith(Entity other) {
		if (other instanceof HeroEntity) {
			hero.setBounds(x, y, sprite.getWidth(), sprite.getHeight());
			enemy.setBounds(other.x, other.y, other.sprite.getWidth(), other.sprite.getHeight());
			if (hero.intersects(enemy))
				return true;
		}
		if (other instanceof Crosshair) {
			enemy.setBounds(x, y, sprite.getWidth(), sprite.getHeight());
			crosshair.setBounds(other.x, other.y, other.sprite.getWidth(), other.sprite.getHeight());
			if (crosshair.intersects(enemy))
				return true;
		}
		return false;
	}

	@Override
	public boolean removedByHero(Entity entity) {
		if (entity instanceof HeroEntity) {
			System.out.println("Hero intersects EnemyEntity");
			return Logic.notifyEnemyHit(entity);
		}
		if (entity instanceof Crosshair) {
			System.out.println("Crosshair intersects EnemyEntity");
			return Logic.notifyCrosshairUsed(entity);
		}
		return false;
	}
}