/*
 * this class is a subtype of Level, so it is also a subtype of world.  All the
 * bodies unique to the level are set here.
 *
 */
package levels;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.Shape;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import city.cs.engine.World;
import levelsEngine.LevelBuilder;
import levelsEngine.LevelBuilder.Backgrounds;
import levelsEngine.LevelBuilder.FrictionCoefficient;
import levelsEngine.LevelBuilder.Textures;
import shapes.Player;
import shapes.SawBlade;
import shapes.Snake;

public final class Level2 extends LevelBuilder implements StepListener, CollisionListener {

    private final Vec2 start;
    private static final BodyImage W3
            = new BodyImage(getTextureLocation(LevelBuilder.Textures.WOOD_01), 15);
    private int numSteps;
    private final Vec2 sawBladePos;
    private Player player;

    public Level2() {
        super();
        start = new Vec2(0, -11.5f);
        numSteps = 0;
        sawBladePos = new Vec2();
        initializeLevel();
    }

    @Override
    protected void initializeLevel() {
        float pi = (float) Math.PI;

        Shape pf0Shape = new BoxShape(2.5f, 5.5f);
        setBody(true, "0Shape", pf0Shape, start, 0);

        start.x += 4.5f;
        start.y -= 5f;
        Shape shape = new BoxShape(3, 0.5f);
        setBody(true, "start", shape, start, 0);

        start.x += 20f;
        start.y += 5f;
        Shape pf1 = new BoxShape(8f, 0.5f);
        setBody(true, "platform1", pf1, start, pi / 6);

        start.x += 13f;
        start.y += 7.5f;
        setBody(true, "platform2", pf1, start, pi / 6);
        sawBladePos.x = start.x + 5;
        sawBladePos.y = start.y + 3;

        start.x += 13.5f;
        start.y += 3.15f;
        setBody(true, "platform3", pf1, start, 0);
        new Snake((World) this).putOn(getBody("platform3"));

        start.x += 15f;
        setBody(true, "platform4", shape, start, 0);
        getBody("platform4").setName("end");
        setBackground(LevelBuilder.Backgrounds.FOREST_BACKGROUND_01);

        changeFriction(getFrictionCoefficient(LevelBuilder.FrictionCoefficient.WOOD));
        getBodies().forEach((k, v) -> {
            v.setClipped(true);
            v.addImage(W3);
        });

    }

    public void spawnSawBlades(Player player) {
        this.player = player;
        addStepListener(this);
    }

    public Vec2 getSawBladePos() {
        return sawBladePos;
    }

    @Override
    public void preStep(StepEvent e) {
        if (!(player.getPosition().sub(sawBladePos).length() < 5)) {
            numSteps++;
            if (numSteps >= 90) {
                numSteps = 0;
                SawBlade sb = new SawBlade((World) this);
                sb.setPosition(sawBladePos);
            }
        } else {
            removeStepListener(this);
        }
    }

    @Override
    public void postStep(StepEvent e) {

    }

    @Override
    public void collide(CollisionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
