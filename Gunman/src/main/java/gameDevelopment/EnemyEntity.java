package gameDevelopment;

import java.awt.Rectangle;

public class EnemyEntity extends Entity {

	private Rectangle hero = new Rectangle();
	private Rectangle crosshair = new Rectangle();
	private Rectangle enemy = new Rectangle();

	public EnemyEntity(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	@Override
	public boolean collidesWith(Entity other) {
		if (other instanceof HeroEntity) {
			enemy.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
			hero.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
			if (enemy.intersects(hero))
				return true;
		}
		if (other instanceof Crosshair) {
			enemy.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
			crosshair.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
			if (enemy.intersects(crosshair))
				return true;
		}
		return false;
	}

	@Override
	public boolean remove(Entity other) {
		System.out.println("Collision detected EnemyEntity");
		return Game.notifyEnemiesHit(this, other);
	}
}
