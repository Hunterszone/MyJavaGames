package sokoban;

import java.awt.Image;
import java.awt.Toolkit;

public class Wall extends Actor {

    private Image image;

    public Wall(int x, int y) {
        super(x, y);

        image = Toolkit.getDefaultToolkit().createImage("images/wall.png");
        this.setImage(image);
        return;
    }
}
