package menu_states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import util.Constants;

public class MenuState {
	
	public static boolean isOn = true;
	
	public void render(Graphics g) {
		final Font titleFont = new Font("arial", Font.BOLD, 50);
		g.setFont(titleFont);
		g.setColor(Color.LIGHT_GRAY);
		
		final Font buttonFont = new Font("arial", Font.BOLD, 30);
		g.setFont(buttonFont);
		
		g.drawImage(Constants.LOAD_ASSETS.volume, 1100, 32, null);
		g.drawImage(Constants.LOAD_ASSETS.header, 428, 32, 352, 49, null);
        g.drawImage(Constants.LOAD_ASSETS.headerBackground, 180, 0, 802, 105, null);
		g.drawImage(Constants.LOAD_ASSETS.playGame, 410, 125, 388, 89, null);
		g.drawImage(Constants.LOAD_ASSETS.controlsButton, 410, 225, 388, 89, null);
		g.drawImage(Constants.LOAD_ASSETS.settingsButton, 410, 325, 388, 89, null);
		g.drawImage(Constants.LOAD_ASSETS.extrasButton, 410, 425, 388, 89, null);
		g.drawImage(Constants.LOAD_ASSETS.quitGame, 410, 525, 388, 89, null);
		g.drawImage(Constants.LOAD_ASSETS.header, 428, 732, 352, 49, null);
		g.drawImage(Constants.LOAD_ASSETS.headerBackground, 180, 700, 802, 105, null);
	}
}