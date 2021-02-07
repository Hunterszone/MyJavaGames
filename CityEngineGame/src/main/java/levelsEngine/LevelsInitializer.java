package levelsEngine;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumSet;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import org.jbox2d.common.Vec2;

import city.cs.engine.DebugViewer;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import city.cs.engine.UserView;
import city.cs.engine.World;
import controller.KeyboardHandler;
import controller.MouseHandler;
import levels.Level1;
import levels.Level2;
import levels.Level3;
import levels.Level4;
import main.HighScore;
import shapes.Player;

/**
 * The Levels object is a collection of objects type Level. This is where the
 * levels are managed, and where the certain body objects (such as Player) are
 * passed through to the levels
 */
public final class LevelsInitializer implements LevelListener, StepListener, EndGameListener {

    /**
     * enum set of the possible levels
     */
    public enum LevelNumber {
        LEVEL1(1), LEVEL2(2), LEVEL3(3), LEVEL4(4);

        /**
         * map of the levels
         */
        private static final Map<Integer, LevelNumber> lookup
                = new HashMap<>();

        /**
         * populate the map immediately
         */
        static {
            EnumSet.allOf(LevelNumber.class).forEach(ln -> {
                lookup.put(ln.getCode(), ln);
            });
        }

        /**
         * the int value of each enum type
         */
        private final int code;

        /**
         * assign a code for the level
         *
         * @param code int value to assign the the object
         */
        private LevelNumber(int code) {
            this.code = code;
        }

        /**
         * finds the integer representation of the level type
         *
         * @return integer representation of level type
         */
        public int getCode() {
            return code;
        }

        /**
         *
         * @param code unique value for each level type
         * @return its integer representation
         */
        public static LevelNumber getLevelNumber(int code) {
            return lookup.get(code);
        }
    }

    /**
     * integer value of the current level
     */
    private LevelNumber levelNumber;
    /**
     * the current level that is being run
     */
    private LevelBuilder level;
    /**
     * the view of the game
     */
    private UserView view;
    /**
     * the current player object
     */
    private Player player;
    /**
     * the keyboard handler that is handling keyboard input
     */
    private final KeyboardHandler kh;
    /**
     * the swing component that all other panels will be added to
     */
    private final JLayeredPane layeredPane;
    /**
     * resolution of the game window
     */
    private final int resolutionX, resolutionY;
    /**
     * the mouse handler that is handing mouse
     */
    private MouseHandler mh;
    /**
     * the window that is displaying the game
     */
    private final JFrame frame;
    /**
     * the frame rate the game is running at
     */
    private final int frameRate;
    /**
     * the background that is set for the current level
     */
    private JLabel background;
    /**
     * the debug viewer of the game, is disabled by default
     */
    private final JFrame debugView;
    /**
     * the highscore object that is associated with the current game session
     */
    private final HighScore highScore;
    /**
     * the amount of milliseconds since epoch that the game was started at
     */
    private final long startTime;

    /**
     * constructs the levels object, and calls the methods to set the current
     * level to level 1
     *
     * @param layeredPane the swing object that contains all other panes in the
     * game
     * @param resolutionX the horizontal resolution of the game window
     * @param resolutionY the vertical resolution of the game window
     * @param frame the window that is displaying the game
     * @param frameRate the frame rate that the game is running at
     */
    public LevelsInitializer(JLayeredPane layeredPane, int resolutionX, int resolutionY, JFrame frame, int frameRate) {
        this.layeredPane = layeredPane;
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        level = new Level1();
        view = new UserView(level, resolutionY, resolutionY);
        player = new Player((World) level, this);

        level.start();
        mh = new MouseHandler(view, level, player);
        kh = new KeyboardHandler(level, player, layeredPane, this);
        this.frame = frame;
        this.frameRate = frameRate;
        levelNumber = LevelNumber.LEVEL1;
        background = new JLabel();
        debugView = new DebugViewer(this.level, resolutionX, resolutionY);
        changeLevel();
        startTime = System.currentTimeMillis();
        double timeElapsed = (System.currentTimeMillis() - startTime) / 1000d;
        highScore = new HighScore();
    }

