package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * this class will act a a handler for the splash screen, it will show the user
 * some options, and the user can interact with them
 */
public class PreGameScreen implements Runnable {

	/**
	 * enum set of all the possible states the screen could be in
	 */
	private enum ScreenState {
		HOME, SETTINGS, HOW_TO_PLAY;
	}

	private static final ImageIcon MOUSE = new ImageIcon("data/mouse.png");

	private static final ImageIcon WASD = new ImageIcon("data/wasd.png");

	private final JFrame frame;
	private final JPanel panel;
	private final LayoutManager layout;
	private final GridBagConstraints gbc;
	private final JButton play, howToPlay, settings, back;
	private ScreenState screenState;
	private JFormattedTextField resX, resY;
	private boolean end;

	/**
	 * initializes all the buttons and text field, then sets the screen state to the
	 * home screen
	 *
	 * @param frame       the JFrame object to display the graphics
	 * @param resolutionX horizontal resolution of the window
	 * @param resolutionY vertical resolution of the window
	 */
	public PreGameScreen(JFrame frame, int resolutionX, int resolutionY) {
		this.frame = frame;
		layout = new GridBagLayout();
		gbc = new GridBagConstraints();
		play = new JButton("Play");
		howToPlay = new JButton("How To Play");
		settings = new JButton("Settings");
		back = new JButton("Back");
		panel = new JPanel(layout);
		resX = new JFormattedTextField();
		resX.setColumns(5);
		resX.setValue(resolutionX);
		resY = new JFormattedTextField();
		resY.setColumns(5);
		resY.setValue(resolutionY);
		screenState = ScreenState.HOME;
	}

	/**
	 * allows the grid bag constraints to be easily changed with few lines of code
	 *
	 * @param width  the grid width to set the grid bag constraints
	 * @param height the grid heigh to set the grid bag constraints
	 * @param x      the x position
	 * @param y      the y position
	 * @param padX   the amount of pixels to pad the horizontal
	 * @param padY   the amount of pixels to pad the vertical
	 * @param insets an Insets object, used to pad the external of a given component
	 */
	private void setGBC(int width, int height, int x, int y, int padX, int padY, Insets insets) {
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.ipadx = padX;
		gbc.ipady = padY;
		gbc.insets = insets;
	}

