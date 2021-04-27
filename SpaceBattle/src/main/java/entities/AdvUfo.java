package entities;

import java.awt.image.BufferedImage;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Ellipse;

public class AdvUfo extends GameObject {

	private double speed = 2;
	private double acceleration = 0.001;

	public AdvUfo(int x, int y, Image image) {
		super(x, y, image);
		collisionSurface = new Ellipse(x, y, 60, 60);
	}

	public AdvUfo(int x, int y, BufferedImage image) {
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
		if (g != null) {
			image.drawCentered(x, y);
//			Color.red.a = 0.4f;
//			g.setColor(Color.red);
//			g.fill(collisionSurface);			
		}
	}
}
