package menu_states;

import java.awt.Graphics;

import util.Constants;

public class ManualState {
    public static boolean isOn;
	public void render(Graphics g) {

		g.drawImage(Constants.LOAD_ASSETS.volume, 1100, 32, null);
		g.drawImage(Constants.LOAD_ASSETS.header, 428, 32, 352, 49, null);
        g.drawImage(Constants.LOAD_ASSETS.headerBackground, 180, 0, 802, 105, null);
        g.drawImage(Constants.LOAD_ASSETS.back, 410, 135, 388, 89, null);
        g.drawImage(Constants.LOAD_ASSETS.manualText, 350, 245, null);
	}
}
