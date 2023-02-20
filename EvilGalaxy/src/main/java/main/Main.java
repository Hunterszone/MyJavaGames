package main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import enums.Sprite;
import game_engine.DrawScene;
import game_engine.InitObjects;
import util.Constants;

public final class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	public Main() throws IOException {
		initGame();
	}

	public void initGame() throws IOException {
		add(new DrawScene());
		setResizable(false);
		setUndecorated(true);
		pack();
		setTitle("EvilGalaxy");
		device.setFullScreenWindow(this);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setShape(new RoundRectangle2D.Double(60, 80, 1200, 1200, 100, 100));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sprite.MYSHIP_ON_FIRE.getImg()));
		final ImageIcon tileIcon = new ImageIcon(Sprite.BORDER_IMAGE.getImg());
		getRootPane().setBorder(BorderFactory.createMatteBorder(100, 100, 100, 100, tileIcon));
		this.setLocation(Constants.DIMENSION.width / 2 - this.getSize().width / 2,
				Constants.DIMENSION.height / 2 - this.getSize().height / 2);
	}

	public static void main(String[] args) throws URISyntaxException {
		InitObjects.initCanvas();
	}

}