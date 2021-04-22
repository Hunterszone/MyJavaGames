package entities;

import java.awt.Rectangle;

import game_engine.MySprite;
import main.Game;

public class HeroEntity extends Entity {

	private Rectangle hero = new Rectangle();
	private Rectangle enemy = new Rectangle();
	private Rectangle health = new Rectangle();
	private Rectangle treasure = new Rectangle();

	public HeroEntity(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	@Override
	public boolean removedByHero(Entity other) {
		if (other instanceof HealthEntity) {
			System.out.println("Hero intersects HealthEntity");
			return Game.notifyHpCollected(new HeroEntity(sprite, x, y), other);
		}
		if (other instanceof TreasureEntity) {
			System.out.println("Hero intersects TreasureEntity");
			return Game.notifyTreasuresCollected(new HeroEntity(sprite, x, y), other);
		}
		if (other instanceof EnemyEntity)
			return Game.notifyEnemyHit(new HeroEntity(sprite, x, y), other);
		return false;
	}

	@Override
	public boolean collidesWith(Entity other) {
		if (other instanceof EnemyEntity) {
			hero.setBounds(x, y, sprite.getWidth(), sprite.getHeight());
			enemy.setBounds(other.x, other.y, other.sprite.getWidth(), other.sprite.getHeight());
			return hero.intersects(enemy);
		}
		if (other instanceof TreasureEntity) {
			hero.setBounds(x, y, sprite.getWidth(), sprite.getHeight());
			treasure.setBounds(other.x, other.y, other.sprite.getWidth(), other.sprite.getHeight());
			return hero.intersects(treasure);
		}
		if (other instanceof HealthEntity) {
			hero.setBounds(x, y, sprite.getWidth(), sprite.getHeight());
			health.setBounds(other.x, other.y, other.sprite.getWidth(), other.sprite.getHeight());
			return hero.intersects(health);
		}
		return false;
	}
}
