package sokoban;

import java.awt.Image;
import java.awt.Toolkit;

public class Area extends Actor {
	
	private Image image;

    public Area(int x, int y) {
        super(x, y);

        image = Toolkit.getDefaultToolkit().createImage("images/holder.png");
        this.setImage(image);
    }
}