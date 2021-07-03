package game_engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import enums.Images;
import main.Main;
import menu_engine.CanvasMenu;
import menu_engine.MouseInputHandler;
import menu_states.MenuState;
import util.Background;
import util.LoadSounds;
import util.TextToSpeech;

public class GameLogic extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final double B_WIDTH = Main.DIM.getWidth();
	private static final double B_HEIGHT = Main.DIM.getHeight();
	private static final double DOT_SIZE = 10;
	private static final int ALL_DOTS = 900; // max size of the snake
	private static final int DELAY = 35;
	private static final Font HELVETICA = new Font("Helvetica", Font.BOLD, 26);
	private static final Random rand = new Random();

	// These two arrays store the x and y coordinates of all joints of the snake.
	private final int[] jointsX = new int[ALL_DOTS];
	private final int[] jointsY = new int[ALL_DOTS];

	private int bodyLength;
	private double appleX;
	private double appleY;
	private int myScore = 0;
	private int level = 1;

	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean inGame = true;

	private Timer timer;
	private transient Image bodySegment;
	private transient Image apple;
	private transient Image head;

	public GameLogic() throws IOException {
		initBoard();
	}

	private void initBoard() throws IOException {

		Background.addBackgrounds();

		addKeyListener(new TAdapter());
		setFocusable(true);

		setPreferredSize(new Dimension((int)B_WIDTH, (int)B_HEIGHT));
		loadImages();
		initGame();
	}

	/*
	 * In the loadImages() method we get the images for the game. The ImageIcon
	 * class is used for displaying PNG images.
	 */
	private void loadImages() {

		ImageIcon snakeBody = new ImageIcon(Images.DOT.getImg());
		bodySegment = snakeBody.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

		ImageIcon itemToCollect = new ImageIcon(Images.APPLE.getImg());
		apple = itemToCollect.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

		ImageIcon snakeHead = new ImageIcon(Images.HEAD.getImg());
		head = snakeHead.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	}

	/*
	 * In the initGame() method we create the snake, randomly locate an apple on the
	 * board and start the timer.
	 */
	private void initGame() {

		bodyLength = 10; // min size of the snake

		for (int i = 0; i < bodyLength; i++) {
			jointsX[i] = 50 - i * 10;
			jointsY[i] = 50;
		}

		setApple();

		timer = new Timer(DELAY, this);
		timer.start();

		LoadSounds.BG_SOUND.loop();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	private void doDrawing(Graphics g) {

		if (!inGame) {
			LoadSounds.BG_SOUND.stop();
			LoadSounds.GAME_OVER.play();
			gameOver(g);
		} else {
			if (!timer.isRunning()) {
				pauseGame(g);
			} else {
				drawLevel(g);
			}
		}
	}

	private void drawLevel(Graphics g) {
		for (int i = 0; i < Background.backgrounds.size(); i++) {
			if (level >= Background.backgrounds.size())
				g.drawImage(Background.backgrounds.get(0), 0, 0, null);
			else
				g.drawImage(Background.backgrounds.get(level - 1), 0, 0, null);
		}
		g.drawImage(apple, (int) appleX, (int) appleY, this);

		g.setColor(Color.white);
		g.setFont(HELVETICA);
		g.drawString("Level: " + level, (int) ((B_WIDTH / 2) - 150), 35);
		g.drawString("Points: " + myScore, (int) ((B_WIDTH / 2) + 100), 35);

		for (int i = 0; i < bodyLength; i++) {
			if (i == 0) {
				g.drawImage(head, jointsX[i], jointsY[i], this);
			} else {
				g.drawImage(bodySegment, jointsX[i], jointsY[i], this);
			}
		}
		Toolkit.getDefaultToolkit().sync();
	}

	private void gameOver(Graphics g) {

		setBackground(Color.black);

		String msg = "Game Over";
		String msg2 = "High score: ";
		String msg3 = "Press 'R' to restart";
		FontMetrics metr = getFontMetrics(HELVETICA);

		g.setColor(Color.white);
		g.setFont(HELVETICA);
		g.drawString(msg, ((int)B_WIDTH - metr.stringWidth(msg)) / 2, (int)B_HEIGHT / 2 - 80);
		g.drawString(msg2 + myScore, ((int)B_WIDTH - metr.stringWidth(msg2)) / 2, (int)B_HEIGHT / 2 - 40);
		g.drawString(msg3, ((int)B_WIDTH - metr.stringWidth(msg3)) / 2, (int)B_HEIGHT / 2);

		level = 1;
	}

	private void pauseGame(Graphics g) {

		setBackground(Color.black);

		String msg = "Paused";
		String msg2 = "Press 'R' to resume";
		FontMetrics metr = getFontMetrics(HELVETICA);

		g.setColor(Color.white);
		g.setFont(HELVETICA);
		g.drawString(msg, ((int)B_WIDTH - metr.stringWidth(msg)) / 2, (int)B_HEIGHT / 2 - 40);
		g.drawString(msg2, ((int)B_WIDTH - metr.stringWidth(msg2)) / 2, (int)B_HEIGHT / 2);
	}

	/*
	 * If the apple collides with the head, we increase the number of joints of the
	 * snake. We call the locateApple() method which randomly positions a new apple
	 * object.
	 */
	private void checkApple() {

		if ((appleX - DOT_SIZE <= jointsX[0] && jointsX[0] <= appleX + DOT_SIZE)
				&& (appleY - DOT_SIZE <= jointsY[0] && jointsY[0] <= appleY + DOT_SIZE)) {
			LoadSounds.BITE.play();
			bodyLength++;
			myScore++;
			setApple();
			if (myScore % 5 == 0) {
				LoadSounds.LEVEL_UP.play();
				level++;
			}
		}
	}

	/*
	 * In the move() method we have the key algorithm of the game. To understand it,
	 * look at how the snake is moving. We control the head of the snake. We can
	 * change its direction with the cursor keys. The rest of the joints move one
	 * position up the chain. The second joint moves where the first was, the third
	 * joint where the second was etc.
	 */
	private void move() {

		// This code moves the joints up the chain.
		for (int z = bodyLength; z > 0; z--) {
			jointsX[z] = jointsX[(z - 1)];
			jointsY[z] = jointsY[(z - 1)];
		}

		// Following lines moves the head to the desired direction.
		if (leftDirection) {
			jointsX[0] -= DOT_SIZE * 2;
		}

		if (rightDirection) {
			jointsX[0] += DOT_SIZE * 2;
		}

		if (upDirection) {
			jointsY[0] -= DOT_SIZE * 2;
		}

		if (downDirection) {
			jointsY[0] += DOT_SIZE * 2;
		}
	}

	private void checkCollision() {
		// If the snake hits one of its joints with its head the game is over.
		for (int z = bodyLength; z > 0; z--) {
			if ((z > 4) && (jointsX[0] == jointsX[z]) && (jointsY[0] == jointsY[z])) {
				inGame = false;
			}
		}

		// The game is finished if the snake hits the bottom of the board.
		if (jointsY[0] >= B_HEIGHT) {
			jointsY[0] = 0;
			return;
		}

		if (jointsY[0] <= 0) {
			jointsY[0] = (int)B_HEIGHT;
			return;
		}

		if (jointsX[0] >= B_WIDTH) {
			jointsX[0] = 0;
			return;
		}

		if (jointsX[0] <= 0) {
			jointsX[0] = (int)B_WIDTH;
			return;
		}

		if (!inGame) {
			timer.stop();
		}
	}

	private void setApple() {
		appleX = rand.nextInt((int)B_WIDTH - 50);
		appleY = rand.nextInt((int)B_HEIGHT - 50);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (inGame) {
			checkApple();
			checkCollision();
			move();
		}
		repaint();
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if (key == KeyEvent.VK_ESCAPE) {
				MenuState.isOn = false;
				LoadSounds.BG_SOUND.stop();
				TextToSpeech.playVoice("Loading main menu...");
				TextToSpeech.voiceInterruptor = true;
				if (MouseInputHandler.main != null) MouseInputHandler.main.dispose();
				MouseInputHandler.main = null;
				MenuState.isOn = true;
				final CanvasMenu engine = new CanvasMenu();
				engine.start();
			}

			if (key == KeyEvent.VK_R && !inGame) { // restart when NOT in a game state
				LoadSounds.RESTART.play();
				myScore = 0;
				inGame = true; // setting in-game state
				loadImages(); // reloading the images
				bodyLength = 10; // reseting the snake initial size
				for (int i = 0; i < bodyLength; i++) {
					// filling the X and Y arrays, based on the snake position
					jointsX[i] = 50 - i * 10;
					jointsY[i] = 50;
				}

				setApple(); // adding randomly the apple on every eaten one
				timer.start();
			}

			if (key == KeyEvent.VK_R) { // restart when in a game state
				timer.restart();
				LoadSounds.BG_SOUND.loop();
			}

			if (key == KeyEvent.VK_P) { // game pause
				timer.stop();
				LoadSounds.BG_SOUND.stop();
			}

			/*
			 * Choose a moving direction on key press; if one directions is allowed, the
			 * others are forbidden
			 */
			if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}

			if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}

			if ((key == KeyEvent.VK_UP) && (!downDirection)) {
				upDirection = true;
				rightDirection = false;
				leftDirection = false;
			}

			if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
				downDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
		}
	}
}