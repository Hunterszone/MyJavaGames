package front_cover_tbd;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GuiPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ArrayList<GuiButton> buttons;

	public GuiPanel() {
		setFocusable(true);
		setPreferredSize(new Dimension(1310, 1040));
		buttons = new ArrayList<GuiButton>();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (GuiButton b : buttons) {
			b.render((Graphics2D) g);
			b.update();
		}
	}

	public void update() {
		for (GuiButton b : buttons)
			b.update();
	}

	public void render(Graphics2D g) {
		for (GuiButton b : buttons)
			b.render(g);
	}

	public void add(GuiButton b) {
		buttons.add(b);
	}

	public void remove(GuiButton b) {
		buttons.remove(b);
	}

	public void mousePressed(MouseEvent e) {
		for (GuiButton b : buttons)
			b.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		for (GuiButton b : buttons)
			b.mousePressed(e);
	}

	public void mouseDraged(MouseEvent e) {
		for (GuiButton b : buttons)
			b.mousePressed(e);
	}
}