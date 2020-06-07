package menu_engine;

import java.awt.Image;

import game_engine.Images;
import game_engine.SpritePattern;

public class Assets extends SpritePattern {

	private static final long serialVersionUID = 1L;
	
	public Assets(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	public Image blackBG;
	public Image menu;
	public Image playGame;
	public Image loadGame;
	public Image controlsButton;
	public Image settingsButton;
	public Image extrasButton;
	public Image quitGame;
	public Image settingsText;
	public Image settingsText2;
	public Image controlsText;
	public Image controlsText2;
	public Image controlsText3;
	public Image info;
	public Image info2;
	public Image info3;
	public Image back;
	public Image updater;
	public Image gamerepo;
	public Image gamesite;
	public Image headerBackground;
	public Image header;
	public Image myShip;
	public Image evilHead;
	public Image manual;
	public Image manualText;
	public Image volume;

	public void init() {
		blackBG = loadImage("images/backgrounds/main.jpg");
		playGame = loadImage("images/buttons/playGame.png");
		loadGame = loadImage("images/buttons/loadGame.png");
		controlsButton = loadImage("images/buttons/controls.png");
		settingsButton = loadImage("images/buttons/settings.png");
		extrasButton = loadImage("images/buttons/extras.png");
		quitGame = loadImage("images/buttons/quitGame.png");
		back = loadImage("images/buttons/back.png");
		updater = loadImage("images/buttons/update.png");
		gamerepo = loadImage("images/buttons/gamerepo.png");
		gamesite = loadImage("images/buttons/gamesite.png");
		settingsText = loadImage("images/settingsText.png");
		settingsText2 = loadImage("images/settingsText2.png");
		controlsText = loadImage("images/controlsText.png");
		controlsText2 = loadImage("images/controlsText2.png");
		controlsText3 = loadImage("images/controlsText3.png");
		info = loadImage("images/info.png");
		info2 = loadImage("images/info2.png");
		info3 = loadImage("images/info3.png");
        headerBackground = loadImage("images/backgrounds/header.png");
        header = loadImage("images/header.png");
        myShip = loadImage(Images.MYSHIPINIT.getImg());
        evilHead = loadImage(Images.EVILHEAD.getImg());
        manual = loadImage("images/manual.png");
        manualText = loadImage("images/manualText.png");
        volume = loadImage(Images.VOLUMEMUTE.getImg());
		getImageDimensions();
	}
}
