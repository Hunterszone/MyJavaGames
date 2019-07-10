package gameDevelopment;

public class Crosshair extends Entity {
	public Crosshair(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	@Override
	public boolean remove(Entity other) {
		return Game.notifyCrosshair(this, new EnemyEntity(sprite, x, y));
	}

}
