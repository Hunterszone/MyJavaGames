package game_engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import animation.AsteroidAnimation;
import animation.AstronautAnimation;
import animation.SatelliteAnimation;
import animation.TheEndAnimationDown;
import animation.TheEndAnimationUp;
import enemy.Alien;
import enemy.Dragon;

public class DrawAnimations {
	
	static void drawAstronaut(Graphics g, DrawScene drawScene) {
		if (Dragon.dragons.isEmpty() && UpdateObjects.getLifeBunker() < 50) {
			final AffineTransform backup = ((Graphics2D) g).getTransform();
			AffineTransform a = AffineTransform.getRotateInstance(360, 400, 200);
			a.rotate(Math.toRadians(Math.ceil(Math.random())), (double) AstronautAnimation.ASTRONAUT.getX() / 2,
					(double) AstronautAnimation.ASTRONAUT.getY() / 2);
			((Graphics2D) g).setTransform(a);
			g.drawImage(AstronautAnimation.ASTRONAUT.getImage(), AstronautAnimation.ASTRONAUT.getX(),
					AstronautAnimation.ASTRONAUT.getY(), drawScene);
			a = AffineTransform.getRotateInstance(181, 850, 700);
			a.rotate(Math.toRadians(Math.ceil(Math.random())), (double) AstronautAnimation.ASTRONAUT.getX() / 2,
					(double) AstronautAnimation.ASTRONAUT.getY() / 2);
			((Graphics2D) g).setTransform(a);
			g.drawImage(AstronautAnimation.ASTRONAUT.getImage(), AstronautAnimation.ASTRONAUT.getX(),
					AstronautAnimation.ASTRONAUT.getY(), drawScene);
			Toolkit.getDefaultToolkit().sync();
			((Graphics2D) g).setTransform(backup);
		}
	}
	
	static void drawAsteroids(Graphics g, DrawScene drawScene) {
		if (Alien.aliens.size() > 0) {
			for (final AsteroidAnimation asteroidsAnim : AsteroidAnimation.ASTEROID_ANIMATION) {
				g.drawImage(asteroidsAnim.getImage(), asteroidsAnim.getX(), asteroidsAnim.getY(), drawScene);
				Toolkit.getDefaultToolkit().sync();
			}
		}
	}

	static void drawSatellite(Graphics g, DrawScene drawScene) {
		if (Alien.aliens.isEmpty() && !Dragon.dragons.isEmpty()) {
			g.drawImage(SatelliteAnimation.SATELLITE.getImage(), SatelliteAnimation.SATELLITE.getX(),
					SatelliteAnimation.SATELLITE.getY(), drawScene);
			final AffineTransform backup = ((Graphics2D) g).getTransform();
			final AffineTransform a = AffineTransform.getRotateInstance(200, 600, 1000);
			a.rotate(Math.toRadians(Math.ceil(Math.random())), (double) SatelliteAnimation.SATELLITE.getX() / 2,
					(double) SatelliteAnimation.SATELLITE.getY() / 2);
			((Graphics2D) g).setTransform(a);
			g.drawImage(SatelliteAnimation.SATELLITE.getImage(), SatelliteAnimation.SATELLITE.getX(),
					SatelliteAnimation.SATELLITE.getY(), drawScene);
			Toolkit.getDefaultToolkit().sync();
			((Graphics2D) g).setTransform(backup);
		}
	}

	static void drawElonsUp(Graphics g, DrawScene drawScene) {
		if (UpdateObjects.getLifeEvilHead() == 50) {
			for (final TheEndAnimationUp elonAnimUp : TheEndAnimationUp.END_ANIMATION_UP) {
				g.drawImage(elonAnimUp.getImage(), elonAnimUp.getX(), elonAnimUp.getY(), drawScene);
				Toolkit.getDefaultToolkit().sync();
			}
		}
			
	}

	static void drawElonsDown(Graphics g, DrawScene drawScene) {
		if (UpdateObjects.getLifeEvilHead() == 50) {
			for (final TheEndAnimationDown elonAnimDown : TheEndAnimationDown.END_ANIMATION_DOWN) {
				g.drawImage(elonAnimDown.getImage(), elonAnimDown.getX(), elonAnimDown.getY(), drawScene);
				Toolkit.getDefaultToolkit().sync();
			}
		}
	}
	
	static void handleAnimations() {

		for (AsteroidAnimation asteroidsAnim : AsteroidAnimation.ASTEROID_ANIMATION) {
			if (asteroidsAnim != null)
				asteroidsAnim = null;
		}
		AsteroidAnimation.ASTEROID_ANIMATION.clear();

		for (TheEndAnimationUp elonAnimUp : TheEndAnimationUp.END_ANIMATION_UP) {
			if (elonAnimUp != null)
				elonAnimUp = null;
		}
		TheEndAnimationUp.END_ANIMATION_UP.clear();

		for (TheEndAnimationDown elonAnimDown : TheEndAnimationDown.END_ANIMATION_DOWN) {
			if (elonAnimDown != null)
				elonAnimDown = null;
		}
		TheEndAnimationDown.END_ANIMATION_DOWN.clear();
	}
}
