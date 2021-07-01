package menu_states;

import java.awt.*;

import util.Constants;

public class ControlsState {
    public static boolean isOn;
	public void render(Graphics g) {

		g.drawImage(Constants.LOAD_ASSETS.volume, 1100, 32, null);
		g.drawImage(Constants.LOAD_ASSETS.header, 428, 32, 352, 49, null);
        g.drawImage(Constants.LOAD_ASSETS.headerBackground, 180, 0, 802, 105, null);
        g.drawImage(Constants.LOAD_ASSETS.back, 410, 135, 388, 89, null);
        g.drawImage(Constants.LOAD_ASSETS.controlsText, 200, 270, 400, 120, null);
		g.drawImage(Constants.LOAD_ASSETS.info, 700, 270, 400, 120, null);
	}
}
