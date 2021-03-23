package game_engine;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;

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

	protected void getImageDimensions() {

		width = image.getWidth(null);
		height = image.getHeight(null);
	}

	public Image loadImage(String imageName) {

		final ImageIcon ii = new ImageIcon(imageName);
		image = ii.getImage();
		return image;
	}

	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisible() {
		return vis;
	}

	public void setVisible(Boolean visible) {
		vis = visible;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

}