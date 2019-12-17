package states;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

import entities.GameObject;
import entities.Lives;
import entities.Points;

public class YouWon extends GameObject {

	private int height;
	private int width;
	private int textWidth;
	private int textHeight;
	private Color transparent;
	private Font fontYouWon;
	private boolean isGameWon;
	private static final String YOU_WON = "Victory!";

	public YouWon(int height, int width, Font fontYouWon) {
		this.height = height;
		this.width = width;
		this.fontYouWon = fontYouWon;
		transparent = new Color(Color.black);
		transparent.a = 0.5f;
		if (fontYouWon != null) {
			textWidth = fontYouWon.getWidth(YOU_WON);
			textHeight = fontYouWon.getHeight(YOU_WON);
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(transparent);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		g.setFont(fontYouWon);
		g.drawString(YOU_WON + "\n" + "Gold: " + Points.points + "/30" + "\n" + "Lives: " + Lives.lives,
				(width / 2) - (textWidth / 2), (height / 2) - textHeight);
		return;
	}

	public void setYouWon(boolean isGameWon) {
		this.isGameWon = isGameWon;
	}

	public boolean isGameWon() {
		return isGameWon;
	}

}
