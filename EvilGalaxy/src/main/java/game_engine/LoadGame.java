package game_engine;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import entities.Alien;
import entities.Bunker;
import entities.Dragon;
import entities.EvilHead;
import entities.MyShip;
import frames.Main;
import items.Gold;
import items.HealthPack;
import menu_engine.Display;
import menu_engine.MouseInputHandler;
import menu_states.MenuState;
import sound_engine.LoadSounds;

public class LoadGame {

	List<Object> loadedAssets = new ArrayList<Object>();

	MyShip savedShip;
	EvilHead savedHead;
	Bunker savedBunker;
	Object savedAliens;
	Object savedDragons;
	Object savedGold;
	Object savedHP;
	Object savedOnL1;
	Object savedOnL2;
	Object savedOnL3;
	Object savedOnL4;

	private void loadGameDataFromFile(File loadfile) throws ClassNotFoundException, IOException {

		LoadSounds.menuMusic.stop();
		
		try {
			FileInputStream fileStream = new FileInputStream(loadfile);
			ObjectInputStream objectStream = new ObjectInputStream(fileStream);

			savedShip = (MyShip) objectStream.readObject();
			savedHead = (EvilHead) objectStream.readObject();
			savedBunker = (Bunker) objectStream.readObject();
			savedAliens = objectStream.readObject();
			savedDragons = objectStream.readObject();
			savedGold = objectStream.readObject();
			savedHP = objectStream.readObject();
			savedOnL1 = objectStream.readObject();
			savedOnL2 = objectStream.readObject();
			savedOnL3 = objectStream.readObject();
			savedOnL4 = objectStream.readObject();

			loadedAssets.add(savedShip);
			loadedAssets.add(savedHead);
			loadedAssets.add(savedBunker);
			loadedAssets.add(savedAliens);
			loadedAssets.add(savedDragons);
			loadedAssets.add(savedGold);
			loadedAssets.add(savedHP);
			loadedAssets.add(savedOnL1);
			loadedAssets.add(savedOnL2);
			loadedAssets.add(savedOnL3);
			loadedAssets.add(savedOnL4);


			System.out.println(loadedAssets.toString());

			objectStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MenuState.isOn = false;
		if(MouseInputHandler.main == null) {
			Display.frame.remove(Display.canvas);
			Display.frame.dispose();
			EventQueue.invokeLater(() -> {
				MouseInputHandler.main = new Main();
				initSavedAssets();
				MouseInputHandler.main.setVisible(true);
			});
		}
		MenuState.isOn = true;
	}

	private void initSavedAssets() {

		DrawScene.voiceInterruptor = false;

		if (savedOnL1 != null && Boolean.TRUE.equals(savedOnL1) && DrawScene.voiceInterruptor == false) {

			DrawScene.initVoice("Game loaded!");

			if (Controls.isEPressed == true)
				Difficulty.easy();
			else if (Controls.isMPressed == true)
				Difficulty.medium();
			else if (Controls.isHPressed == true)
				Difficulty.hard();
			else
				Difficulty.restart();

			Alien.aliens = new ArrayList<>();
			for (int i = 0; i < 40 - Collisions.alienKilled; i++) {
				Alien born = new Alien((int) Math.ceil(Math.random() * 7000), (int) Math.ceil(Math.random() * 800));
				Alien.aliens.add(born);
			}

			System.out.println("Score after loading will be added to " + Alien.class.getName());
			DrawScene.voiceInterruptor = true;
			return;
		}

		DrawScene.voiceInterruptor = false;

		if (savedOnL2 != null && Boolean.TRUE.equals(savedOnL2) && DrawScene.voiceInterruptor == false) {

			DrawScene.initVoice("Game loaded!");

			if (Controls.isEPressed == true)
				Difficulty.easy();
			else if (Controls.isMPressed == true)
				Difficulty.medium();
			else if (Controls.isHPressed == true)
				Difficulty.hard();
			else
				Difficulty.restart();

			Alien.aliens.clear();
			Dragon.dragons = new ArrayList<>();
			for (int i = 0; i < 30 - Collisions.dragonKilled; i++) {
				Dragon born = new Dragon((int) Math.ceil(Math.random() * 4700), (int) Math.ceil(Math.random() * 800));
				Dragon.dragons.add(born);
			}

			System.out.println("Score after loading will be added to " + Dragon.class.getName());
			DrawScene.voiceInterruptor = true;
			return;
		}

		DrawScene.voiceInterruptor = false;

		if (savedOnL3 != null && Boolean.TRUE.equals(savedOnL3) && DrawScene.voiceInterruptor == false) {

			DrawScene.initVoice("Game loaded!");

			if (Controls.isEPressed == true)
				Difficulty.easy();
			else if (Controls.isMPressed == true)
				Difficulty.medium();
			else if (Controls.isHPressed == true)
				Difficulty.hard();
			else
				Difficulty.restart();

			Alien.aliens.clear();
			Dragon.dragons.clear();
			DrawScene.voiceInterruptor = true;
			return;
		}

		DrawScene.voiceInterruptor = false;

		if (savedOnL4 != null && Boolean.TRUE.equals(savedOnL4) && DrawScene.voiceInterruptor == false) {

			DrawScene.initVoice("Game loaded!");

			if (Controls.isEPressed == true)
				Difficulty.easy();
			else if (Controls.isMPressed == true)
				Difficulty.medium();
			else if (Controls.isHPressed == true)
				Difficulty.hard();
			else
				Difficulty.restart();

			Alien.aliens.clear();
			Dragon.dragons.clear();
			UpdateObjects.lifeBunker = 50;
			DrawScene.voiceInterruptor = true;
			return;
		}

		EvilHead.evilHead = new EvilHead(500, 500);
		EvilHead.evilHead.isVisible();
		EvilHead.evilHead.AIOnEasy();

		Gold.goldstack.add(new Gold(150, 150));

		HealthPack.healthpack.add(new HealthPack(400, 400));

	}

	public void openFileChooser() {

		JFileChooser chooser = new JFileChooser(new File("./saves"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
		int returnVal = chooser.showOpenDialog(null);

		chooser.setFileFilter(filter);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				loadGameDataFromFile(chooser.getSelectedFile());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
		}
	}

}