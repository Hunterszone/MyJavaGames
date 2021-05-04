package entities;

import java.awt.Rectangle;

import game_engine.MySprite;
import main.Game;

public class TreasureEntity extends Entity {

	private Rectangle hero = new Rectangle();
	private Rectangle treasure = new Rectangle();

	public TreasureEntity(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	public TreasureEntity() {
		super(null, 0, 0);
	}

	@Override
	public boolean removedByHero(Entity entity) {
		return Game.notifyTreasuresCollected(entity);
	}

	@Override
	public boolean collidesWith(Entity other) {
		hero.setBounds(x, y, sprite.getWidth(), sprite.getHeight());
		treasure.setBounds(other.x, other.y, other.sprite.getWidth(), other.sprite.getHeight());
		if (hero.intersects(treasure))
			return true;
		return false;
	}
}
