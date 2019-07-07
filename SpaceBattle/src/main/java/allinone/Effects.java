package allinone;

import java.io.IOException;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class Effects extends GameObject {

	private ParticleSystem particleSystem;
	private ConfigurableEmitter rocketSmoke;
	private ConfigurableEmitter ufoExplosion;
	private ConfigurableEmitter shotParticlesShip;
	private ConfigurableEmitter shotParticlesMoon;

	public Effects() throws SlickException {
		try {
			particleSystem = ParticleIO.loadConfiguredSystem("res/particles/empty_system.xml");
			shotParticlesShip = ParticleIO.loadEmitter("res/particles/shot_trace_emitter.xml");
			shotParticlesMoon = ParticleIO.loadEmitter("res/particles/shot_trace_emitter_moon.xml");
			rocketSmoke = ParticleIO.loadEmitter("res/particles/rocket_smoke.xml");
			particleSystem.addEmitter(rocketSmoke);
			ufoExplosion = ParticleIO.loadEmitter("res/particles/ufo_explosion.xml");
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

	public ConfigurableEmitter getShotEmitterShip() {
		ConfigurableEmitter emitter = shotParticlesShip.duplicate();
		particleSystem.addEmitter(emitter);
		return emitter;
	}

	public ConfigurableEmitter getShotEmitterMoon() {
		ConfigurableEmitter emitter = shotParticlesMoon.duplicate();
		particleSystem.addEmitter(emitter);
		return emitter;
	}

	public ConfigurableEmitter getRocketSmokeEmitter() {
		return rocketSmoke;
	}

	public void ufoExplosion(int x, int y) {
		ConfigurableEmitter explosion = ufoExplosion.duplicate();
		explosion.setPosition(x, y);
		particleSystem.addEmitter(explosion);
	}
}
