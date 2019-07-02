package sokoban;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import sound_engine.LoadSounds;
import sound_engine.PlayWave1st;

public class Board extends JPanel {

	private static final long serialVersionUID = 1L;
	private final int OFFSET = 50;
	private final int SPACE = 20;
	private final double LEFT_COLLISION = 1;
	private final double RIGHT_COLLISION = 2;
	private final double TOP_COLLISION = 3;
	private final double BOTTOM_COLLISION = 4;
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private ArrayList<Water> traps = new ArrayList<Water>();
	private ArrayList<Bridge> bridges = new ArrayList<Bridge>();
	private ArrayList<Nut> nuts = new ArrayList<Nut>();
	private ArrayList<Area> areas = new ArrayList<Area>();
	private Font font = new Font("Helvetica", Font.BOLD, 14);
	private Player squirrel;
	private int w = 0;
	private int h = 0;
	private int levelNum = 0;
	private int myScore = 500;
	private boolean completed = false;
	private boolean help = false;

	private static final String VOICENAME = "kevin16";
	static boolean voiceStopped;
	static Voice voice;
	public static boolean voiceInterruptor = false;

	public static void initVoice(String message) {

		VoiceManager vm = VoiceManager.getInstance();
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

		LevelsAndBgs.addLevels();
		LevelsAndBgs.addBackgrounds();

		initWorld();
	}

	public int getBoardWidth() {
		return this.w;
	}

	public int getBoardHeight() {
		return this.h;
	}

