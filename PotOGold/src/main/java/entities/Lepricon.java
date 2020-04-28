package entities;

import java.awt.image.BufferedImage;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;

public class Lepricon extends GameObject {

	private ConfigurableEmitter lepriMagic;
	public Shape collisionSurface;
	public static double speedX;
	public static double speedY;

	public Lepricon(int x, int y, Image image, Input input, ConfigurableEmitter emitter) {
		super(image);
		this.lepriMagic = emitter;
		collisionSurface = new Ellipse(x, y, 95, 105);
	}

	public Lepricon(int x, int y, BufferedImage image) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(int delta) {
		x = 500;
		y = 630;
		x += speedX;
		y += speedY;
		lepriMagic.setPosition(x, y + 45);
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