    /**
     * the action to be called to set the level to the level whose integer
     * representation corresponds to levelNumber
     */
    public void changeLevel() {
        switch (levelNumber) {
            case LEVEL1:
                LevelBuilder level1 = new Level1();
                switchLevel(level1);
                break;

            case LEVEL2:
                Level2 level2 = new Level2();
                switchLevel(level2);
                level2.spawnSawBlades(player);

//                Vec2 sawBladePosition = level2.getSawBladePos();                
//                level2.addStepListener(new StepListener(){
//                    @Override
//                    public void postStep(StepEvent e){
//                        if (player.getPosition().sub(sawBladePosition).length()<5){
//                            level2.spawnSawBlades(false);
//                        }
//                    }
//                    
//                    @Override
//                    public void preStep(StepEvent e) {}
//                });
                break;

            case LEVEL3:
                Level3 level3 = new Level3();
                switchLevel(level3);
                level3.spawnBarrels();
                break;

            case LEVEL4:
                Level4 level4 = new Level4();
                switchLevel(level4);
                break;

            default:
                System.out.println("handle this pls");
        }

    }

    /**
     * this will load the next level and remove the old one
     * @param level the level to switch to
     */
    private void switchLevel(LevelBuilder level) {
        nextLevel(level);

        layeredPane.remove(view);
        initializeView();
        layeredPane.add(view, 1);

        level.start();
    }

    /**
     * finalizes the current level by removing all the listeners, then
     * initializing the next level.
     *
     * Also calls the player object to be initialized
     *
     * @param level the level to switch to
     */
    private void nextLevel(LevelBuilder level) {
        //uncomment to show debug viewer
        debugView.dispose();
        this.level.removeChangeLevelListener(this);
        this.level.removeEndGameListener(this);
        this.level.removeStepListener(this);
        this.level.getBodies().forEach((k, v) -> v.destroy());
        this.level = level;
        this.level.addChangeLevelListener(this);
        this.level.addEndGameListener(this);
        this.level.addStepListener(this);
        initializePlayer(this.level);
        initializeBackground();
        //uncomment to show debug viewer
//        debugView = new DebugViewer(this.level, resolutionX, resolutionY);
        level.start();
    }

    /**
     * finalizes the player object, and initializes a new one
     *
     * @param level the level that the game is changing to
     */
    private void initializePlayer(LevelBuilder level) {
        player.cleanup();
        player = new Player(level, this);
        Vec2 startPosition = level.getBody("start").getPosition();
        startPosition.y += 1;
        player.setPosition(startPosition);
//        player.putOn(level.getBody("start"));
        player.setAngle(0);
        player.getHealthPanel().setBounds(20, 5, resolutionX - 20, 50);
        player.getProjectilePanel().setBounds(20, player.getHealthPanel().getHeight() + 5, resolutionX - 20, 50);
        player.getScorePanel().setBounds(520, 0, resolutionX - 20, 50);
        layeredPane.add(player.getHealthPanel(), 0);
        layeredPane.add(player.getProjectilePanel(), 0);
        layeredPane.add(player.getScorePanel(), 0);
    }

    /**
     * when a level is changed, this is called as it will finalize the old view
     * then make a new one
     */
    private void initializeView() {
        view.removeMouseListener(mh);
        view = new UserView(level, resolutionX, resolutionY);
        view.setBounds(0, 0, resolutionX, resolutionY);
        view.setOpaque(false);
        mh = new MouseHandler(view, level, player);
        view.addMouseListener(mh);

        kh.setWorld(player.getWorld());
        kh.setPlayer(player);
        frame.requestFocus();
    }

    /**
     * set the background of the game to the background of the current level. If
     * the background image does not exist, the background will be set to a cyan
     * fill
     */
    private void initializeBackground() {
        layeredPane.remove(background);
        background = new JLabel();
        try {
            BufferedImage myPicture = ImageIO.read(level.getBackground());
            background = new JLabel(new ImageIcon(myPicture));
        } catch (IOException e) {
            //if the image is missing paint the background blue
            System.out.println("Background image missing\n" + e);
            background.setOpaque(true);
            background.setBackground(Color.CYAN);
        } finally {
            layeredPane.add(background, 2);
            background.setBounds(0, 0, resolutionX, resolutionY);
        }
    }

