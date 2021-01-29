package main;

// import java libraries
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import game_engine.Board;

// Main class
public class Main extends JFrame {
    private static final long serialVersionUID = 1L;
    public static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    public Main(){
        initGame();
    }

    // Start game in fullscreen
    public void initGame(){
    	add(new Board()); // Generate level and JPanel
        setResizable(false); // user can't resize
        setUndecorated(true);
        setTitle("GetNuts");
        device.setFullScreenWindow(this); // this or frame
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    // Run Main class
    public static void main(String[] args) throws URISyntaxException {
    	Main sokoban = new Main();
		sokoban.setVisible(true);
    }
}
