package snake;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
    public static final GraphicsDevice DEVICE = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    public static final Dimension DIM = Toolkit.getDefaultToolkit().getScreenSize();
    
	public Main() throws IOException {
		initUI();
	}

	private void initUI() throws IOException {

		add(new GameScene());

		setResizable(false);
        setUndecorated(true);
		setTitle("Snake");
		DEVICE.setFullScreenWindow(this); // this or frame
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(DIM.width / 2 - this.getSize().width / 2, DIM.height / 2 - this.getSize().height / 2);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			JFrame ex = null;
			try {
				ex = new Main();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ex.setVisible(true);
		});
	}
}