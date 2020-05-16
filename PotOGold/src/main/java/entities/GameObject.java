package entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class GameObject {

	protected int x;
	protected int y;
	protected Image image;

	public abstract void draw(Graphics g);

	public void update(int delta) {
	};

	public GameObject(int x, int y, Image image) {
		this(x, y);
		this.image = image;
	}

	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public GameObject(Image image) {
		this.image = image;
	}

	public GameObject() {
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

	public static String[] getLivesAndPoints() {
		return new String[] { Integer.toString(Lives.lives), Integer.toString(Points.points) };
	};
}
