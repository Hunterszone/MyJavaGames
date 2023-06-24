package game_engine;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Objects;

import javax.swing.ImageIcon;

public abstract class SpritePattern implements Serializable {

	private static final long serialVersionUID = 1L;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean vis;
	transient protected Image image;

	public SpritePattern(int x, int y) {
		this.x = x;
		this.y = y;
		vis = true;
	}

	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public boolean isVisible() {
		return vis;
	}

	public void setVisible(boolean visible) {
		vis = visible;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	protected void getImageDimensions() {
		width = image.getWidth(null);
		height = image.getHeight(null);
	}

	protected Image loadImage(String imageName) {
		final ImageIcon ii = new ImageIcon(Objects.requireNonNull(imageName));
		image = ii.getImage();
		return image;
	}

}