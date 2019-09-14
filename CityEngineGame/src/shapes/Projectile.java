package shapes;

import org.jbox2d.common.Vec2;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.DynamicBody;
import city.cs.engine.Shape;
import city.cs.engine.World;

/**
 * this class is the projectile object.  It is designed to be shot from a body
 */
public class Projectile extends DynamicBody implements CollisionListener {
    
    /**
     * the body the projectile is shot from
     */
    private DynamicBody shootingBody; //the body the projectile is shot from

    /**
     * constructs the projectile, and shoots it in a direction 
     * @param w the world the projectile is in
     * @param shape the shape object describing the shape of the projectile
     * @param force the force the projectile is shot at
     * @param start the position the projectile is shot from
     * @param angle the angle at which the projectile is shot, in order to 
     * correctly align the image
     */
    public Projectile(World w, Shape shape, Vec2 force, Vec2 start, float angle) {
        super(w, shape);
        this.setPosition(start);
        this.setAngle(angle);   //angle to orient bullet in order to know how to orient the projectile
        this.applyForce(force); //speed/direction of projectile
        this.setName("projectileGeneric");
    }

    /**
     * assigns the body that shot the projectile
     * @param shootingBody 
     */
    public void setShootingBody(DynamicBody shootingBody) {
        this.shootingBody = shootingBody;
        this.addCollisionListener(this);
    }

//collision listener for this body
    /**
     * handles all the actions when there is a collision.  
     * @param e a collision event that is raised when there is a collision 
     * between this and another body
     */
    @Override
    public void collide(CollisionEvent e) {
        String otherName = e.getOtherBody().getName();
//        System.out.println(otherName);

// destroy this object if it hits an object other than its shooting body
        if (!(e.getOtherBody() instanceof Snake) && !(e.getOtherBody() instanceof SawBlade)) {

            if (otherName == null) {
                this.destroy();
            } else if (otherName.equals("destructable")) {
                e.getOtherBody().destroy();
                this.destroy();
//            System.out.println("true");
            }
        } else if (e.getOtherBody() instanceof Player
                || e.getOtherBody() instanceof Projectile) {

            this.destroy();
        } else {
            Player tempPlayer = (Player) shootingBody;
            if (e.getOtherBody() instanceof Snake) {
                tempPlayer.shotSawBlade();
            } else if (e.getOtherBody() instanceof SawBlade) {
                tempPlayer.shotSnake();
            }
        }
    }

}
