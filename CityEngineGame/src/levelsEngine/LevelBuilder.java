package levelsEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jbox2d.common.Vec2;

import city.cs.engine.Body;
import city.cs.engine.DynamicBody;
import city.cs.engine.Shape;
import city.cs.engine.SolidFixture;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

/**
 * the level object is a subtype of world, and stores a collection of bodies
 * within the level. The class can also be used to alter the physical properties
 * of the bodies
 */
public abstract class LevelBuilder extends World {

    /**
     * map of all the bodies in the level
     */
    private final Map<String, Body> bodies;
    /**
     * map of all the shapes used to make bodies in the level
     */
    private final Map<String, Shape> shapes;
    /**
     * list of all the change level listeners
     */
    private static List<LevelListener> _listeners;
    /**
     * list of all the end game listeners
     */
    private static List<EndGameListener> _listenersEndGame;
    /**
     * used to store the coefficient of friction to be used for the level
     * platforms
     */
    private float mu;
    /**
     * current background the level is using
     */
    private Backgrounds background;

    /**
     * enum set of all the textures that can be applied to the ingame platforms
     */
    public enum Textures {
        CONCRETE_01, CONCRETE_02, CONCRETE_03, METAL_01,
        METAL_02, METAL_03, WOOD_01, WOOD_02, WOOD_03
    }

    /**
     * enum set of all the friction coefficients that can be used for the level
     */
    public enum FrictionCoefficient {
        WOOD, CONCRETE, METAL
    }

    /**
     * enum set of all the possible game backgrounds
     */
    public enum Backgrounds {
        FOREST_BACKGROUND_01, FOREST_BACKGROUND_02, FOREST_BACKGROUND_03,
        SKY_BACKGROUND_01;
    }

    /**
     * used to get the friction coefficient, to ensure that all the levels use
     * appropriate values
     *
     * @param frictionCoefficient object of type FrictionCoefficient
     * @return a float value of the friction
     */
    public static float getFrictionCoefficient(FrictionCoefficient frictionCoefficient) {
        switch (frictionCoefficient) {
            case WOOD:
                return 0.5f;
            case CONCRETE:
                return 0.8f;
            case METAL:
                return 0.7f;
            default:
                return 0.0f;
        }
    }

    /**
     * used to find the path to the texture
     *
     * @param textures an object of type Textures
     * @return the path to the requested texture
     */
    public static String getTextureLocation(Textures textures) {

        switch (textures) {
            case CONCRETE_01:
                return "data/concreteTexture01.jpg";
            case CONCRETE_02:
                return "data/concreteTexture02.jpg";
            case CONCRETE_03:
                return "data/concreteTexture03.jpg";
            case METAL_01:
                return "data/metalTexture01.jpg";
            case METAL_02:
                return "data/metalTexture02.jpg";
            case METAL_03:
                return "data/metalTexture03.jpg";
            case WOOD_01:
                return "data/woodTexture01.jpg";
            case WOOD_02:
                return "data/woodTexture02.jpeg";
            case WOOD_03:
                return "data/woodTexture03.jpeg";
            default:
                return null;
        }
    }

    /**
     * used to find the file that corresponds to each background. The background
     * is added to the view in the levels class
     *
     * @param background an object of type Backgrounds
     * @return the image that corresponds to the given background
     */
    public File chooseBackground(Backgrounds background) {
        switch (background) {
            case FOREST_BACKGROUND_01:
                return new File("data/bg1.jpg");
            case FOREST_BACKGROUND_02:
                return new File("data/bg2.gif");
            case FOREST_BACKGROUND_03:
                return new File("data/bg3.jpg");
            case SKY_BACKGROUND_01:
                return new File("data/skyBackground01.png");
            default:
                return null;
        }

    }

    /**
     * initialize bodies, shapes, change level listeners and end game listeners.
     */
    public LevelBuilder() {
        shapes = new HashMap<>();
        bodies = new HashMap<>();
        _listeners = new ArrayList<>();
        _listenersEndGame = new ArrayList<>();
        this.stop();
    }

    /**
     * unique to each implementation of this class
     */
    protected abstract void initializeLevel();

    /**
     * level is a subtype of world, so it can pass itself as a world object
     *
     * @return the current world object
     */
    public World getWorld() {
        return this;
    }