	// Shuffles the level string a.k.a random level generator:
	public String shuffleString(String string) {
		List<String> symbols = Arrays.asList(string.split("\\r?\\n"));
		Collections.shuffle(symbols);
		String shuffled = "";
		for (String symbol : symbols) {
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

		LoadSounds.bgMusic.loop();

		for (int i = 0; i < LevelsAndBgs.levels.get(levelNum).length(); i++) {

			char item = LevelsAndBgs.levels.get(levelNum).charAt(i);

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

		for (int i = 0; i < LevelsAndBgs.backgrounds.size(); i++) {
			if (completed == false) {
				if (levelNum >= LevelsAndBgs.backgrounds.size()) {
					g.drawImage(LevelsAndBgs.backgrounds.get(2), 0, 0, null);
				} else {
					g.drawImage(LevelsAndBgs.backgrounds.get(levelNum), 0, 0, null);
				}

			} else {
				g.setColor(new Color(255, 136, 0));
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				g.setColor(new Color(233, 233, 12));
				g.setFont(font);
				g.drawString("PRESS ENTER TO CONTINUE", 150, 250);
				return;
			}
		}

		ArrayList<Actor> world = new ArrayList<Actor>();
		world.addAll(walls);
		world.addAll(traps);
		world.addAll(bridges);
		world.addAll(areas);
		world.addAll(nuts);
		world.add(squirrel);

		for (int i = 0; i < world.size(); i++) {

			Actor item = world.get(i);

			if ((item instanceof Player) || (item instanceof Nut)) {
				g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
			} else {
				g.drawImage(item.getImage(), item.x(), item.y(), this);
			}
		}
	}

	public void loadNextLevel(Graphics g) {
		for (int i = 0; i <= LevelsAndBgs.levels.size(); i++) {
			if (completed == true) {
				g.setFont(font);
				g.setColor(new Color(233, 233, 12));
				if (levelNum <= LevelsAndBgs.levels.size() - 2) {
					g.drawString("LEVEL " + (levelNum + 1) + " COMPLETED!", 150, 200);
				}
				if (levelNum > LevelsAndBgs.levels.size() - 2) {
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

		g.setColor(new Color(255, 0, 0));
		g.setFont(font);
		g.drawString("Level: " + (levelNum + 1), 50, 20);
		g.drawString("Moves left: " + myScore, 340, 20);

		if (help == true) {
			g.drawString("Shuffle ON", 195, 20);
		}

		if (help == false) {
			g.drawString("Shuffle OFF", 195, 20);
		}

		if (myScore == 0) {
			voiceInterruptor = true;
			if (voiceInterruptor == true) {
				initVoice("Game over!");
				voiceInterruptor = false;
			}
			g.setColor(new Color(255, 136, 0));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(new Color(233, 233, 12));
			g.setFont(font);
			g.drawString("GAME OVER!", 150, 200);
			g.drawString("PRESS ENTER TO CONTINUE", 150, 250);
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

	class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			if (completed) {
				return;
			}

			int key = e.getKeyCode();

			if (key == KeyEvent.VK_H) {
				if (help == false) {
					help = true;
					if (help == true) {
						Collections.shuffle(LevelsAndBgs.levels); // changes
																	// order of
																	// levels
						Collections.shuffle(LevelsAndBgs.backgrounds); // changes
																		// order
																		// of
																		// bgs
						LevelsAndBgs.levels.add(shuffleString(LevelsAndBgs.levels.get(levelNum))); // adds
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
				for (int i = 0; i <= walls.size(); i++) {
					walls.remove(walls.get(i));
				}
			}

			if (key == KeyEvent.VK_LEFT) {
				if (checkWallCollision(squirrel, LEFT_COLLISION)) {
					new PlayWave1st("sounds/denied.wav").start();
					return;
				}

				if (checkWaterCollision(squirrel, LEFT_COLLISION)) {
					new PlayWave1st("sounds/dead.wav").start();
					restartLevel();
					return;
				}

				if (checkBagCollision(LEFT_COLLISION)) {
					new PlayWave1st("sounds/boing.wav").start();
					return;
				}

				squirrel.move(-SPACE, 0);
				myScore--;
				new PlayWave1st("sounds/move.wav").start();

			} else if (key == KeyEvent.VK_RIGHT) {

				if (checkWallCollision(squirrel, RIGHT_COLLISION)) {
					new PlayWave1st("sounds/denied.wav").start();
					return;
				}

				if (checkWaterCollision(squirrel, RIGHT_COLLISION)) {
					new PlayWave1st("sounds/dead.wav").start();
					restartLevel();
					return;
				}

				if (checkBagCollision(RIGHT_COLLISION)) {
					new PlayWave1st("sounds/boing.wav").start();
					return;
				}

				squirrel.move(SPACE, 0);
				myScore--;
				new PlayWave1st("sounds/move.wav").start();

			} else if (key == KeyEvent.VK_UP) {

				if (checkWallCollision(squirrel, TOP_COLLISION)) {
					new PlayWave1st("sounds/denied.wav").start();
					return;
				}

				if (checkWaterCollision(squirrel, TOP_COLLISION)) {
					new PlayWave1st("sounds/dead.wav").start();
					restartLevel();
					return;
				}

				if (checkBagCollision(TOP_COLLISION)) {
					new PlayWave1st("sounds/boing.wav").start();
					return;
				}

				squirrel.move(0, -SPACE);
				myScore--;
				new PlayWave1st("sounds/move.wav").start();

			} else if (key == KeyEvent.VK_DOWN) {

				if (checkWallCollision(squirrel, BOTTOM_COLLISION)) {
					new PlayWave1st("sounds/denied.wav").start();
					return;
				}

				if (checkWaterCollision(squirrel, BOTTOM_COLLISION)) {
					new PlayWave1st("sounds/dead.wav").start();
					restartLevel();
					return;
				}

				if (checkBagCollision(BOTTOM_COLLISION)) {
					new PlayWave1st("sounds/boing.wav").start();
					return;
				}

				squirrel.move(0, SPACE);
				myScore--;
				new PlayWave1st("sounds/move.wav").start();

			} else if (key == KeyEvent.VK_R) {
				restartLevel();
				help = false;
			} else if (key == KeyEvent.VK_ENTER) {
				completed = false;
			} else if (key == KeyEvent.VK_S) {
				LoadSounds.bgMusic.stop();
			} else if (key == KeyEvent.VK_A) {
				LoadSounds.bgMusic.loop();
			} else if (key == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}

			repaint();
		}
	}

	private boolean checkWaterCollision(Actor actor, double type) {
		if (type == LEFT_COLLISION) {

			for (int i = 0; i < traps.size(); i++) {
				Water terrain = traps.get(i);
				if (actor.isLeftCollision(terrain)) {
					return true;
				}
			}
			return false;

		} else if (type == RIGHT_COLLISION) {

			for (int i = 0; i < traps.size(); i++) {
				Water terrain = traps.get(i);
				if (actor.isRightCollision(terrain)) {
					return true;
				}
			}
			return false;

		} else if (type == TOP_COLLISION) {

			for (int i = 0; i < traps.size(); i++) {
				Water terrain = traps.get(i);
				if (actor.isTopCollision(terrain)) {
					return true;
				}
			}
			return false;

		} else if (type == BOTTOM_COLLISION) {

			for (int i = 0; i < traps.size(); i++) {
				Water terrain = traps.get(i);
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
				Wall wall = walls.get(i);
				if (actor.isLeftCollision(wall)) {
					return true;
				}
			}
			return false;

		} else if (type == RIGHT_COLLISION) {

			for (int i = 0; i < walls.size(); i++) {
				Wall wall = walls.get(i);
				if (actor.isRightCollision(wall)) {
					return true;
				}
			}
			return false;

		} else if (type == TOP_COLLISION) {

			for (int i = 0; i < walls.size(); i++) {
				Wall wall = walls.get(i);
				if (actor.isTopCollision(wall)) {
					return true;
				}
			}
			return false;

		} else if (type == BOTTOM_COLLISION) {

			for (int i = 0; i < walls.size(); i++) {
				Wall wall = walls.get(i);
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

				Nut bag = nuts.get(i);
				if (squirrel.isLeftCollision(bag)) {

					for (int j = 0; j < nuts.size(); j++) {
						Nut item = nuts.get(j);
						if (!bag.equals(item)) {
							if (bag.isLeftCollision(item)) {
								return true;
							}
						}
						if (checkWallCollision(bag, LEFT_COLLISION)) {
							// LoadSounds.correctHolder.play();
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

				Nut nut = nuts.get(i);
				if (squirrel.isRightCollision(nut)) {
					for (int j = 0; j < nuts.size(); j++) {

						Nut item = nuts.get(j);
						if (!nut.equals(item)) {
							if (nut.isRightCollision(item)) {
								return true;
							}
						}
						if (checkWallCollision(nut, RIGHT_COLLISION)) {
							// LoadSounds.correctHolder.play();
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

				Nut bag = nuts.get(i);
				if (squirrel.isTopCollision(bag)) {
					for (int j = 0; j < nuts.size(); j++) {

						Nut item = nuts.get(j);
						if (!bag.equals(item)) {
							if (bag.isTopCollision(item)) {
								return true;
							}
						}
						if (checkWallCollision(bag, TOP_COLLISION)) {
							// LoadSounds.correctHolder.play();
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

				Nut bag = nuts.get(i);
				if (squirrel.isBottomCollision(bag)) {
					for (int j = 0; j < nuts.size(); j++) {

						Nut item = nuts.get(j);
						if (!bag.equals(item)) {
							if (bag.isBottomCollision(item)) {
								return true;
							}
						}
						if (checkWallCollision(bag, BOTTOM_COLLISION)) {
							// LoadSounds.correctHolder.play();
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

	public void isCompleted() {

		int num = nuts.size();
		int compl = 0;

		for (int i = 0; i < num; i++) {
			Nut bag = nuts.get(i);
			for (int j = 0; j < num; j++) {
				Area area = areas.get(j);
				if (bag.x() == area.x() && bag.y() == area.y()) {
					compl += 1;
				}
			}

		}

		if (compl == num) {
			completed = true;
			LoadSounds.bgMusic.stop();
			new PlayWave1st("sounds/highsc.wav").start();
			repaint();
		}

	}

	public void restartLevel() {
		areas.clear();
		nuts.clear();
		walls.clear();
		traps.clear();
		bridges.clear();
		myScore = (500 - 20 * levelNum);
		if (completed) {
			completed = false;
		}
		initWorld();
	}
}