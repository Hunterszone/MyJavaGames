package potogold;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class GameOver extends GameObject {

	private int height;
	private int width;
	private int textWidth ;
	private int textHeight;
	private Color transparent;
	private Font fontGameOver;
	private boolean isGameOver;
	private static final String GAME_OVER = "FAILED!";

	public GameOver(int height, int width, Font fontGameOver) {
		this.height = height;
		this.width = width;
		this.fontGameOver = fontGameOver;
		transparent = new Color(Color.black);
		transparent.a = 0.5f;
		textWidth = fontGameOver.getWidth(GAME_OVER);
		textHeight = fontGameOver.getHeight(GAME_OVER);
		
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(transparent);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		g.setFont(fontGameOver);
		g.drawString(GAME_OVER + "\n" + 
		"Lives: " + Lives.lives + "\n" +
		"Gold: " + Points.points + "/30" + "\n" +
		"Target: 30" + "\n", (width / 2) - (textWidth / 2), (height / 3) - textHeight);
		return;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public boolean isGameOver() {
		return isGameOver;
	}
}
