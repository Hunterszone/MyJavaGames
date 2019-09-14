/*
 * this class is a subtype of Level, so it is also a subtype of world.  All the
 * bodies unique to the level are set here.
 *
 */
package levels;

import org.jbox2d.common.Vec2;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.World;
import levelsEngine.LevelBuilder;
import levelsEngine.LevelBuilder.Backgrounds;
import levelsEngine.LevelBuilder.FrictionCoefficient;
import levelsEngine.LevelBuilder.Textures;
import shapes.SpikedBarrel;
import shapes.Snake;


public final class Level1 extends LevelBuilder {

    private final Vec2 start;
    private static final BodyImage W3
            = new BodyImage(getTextureLocation(Textures.CONCRETE_03), 15);

    public Level1() {
        super();
        start = new Vec2(0, -11.5f);
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

        start.x += 2;
        start.y += 0.5f;
        Shape pf1Shape = new BoxShape((float) Math.sqrt(2), 0.5f, new Vec2((float) Math.sqrt(2), -0.5f));
        setBody(true, "platform1", pf1Shape, start, pi / 4);

        start.x += 5;
        start.y += 1.5f;
        Shape pf2Shape = new BoxShape(3, 0.5f);
        setBody(true, "platform2", shape, start, 0);

        start.x += 3.5f;
        Shape pf3Shape = new BoxShape((float) Math.sqrt(2) / 2, 0.5f, new Vec2((float) Math.sqrt(2) / 2, 0.25f));
        setBody(true, "platform3", pf3Shape, start, pi / 4);

        start.x += 3.5f;
        start.y += 1;
        setBody(true, "platform4", pf2Shape, start, 0);

        start.x += 3;
        start.y -= 0.1f;
        Shape pf5Shape = new BoxShape((float) Math.sqrt(15.25f) / 2, 0.5f, new Vec2((float) Math.sqrt(15.25f) / 2, 0.25f));
        setBody(true, "platform5", pf5Shape, start, pi/5);

        start.x += 3.5f;
        start.y += 2.4f;
        Shape pf6Shape = new BoxShape(0.75f, 3, new Vec2(0, -2.5f));
        setBody(true, "platform6", pf6Shape, start, 0);

        start.x += 6.5f;
        start.y += 2.75f;
        setBody(true, "platform7", pf6Shape, start, 0);

        start.x += 0.5f;
        start.y -= 0.25f;
        Shape pf8Shape = new BoxShape((float) Math.sqrt(85) / 2, 0.5f, new Vec2((float) Math.sqrt(85) / 2, 0.5f));
        setBody(true, "platform8", pf8Shape, start, pi/5);
        
        start.y +=2;
        Shape plankShape = new BoxShape (0.5f,1.5f);
        setBody(true, "plank1", plankShape, start, pi/5);
        getBody("plank1").setName("destructable");
        
        for (int i = 0; i < 3; i++) {
            new SpikedBarrel((World)this).setPosition(new Vec2(start.x+i+2, start.y+i+5));
        }

        start.x += 12.5f;
        start.y += 3.5f;
        Shape pf9Shape = new BoxShape(5.5f, 0.5f);
        setBody(true, "platform9", pf9Shape, start, 0);
        new Snake((World)this).putOn(getBody("platform9"));
        getBody("platform9").setName("end");
        
        
        setBackground(Backgrounds.FOREST_BACKGROUND_02);
        
        changeFriction(getFrictionCoefficient(FrictionCoefficient.CONCRETE));
        getBodies().forEach((k, v) -> {
            v.setClipped(true);
            v.addImage(W3);
        });
    }
    
   

}
