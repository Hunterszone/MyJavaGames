package game_engine;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import entities.Area;
import entities.Bridge;
import entities.Nut;
import entities.Player;
import entities.Wall;
import entities.Water;
import main.Main;
import sound_engine.LoadSounds;

public class Board extends JPanel {

	private static final long serialVersionUID = 1L;
	private final int OFFSET = 300;
	private final int SPACE = 20;
	private final double LEFT_COLLISION = 1;
	private final double RIGHT_COLLISION = 2;
	private final double TOP_COLLISION = 3;
	private final double BOTTOM_COLLISION = 4;
	private final ArrayList<Wall> walls = new ArrayList<Wall>();
	private final ArrayList<Water> traps = new ArrayList<Water>();
	private final ArrayList<Bridge> bridges = new ArrayList<Bridge>();
	private final ArrayList<Nut> nuts = new ArrayList<Nut>();
	private final ArrayList<Area> areas = new ArrayList<Area>();
	private final Font font = new Font("Helvetica", Font.BOLD, 24);
	private Player squirrel;
	private int w = (int) Main.dim.getWidth();
	private int h = (int) Main.dim.getHeight();
	private int levelNum = 0;
	private int myScore = 1000;
	private boolean completed = false;
	private boolean help = false;
	private boolean isPressed = false;

	private static final String VOICENAME = "kevin16";
	static boolean voiceStopped;
	static Voice voice;
	public static boolean voiceInterruptor = false;

	public static void initVoice(String message) {

		final VoiceManager vm = VoiceManager.getInstance();
		voice = vm.getVoice(VOICENAME);
		voice.allocate();
		voice.speak(message);

		if (voiceStopped == false) {
			voiceStopped = true;
		}
	}

	public Board() {

		addKeyListener(new TAdapter());
		setFocusable(true);

		LevelsBgsEngine.addLevels();
		LevelsBgsEngine.addBackgrounds();

		initWorld();
	}

	public int getBoardWidth() {
		return this.w;
	}

	public int getBoardHeight() {
		return this.h;
	}
	
	public static Dimension getCoordinates() {
		return Main.dim = Toolkit.getDefaultToolkit().getScreenSize();
	}

	// Shuffles the level string a.k.a random level generator:
	public String shuffleString(String string) {
		final List<String> symbols = Arrays.asList(string.split("\\r?\\n"));
		Collections.shuffle(symbols);
		String shuffled = "";
		for (final String symbol : symbols) {
			shuffled += (symbol + "\n");
			shuffled.trim();
		}
		return shuffled;
	}

	public final void initWorld() {

		int x = OFFSET;
		int y = OFFSET;

		Wall wall;
		Water terrain;
		Bridge bridge;
		Nut b;
		Area a;

		LoadSounds.BG_MUSIC.loop();

		for (int i = 0; i < LevelsBgsEngine.levels.get(levelNum).length(); i++) {

			final char item = LevelsBgsEngine.levels.get(levelNum).charAt(i);

			if (item == '\n') {
				y += SPACE;
				if (this.w < x) {
					this.w = x;
				}
				x = OFFSET;
			} else if (item == '#') {
				wall = new Wall(x, y);
				walls.add(wall);
				x += SPACE;
			} else if (item == 't') {
				terrain = new Water(x, y);
				traps.add(terrain);
				x += SPACE;
			} else if (item == '&') {
				bridge = new Bridge(x, y);
				bridges.add(bridge);
				x += SPACE;
			} else if (item == '$') {
				b = new Nut(x, y);
				nuts.add(b);
				x += SPACE;
			} else if (item == '.') {
				a = new Area(x, y);
				areas.add(a);
				x += SPACE;
			} else if (item == '@') {
				squirrel = new Player(x, y);
				x += SPACE;
			} else if (item == ' ') {
				x += SPACE;
			}

			h = y;
		}
	}

