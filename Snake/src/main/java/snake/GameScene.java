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

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameScene extends JPanel implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	//	First we will define the constants used in our game.
/*
 * The B_WIDTH and B_HEIGHT constants determine the size of the board.
 * The DOT_SIZE is the size of the apple and the dot of the snake.
 * The ALL_DOTS constant defines the maximum number of possible dots on the board (900 = (300*300)/(10*10)).
 * The RAND_POS constant is used to calculate a random position for an apple.
 * The DELAY constant determines the speed of the game.
 * */
    private final int B_WIDTH = 640;
    private final int B_HEIGHT = 480;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900; //max size of the snake
    private final int RAND_POS = 29;
    private final int DELAY = 140;

    //	These two arrays store the x and y coordinates of all joints of the snake.
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int bodyLength;
    private int apple_x;
    private int apple_y;
    //We need to declare a global variable to keep our score and to initialize it on 0
    private int myScore = 0;
    private int level = 1;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image bodySegment;
    private Image apple;
    private Image head;
    private Image bg;

    public GameScene() {

        initBoard();
    }

    private void initBoard() {

		Backgrounds.addBackgrounds();

        addKeyListener(new TAdapter());
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    /*In the loadImages() method we get the images for the game.
     * The ImageIcon class is used for displaying PNG images.
     * */
    private void loadImages() {

        ImageIcon snakeBody = new ImageIcon("res/dot.png");
        bodySegment = snakeBody.getImage();

        ImageIcon itemToCollect = new ImageIcon("res/apple.png");
        apple = itemToCollect.getImage();

        ImageIcon snakeHead = new ImageIcon("res/head.png");
        head = snakeHead.getImage();
    }

    /* In the initGame() method we create the snake,
     * randomly locate an apple on the board and start the timer.*/
    private void initGame() {

        bodyLength = 3; // min size of the snake

        for (int i = 0; i < bodyLength; i++) {
            x[i] = 50 - i * 10;
            y[i] = 50;
        }

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        if (inGame) {

        	for (int i = 0; i < Backgrounds.backgrounds.size(); i++) {
            		if (level >= Backgrounds.backgrounds.size())
    					g.drawImage(Backgrounds.backgrounds.get(0), 0, 0, null);
            		else
            			g.drawImage(Backgrounds.backgrounds.get(level-1), 0, 0, null);
            }
        	g.drawImage(apple, apple_x, apple_y, this);

        	Font small = new Font("Helvetica", Font.BOLD, 16);
            g.setColor(Color.white);
            g.setFont(small);
            // Here we will draw the score on the board
        	g.drawString("Points: " + myScore, 530, 25);
        	g.drawString("Level: " + level, 50, 25);


            for (int i = 0; i < bodyLength; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(bodySegment, x[i], y[i], this);
                }
            }


            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {

    	setBackground(Color.black);

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 16);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2 - 20);
        //Here we draw the high score on the Game Over screen
        g.drawString("High score: " + myScore, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2 + 10);

        level = 1;
    }

    /*If the apple collides with the head, we increase the number of joints of the snake.
     * We call the locateApple() method which randomly positions a new apple object.*/
    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            bodyLength++;
            //Here we will increment the score
            myScore++;
            locateApple();
            if(myScore % 5 == 0)
            	level++;
        }
    }

    /*In the move() method we have the key algorithm of the game.
     * To understand it, look at how the snake is moving.
     * We control the head of the snake. We can change its direction with the cursor keys.
     * The rest of the joints move one position up the chain.
     * The second joint moves where the first was, the third joint where the second was etc.*/
    private void move() {


        //	This code moves the joints up the chain.
    	for (int z = bodyLength; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

    	//  Following lines moves the head to the desired direction.
        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    // In the checkCollision() method, we determine if the snake has hit itself or one of the walls.
    private void checkCollision() {


        // If the snake hits one of its joints with its head the game is over.
        for (int z = bodyLength; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        // The game is finished if the snake hits the bottom of the board.
        if (y[0] >= B_HEIGHT) {
            inGame = false;
//        	y[0] = 0;
        }

        if (y[0] <= 0) {
            inGame = false;
//        	y[0] = B_HEIGHT;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
//        	x[0] = 0;
        }

        if (x[0] <= 0) {
            inGame = false;
//        	x[0] = B_WIDTH;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {

        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
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

            if (key == KeyEvent.VK_ESCAPE)
            		System.exit(0);

            //as a bonus I will also add restart functionality for you:

            if(key == KeyEvent.VK_R && inGame == false) { //restart when NOT in a game state

            	//If game is over, we need to reassign 0 to myScore, so that we begin incrementing from zero
            	// the next time
                myScore = 0;
            	inGame = true; //setting in-game state
            	loadImages(); // reloading the images
            	bodyLength = 3; // reseting the snake initial size

            	for(int i = 0; i < bodyLength; i++) {
            		//filling the X and Y arrays, based on the snake position
            		x[i] = 50 - i*10;
            		y[i] = 50;
            	}

            	locateApple(); //adding randomly the apple on every eaten one

            	timer.start(); //only starting the time, WITHOUT incrementing it !
            }

            if(key == KeyEvent.VK_R && timer.isRunning() == false) { //restart when in a game state

            	timer.restart();
            }

            if(key == KeyEvent.VK_P && inGame == true) { // game pause

            	timer.stop();
            }

            /* Choose a moving direction on key press;
             * if one directions is allowed, the others are forbidden */
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