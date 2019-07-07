package allinone;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class GameMedium extends GameObject {

	private int height;
	private int width;
	private int textWidth ;
	private int textHeight;
	private Color transparent;
	private Font fontGameMedium;
	private boolean isGameMedium;
	private static final String GAME_MEDIUM = "Med";

	public GameMedium(int height, int width, Font fontGameMedium) {
		this.height = height;
		this.width = width;
		this.fontGameMedium = fontGameMedium;
		textWidth = fontGameMedium.getWidth(GAME_MEDIUM);
		textHeight = fontGameMedium.getHeight(GAME_MEDIUM);

	}

	@Override
	public void draw(Graphics g) {
		g.setColor(transparent);
		g.setFont(fontGameMedium);
		g.drawString(GAME_MEDIUM, (width / 2) - (textWidth / 2), (height / 2) - textHeight - 290);
	}

	public void setGameMedium(boolean isGameMedium) {
		this.isGameMedium = isGameMedium;
	}

	public boolean isGameMedium() {
		return isGameMedium;
	}
}
