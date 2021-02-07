package main;
/*
 * This is the main class, the levels, frame and panels are instantiated here.
 *
 */

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import city.cs.engine.SimulationSettings;
import city.cs.engine.UserView;
import city.cs.engine.World;
import controller.KeyboardHandler;
import levelsEngine.LevelsInitializer;

public class Main extends SimulationSettings implements Runnable {
	private JLayeredPane layeredPane;
	private World world;
	private JPanel health, projectilePanel, scorePanel;
	private final KeyboardHandler kh;
	private final UserView view;
	private final LevelsInitializer levels;
	private JFrame frame;
	private int resolutionX, resolutionY;

	/**
	 * initializes the game, the gui and constructs the levels object.
	 *
	 * @param resolutionX the horizontal resolution of the game window
	 * @param resolutionY the vertical resolution of the game window
	 * @param fps         the frame rate of the game
	 */
	public Main(int resolutionX, int resolutionY, int fps) {
		super(fps);

		this.resolutionX = resolutionX;
		this.resolutionY = resolutionY;
		// display the view in a frame
		frame = new JFrame("MyGame");
		frame.setBackground(Color.blue);
		frame.setSize(resolutionX, resolutionY);
		layeredPane = new JLayeredPane();
		levels = new LevelsInitializer(layeredPane, resolutionX, resolutionY, frame, fps);
		this.world = levels.getLevel();
		health = levels.getPlayer().getHealthPanel();
		view = levels.getView();
		kh = levels.getKeyboardHandler();

		frame.setFocusable(true);
		// quit the application when the game window is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setAutoRequestFocus(true);
		// don't let the game window be resized
		frame.setResizable(false);
	}

	/**
	 * get the frame
	 * 
	 * @return the game window
	 */
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
		this.frame.setSize(resolutionX, resolutionY);
		this.frame.setFocusable(true);
		// quit the application when the game window is closed
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocationByPlatform(true);
		this.frame.setAutoRequestFocus(true);
		// don't let the game window be resized
		this.frame.setResizable(false);
	}

	/**
	 * when this is called, the frame will be setup, and all the parts of the gui
	 * will be added to the frame.
	 * 
	 */
	@Override
	public void run() {
		resolutionX = frame.getWidth();
		resolutionY = frame.getHeight();

		layeredPane.setOpaque(false);
		layeredPane.setPreferredSize(new Dimension(resolutionX, resolutionY));

		view.setCentre(levels.getPlayer().getPosition());
		view.setBounds(0, 0, resolutionX, resolutionY);
		levels.setView(view);

		// uncomment this to draw a 1-metre grid over the view
//        view.setGridResolution(1);

		levels.getPlayer().drawPlayerHealth();
		levels.getPlayer().drawPlayerScore();

		layeredPane.add(health, 0);
		health.setBounds(20, 5, resolutionX - 20, 50);

		projectilePanel = levels.getPlayer().getProjectilePanel();
		levels.getPlayer().drawPlayerShots();

		layeredPane.add(projectilePanel, 0);
		projectilePanel.setBounds(20, health.getHeight() + 5, resolutionX - 20, 50);

		layeredPane.add(view, 1);

		frame.addKeyListener(kh);
		frame.add(layeredPane);
		// size the game window to fit the world view
		frame.pack();
		// make the window visible
		frame.setVisible(true);
	}

	public static void main(String[] args) throws InterruptedException {
		int horizontal = 1024, vertical = 768;

		Main game = new Main(horizontal, vertical, 60);
		Thread t1 = new Thread(game);

		PreGameScreen pg = new PreGameScreen(game.getFrame(), horizontal, vertical);
		Thread t2 = new Thread(pg);
		t2.start();
		t2.join();
		t1.start();

		// System.out.println(game.getFpsAverageCount());
		// System.out.println(Thread.currentThread().getName());
	}

}