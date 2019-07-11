package gameDevelopment;

import java.awt.Rectangle;

public class TreasureEntity extends Entity {

	private Rectangle hero = new Rectangle();
	private Rectangle treasure = new Rectangle();

	public TreasureEntity(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	@Override
	public boolean remove(Entity other) {
		System.out.println("Collision detected TreasureEntity");
		return Game.notifyItemsCollected(new HeroEntity(sprite, x, y), other);
	}

	@Override
	public boolean collidesWith(Entity other) {
		hero.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
		treasure.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
		if (hero.intersects(treasure))
			return true;
		return false;
	}
}
