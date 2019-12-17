/*
 * @version 0.0 06.01.2011
 * @author Tobse F
 */
package states;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

import entities.GameObject;

public class GamePause extends GameObject {

	private int height;
	private int width;
	private int textWidth ;
	private int textHeight;
	private Color transparent;
	private Font fontGamePaused;
	private boolean isGamePaused;
	private static final String GAME_PAUSED = "Paused";

	public GamePause(int height, int width, Font fontGamePaused) {
		this.height = height;
		this.width = width;
		this.fontGamePaused = fontGamePaused;
		transparent = new Color(Color.black);
		transparent.a = 0.5f;
		if(fontGamePaused != null) {
			textWidth = fontGamePaused.getWidth(GAME_PAUSED);
			textHeight = fontGamePaused.getHeight(GAME_PAUSED);	
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(transparent);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		g.setFont(fontGamePaused);
		g.drawString(GAME_PAUSED, (width / 2) - (textWidth / 2), (height / 2) - textHeight);
	}

	public void setGamePaused(boolean isGamePaused) {
		this.isGamePaused = isGamePaused;
	}

	public boolean isGamePaused() {
		return isGamePaused;
	}
}
