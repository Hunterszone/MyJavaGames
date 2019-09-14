/*
 *  this class is designed to handle key inputs given while focus is on the 
 *  jframe.  the class also stores bound keys in a tree map, and allows for 
 *  keys to be rebound.
 */
package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jbox2d.common.Vec2;

import city.cs.engine.World;
import levelsEngine.LevelsInitializer;
import shapes.Player;

/**
 * used to handle the players key inputs
 */
public class KeyboardHandler implements KeyListener, ActionListener {

	private static LevelsInitializer levels;
	private final Runnable r1;
	private int key;
	private final LayoutManager layout;
	private final GridBagConstraints gBC;
	private JLabel pauseText;
	private JLabel emptyRow;
	private final JPanel pauseBackground;
	private JButton rebindKey, play, exit, level1, level2, level3, level4;
	private World world;
	private Player player;
	private final JLayeredPane layeredPane;
	// player bound keys
	private final Map<String, Integer> keyBinds;
	private boolean rebindingKey;
	private Map<String, JFormattedTextField> textFields;

	/**
	 * sets the key binds for the game, and builds the pause background
	 * 
	 * @param world       the current world that is being used
	 * @param player      the current player object
	 * @param layeredPane the JLayeredPane that handles all other panels
	 * @param levels      the levels object that handles levels
	 */
	public KeyboardHandler(World world, Player player, JLayeredPane layeredPane, LevelsInitializer levels) {
		this.world = world;
		this.player = player;
		this.layeredPane = layeredPane;
		KeyboardHandler.levels = levels;
		keyBinds = new TreeMap<>();

		// default key bindings
		keyBinds.put("left", KeyEvent.VK_A);
		keyBinds.put("right", KeyEvent.VK_D);
		keyBinds.put("jump1", KeyEvent.VK_W);
		keyBinds.put("jump2", KeyEvent.VK_SPACE);
		keyBinds.put("pause", KeyEvent.VK_ESCAPE);

		// adding the pause overlay
		layout = new GridBagLayout();
		gBC = new GridBagConstraints();
		pauseBackground = new JPanel(layout);
		buildPauseBackground();
		layeredPane.add(pauseBackground, -1);

		// set the game to pause after the first 500 milleseconds of runtime.
		// this is to allow for the game to fully initialize before it starts.
		r1 = () -> {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					pause();
				}
			}, 250);
		};
	}

	public void initialize() {
		new Thread(r1).start();
		System.out.println("initialized");
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	/**
	 * this method handles what happens when the player clicks on the rebind key
	 * button. The player is sent to a screen that will show them all current key
	 * binds and will have the option to rebind any key.
	 */
	private void rebindKey() {
		rebindingKey = true;
		layeredPane.moveToBack(pauseBackground);
		pauseBackground.remove(exit);
		pauseBackground.remove(rebindKey);
		pauseBackground.remove(level1);
		pauseBackground.remove(level2);
		pauseBackground.remove(level3);
		pauseBackground.remove(level4);
		gBC.gridy = 2;
		gBC.gridwidth = 2;
		pauseBackground.add(new JLabel(" "), gBC);
		gBC.gridwidth = 1;
		int i = 2;
		for (Map.Entry<String, Integer> entry : keyBinds.entrySet()) {
			gBC.fill = GridBagConstraints.BOTH;
			gBC.gridx = 1;
			gBC.gridy = i + 1;
			pauseBackground.add(new JLabel(entry.getKey()), gBC);
			gBC.gridx = 2;
			JFormattedTextField textField = new JFormattedTextField();
			String keyValue;
			int value = entry.getValue();
			if (Character.isAlphabetic(entry.getValue())) {
				keyValue = Character.toString((char) value);
			} else {
				keyValue = Character.getName(entry.getValue());
			}

			textField.setValue(keyValue);
			textField.setColumns(5);
			textField.setEditable(false);

			textField.addMouseListener(new MouseListener() {
				private final String key = entry.getKey();
				private final JFormattedTextField tempTextField = textField;

				@Override
				public void mouseClicked(MouseEvent me) {
					JOptionPane.showInternalMessageDialog(pauseBackground, "Press ok then type any key");
					pauseBackground.requestFocus();
					pauseBackground.addKeyListener(new KeyAdapter() {
						@Override
						public void keyPressed(KeyEvent e) {
							System.out.println("ranked!");
							setKeyBind(key, e.getKeyCode());

							int tempValue = keyBinds.get(key);
							String newValue;
							if (Character.isAlphabetic((char) tempValue)) {
								newValue = Character.toString((char) tempValue);
							} else {
								newValue = Character.getName((char) tempValue);
							}
							tempTextField.setValue(newValue);
							for (KeyListener k : pauseBackground.getKeyListeners()) {
								pauseBackground.removeKeyListener(k);
							}
							pauseBackground.revalidate();
							pauseBackground.repaint();
						}
					});
					pauseBackground.revalidate();
					pauseBackground.repaint();

				}

				@Override
				public void mousePressed(MouseEvent me) {
				}

				@Override
				public void mouseReleased(MouseEvent me) {
				}

				@Override
				public void mouseEntered(MouseEvent me) {
				}

				@Override
				public void mouseExited(MouseEvent me) {
				}
			});

			gBC.fill = GridBagConstraints.VERTICAL;
			pauseBackground.add(textField, gBC);
			i++;
		}
		layeredPane.moveToFront(pauseBackground);
		pauseBackground.revalidate();
		pauseBackground.repaint();
	}

	/**
	 * this will add all the buttons and the listeners to the menu that shows up
	 * when the user presses the pause button.
	 */
	private void buildPauseBackground() {
		pauseBackground.removeAll();
		pauseBackground.setOpaque(true);
		pauseBackground.setBackground(new Color(135, 135, 135, 225));
		pauseBackground.setBounds(0, 0, 1024, 768);

		pauseText = new JLabel("The game is currently paused\n");
		pauseText.setFont(new Font(pauseText.getFont().getName(), Font.PLAIN, 20));
		emptyRow = new JLabel("\n");

		gBC.gridx = 1;
		gBC.gridy = 1;
		gBC.gridwidth = 0;
		pauseBackground.add(pauseText, gBC);

		gBC.gridy++;
		pauseBackground.add(emptyRow, gBC);

		play = new JButton("    Resume   ");
		play.addActionListener(this);
		gBC.gridy++;
		pauseBackground.add(play, gBC);

		rebindKey = new JButton("   Controls    ");
		rebindKey.addActionListener(this);
		gBC.gridy++;
		pauseBackground.add(rebindKey, gBC);

		exit = new JButton("  Exit game  ");
		exit.addActionListener(this);
		gBC.gridy++;
		pauseBackground.add(exit, gBC);

//        gBC.fill = GridBagConstraints.HORIZONTAL;

		gBC.gridwidth = 1;
		gBC.gridy = 1;
		gBC.gridx = 0;
		pauseBackground.add(emptyRow, gBC);

		gBC.gridy++;
		level1 = new JButton("LEVEL 1");
		level1.addActionListener(this);
		pauseBackground.add(level1, gBC);

		gBC.gridy++;
		level2 = new JButton("LEVEL 2");
		level2.addActionListener(this);
		pauseBackground.add(level2, gBC);

		gBC.gridy++;
		level3 = new JButton("LEVEL 3");
		level3.addActionListener(this);
		pauseBackground.add(level3, gBC);

		gBC.gridy++;
		level4 = new JButton("LEVEL 4");
		level4.addActionListener(this);
		pauseBackground.add(level4, gBC);
		pauseBackground.revalidate();
		pauseBackground.repaint();

	}

	private void exit() {
		System.exit(0);
	}

	private void pause() {
		world.stop();
		layeredPane.moveToFront(pauseBackground);
	}

	private void unPause() {
		world.start();
		// used to build set the background back to its initial state
		buildPauseBackground();
		layeredPane.moveToBack(pauseBackground);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		key = e.getKeyCode();
		if (key == KeyEvent.VK_Q) {
			System.out.println("q");
		}
		// global code to change pause state
		if (key == keyBinds.get("pause")) {

			if (world.isRunning()) {
				pause();
			} else {
				unPause();
			}
		}

		// player related code
		if (player.canJump()) { // only move when player is on platform
			if (key == keyBinds.get("left")) {
				player.moveLeft();
			} else if (key == keyBinds.get("right")) {
				player.moveRight();
			}
		}

		if (key == keyBinds.get("jump1") || key == keyBinds.get("jump2")) { // only jump when player is on a platform
			if (player.canJump()) {
				player.jump();
				player.setCanJump(false);
			}
		}

		// if the player is in the air and are moving along the x-axis
		// they can press move in the opposite direction to move slightly
		// in the other direction
		if (!player.canJump()) {
			if (player.getLinearVelocity().x >= 0 && key == keyBinds.get("left")) {
				player.setLinearVelocity(new Vec2(player.getMoveSpeed() / 3f, player.getLinearVelocity().y));
			} else if (player.getLinearVelocity().x <= 0 && key == keyBinds.get("right")) {
				player.setLinearVelocity(new Vec2(-player.getMoveSpeed() / 3f, player.getLinearVelocity().y));
			}
		}
	}

	public JPanel getPauseBackground() {
		return pauseBackground;
	}

	/**
	 *
	 * @param e an event generated when the users releases a key
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		key = e.getKeyCode();

		if (key == keyBinds.get("left") || key == keyBinds.get("right")) { // stop moving when player releases move key
			player.stopWalking();

			float velocityX = player.getLinearVelocity().x;
			float velocityY = player.getLinearVelocity().y;
			// if the player is on a platform and stops moving, make the player move
			// slowly in the same direction to simulate deceleration
			if (player.canJump()) {
				player.setLinearVelocity(new Vec2(velocityX / 3, velocityY));
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public int getKey() {
		return key;
	}

	/**
	 * allow player to be able to bind keys
	 *
	 * @param action    the action to set a bind to
	 * @param keyNumber the key to associate with an action
	 */
	public void setKeyBind(String action, int keyNumber) {

		if (keyBinds.containsKey(action)) {

			Optional<String> oldBinding = keyBinds.entrySet().stream().filter(e -> e.getValue() == keyNumber)
					.map(Map.Entry::getKey).findFirst();

			if (oldBinding.isPresent()) {
				// put code here to ask player if they are sure they want
				// to unbind the action bound to key
				String keyName = Character.getName(keyNumber);
				String confirmation = keyName + " is already bound to " + oldBinding
						+ ", please choose a new binding for " + action;

				JOptionPane.showInternalMessageDialog(pauseBackground, confirmation);
			}
			keyBinds.put(action, keyNumber);

		} else {
			System.out.println("action not present");
		}
	}

	/**
	 * handles what to do when the user clicks on a button.
	 *
	 * @param e an event generated when the user clicks a button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(play)) {
			if (rebindingKey) {
				rebindingKey = false;
			}
			unPause();
		} else if (e.getSource().equals(rebindKey)) {
			rebindKey();
		} else if (e.getSource().equals(exit)) {
			exit();
		} else if (e.getSource().equals(level1)) {
			levels.setLevel(LevelsInitializer.LevelNumber.LEVEL1);
		} else if (e.getSource().equals(level2)) {
			levels.setLevel(LevelsInitializer.LevelNumber.LEVEL2);
		} else if (e.getSource().equals(level3)) {
			levels.setLevel(LevelsInitializer.LevelNumber.LEVEL3);
		} else if (e.getSource().equals(level4)) {
			levels.setLevel(LevelsInitializer.LevelNumber.LEVEL4);
		}

	}

}
