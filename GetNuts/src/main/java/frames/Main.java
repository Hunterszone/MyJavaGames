// frames.Main
//
// Author: talemache
//

package frames;

// import java libraries
import java.awt.*;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

// import game packages
import game_engine.Area;
import game_engine.Board;
import game_engine.LevelsBgsEngine;

// Main class
public class Main {
    private static final long serialVersionUID = 1L;
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    public static Dimension dim;

    public Main(){
        initGame();
    }

    // Start game in fullscreen
    public void initGame(){
        // add(new Board()); // Generate level and JPanel
        JFrame frame = new JFrame();

        frame.setResizable(false); // user can't resize
        frame.setUndecorated(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setSize(dim);
        frame.setTitle("GetNuts");
        device.setFullScreenWindow(frame); // this or frame
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    // Run Main class
    public static void main(String[] args) throws URISyntaxException {

    }
}
