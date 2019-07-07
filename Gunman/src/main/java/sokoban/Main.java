package sokoban;

import java.awt.Toolkit;

import javax.swing.JFrame;

public final class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	public Main() {
		InitUI();
	}

	public void InitUI() {
		Board board = new Board();
		add(board);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("GetNuts");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/sokoban.png"));
	}

	public static void main(String[] args) {
		Main sokoban = new Main();
		sokoban.setVisible(true);
	}
}
