package entities;

import java.awt.Rectangle;

import game_engine.MySprite;
import main.Game;

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
			hero.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
			enemy.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
			if (hero.intersects(enemy))
				return true;
		}
		if (other instanceof Crosshair) {
			enemy.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
			crosshair.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
			if (crosshair.intersects(enemy))
				return true;
		}
		return false;
	}

	@Override
	public boolean removedByHero(Entity other) {
		if (other instanceof HeroEntity) {
			System.out.println("Hero intersects EnemyEntity");
			return Game.notifyEnemyHit(new HeroEntity(sprite, x, y), other);
		}
		if (other instanceof Crosshair) {
			System.out.println("Crosshair intersects EnemyEntity");
			return Game.notifyCrosshairUsed(new Crosshair(sprite, x, y), other);
		}
		return false;
	}
}