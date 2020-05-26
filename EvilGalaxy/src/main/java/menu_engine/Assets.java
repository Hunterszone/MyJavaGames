package menu_engine;

import java.awt.Image;

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
	public Image settingsButton;
	public Image quitGame;
	public Image settingsText;
	public Image settingsText2;
	public Image settingsText3;
	public Image info;
	public Image info2;
	public Image info3;
	public Image back;
	public Image headerBackground;
	public Image header;

	public void init() {
		blackBG = loadImage("images/backgrounds/main.jpg");
		menu = loadImage("images/buttons/menu.png");
		playGame = loadImage("images/buttons/playGame.png");
		settingsButton = loadImage("images/buttons/settings.png");
		quitGame = loadImage("images/buttons/quitGame.png");
		settingsText = loadImage("images/settingsText.png");
		settingsText2 = loadImage("images/settingsText2.png");
		settingsText3 = loadImage("images/settingsText3.png");
		info = loadImage("images/info.png");
		info2 = loadImage("images/info2.png");
		info3 = loadImage("images/info3.png");
		back = loadImage("images/buttons/back.png");
        headerBackground = loadImage("images/backgrounds/header.png");
        header = loadImage("images/header.png");
		getImageDimensions();
	}
}
