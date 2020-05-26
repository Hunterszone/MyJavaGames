package menu_states;

import menu_engine.Constants;

import java.awt.*;

public class SettingsState {
    public static boolean isOn;
	public void render(Graphics g) {

        g.drawImage(Constants.LOAD_ASSETS.header, 428, 32, 352, 49, null);
        g.drawImage(Constants.LOAD_ASSETS.headerBackground, 180, 0, 802, 105, null);
        g.drawImage(Constants.LOAD_ASSETS.back, 410, 135, 388, 89, null);
        g.drawImage(Constants.LOAD_ASSETS.settingsText, 200, 270, 400, 120, null);
		g.drawImage(Constants.LOAD_ASSETS.info, 700, 270, 400, 120, null);
		g.drawImage(Constants.LOAD_ASSETS.settingsText2, 195, 450, 400, 120, null);
		g.drawImage(Constants.LOAD_ASSETS.info2, 700, 450, 400, 120, null);
		g.drawImage(Constants.LOAD_ASSETS.settingsText3, 180, 630, 400, 120, null);
		g.drawImage(Constants.LOAD_ASSETS.info3, 700, 630, 400, 120, null);
	}
}
