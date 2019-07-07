package front_cover_tbd;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GuiButton {

	private State currentState = State.RELEASED;
	private Rectangle clickBox;
	private ArrayList<ActionListener> actionListeners;
	private String text = "Hiiii";
	private Color released;
	private Color hover;
	private Color pressed;
	private Font font = new Font("Helvetica", Font.BOLD, 17);

	public GuiButton(int x, int y, int width, int height) {
		clickBox = new Rectangle(x, y, width, height);
		actionListeners = new ArrayList<ActionListener>();
		released = new Color(173, 177, 179);
		hover = new Color(150, 156, 158);
		pressed = new Color(111, 116, 117);
	}

	private enum State {

		RELEASED, HOVER, PRESSED
	}

	public void update() {

	}

	public void render(Graphics2D g) {
		if (currentState == State.RELEASED) {
			g.setColor(released);
			g.fill(clickBox);
		} else if (currentState == State.HOVER) {
			g.setColor(hover);
			g.fill(clickBox);
		} else {
			g.setColor(pressed);
			g.fill(clickBox);
		}

		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(text, clickBox.x + clickBox.width / 2 - 50, clickBox.y + clickBox.height / 2 + 50);
	}

	public void addActionListener(ActionListener listener) {
		actionListeners.add(listener);
	}

	public void mousePressed(MouseEvent e) {
		if (clickBox.contains(e.getPoint()))
			currentState = State.PRESSED;
	}

	public void mouseReleased(MouseEvent e) {
		if (clickBox.contains(e.getPoint())) {
			for (ActionListener al : actionListeners) {
				al.actionPerformed(null);
			}
		}
	}

	public void mouseDraged(MouseEvent e) {
		if (clickBox.contains(e.getPoint()))
			currentState = State.PRESSED;
		else
			currentState = State.RELEASED;
	}

	public void mouseMoved(MouseEvent e) {
		if (clickBox.contains(e.getPoint()))
			currentState = State.HOVER;
		else
			currentState = State.RELEASED;
	}

	public int getX() {
		return clickBox.x;
	}

	public int getY() {
		return clickBox.y;
	}

	public int getWidth() {
		return clickBox.width;
	}

	public int getHeight() {
		return clickBox.height;
	}
}