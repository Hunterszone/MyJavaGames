package sokoban;

import java.awt.Image;
import java.awt.Toolkit;

public class Water extends Actor {

    private Image image;

    public Water(int x, int y) {
        super(x, y);

        image = Toolkit.getDefaultToolkit().createImage("images/terrain.png");
        this.setImage(image);
        return;
    }
}