    /**
     * set the current level to a certain number
     *
     * @param levelNumber the integer representation of the level to be set
     */
    public void setLevel(LevelNumber levelNumber) {
        this.levelNumber = LevelNumber.getLevelNumber(levelNumber.getCode());
        changeLevel();
    }

    /**
     *
     */
    public void incrementLevel() {
        highScore.completedLevel();
        int levelCount = LevelNumber.lookup.size();
        if (levelNumber.getCode() < levelCount) {
            levelNumber = LevelNumber.getLevelNumber(levelNumber.getCode() + 1);
            changeLevel();
        } else {
            gameComplete();
        }
    }

    /**
     * shows the high scores to the player at the end of the game
     */
    private void gameComplete() {
        level.stop();
        JPanel scorePanel = new JPanel();
        layeredPane.add(scorePanel);
        scorePanel.setBounds(0, 0, resolutionX, resolutionY);
        highScore.finish(scorePanel);
    }

    /**
     * gets high score object
     *
     * @return the highScore object for the current game session
     */
    public HighScore getHighScore() {
        return highScore;
    }

    /**
     * gets the level that is being run
     *
     * @return the level that is being run
     */
    public LevelBuilder getLevel() {
        return level;
    }

    /**
     * assign a new view object to view
     *
     * @param view the view object to be assigned
     */
    public void setView(UserView view) {
        this.view = view;
    }

    /**
     * returns the view object that is being used
     *
     * @return the view object that is being used
     */
    public UserView getView() {
        return view;
    }

    /**
     * gets the player that is being used in the current level
     *
     * @return a player object that has been instanced for the current level
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * gets the keyboard handler object that is being assigned to the game
     * window
     *
     * @return a keyboard handler object
     */
    public KeyboardHandler getKeyboardHandler() {
        return kh;
    }

    /**
     * gets the layered pane that is associated with the current game window
     *
     * @return a JLayeredPane object
     */
    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }

    /**
     * when a change level event is sent, this will be called, and will change
     * the current level to the next one
     *
     * @param e a change level event
     */
    @Override
    public void changeLevel(EventObject e) {
        incrementLevel();
    }

    /**
     * occurs before a simulation event occurs
     *
     * @param e a step event
     */
    @Override
    public void preStep(StepEvent e) {
        highScore.timePenalty(frameRate);
//        System.out.println(highScore.getScore());
    }

    /**
     * occurs after a simulation event occurs. This will choose when to centre
     * the view on the player, and check if the player is out of bounds thus
     * killing the player. In addition, the JLayeredPane is revalidated and
     * redrawn
     *
     * @param e a step event
     */
    @Override
    public void postStep(StepEvent e) {
        //the xaxis of the view will always follow the player
        Vec2 newCentre = new Vec2(player.getPosition().x, view.getCentre().y);

        //the y axis will only centre when player is on a platform,
        //or when there is a 10 unit difference between the player and the view
        if (player.canJump() && player.getLinearVelocity().y == 0) {
            newCentre.y = player.getPosition().y;
            player.setDefaultImage();
        } else if (Math.abs(view.getCentre().y - player.getPosition().y) > 10) {
            newCentre.y = player.getPosition().y;
        }
        if (!player.canJump() && player.getLinearVelocity().y < -0.5f) {
            player.jumpDown();
        }

        if (player.getLinearVelocity().y > 10) {
            player.setLinearVelocity(new Vec2(player.getLinearVelocity().x, 9));
        }

        //if player falls off a platform
        if (player.getPosition().y < -30f) {
            System.out.println("ending game");
            level.endGame();
        }
//        System.out.println(player.getPosition());
        view.setCentre(newCentre);

        player.drawPlayerShots();
        player.drawPlayerHealth();
        player.drawPlayerScore();
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    @Override
    public void endGame(EventObject e) {
        System.out.println("game over");
        changeLevel();

    }

}
