package potogold;

import java.io.IOException;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class Effects extends GameObject {

	private ParticleSystem particleSystem;
	private ConfigurableEmitter santaFart;
	private ConfigurableEmitter objCollision;
	
	public Effects() throws SlickException {
		try {
			particleSystem = ParticleIO.loadConfiguredSystem("res/particles/empty_system.xml");
			santaFart = ParticleIO.loadEmitter("res/particles/rocket_smoke.xml");
			particleSystem.addEmitter(santaFart);
			objCollision = ParticleIO.loadEmitter("res/particles/ufo_explosion.xml");
		} catch (IOException e) {
			throw new SlickException("Particle system cannot be loaded!", e);
		}
	}

	@Override
	public void update(int delta) {
		particleSystem.update(delta);
	}

	@Override
	public void draw(Graphics g) {
		particleSystem.render();
	}

	public ConfigurableEmitter getRocketSmokeEmitter() {
		return santaFart;
	}

	public void objColliding(int x, int y) {
		ConfigurableEmitter explosion = objCollision.duplicate();
		explosion.setPosition(x, y);
		particleSystem.addEmitter(explosion);
	}
}
