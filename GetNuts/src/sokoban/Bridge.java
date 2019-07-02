package sokoban;

import java.awt.Image;
import java.awt.Toolkit;

public class Bridge extends Actor {

    private Image image;

    public Bridge(int x, int y) {
        super(x, y);

        image = Toolkit.getDefaultToolkit().createImage("images/bridge.png");
        this.setImage(image);
        return;
    }
}
