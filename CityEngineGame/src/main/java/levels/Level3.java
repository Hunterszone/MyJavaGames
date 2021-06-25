/*
 * this class is a subtype of Level, so it is also a subtype of world.  All the
 * bodies unique to the level are set here.
 *
 */
package levels;

import java.util.Random;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import city.cs.engine.World;
import levelsEngine.LevelBuilder;
import levelsEngine.LevelBuilder.Backgrounds;
import levelsEngine.LevelBuilder.FrictionCoefficient;
import levelsEngine.LevelBuilder.Textures;
import shapes.SpikedBarrel;

public final class Level3 extends LevelBuilder implements StepListener {

    private final Vec2 start;
    private static final BodyImage W3
            = new BodyImage(getTextureLocation(Textures.WOOD_03), 15);
    private float barrelSpawnHeight;
    private float barrelSpawnXLow, barrelSpawnXHigh;
    private int stepCount;
    private final Random rFloat;

    public Level3() {
        super();
        stepCount = 0;
        start = new Vec2(0, -11.5f);
        initializeLevel();

        //give random function a seed for debugging purposes
        rFloat = new Random(50);
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

        start.x += 10f;
        start.y += 1f;
        Shape pf1 = new BoxShape(3, 0.5f);
        setBody(true, "platform1", pf1, start, 0);

        start.x += 20f;
        Shape pf2 = new BoxShape(7, 0.5f);
        setBody(true, "platform2", pf2, start, 0);

        start.x += 11f;
        start.y += 7f;
        Shape pf3 = new BoxShape(7.25f, 0.5f);
        setBody(true, "platform3", pf3, start, pi / 2);

        start.x -= 11f;
        start.y -= 1f;
        Shape pf4 = new BoxShape(3f, 0.5f);
        setBody(true, "platform4", pf4, start, 0);

        start.x += 9f;
        start.y += 6.15f;
        setBody(true, "platform5", pf4, start, pi / 4);

        start.x += 11f;
        start.y += 3f;
        Shape pf6 = new BoxShape(4f, 0.5f);
        setBody(true, "platform6", pf6, start, 0);

        start.y -= 17f;
        setBody(true, "platform7", pf6, start, 0);
        barrelSpawnXLow = start.x + 4.25f;
//        getBody("platform7").setName("end");

        start.x += 4f;
        start.y += 21f;
        setBody(true, "platform8", pf6, start, pi / 2);

        start.y -= 23.25f;
        start.x += 3.25f;
        setBody(true, "platform9", pf6, start, -pi / 6);

        start.x += 14f;
        setBody(true, "platform10", pf6, start, pi / 6);

        start.y += 2.25;
        start.x += 7.25f;
        setBody(true, "platform11", pf6, start, 0);
        barrelSpawnHeight = start.y + 17;
        barrelSpawnXHigh = start.x - 4.25f;

        start.x += 15f;
        setBody(true, "platform12", pf6, start, 0);
        getBody("platform12").setName("end");

        setBackground(Backgrounds.FOREST_BACKGROUND_03);

        changeFriction(getFrictionCoefficient(FrictionCoefficient.WOOD));
        getBodies().forEach((k, v) -> {
            v.setClipped(true);
            v.addImage(W3);
        });

    }

    public void spawnBarrels() {
        addStepListener(this);
    }

    @Override
    public void preStep(StepEvent e) {
        //do nothing    
    }

    @Override
    public void postStep(StepEvent e) {
        stepCount++;
        if (stepCount % 45 == 0) {
            stepCount = 0;
            // x is position i have, a and b is 0.0 and 1.0
            // d and c are limits for barrel spawnPosition
            // ((x-a)/(b-a) * (d-c))+ c
            float mappedPosition = (rFloat.nextFloat() * (barrelSpawnXHigh - barrelSpawnXLow)) + barrelSpawnXLow;
            Vec2 spawnPosition = new Vec2(mappedPosition, barrelSpawnHeight);

            new SpikedBarrel((World) this).setPosition(spawnPosition);
            //spawn barrel
        }
    }

}
