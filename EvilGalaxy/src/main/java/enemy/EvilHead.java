package enemy;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import enums.Sprite;
import enums.SoundEffect;
import game_engine.SpritePattern;
import items.CanonBall;
import items.PlasmaBall;
import menu_engine.CanvasMenu;
import menu_engine.ImageColorizer;
import sound_engine.PlayWave1st;
import util.Constants;
import util.LivesAndCounts;

public class EvilHead extends SpritePattern {

	public static EvilHead evilHead;
	public double speedX, speedY;
	private static final long serialVersionUID = 1L;
	
	public EvilHead(int x, int y) {
		super(x, y);
		drawHead();
		initAmmo();
	}

	public void drawHead() {
		String imageName = Sprite.EVILHEAD.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	public void strikeHead() {
		String imageName = Sprite.STRIKEHEAD.getImg();
		assert imageName != null;
		loadImage(imageName);
		getImageDimensions();
	}

	private void initAmmo() {
		PlasmaBall.plasmaBalls = new ArrayList<>();
		CanonBall.canonBalls = new ArrayList<>();
	}

	public List<PlasmaBall> getEvilPlasmaBalls() {
		return PlasmaBall.plasmaBalls;
	}

	public List<CanonBall> getCanons() {
		return CanonBall.canonBalls;
	}

	public void loadPlasmaBall() {
		PlasmaBall.plasmaBalls.add(new PlasmaBall(x + width, y + height / 2));
		if (LivesAndCounts.getLifeBunker() >= 50) new PlayWave1st(SoundEffect.BOING_2.getSound()).start();
	}

	public void loadCanon() {
		CanonBall.canonBalls.add(new CanonBall(x + width, y + height / 2));
		if (LivesAndCounts.getLifeBunker() >= 50) new PlayWave1st(SoundEffect.BOING.getSound()).start();
	}

	public void renderEvilHead(Graphics g) {
		g.drawImage(ImageColorizer.dye(Constants.LOAD_ASSETS.evilHead, CanvasMenu.color2.getColor()),
				Math.round(this.getX()), Math.round(this.getY()), null);
	}
}