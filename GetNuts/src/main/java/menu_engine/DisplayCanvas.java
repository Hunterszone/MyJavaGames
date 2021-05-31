package menu_engine;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import enums.Images;
import sound_engine.LoadSounds;

public class DisplayCanvas {
	
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 800;
	
	public static JFrame frame;
	public static Canvas canvas;
	
	static Dimension dim;
	
	public JFrame getFrame() {
		return frame;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	private void init() {
		frame = new JFrame("GetNuts Menu");
		frame.setUndecorated(true);
		
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
	    frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	    frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
	    frame.setFocusable(true);
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Images.BAGGAGE.getImg()));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        
        canvas = new Canvas();
        
        canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setVisible(true);
        
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        
        
        LoadSounds.BG_MUSIC.loop();
        
        frame.add(canvas);
        frame.pack();
	}
}