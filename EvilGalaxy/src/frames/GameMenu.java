package frames;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import entities.Alien;
import entities.Bunker;
import entities.Dragon;
import entities.EvilHead;
import entities.MyShip;
import game_engine.Collisions;
import game_engine.Difficulty;
import game_engine.DrawScene;
import game_engine.InitObjects;
import game_engine.UpdateObjects;
import items.Gold;
import items.HealthPack;
import launcher.Launcher;
import multiplayer_tbd.JoinGame;
import sound_engine.LoadSounds;

@SuppressWarnings("serial")
public class GameMenu extends JFrame {

	public static boolean savedOnL1 = false;
	public static boolean savedOnL2 = false;
	public static boolean savedOnL3 = false;
	public static boolean savedOnL4 = false;

	public static JCheckBoxMenuItem autosave;

	public GameMenu() {

		createMenu();
	}

	private void createMenu() {

		// Menu Bar
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		JMenu game = new JMenu("Game");
		game.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menubar.add(game);
		JMenuItem newgame = new JMenuItem("Restart      R");
		newgame.setFont(new Font("Segoe UI", Font.BOLD, 14));
		JMenuItem pause = new JMenuItem("Pause      P");
		pause.setFont(new Font("Segoe UI", Font.BOLD, 14));
		JMenuItem save = new JMenuItem("Save Game");
		save.setFont(new Font("Segoe UI", Font.BOLD, 14));
		save.setMnemonic(KeyEvent.VK_Z);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.ALT_MASK));
		JMenuItem load = new JMenuItem("Load Game");
		load.setFont(new Font("Segoe UI", Font.BOLD, 14));
		load.setMnemonic(KeyEvent.VK_C);
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		JMenuItem join = new JMenuItem("Join game");
		join.setFont(new Font("Segoe UI", Font.BOLD, 14));
		JMenuItem exit = new JMenuItem("Exit           Esc");
		exit.setFont(new Font("Segoe UI", Font.BOLD, 14));
		game.add(newgame);
		game.addSeparator();
		game.add(pause);
		game.addSeparator();
		game.add(save);
		game.addSeparator();
		game.add(load);
		game.addSeparator();
		game.add(join);
		game.addSeparator();
		game.add(exit);
		JMenu levels = new JMenu("Levels");
		levels.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menubar.add(levels);
		JMenuItem level1 = new JMenuItem("Level 1 (or key 1)");
		level1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		JMenuItem level2 = new JMenuItem("Level 2 (or key 2)");
		level2.setFont(new Font("Segoe UI", Font.BOLD, 14));
		JMenuItem level3 = new JMenuItem("Level 3 (or key 3)");
		level3.setFont(new Font("Segoe UI", Font.BOLD, 14));
		JMenuItem level4 = new JMenuItem("Level 4 (or key 4)");
		level4.setFont(new Font("Segoe UI", Font.BOLD, 14));
		levels.add(level1);
		levels.addSeparator();
		levels.add(level2);
		levels.addSeparator();
		levels.add(level3);
		levels.addSeparator();
		levels.add(level4);
		JMenu difficulty = new JMenu("Difficulty");
		difficulty.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menubar.add(difficulty);
		JMenuItem easy = new JMenuItem("Easy           E");
		easy.setFont(new Font("Segoe UI", Font.BOLD, 14));
		JMenuItem medium = new JMenuItem("Medium    M");
		medium.setFont(new Font("Segoe UI", Font.BOLD, 14));
		JMenuItem hard = new JMenuItem("Hard         H");
		hard.setFont(new Font("Segoe UI", Font.BOLD, 14));
		difficulty.add(easy);
		difficulty.addSeparator();
		difficulty.add(medium);
		difficulty.addSeparator();
		difficulty.add(hard);
		JMenu help = new JMenu("Help");
		help.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menubar.add(help);
		JMenuItem manual = new JMenuItem("Manual    O");
		manual.setFont(new Font("Segoe UI", Font.BOLD, 14));
		help.add(manual);
		JMenu about = new JMenu("About");
		about.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menubar.add(about);
		JMenuItem gamerepo = new JMenuItem("Game repo");
		gamerepo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		about.add(gamerepo);
		about.addSeparator();
		gamerepo.addActionListener(e -> {
			try {
				Desktop.getDesktop().browse(new URI("https://github.com/Hunterszone/EvilGalaxy"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		JMenuItem gamesite = new JMenuItem("Game webpage");
		gamesite.setFont(new Font("Segoe UI", Font.BOLD, 14));
		about.add(gamesite);
		gamesite.addActionListener(e -> {
			try {
				Desktop.getDesktop().browse(new URI("http://me4gaming.com/index.php/en/gamedev/6-articles"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		JMenu tools = new JMenu("Tools");
		tools.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menubar.add(tools);
		JMenuItem launcher = new JMenuItem("Updater");
		launcher.setFont(new Font("Segoe UI", Font.BOLD, 14));
		tools.add(launcher);
		tools.addSeparator();
		autosave = new JCheckBoxMenuItem("Auto-Save");
		autosave.setFont(new Font("Segoe UI", Font.BOLD, 14));
		autosave.setMnemonic(KeyEvent.VK_X);
		autosave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
		tools.add(autosave);

		// Menu SubItems

		class Updater implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Launcher.main(null);
				System.exit(0);
			}

		} 

		class ExitAction implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}

		class NewGame implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				Difficulty.restart();

			}
		}

		class PauseGame implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				InitObjects.timerEasy.stop();
				InitObjects.timerHard.stop();
				InitObjects.timerMedium.stop();

				LoadSounds.bgMusic.stop();
				LoadSounds.roar.stop();
			}
		}

		class SaveGame implements ActionListener {

			String savesPath = "saves/save.txt";
			File file = new File(savesPath);

			public void saveGameDataToFile(File savefile) {

				DrawScene.voiceInterruptor = false;

				if (InitObjects.ingame == true) {

					if (Alien.aliens.size() > 0 && DrawScene.voiceInterruptor == false) {

						savedOnL2 = false;
						savedOnL3 = false;
						savedOnL4 = false;
						savedOnL1 = true;
						DrawScene.initVoice("Game saved!");
						DrawScene.voiceInterruptor = true;
					}

					DrawScene.voiceInterruptor = false;

					if (Alien.aliens.isEmpty() && Dragon.dragons.size() > 0 && DrawScene.voiceInterruptor == false) {

						savedOnL1 = false;
						savedOnL3 = false;
						savedOnL4 = false;
						savedOnL2 = true;
						DrawScene.initVoice("Game saved!");
						DrawScene.voiceInterruptor = true;
					}

					DrawScene.voiceInterruptor = false;

					if (Dragon.dragons.isEmpty() && UpdateObjects.lifeBunker < 50
							&& DrawScene.voiceInterruptor == false) {

						savedOnL2 = false;
						savedOnL1 = false;
						savedOnL4 = false;
						savedOnL3 = true;
						DrawScene.initVoice("Game saved!");
						DrawScene.voiceInterruptor = true;
					}

					DrawScene.voiceInterruptor = false;

					if (UpdateObjects.lifeBunker == 50 && DrawScene.voiceInterruptor == false) {

						savedOnL1 = false;
						savedOnL2 = false;
						savedOnL3 = false;
						savedOnL4 = true;
						DrawScene.initVoice("Game saved!");
						DrawScene.voiceInterruptor = true;
					}

					try {
						FileOutputStream fileStream = new FileOutputStream(savefile);
						ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

						objectStream.writeObject(MyShip.myShip);
						objectStream.writeObject(EvilHead.evilHead);
						objectStream.writeObject(Bunker.bunkerObj);
						objectStream.writeObject(Alien.aliens);
						objectStream.writeObject(Dragon.dragons);
						objectStream.writeObject(Gold.goldstack);
						objectStream.writeObject(HealthPack.healthpack);

						objectStream.close();
						fileStream.close();

//						JOptionPane.showConfirmDialog(frame, "Saved game state successfully.", "Save game",
//								JOptionPane.DEFAULT_OPTION);
					} catch (Exception e) {
//						JOptionPane.showConfirmDialog(frame, e.toString() + "\nFail to save game state.", "Save game",
//								JOptionPane.DEFAULT_OPTION);
					}

				}

			}

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				saveGameDataToFile(file);

			}

		}

		class LoadGame implements ActionListener {

			String savesPath = "saves/save.txt";
			File file = new File(savesPath);

			List<Object> loadedAssets = new ArrayList<Object>();

			MyShip savedShip;
			EvilHead savedHead;
			Bunker savedBunker;
			Object savedAliens;
			Object savedDragons;
			Object savedGold;
			Object savedHP;

			public void loadGameDataFromFile(File loadfile) throws ClassNotFoundException, IOException {

				try {
					FileInputStream fileStream = new FileInputStream(loadfile);
					ObjectInputStream objectStream = new ObjectInputStream(fileStream);

					savedShip = (MyShip) objectStream.readObject();
					savedHead = (EvilHead) objectStream.readObject();
					savedBunker = (Bunker) objectStream.readObject();
					savedAliens = (Object) objectStream.readObject();
					savedDragons = (Object) objectStream.readObject();
					savedGold = (Object) objectStream.readObject();
					savedHP = (Object) objectStream.readObject();

					loadedAssets.add(savedShip);
					loadedAssets.add(savedHead);
					loadedAssets.add(savedBunker);
					loadedAssets.add(savedAliens);
					loadedAssets.add(savedDragons);
					loadedAssets.add(savedGold);
					loadedAssets.add(savedHP);

					System.out.println(loadedAssets.toString());

					initSavedAssets();

//					objectStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			void initSavedAssets() {

				DrawScene.voiceInterruptor = false;

				if (savedOnL1 == true && DrawScene.voiceInterruptor == false) {

					DrawScene.initVoice("Game loaded!");

					Difficulty.restart();
					Alien.aliens = new ArrayList<>();

					for (int i = 0; i < 40 - Collisions.alienKilled; i++) {
						Alien born = new Alien((int) Math.ceil(Math.random() * 7000),
								(int) Math.ceil(Math.random() * 800));
						Alien.aliens.add(born);
					}

					DrawScene.voiceInterruptor = true;
					savedOnL1 = false;
					return;
				}

				DrawScene.voiceInterruptor = false;

				if (savedOnL2 && DrawScene.voiceInterruptor == false) {
					DrawScene.initVoice("Game loaded!");
					Difficulty.restart();
					Alien.aliens.clear();
					Dragon.dragons = new ArrayList<>();
//					for (int i = 0; i < 30 - Collisions.dragonKilled; i++) {
//						Dragon born = new Dragon((int) Math.ceil(Math.random() * 7000), (int) Math.ceil(Math.random() * 800));
//						Dragon.dragons.add(born);
//					}
					for (Object born : loadedAssets) {
						born = new Dragon((int) Math.ceil(Math.random() * 4000), (int) Math.ceil(Math.random() * 800));
						Dragon.dragons.add((Dragon) born);
						if (Dragon.dragons.size() == 14)
							break;
					}

					DrawScene.voiceInterruptor = true;
					savedOnL2 = false;
					return;
				}

				DrawScene.voiceInterruptor = false;

				if (savedOnL3 && DrawScene.voiceInterruptor == false) {
					DrawScene.initVoice("Game loaded!");
					Difficulty.restart();
					Alien.aliens.clear();
					Dragon.dragons.clear();
					DrawScene.voiceInterruptor = true;
					savedOnL3 = false;
					return;
				}

				DrawScene.voiceInterruptor = false;

				if (savedOnL4 && DrawScene.voiceInterruptor == false) {
					DrawScene.initVoice("Game loaded!");
					Difficulty.restart();
					Alien.aliens.clear();
					Dragon.dragons.clear();
					UpdateObjects.lifeBunker = 50;
					DrawScene.voiceInterruptor = true;
					savedOnL4 = false;
					return;
				}

				EvilHead.evilHead = new EvilHead(500, 500);
				EvilHead.evilHead.isVisible();
				EvilHead.evilHead.AIOnEasy();

				Gold.goldstack.add(new Gold(150, 150));

				HealthPack.healthpack.add(new HealthPack(400, 400));

			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if(!InitObjects.ingame)
						loadGameDataFromFile(file);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		}

		class Join implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JoinGame joingame = new JoinGame();
				joingame.setVisible(true);
			}

		}

		class InvokeManual implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				Manual manual = new Manual();

				if (!InitObjects.manualON) {
					manual.setVisible(true);
					if (!InitObjects.manualON == true) {
						InitObjects.manualON = true;
					}
				}

			}
		}

		class Level1 implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Difficulty.restart();
			}

		}

		class Level2 implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Difficulty.restart();
				Alien.aliens.clear();
			}

		}

		class Level3 implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Difficulty.restart();
				Alien.aliens.clear();
				Dragon.dragons.clear();
			}

		}

		class Level4 implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Difficulty.restart();
				Alien.aliens.clear();
				Dragon.dragons.clear();
				UpdateObjects.lifeBunker = 50;
			}

		}

		class Easy implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {

				InitObjects.timerMedium.stop();
				InitObjects.timerHard.stop();
				InitObjects.timerEasy.start();
				LoadSounds.bgMusic.loop();
				if (Alien.aliens.isEmpty()) {
					LoadSounds.roar.loop();
				}
				if (!InitObjects.ingame) {
					Difficulty.easy();
					return;
				}

			}
		}

		class Medium implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {

				InitObjects.timerEasy.stop();
				InitObjects.timerHard.stop();
				InitObjects.timerMedium.start();
				LoadSounds.bgMusic.loop();
				if (Alien.aliens.isEmpty()) {
					LoadSounds.roar.loop();
				}
				if (!InitObjects.ingame) {
					Difficulty.medium();
					return;
				}
			}
		}

		class Hard implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {

				InitObjects.timerEasy.stop();
				InitObjects.timerMedium.stop();
				InitObjects.timerHard.start();
				LoadSounds.bgMusic.loop();
				if (Alien.aliens.isEmpty()) {
					LoadSounds.roar.loop();
				}
				if (!InitObjects.ingame) {
					Difficulty.hard();
					return;
				}
			}
		}

		// Menu Events
		launcher.addActionListener(new Updater());
		exit.addActionListener(new ExitAction());
		manual.addActionListener(new InvokeManual());
		newgame.addActionListener(new NewGame());
		pause.addActionListener(new PauseGame());
		save.addActionListener(new SaveGame());
		load.addActionListener(new LoadGame());
		join.addActionListener(new Join());
		level1.addActionListener(new Level1());
		level2.addActionListener(new Level2());
		level3.addActionListener(new Level3());
		level4.addActionListener(new Level4());
		easy.addActionListener(new Easy());
		medium.addActionListener(new Medium());
		hard.addActionListener(new Hard());

	}

}