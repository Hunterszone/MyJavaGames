package gameDevelopment;

import java.awt.Rectangle;

public class HeroEntity extends Entity {

	private Rectangle hero = new Rectangle();
	private Rectangle enemy = new Rectangle();
	private Rectangle health = new Rectangle();
	private Rectangle treasure = new Rectangle();

	public HeroEntity(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	@Override
	public boolean remove(Entity other) {
		if (other instanceof HealthEntity || other instanceof TreasureEntity)
			Game.notifyItemsCollected(new HeroEntity(sprite, x, y), other);
		if (other instanceof EnemyEntity)
			Game.notifyEnemiesHit(new HeroEntity(sprite, x, y), other);
		return false;
	}

	public boolean collidesWith(Entity other) {
		if (other instanceof EnemyEntity) {
			hero.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
			enemy.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
			return hero.intersects(enemy);
		}
		if (other instanceof HealthEntity) {
			hero.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
			health.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
			return hero.intersects(health);
		}
		if (other instanceof TreasureEntity) {
			hero.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
			treasure.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
			return hero.intersects(treasure);
		}
		return false;
	}
}
