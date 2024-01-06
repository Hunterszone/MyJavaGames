package items;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.List;
import java.util.Objects;

import enemy.Alien;
import enemy.Dragon;
import enums.SoundEffect;
import enums.Sprite;
import game_engine.SpritePattern;
import sound_engine.PlayWave1st;
import util.Intersectable;
import util.KilledByChecker;
import util.LivesAndCounts;
import util.Moveable;

public class PlasmaBall extends SpritePattern implements Moveable, Intersectable {

	public static List<PlasmaBall> plasmaBalls;
	
	private static final long serialVersionUID = 1L;
	private final int EVILGUN_SPEED_X = 9;
	private final double EVILGUN_SPEED_Y = 2.5;
	private String imageName;

	public PlasmaBall(int x, int y) {
		super(x, y);
		initPlasmaBall();
	}

	public void initPlasmaBall() {
		imageName = Objects.requireNonNull(Sprite.PLASMABALL_INIT.getImg());
		loadImage(imageName);
		getImageDimensions();
	}

	@Override
	public void move() {
		x -= EVILGUN_SPEED_X;
	}

	public void movePlasmaBallDiagUp() {
		x -= EVILGUN_SPEED_X;
		y -= EVILGUN_SPEED_Y;
	}

	public void movePlasmaBallDiagDown() {
		x -= EVILGUN_SPEED_X;
		y += EVILGUN_SPEED_Y;
	}

	@Override
	public void intersects(Shape entity) {
		if (Alien.aliens.isEmpty() && Dragon.dragons.isEmpty() && LivesAndCounts.getLifeBunker() >= 50
				&& this.getBounds().intersects((Rectangle) entity)) {
//			FrameUtils.vibrate(MouseInputHandler.main);
			LivesAndCounts.decreasePlayerShipLife(1);
			this.setVisible(false);
			Explosion.explosions.add(new Explosion(((Rectangle) entity).getX(), ((Rectangle) entity).getY(), 30, 30));
			new PlayWave1st(SoundEffect.SCREAM.getSound()).start();
			KilledByChecker.checkKilledByEvilHead();
		}
	}

}