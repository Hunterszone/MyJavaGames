package entities;

import java.io.IOException;

import game_engine.MySprite;

interface RemoveEntities {
	boolean removedByHero(Entity other) throws IOException;
}

interface CheckCollision {
	boolean collidesWith(Entity other);
}

public abstract class Entity implements RemoveEntities, CheckCollision {
	protected int x;
	protected int y;
	protected MySprite sprite;
	private boolean visible;

	public Entity(MySprite sprite, int x, int y) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.visible = true;
	}

	public void draw() {
		sprite.draw(x, y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return sprite.getWidth();
	}

	public int getHeight() {
		return sprite.getHeight();
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
