package menu_engine;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import enums.Images;
import util.LoadSounds;

public class DisplayCanvas {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;    
    public static final JFrame frame = new JFrame("Evil Menu");
    public static final Canvas canvas = new Canvas();
//	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    static Dimension dim;
    
    public DisplayCanvas() {
    	init();
    }

    public JFrame getFrame() {
        return frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    private static void init() {
        frame.setUndecorated(true);
//        device.setFullScreenWindow(frame); 
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Images.ICON.getImg()));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        
        canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setVisible(true);
        
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Image image = toolkit.getImage(Images.CURSOR.getImg());
		final Cursor c = toolkit.createCustomCursor(image, new Point(DisplayCanvas.canvas.getX(), 
				DisplayCanvas.canvas.getY()), "img");
		DisplayCanvas.canvas.setCursor(c);

		LoadSounds.BG_SOUND.loop();

        frame.add(canvas);
        frame.pack();
    }
}
