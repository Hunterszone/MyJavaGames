package shapes;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.DynamicBody;
import city.cs.engine.PolygonShape;
import city.cs.engine.SolidFixture;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import city.cs.engine.World;

/**
 * The barrel is a obstacle to the player, with no ai, and can cause half a
 * heart of damage. The object is then destroyed when it touches the player
 */
public class SpikedBarrel extends DynamicBody implements StepListener {

    /**
     * the image to be used for the spiked barrel
     */
    private static final BodyImage image = new BodyImage("data/spikedBarrel.png", 3);
    /**
     * the world object that the barrel exists in
     */
    private final World world;

    /**
     * construct the barrel, and build the fixtures
     * @param world the world object that the barrel exists in
     */
    public SpikedBarrel(World world) {
        super(world);
        this.world = world;
        buildBarrel();

    }

    /**
     * builds all of the fixtures of the barrel, and sets the image
     */
    private void buildBarrel() {
        ArrayList<PolygonShape> shapes = new ArrayList<>();
        shapes.add(new PolygonShape(-0.04f, 0.72f, 0.08f, 1.13f, 0.19f, 0.73f));
        shapes.add(new PolygonShape(0.55f, 0.56f, 0.98f, 0.82f, 0.73f, 0.38f));
        shapes.add(new PolygonShape(0.82f, 0.1f, 1.25f, -0.02f, 0.83f, -0.14f));
        shapes.add(new PolygonShape(0.7f, -0.45f, 1.01f, -0.92f, 0.52f, -0.63f));
        shapes.add(new PolygonShape(0.28f, -0.75f, 0.15f, -1.27f, 0.01f, -0.74f));
        shapes.add(new PolygonShape(-0.29f, -0.64f, -0.77f, -0.93f, -0.51f, -0.45f));
        shapes.add(new PolygonShape(-0.63f, -0.11f, -1.15f, -0.02f, -0.62f, 0.13f));
        shapes.add(new PolygonShape(-0.51f, 0.39f, -0.75f, 0.85f, -0.29f, 0.59f));
        shapes.add(new PolygonShape(-0.63f, 0.11f, -0.33f, 0.58f, 0.19f, 0.73f, 0.71f, 0.41f, 0.85f, -0.13f, 0.56f, -0.62f, 0.03f, -0.76f, -0.5f, -0.47f));
        shapes.forEach(s -> new SolidFixture(this, s));
        this.setPosition(new Vec2(0, 0));
        this.setName("standardEnemy");
        addImage(image);
        world.addStepListener(this);

    }

    @Override
    public void preStep(StepEvent e) {
        //
    }

    /**
     * on every simulation step, this checks if the barrel is below the kill 
     * floor.  If it is it is destroyed.
     * @param e a step event
     */
    @Override
    public void postStep(StepEvent e) {
        if (getPosition().y < -30) {
            world.removeStepListener(this);
            destroy();
        }

    }

}
