package potogold;

import java.awt.image.BufferedImage;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

public class Bomb extends GameObject {

	private Shape collisionSurface;
	private double speed = 2;
	private double acceleration = 0.001;

	public Bomb(int x, int y, Image image) {
		super(x, y, image);
		collisionSurface = new Ellipse(x, y, 60, 60);
	}

	public Bomb(int x, int y, BufferedImage image) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(int delta) {
		speed += acceleration;
		y += speed;
		if (y >= 1000) {
			y = 0;
		}
		collisionSurface.setCenterX(x);
		collisionSurface.setCenterY(y);
	}

	@Override
	public void draw(Graphics g) {
		image.drawCentered(x, y);
	}

	public boolean checkCollision(GameObject gameObject) {
		if (collisionSurface != null)
			return collisionSurface.contains(gameObject.getX(), gameObject.getY());
		return false;
	}
}