	public void buildWorld(Graphics g) {

		for (int i = 0; i < LevelsBgsEngine.backgrounds.size(); i++) {
			if (completed == false) {
				if (levelNum >= LevelsBgsEngine.backgrounds.size()) {
					g.drawImage(LevelsBgsEngine.backgrounds.get(2), 0, 0, null);
				} else {
					g.drawImage(LevelsBgsEngine.backgrounds.get(levelNum), 0, 0, null);
				}
			} else {
				g.setColor(new Color(255, 136, 0));
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				g.setColor(new Color(233, 233, 12));
				g.setFont(font);
				g.drawString("PRESS ENTER TO CONTINUE", ((int) getCoordinates().getWidth() - 400) / 2, ((int) getCoordinates().getHeight() / 2) - 100);
				return;
			}
		}

		final ArrayList<Actor> world = new ArrayList<Actor>();
		world.addAll(walls);
		world.addAll(traps);
		world.addAll(bridges);
		world.addAll(areas);
		world.addAll(nuts);
		world.add(squirrel);

		for (int i = 0; i < world.size(); i++) {

			final Actor item = world.get(i);

			if ((item instanceof Player) || (item instanceof Nut)) {
				g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
			} else {
				g.drawImage(item.getImage(), item.x(), item.y(), this);
			}
		}
	}

	public void loadNextLevel(Graphics g) {
		for (int i = 0; i <= LevelsBgsEngine.levels.size(); i++) {
			if (completed == true) {
				g.setFont(font);
				g.setColor(new Color(233, 233, 12));
				if (levelNum <= LevelsBgsEngine.levels.size() - 2) {
					g.drawString("LEVEL " + (levelNum + 1) + " COMPLETED!", ((int) getCoordinates().getWidth() - 400) / 2, ((int) getCoordinates().getHeight() / 2) - 150);
				}
				if (levelNum > LevelsBgsEngine.levels.size() - 2) {
					g.drawString("VICTORY!", 150, 200);
					levelNum = 0;
					restartLevel();
					return;
				}
				levelNum++;
				restartLevel();
			}
		}
	}

	private void drawScore(Graphics g) {

		g.setColor(new Color(220,20,60));
		g.setFont(font);
		g.drawString("Level: " + (levelNum + 1), 650, 50);
		g.drawString("     Moves left: " + myScore, 940, 50);

		if (help == true) {
			g.drawString("Shuffle ON", 795, 50);
		}

		if (help == false) {
			g.drawString("Shuffle OFF", 795, 50);
		}

		if (myScore <= 0) {
			voiceInterruptor = true;
			if (voiceInterruptor == true) {
				initVoice("Game over!");
				voiceInterruptor = false;
			}
			g.setColor(new Color(255, 136, 0));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(new Color(233, 233, 12));
			g.setFont(font);
			g.drawString("GAME OVER!", ((int) getCoordinates().getWidth() - 400) / 2, ((int) getCoordinates().getHeight() / 2) - 150);
			g.drawString("PRESS ANY KEY TO RESTART", ((int) getCoordinates().getWidth() - 400) / 2, ((int) getCoordinates().getHeight() / 2) - 100);
			restartLevel();
		}

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		buildWorld(g);
		loadNextLevel(g);
		drawScore(g);
	}

	private boolean checkWaterCollision(Actor actor, double type) {
		if (type == LEFT_COLLISION) {

			for (int i = 0; i < traps.size(); i++) {
				final Water terrain = traps.get(i);
				if (actor.isLeftCollision(terrain)) {
					return true;
				}
			}
			return false;

		} else if (type == RIGHT_COLLISION) {

			for (int i = 0; i < traps.size(); i++) {
				final Water terrain = traps.get(i);
				if (actor.isRightCollision(terrain)) {
					return true;
				}
			}
			return false;

		} else if (type == TOP_COLLISION) {

			for (int i = 0; i < traps.size(); i++) {
				final Water terrain = traps.get(i);
				if (actor.isTopCollision(terrain)) {
					return true;
				}
			}
			return false;

		} else if (type == BOTTOM_COLLISION) {

			for (int i = 0; i < traps.size(); i++) {
				final Water terrain = traps.get(i);
				if (actor.isBottomCollision(terrain)) {
					return true;
				}
			}
			return false;
		}
		return false;

	}

