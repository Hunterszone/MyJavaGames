package gameDevelopment;

import java.awt.Rectangle;

public abstract class Entity {
	protected int x;
	protected int y;
	protected MySprite sprite;
	private boolean visible;

	private Rectangle hero = new Rectangle();
	private Rectangle object = new Rectangle();

	public Entity(MySprite sprite, int x, int y) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.visible = true;
	}

	public abstract boolean remove(Entity other);

	public boolean collidesWith(Entity other) {
		hero.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
		object.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
		return hero.intersects(object);
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

	public void collidedWith(HeroEntity other) {
		// TODO Auto-generated method stub

	}
}
