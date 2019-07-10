package gameDevelopment;

public class TreasureEntity extends Entity {

	public TreasureEntity(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	@Override
	public boolean remove(Entity other) {
		return false;
	}

}
