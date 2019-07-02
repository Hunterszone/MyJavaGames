package sokoban;

import java.awt.Image;
import java.awt.Toolkit;

public class Player extends Actor {
	
	Image sokoimage;

    public Player(int x, int y) {
        super(x, y);

        sokoimage = Toolkit.getDefaultToolkit().createImage("images/sokoban.png");
        this.setImage(sokoimage);
    }

    public void move(int x, int y) {
        int nx = this.x() + x;
        int ny = this.y() + y;
        this.setX(nx);
        this.setY(ny);
    }
}
