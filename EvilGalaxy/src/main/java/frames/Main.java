package frames;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import enums.Images;
import game_engine.DrawScene;
import menu_engine.CanvasMenu;

public final class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	public static Dimension dim;

	public Main() {
		initGame();
	}

	public void initGame() {

		add(new DrawScene());
		setResizable(false);
		setUndecorated(true);
		pack();
		setTitle("EvilGalaxy");
		device.setFullScreenWindow(this); 
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setShape(new RoundRectangle2D.Double(60, 80, 1200, 1200, 100, 100));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Images.MYSHIPONFIRE.getImg()));
		ImageIcon tileIcon = new ImageIcon("images/shadow1.png");
		getRootPane().setBorder(BorderFactory.createMatteBorder(150, 150, 150, 150, tileIcon));
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	public static void main(String[] args) throws URISyntaxException {
		CanvasMenu engine = new CanvasMenu();
		engine.start();
	}
}