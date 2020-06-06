package menu_engine;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import sound_engine.LoadSounds;

public class Display {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    
    public JFrame getFrame() {

        return frame;
    }

    public static JFrame frame;
    public static Canvas canvas;

    public Display() {
        init();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    private void init() {
        frame = new JFrame("Evil Menu");
        frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        canvas = new Canvas();

        canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setVisible(true);
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("images/cursor.png");
		Cursor c = toolkit.createCustomCursor(image, new Point(Display.canvas.getX(), 
				Display.canvas.getY()), "img");
		Display.canvas.setCursor(c);

		LoadSounds.menuMusic.loop();

        frame.add(canvas);
        frame.pack();
    }
}
