/*
 * This is a subtype of body, and is designed to rotate in a given direction,
 * it will take two shots to destroy this.
 */
package shapes;

import city.cs.engine.BodyImage;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.DynamicBody;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import city.cs.engine.SolidFixture;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import city.cs.engine.World;

/**
 * this object is an enemy, that is moving at an angular velocity and can be
 * shot by the player
 */
public class SawBlade extends DynamicBody implements StepListener, CollisionListener {

    /**
     * the image to be used for the body of the saw blade
     */
    private static final BodyImage image = new BodyImage("data/sawBlade.png", 4);
    /**
     * the world object the saw blade is in
     */
    private final World world;
    /**
     * the speed the saw blade is rotating at
     */
    private float angVelocity;
    /**
     * value of pi
     */
    private static final double PI = Math.PI;
    /**
     * health value of the saw blade
     */
    private byte health;

    /**
     * construct a SawBlade object and start it moving
     * @param world the world object the saw blade is in
     */
    public SawBlade(World world) {
        super(world);
        this.world = world;
        angVelocity = (float) (2 * PI);
        health = 2;

        drawSawBlade();
    }

    /**
     * create the fixtures, and set the properties of the saw blade
     */
    private void drawSawBlade() {
        Shape bodyShape = new PolygonShape(-1.0f, 1.08f, 0.02f, 1.47f, 1.06f, 1.06f, 1.47f, 0.17f, 1.16f, -0.93f, 0.06f, -1.47f, -1.12f, -1.0f, -1.47f, 0.11f);
        SolidFixture bodyFixture = new SolidFixture(this, bodyShape);
        bodyFixture.setFriction(0.8f);
        bodyFixture.setRestitution(0.7f);
        bodyFixture.setDensity(40);
        addImage(image);
        setName("standardEnemy");

        addCollisionListener(this);
        world.addStepListener(this);
    }

    /**
     * get the angular velocity of the saw blade
     * @return the angular velocity of the saw blade
     */
    public float getAngVelocity() {
        return angVelocity;
    }

    /**
     * set the angular velocity of the saw blade
     * @param angVelocity to assign to the saw blade
     */
    public void setAngVelocity(float angVelocity) {
        this.angVelocity = angVelocity;
    }

    @Override
    public void preStep(StepEvent e) {
        //do nothing for now
    }

    /**
     * on every step event, the saw blades velocity is reset, and if it is below
     * the kill floor, it will be destroyed
     * @param e a step event
     */
    @Override
    public void postStep(StepEvent e) {
        setAngularVelocity(angVelocity);
        if (this.getPosition().y < -30f) {
            destroy();
            world.removeStepListener(this);
            removeCollisionListener(this);
        }
    }

    /**
     * on every collision event, checks if the saw blade has collided with 
     * something else.
     * @param e a collision event
     */
    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Projectile) {
            e.getOtherBody().destroy();
            if (health > 1) {
                health--;
            } else if (health <= 1) {
                destroy();
                world.removeStepListener(this);
                removeCollisionListener(this);
            }
        }
    }

}
