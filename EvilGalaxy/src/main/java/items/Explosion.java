package items;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import util.Drawable;

public class Explosion implements Drawable {
	
	public static List<Explosion> explosions;
	private final double x, y;
	private int r;
	private final int maxRadius;
	
	public Explosion(double x, double y, int r, int maxRadius) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
		this.maxRadius = maxRadius;
	}
	
	public int getR() {
		return r;
	}

	public void update() {
		do {
			r++;			
		} while(r < maxRadius);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 255, 128));
		g.drawOval((int)(x-r), (int)(y-r), 2*r, 2*r);
	}

}
