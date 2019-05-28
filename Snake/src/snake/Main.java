package snake;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Main() {

        initUI();
    }

    private void initUI() {

        add(new GameScene());

        /*The setResizable() method affects the insets of the JFrame container on some platforms.
         * Therefore, it is important to call it before the pack() method.
         * Otherwise, the collision of the snake's head with the right and bottom borders might not work correctly.*/
        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame ex = new Main();
            ex.setVisible(true);
        });
    }
}