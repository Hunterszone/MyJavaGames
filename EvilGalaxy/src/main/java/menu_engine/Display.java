package menu_engine;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

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

        frame.add(canvas);
        frame.pack();
    }
}
