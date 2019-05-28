package allinone;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

public class Ufo extends GameObject {

	private Shape collisionSurface;
	private double speed = 2;
	private double acceleration = 0.001;

	public Ufo(int x, int y, Image image) {
		super(x, y, image);
		collisionSurface = new Ellipse(x, y, 60, 60);
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
//		Color.red.a = 0.4f;
//		g.setColor(Color.red);
//		g.fill(collisionSurface);
	}

	public boolean checkCollision(GameObject gameObject) {
		return collisionSurface.contains(gameObject.getX(), gameObject.getY());
	}
}