	/**
	 * a simple switch statement, used to navigate into sub menus.
	 *
	 * @param screenState the state to set the screen to
	 */
	private void setScreenState(ScreenState screenState) {
		this.screenState = screenState;
		panel.removeAll();

		switch (screenState) {
		case HOME:
			setGBC(3, 2, 0, 0, 100, 50, new Insets(0, 0, 50, 0));
			panel.add(play, gbc);

			setGBC(1, 1, gbc.gridx, 2, 50, 25, new Insets(0, 0, 0, 0));
			panel.add(howToPlay, gbc);

			gbc.gridx = 1;
			panel.add(settings, gbc);

//                gbc.gridx = 0;
//                gbc.gridy = 3;
//                gbc.insets = new Insets(50,0,0,0);
//                panel.add(back,gbc);
			break;

		case SETTINGS:

			setGBC(7, 1, 1, 1, 50, 25, new Insets(0, 0, 0, 0));
			JLabel text = new JLabel("Type a resolution in the text boxes, " + "and click apply resolution to change"
					+ " the resolution of the game.");
			panel.add(text, gbc);

			JButton setResolution = new JButton("Apply Resolution");
			setResolution.addActionListener((ActionEvent ae) -> {
				int tempX = (int) resX.getValue();
				int tempY = (int) resY.getValue();
				if (tempX > 1920 || tempY > 1080 || tempX < 800 || tempY < 600) {
					panel.removeAll();
					JOptionPane.showMessageDialog(panel,
							String.valueOf(tempX) + " x " + String.valueOf(tempY) + " is not a supported resolution");
				} else {
					JOptionPane.showMessageDialog(panel,
							"Warning, a non 4:3" + " resolution may result in visual errors");
					frame.setSize(tempX, tempY);
				}
			});
			setGBC(1, 1, 2, 3, 10, 10, new Insets(0, 100, 0, 0));
			panel.add(setResolution, gbc);

			gbc.gridx++;
			gbc.insets = new Insets(0, 30, 0, 0);
			panel.add(resX, gbc);

			gbc.gridx++;
			gbc.insets = new Insets(0, 0, 0, 0);
			panel.add(resY, gbc);

			setGBC(2, 2, 3, 4, 25, 25, new Insets(50, 0, 0, 0));
			panel.add(back, gbc);
			break;

		case HOW_TO_PLAY:
			float scaleX = frame.getWidth() / 4;
			float scaleY = frame.getHeight() / 4;
			JLabel mouseIcon = new JLabel(new ImageIcon(MOUSE.getImage().getScaledInstance(
					(int) (MOUSE.getIconWidth() * ((float) scaleX / MOUSE.getIconWidth() / 2)),
					(int) (MOUSE.getIconHeight() * ((float) scaleY / MOUSE.getIconHeight() / 2)),
					Image.SCALE_DEFAULT)));
			JLabel keyboardIcon = new JLabel(new ImageIcon(WASD.getImage().getScaledInstance(
					(int) (WASD.getIconWidth() * ((float) scaleX / WASD.getIconWidth() / 2)),
					(int) (WASD.getIconHeight() * ((float) scaleY / WASD.getIconHeight() / 2)), Image.SCALE_DEFAULT)));

			JLabel hint1 = new JLabel("The objective of the game is to get"
					+ " to the end as quick as possible, while trying not" + " to die.");

			JTextArea hint2 = new JTextArea("You can move the player with WASD.\n\n"
					+ "W - jump, space will also jump\n" + "A - move left.\n" + "D - move right.");
			hint2.setEditable(false);
			hint2.setLineWrap(true);
			hint2.setWrapStyleWord(true);
			hint2.setPreferredSize(new Dimension(200, 100));
			hint2.setBackground(Color.LIGHT_GRAY);
			hint2.setOpaque(false);

			JTextArea hint3 = new JTextArea(
					"As long as you have shots, you can " + "shoot a projectile by clicking on the screen with "
							+ "the mouse." + "  The projectile will shoot from the player towards the " + "mouse.");
			hint3.setEditable(false);
			hint3.setLineWrap(true);
			hint3.setWrapStyleWord(true);
			hint3.setPreferredSize(new Dimension(200, 100));
			hint3.setBackground(Color.LIGHT_GRAY);
			hint3.setOpaque(false);

			setGBC(7, 1, 0, 0, 0, 20, new Insets(0, 0, 0, 0));
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 7;
			panel.add(hint1, gbc);

			gbc.gridy = 1;
			gbc.gridwidth = 1;
			panel.add(keyboardIcon, gbc);

			gbc.gridx = 1;
			panel.add(hint2, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.insets = new Insets(20, 0, 0, 0);
			panel.add(hint3, gbc);

			gbc.gridx = 1;
			panel.add(mouseIcon, gbc);

			setGBC(2, 2, 0, 3, 25, 25, new Insets(50, 0, 0, 0));
			panel.add(back, gbc);
			break;

		default:
			break;
		}
		panel.revalidate();
		panel.repaint();

	}

	/**
	 * When the thread starts, all the buttons will get their action listeners
	 * assigned, and the user will be displayed with the home screen
	 */
	@Override
	public void run() {
//        panel.add(settings);     
		setScreenState(screenState);

		frame.setBackground(Color.blue);
		frame.add(panel);
		frame.setVisible(true);

		play.addActionListener((ActionEvent ae) -> {
			end = true;
			play.removeActionListener(play.getActionListeners()[0]);
		});

		howToPlay.addActionListener((ActionEvent ae) -> {
			setScreenState(ScreenState.HOW_TO_PLAY);
		});

		settings.addActionListener((ActionEvent ae) -> {
			System.out.println("start");
			setScreenState(ScreenState.SETTINGS);
			System.out.println("end");
		});

		back.addActionListener((ActionEvent ae) -> {
			setScreenState(ScreenState.HOME);
		});

		while (!end) {
			Thread.currentThread().interrupt();
		}

		System.out.println("done");
		frame.remove(panel);

	}

}