	private boolean checkWallCollision(Actor actor, double type) {

		if (type == LEFT_COLLISION) {

			for (int i = 0; i < walls.size(); i++) {
				final Wall wall = walls.get(i);
				if (actor.isLeftCollision(wall)) {
					return true;
				}
			}
			return false;

		} else if (type == RIGHT_COLLISION) {

			for (int i = 0; i < walls.size(); i++) {
				final Wall wall = walls.get(i);
				if (actor.isRightCollision(wall)) {
					return true;
				}
			}
			return false;

		} else if (type == TOP_COLLISION) {

			for (int i = 0; i < walls.size(); i++) {
				final Wall wall = walls.get(i);
				if (actor.isTopCollision(wall)) {
					return true;
				}
			}
			return false;

		} else if (type == BOTTOM_COLLISION) {

			for (int i = 0; i < walls.size(); i++) {
				final Wall wall = walls.get(i);
				if (actor.isBottomCollision(wall)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	private boolean checkBagCollision(double type) {

		if (type == LEFT_COLLISION) {

			for (int i = 0; i < nuts.size(); i++) {

				final Nut bag = nuts.get(i);
				if (squirrel.isLeftCollision(bag)) {

					for (int j = 0; j < nuts.size(); j++) {
						final Nut item = nuts.get(j);
						if (!bag.equals(item)) {
							if (bag.isLeftCollision(item)) {
								return true;
							}
						}
						if (checkWallCollision(bag, LEFT_COLLISION)) {
							return true;
						}
					}
					bag.move(-SPACE, 0);
					isCompleted();
				}
			}
			return false;

		} else if (type == RIGHT_COLLISION) {

			for (int i = 0; i < nuts.size(); i++) {

				final Nut nut = nuts.get(i);
				if (squirrel.isRightCollision(nut)) {
					for (int j = 0; j < nuts.size(); j++) {

						final Nut item = nuts.get(j);
						if (!nut.equals(item)) {
							if (nut.isRightCollision(item)) {
								return true;
							}
						}
						if (checkWallCollision(nut, RIGHT_COLLISION)) {
							return true;
						}
					}
					nut.move(SPACE, 0);
					isCompleted();
				}
			}
			return false;

		} else if (type == TOP_COLLISION) {

			for (int i = 0; i < nuts.size(); i++) {

				final Nut bag = nuts.get(i);
				if (squirrel.isTopCollision(bag)) {
					for (int j = 0; j < nuts.size(); j++) {

						final Nut item = nuts.get(j);
						if (!bag.equals(item)) {
							if (bag.isTopCollision(item)) {
								return true;
							}
						}
						if (checkWallCollision(bag, TOP_COLLISION)) {
							return true;
						}
					}
					bag.move(0, -SPACE);
					isCompleted();
				}
			}

			return false;

		} else if (type == BOTTOM_COLLISION) {

			for (int i = 0; i < nuts.size(); i++) {

				final Nut bag = nuts.get(i);
				if (squirrel.isBottomCollision(bag)) {
					for (int j = 0; j < nuts.size(); j++) {

						final Nut item = nuts.get(j);
						if (!bag.equals(item)) {
							if (bag.isBottomCollision(item)) {
								return true;
							}
						}
						if (checkWallCollision(bag, BOTTOM_COLLISION)) {
							return true;
						}
					}
					bag.move(0, SPACE);
					isCompleted();
				}
			}
		}

		return false;
	}

	public boolean isCompleted() {

		final int num = nuts.size();
		int compl = 0;

		for (int i = 0; i < num; i++) {
			final Nut bag = nuts.get(i);
			for (int j = 0; j < num; j++) {
				final Area area = areas.get(j);
				if (bag.x() == area.x() && bag.y() == area.y()) {
					compl += 1;
				}
			}
		}

		if (compl == num) {
			completed = true;
			LoadSounds.BG_MUSIC.stop();
			LoadSounds.HIGHSC.play();
			repaint();
			return true;
		}
		return false;

	}

	public void restartLevel() {
		areas.clear();
		nuts.clear();
		walls.clear();
		traps.clear();
		bridges.clear();
		myScore = (1000 - 20 * levelNum);
		if (completed) {
			completed = false;
		}
		initWorld();
	}
	
	class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			if (completed) {
				return;
			}

			final int key = e.getKeyCode();

			if (key == KeyEvent.VK_H) {
				if (help == false) {
					help = true;
					if (help == true) {
						Collections.shuffle(LevelsBgsEngine.levels); // changes
																		// order of
																		// levels
						Collections.shuffle(LevelsBgsEngine.backgrounds); // changes
																			// order
																			// of
																			// bgs
						LevelsBgsEngine.levels.add(shuffleString(LevelsBgsEngine.levels.get(levelNum))); // adds
						// random
						// levels
						// to
						// levels-ArrayList
					}
				} else {
					help = false;
				}
				// levelNum++;
			}

			// Cut some trees
			if (key == KeyEvent.VK_C) {
				myScore-=100;
				for (int i = 0; i <= walls.size(); i++) {
					walls.remove(walls.get(i));
				}
			}

			if (key == KeyEvent.VK_LEFT) {
				if (checkWallCollision(squirrel, LEFT_COLLISION)) {
					LoadSounds.NEGATIVE.play();
					if(e.isConsumed()) {
						LoadSounds.NEGATIVE.stop();	
					}
					return;
				}

				if (checkWaterCollision(squirrel, LEFT_COLLISION)) {
					LoadSounds.DEAD.play();
					restartLevel();
					return;
				}

				if (checkBagCollision(LEFT_COLLISION)) {
					LoadSounds.BOING.play();
					return;
				}

				squirrel.move(-SPACE, 0);
				myScore--;
				LoadSounds.MOVE.play();

			} else if (key == KeyEvent.VK_RIGHT) {

				if (checkWallCollision(squirrel, RIGHT_COLLISION)) {
					LoadSounds.NEGATIVE.play();
					if(e.isConsumed()) {
						LoadSounds.NEGATIVE.stop();	
					}
					return;
				}

				if (checkWaterCollision(squirrel, RIGHT_COLLISION)) {
					LoadSounds.DEAD.play();
					restartLevel();
					return;
				}

				if (checkBagCollision(RIGHT_COLLISION)) {
					LoadSounds.BOING.play();
					return;
				}

				squirrel.move(SPACE, 0);
				myScore--;
				LoadSounds.MOVE.play();

			} else if (key == KeyEvent.VK_UP) {

				if (checkWallCollision(squirrel, TOP_COLLISION)) {
					LoadSounds.NEGATIVE.play();
					if(e.isConsumed()) {
						LoadSounds.NEGATIVE.stop();	
					}
					return;
				}

				if (checkWaterCollision(squirrel, TOP_COLLISION)) {
					LoadSounds.DEAD.play();
					restartLevel();
					return;
				}

				if (checkBagCollision(TOP_COLLISION)) {
					LoadSounds.BOING.play();
					return;
				}

				squirrel.move(0, -SPACE);
				myScore--;
				LoadSounds.MOVE.play();

			} else if (key == KeyEvent.VK_DOWN) {

				if (checkWallCollision(squirrel, BOTTOM_COLLISION)) {
					LoadSounds.NEGATIVE.play();
					if(e.isConsumed()) {
						LoadSounds.NEGATIVE.stop();	
					}
					return;
				}

				if (checkWaterCollision(squirrel, BOTTOM_COLLISION)) {
					LoadSounds.DEAD.play();
					restartLevel();
					return;
				}

				if (checkBagCollision(BOTTOM_COLLISION)) {
					LoadSounds.BOING.play();
					return;
				}

				squirrel.move(0, SPACE);
				myScore--;
				LoadSounds.MOVE.play();

			} else if (key == KeyEvent.VK_R) {
				restartLevel();
				help = false;
			} else if (key == KeyEvent.VK_ENTER) {
				if (!isPressed) {
					LevelsBgsEngine.addLevels();
					completed = false;
					isPressed = true;
				} else {
					if (isCompleted())
						completed = true;
					isPressed = false;
					try {
						Runtime.getRuntime().exec("cls");
					} catch (final IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else if (key == KeyEvent.VK_S) {
				LoadSounds.BG_MUSIC.stop();
			} else if (key == KeyEvent.VK_A) {
				LoadSounds.BG_MUSIC.loop();
			} else if (key == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}

			repaint();
		}
	}
}