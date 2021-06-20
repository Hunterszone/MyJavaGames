package snake;

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

import util.LoadSounds;

public class GameScene extends JPanel implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// First we will define the constants used in our game.
	/*
	 * The B_WIDTH and B_HEIGHT constants determine the size of the board. The
	 * DOT_SIZE is the size of the apple and the dot of the snake. The ALL_DOTS
	 * constant defines the maximum number of possible dots on the board (900 =
	 * (300*300)/(10*10)). The RAND_POS constant is used to calculate a random
	 * position for an apple. The DELAY constant determines the speed of the game.
	 */
	private final int B_WIDTH = (int) Main.DIM.getWidth();
	private final int B_HEIGHT = (int) Main.DIM.getHeight();
	private static final int DOT_SIZE = 10;
	private static final int ALL_DOTS = 900; // max size of the snake
	private static final int RAND_POS = 100;
	private static final int DELAY = 140;
	private static final Font HELVETICA = new Font("Helvetica", Font.BOLD, 26);
	private static final Random rand = new Random();

	// These two arrays store the x and y coordinates of all joints of the snake.
	private final int[] jointsX = new int[ALL_DOTS];
	private final int[] jointsY = new int[ALL_DOTS];

	private int bodyLength;
	private int appleX;
	private int appleY;
	private int myScore = 0;
	private int level = 1;

	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean inGame = true;
	private boolean isPaused;

	private Timer timer;
	private transient Image bodySegment;
	private transient Image apple;
	private transient Image head;

	public GameScene() throws IOException {

		initBoard();
	}

	private void initBoard() throws IOException {

		Background.addBackgrounds();

		addKeyListener(new TAdapter());
		setFocusable(true);

		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		loadImages();
		initGame();
	}

	/*
	 * In the loadImages() method we get the images for the game. The ImageIcon
	 * class is used for displaying PNG images.
	 */
	private void loadImages() {

		ImageIcon snakeBody = new ImageIcon("res/dot.png");
		bodySegment = snakeBody.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

		ImageIcon itemToCollect = new ImageIcon("res/apple.png");
		apple = itemToCollect.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

		ImageIcon snakeHead = new ImageIcon("res/head.png");
		head = snakeHead.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	}

	/*
	 * In the initGame() method we create the snake, randomly locate an apple on the
	 * board and start the timer.
	 */
	private void initGame() {

		bodyLength = 3; // min size of the snake

		for (int i = 0; i < bodyLength; i++) {
			jointsX[i] = 50 - i * 10;
			jointsY[i] = 50;
		}

		setApple();

		timer = new Timer(DELAY, this);
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);
	}

	private void doDrawing(Graphics g) {

		int caseNumber = 0;
		if(!inGame) {
			caseNumber = 0;
		}
		if (inGame && isPaused) {
			caseNumber = 1;
		} 
		if (inGame && !isPaused) {
			caseNumber = 2;
		} 
		
		switch (caseNumber) {
		case 0:
			LoadSounds.bgMusic.stop();
			LoadSounds.die.play();
			gameOver(g);
			break;
		case 1:
			pauseGame(g);
			break;
		case 2:
			for (int i = 0; i < Background.backgrounds.size(); i++) {
				if (level >= Background.backgrounds.size())
					g.drawImage(Background.backgrounds.get(0), 0, 0, null);
				else
					g.drawImage(Background.backgrounds.get(level - 1), 0, 0, null);
			}
			g.drawImage(apple, appleX, appleY, this);

			g.setColor(Color.white);
			g.setFont(HELVETICA);
			// Here we will draw the score on the board
			g.drawString("Level: " + level, (B_WIDTH / 2) - 150, 35);
			g.drawString("Points: " + myScore, (B_WIDTH / 2) + 100, 35);

			for (int i = 0; i < bodyLength; i++) {
				if (i == 0) {
					g.drawImage(head, jointsX[i], jointsY[i], this);
				} else {
					g.drawImage(bodySegment, jointsX[i], jointsY[i], this);
				}
			}
			Toolkit.getDefaultToolkit().sync();
			break;
		default:
			break;
		}
	}

	private void gameOver(Graphics g) {

		setBackground(Color.black);

		String msg = "Game Over";
		String msg2 = "High score: ";
		String msg3 = "Press 'R' to restart";
		FontMetrics metr = getFontMetrics(HELVETICA);

		g.setColor(Color.white);
		g.setFont(HELVETICA);
		g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2 - 80);
		g.drawString(msg2 + myScore, (B_WIDTH - metr.stringWidth(msg2)) / 2, B_HEIGHT / 2 - 40);
		g.drawString(msg3, (B_WIDTH - metr.stringWidth(msg3)) / 2, B_HEIGHT / 2);

		level = 1;
	}

	private void pauseGame(Graphics g) {

		setBackground(Color.black);

		String msg = "Paused";
		String msg2 = "Press 'R' to resume";
		FontMetrics metr = getFontMetrics(HELVETICA);

		g.setColor(Color.white);
		g.setFont(HELVETICA);
		g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2 - 40);
		g.drawString(msg2, (B_WIDTH - metr.stringWidth(msg2)) / 2, B_HEIGHT / 2);
	}

	/*
	 * If the apple collides with the head, we increase the number of joints of the
	 * snake. We call the locateApple() method which randomly positions a new apple
	 * object.
	 */
	private void checkApple() {

		if ((appleX-DOT_SIZE <= jointsX[0] && jointsX[0] <= appleX+DOT_SIZE) 
				&& (appleY-DOT_SIZE <= jointsY[0] && jointsY[0] <= appleY+DOT_SIZE)) {
			LoadSounds.bite.play();
			bodyLength++;
			// Here we will increment the score
			myScore++;
			setApple();
			if (myScore % 5 == 0) {
				LoadSounds.levelUp.play();
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
			jointsX[0] -= DOT_SIZE*2;
		}

		if (rightDirection) {
			jointsX[0] += DOT_SIZE*2;
		}

		if (upDirection) {
			jointsY[0] -= DOT_SIZE*2;
		}

		if (downDirection) {
			jointsY[0] += DOT_SIZE*2;
		}
	}

	// In the checkCollision() method, we determine if the snake has hit itself or
	// one of the walls.
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
			jointsY[0] = B_HEIGHT;
			return;
		}

		if (jointsX[0] >= B_WIDTH) {
			jointsX[0] = 0;
			return;
		}

		if (jointsX[0] <= 0) {
			jointsX[0] = B_WIDTH;
			return;
		}

		if (!inGame) {
			timer.stop();
		}
	}

	private void setApple() {
		appleX = appleY = (rand.nextInt(RAND_POS) * DOT_SIZE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (inGame) {
			if (!isPaused) {
				LoadSounds.bgMusic.loop();
			}
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

			if (key == KeyEvent.VK_ESCAPE)
				System.exit(0);

			// as a bonus I will also add restart functionality for you:

			if (key == KeyEvent.VK_R && !inGame) { // restart when NOT in a game state

				// If game is over, we need to reassign 0 to myScore, so that we begin
				// incrementing from zero
				// the next time
				LoadSounds.restart.play();
				myScore = 0;
				inGame = true; // setting in-game state
				loadImages(); // reloading the images
				bodyLength = 3; // reseting the snake initial size

				for (int i = 0; i < bodyLength; i++) {
					// filling the X and Y arrays, based on the snake position
					jointsX[i] = 50 - i * 10;
					jointsY[i] = 50;
				}

				setApple(); // adding randomly the apple on every eaten one

				timer.start(); // only starting the time, WITHOUT incrementing it !
			}

			if (key == KeyEvent.VK_R) { // restart when in a game state
				isPaused = false;
			}

			if (key == KeyEvent.VK_P) { // game pause
				LoadSounds.bgMusic.stop();
				isPaused = true;
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