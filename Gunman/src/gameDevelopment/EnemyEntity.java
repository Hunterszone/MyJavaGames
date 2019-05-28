package gameDevelopment;

public class EnemyEntity extends Entity {
	private Object game;

	public EnemyEntity(Object game, MySprite sprite, int x, int y) {
		super(sprite, x, y);
		this.game = game;
	}

	@Override
	public void collidedWith(HeroEntity other) {
		Game myGame = (Game) game;
		System.out.println("Collision detected Enemy");
		myGame.notifyEnemiesHit(this, other);
	}


	@Override
	public void remove(Entity other) {
		// TODO Auto-generated method stub
		
	}

}
