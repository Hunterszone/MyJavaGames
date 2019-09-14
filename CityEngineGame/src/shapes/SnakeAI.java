package shapes;

import java.util.logging.Level;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;

public class SnakeAI implements StepListener{
    
    private Player player;
    private Level level;
    private Snake snake;
    private float moveSpeed;
    private float jumpHeight;
    
    public SnakeAI(Player player, Level level, Snake snake){
        this.player=player;        
        this.level=level;
        this.snake=snake;
        moveSpeed=5;
        jumpHeight=4;
                   
    }            
    
    public void setMoveSpeed(float moveSpeed){
        this.moveSpeed=moveSpeed;
    }
    public float getMoveSpeed(){
        return moveSpeed;
    }
    public void setJumpHeight(float jumpHeight){
        this.jumpHeight=jumpHeight;
    }
    public float getJumpHeight(){
        return jumpHeight;
    }
    
    
    //implementation of abstract methods
    @Override
    public void preStep(StepEvent e) {
        
    }

    @Override
    public void postStep(StepEvent e) {
        
    }
    
}
