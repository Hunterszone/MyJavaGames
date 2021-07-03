package main;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import enums.Images;
import game_engine.GameLogic;
import menu_engine.CanvasMenu;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
    public static final GraphicsDevice DEVICE = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    public static final Dimension DIM = Toolkit.getDefaultToolkit().getScreenSize();
    
	public Main() throws IOException {
		initUI();
	}

	private void initUI() throws IOException {

		add(new GameLogic());

		setResizable(false);
        setUndecorated(true);
		setTitle("Snake");
		DEVICE.setFullScreenWindow(this); // this or frame
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(Images.APPLE.getImg()));
        this.setLocation(DIM.width / 2 - this.getSize().width / 2, DIM.height / 2 - this.getSize().height / 2);
	}

	public static void main(String[] args) throws URISyntaxException {
		final CanvasMenu engine = new CanvasMenu();
		engine.start();
	}
}