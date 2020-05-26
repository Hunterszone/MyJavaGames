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

    private JFrame frame;
    private Canvas canvas;

    public Display() {
        init();
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    private void init() {
        this.frame = new JFrame("Evil Galaxy CanvasMenu");
        this.frame.setVisible(true);
        this.frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        this.frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.frame.setFocusable(true);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);

        this.canvas = new Canvas();

        this.canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        this.canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.canvas.setVisible(true);

        this.frame.add(canvas);
        this.frame.pack();
    }
}
