package gameDevelopment;

public class HealthEntity extends Entity {

	public HealthEntity(MySprite sprite, int x, int y) {
		super(sprite, x, y);
	}

	@Override
	public boolean remove(Entity other) {
		return false;
	}

	@Override
	public boolean collidesWith(Entity other) {
		// TODO Auto-generated method stub
		return false;
	}
}
