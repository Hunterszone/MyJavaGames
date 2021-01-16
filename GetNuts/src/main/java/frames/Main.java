// frames.Main
//

package frames;

// import java libraries
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.net.URISyntaxException;

import javax.swing.JFrame;

// import game packages
import game_engine.Board;

// Main class
public class Main extends JFrame {
    private static final long serialVersionUID = 1L;
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    public static Dimension dim;

    public Main(){
        initGame();
    }

    // Start game in fullscreen
    public void initGame(){
        Board board = new Board();
        add(board); // Generate level and JPanel

        setResizable(false); // user can't resize
        setUndecorated(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        setSize(dim);
        setTitle("GetNuts");
        device.setFullScreenWindow(this);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Run Main class
    public static void main(String[] args) throws URISyntaxException {
        Main sokoban = new Main();
        sokoban.setVisible(true);
    }
}
