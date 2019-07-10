package gameDevelopment;

public class HeroEntity extends Entity {
	public HeroEntity(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	@Override
	public boolean remove(Entity other) {
		System.out.println("Collision detected HeroEntity");
		Game.notifyItemsCollected(this, other);
		return false;
	}

}
