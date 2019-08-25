package allinone;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;

public class Spaceship extends GameObject {

	private ConfigurableEmitter emitter;
	private Input input;
	public Shape collisionSurface;
	static double speedX;
	static double speedY;

	public Spaceship(int x, int y, Image image, Input input, ConfigurableEmitter emitter) {
		super(image);
		this.input = input;
		this.emitter = emitter;
		collisionSurface = new Ellipse(x, y, 30, 45);
	}

	public Spaceship(int x, int y, Image image) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(int delta) {
		x = input.getMouseX();
		y = input.getMouseY() + 250;
		x += speedX;
		y += speedY;
		emitter.setPosition(x, y + 45, false);
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
