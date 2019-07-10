package gameDevelopment;

public class EnemyEntity extends Entity {
	public EnemyEntity(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	@Override
	public void collidedWith(HeroEntity other) {
		System.out.println("Collision detected Enemy");
		Game.notifyEnemiesHit(this, other);
	}

	@Override
	public boolean remove(Entity other) {
		return false;
	}

}
