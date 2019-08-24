package allinone;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;
import org.newdawn.slick.particles.ConfigurableEmitter;

public class ShotMoon extends GameObject {

	private int speed = 5;
	private int radius = 4;
	private ConfigurableEmitter emitter;

	public ShotMoon(int x, int y, Sound blasterSound, ConfigurableEmitter emitter) {
		super(x, y);
		this.emitter = emitter;
		if (emitter != null && blasterSound != null) {
			emitter.setPosition(x, y);
			blasterSound.playAt(x, y, 0);
		}
	}

	@Override
	public void update(int delta) {
		y += speed;
		x += speed;
		emitter.setPosition(x, y, false);
	}

	@Override
	public void draw(Graphics g) {
		if (g != null) {
			g.setColor(Color.red);
			g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
		}
	}

	public void disappear() {
		emitter.setEnabled(false);
	}
}