    /**
     *
     * @return the map of all bodies in the level
     */
    public Map<String, Body> getBodies() {
        return bodies;
    }

    /**
     * called from implementations of this object, and is used to place a new
     * body in the level, i.e. a platform
     *
     * @param isStatic is the body static or dynamic, respectively true or false
     * @param bodyName name to assign to the body
     * @param shape the shape of the body
     * @param position the vector where the body is to be placed
     * @param angle the angle at which to incline the body
     */
    protected void setBody(boolean isStatic, String bodyName, Shape shape, Vec2 position, float angle) {
        //initialize bodies in a level
        if (isStatic) {
            setStaticBodies(bodyName, shape);
        } else {
            setDynamicBodies(bodyName, shape);
        }
        getBody(bodyName).setPosition(position);
        getBody(bodyName).setAngle(angle);
    }

    /**
     * search for a body object in map bodies
     *
     * @param key the body to look for
     * @return a body object from the current level
     */
    public Body getBody(String key) {
        return bodies.get(key);
    }

    /**
     * search for a shape object in map shapes
     *
     * @param key the shape to look for
     * @return a shape object from the current level
     */
    public Shape getShape(String key) {
        return shapes.get(key);
    }

    /**
     * only called by this class, and is used to place static bodies, and their
     * shapes into the maps bodies and shapes.
     *
     * @param key the unique value assigned to the body
     * @param shape the shape to be placed in the map
     */
    private void setStaticBodies(String key, Shape shape) {
        shapes.put(key, shape);
        Body sb = new StaticBody(this, shape);
        bodies.put(key, sb);
    }

    /**
     * only called by this class, and is used to place dynamic bodies, and their
     * shapes into the maps bodies and shapes.
     *
     * @param key the unique value assigned to the body
     * @param shape the shape to be placed in the map
     */
    private void setDynamicBodies(String key, Shape shape) {
        shapes.put(key, shape);
        Body sb = new DynamicBody(this, shape);
        bodies.put(key, sb);
    }

    /**
     * allow implementations of the class to assign a background
     *
     * @param background the Backgrounds object to be set as the background
     */
    protected void setBackground(Backgrounds background) {
        this.background = background;
    }

    /**
     * finds the current background
     *
     * @return the background file for the current level
     */
    public File getBackground() {
        return chooseBackground(background);
    }

    /**
     * set the friction for all static bodies in the level
     *
     * @param mu the coefficient of friction
     */
    protected void changeFriction(float mu) {
        //change friction for all bodies
        bodies.forEach((k, v) -> {
            this.mu = mu;
            if (v.getAngle() != 0) {
                this.mu = 1;
            }
            new SolidFixture(v, getShape(k)).setFriction(this.mu);
        });
    }

    /**
     * add a new change level listener object to the list of listeners
     *
     * @param listener a new change level listener object
     */
    public synchronized void addChangeLevelListener(LevelListener listener) {
        _listeners.add(listener);
    }

    /**
     * remove a change level listener object from the list of listeners
     *
     * @param listener an existing change level listener object
     */
    public synchronized void removeChangeLevelListener(LevelListener listener) {
        _listeners.remove(listener);
    }

    /**
     * fires a change level event, which will be read by a change level
     * listener.
     */
    public void fireChangeLevelEvent() {
        //create new event to allow the main class to create a new step listener
        LevelEvent event = new LevelEvent(this);
        Iterator listeners = _listeners.iterator();
        while (listeners.hasNext()) {
            ((LevelListener) listeners.next()).changeLevel(event);
        }
    }

    /**
     * add a new end game listener object to the list of listeners
     *
     * @param listener a new end game listener object
     */
    public synchronized void addEndGameListener(EndGameListener listener) {
        _listenersEndGame.add(listener);
    }

    /**
     * remove an end game listener object from the list of listeners
     *
     * @param listener an existing end game listener object
     */
    public synchronized void removeEndGameListener(EndGameListener listener) {
        _listenersEndGame.remove(listener);
    }

    /**
     * fires an end game event, which will be read by an endgame listener
     */
    public synchronized void endGame() {
        EndGameEvent event = new EndGameEvent(this);
        Iterator listeners = _listenersEndGame.iterator();
        while (listeners.hasNext()) {
            ((EndGameListener) listeners.next()).endGame(event);
        }
    }

}
