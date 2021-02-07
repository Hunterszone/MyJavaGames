/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

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
import shapes.SawBlade;

public final class Level4 extends LevelBuilder implements StepListener {

    private static float pi =(float) Math.PI;
    private int numSteps;
    private final Vec2 start, sawBladePos;
    private static final BodyImage W3
            = new BodyImage(getTextureLocation(LevelBuilder.Textures.METAL_03), 15);

    public Level4() {
        super();
        start = new Vec2(0, -11.5f);
        sawBladePos = new Vec2();
        numSteps = 0;
        initializeLevel();
    }

    @Override
    protected void initializeLevel() {        

        Shape pf0Shape = new BoxShape(2.5f, 5.5f);
        setBody(true, "0Shape", pf0Shape, start, 0);

        start.x += 4.5f;
        start.y -= 5f;
        Shape shape = new BoxShape(3, 0.5f);
        setBody(true, "start", shape, start, 0);

        start.x += 19f;
        Shape pf1 = new BoxShape(7f, 0.5f);
        setBody(true, "platform1", pf1, start, 0);

        start.x += 14.5f;
        start.y -= 4f;
        setBody(true, "platform2", pf1, start, -pi / 6);
        sawBladePos.x = start.x ;
        sawBladePos.y = start.y;
        
        start.x += 10f;
        setBody(true, "platform3", shape, start, 0);
        getBody("platform3").setName("end");

        setBackground(LevelBuilder.Backgrounds.SKY_BACKGROUND_01);

        changeFriction(getFrictionCoefficient(LevelBuilder.FrictionCoefficient.METAL));
        getBodies().forEach((k, v) -> {
            v.setClipped(true);
            v.addImage(W3);
        });
        
        addStepListener(this);
    }

    @Override
    public void preStep(StepEvent e) {
        //do nothing
    }

    @Override
    public void postStep(StepEvent e) {
        numSteps++;
        if (numSteps % 90 == 0) {
            numSteps = 0;
            SawBlade sb = new SawBlade((World)this);
            sb.setPosition(sawBladePos);
//            sb.putOn(sawBladeXPos, getBody("platform2"));
            sb.setAngVelocity(8*pi);
        }
    }

}
