package snake;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
    public static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    
	public Main() {

		initUI();
	}

	private void initUI() {

		add(new GameScene());

		setResizable(false);
        setUndecorated(true);
		setTitle("Snake");
		device.setFullScreenWindow(this); // this or frame
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			JFrame ex = new Main();
			ex.setVisible(true);
		});
	}
}