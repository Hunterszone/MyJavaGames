package gameDevelopment;

public class Crosshair extends Entity {
	private Object game;

	public Crosshair(Object game, MySprite sprite, int x, int y) {
		super(sprite, x, y);
		this.game = game;
	}

	@Override
	public void remove(Entity other) {
		Game myGame = (Game) game;
		myGame.notifyCrosshair(this, other);

	}

}
