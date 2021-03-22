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
import entities.PlayerShip;
import items.Gold;
import items.HealthPack;
import main.Main;
import marytts.exceptions.MaryConfigurationException;
import menu_engine.DisplayCanvas;
import menu_engine.MouseInputHandler;
import menu_states.MenuState;
import util.LoadSounds;
import util.TextToSpeech;

public class LoadGame {

	List<Object> loadedAssets = new ArrayList<Object>();

	PlayerShip savedShip;
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

			savedShip = (PlayerShip) objectStream.readObject();
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
			DisplayCanvas.frame.remove(DisplayCanvas.canvas);
			DisplayCanvas.frame.dispose();
			EventQueue.invokeLater(() -> {
				MouseInputHandler.main = new Main();
				try {
					initSavedAssets();
				} catch (MaryConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				MouseInputHandler.main.setVisible(true);
			});
		}
		MenuState.isOn = true;
	}

	private void initSavedAssets() throws MaryConfigurationException {

		TextToSpeech.voiceInterruptor = false;

		if (savedOnL1 != null && Boolean.TRUE.equals(savedOnL1) && TextToSpeech.voiceInterruptor == false) {

			TextToSpeech.playVoice("Game loaded!");

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
			TextToSpeech.voiceInterruptor = true;
			savedOnL1 = false;
			return;
		}

		TextToSpeech.voiceInterruptor = false;

		if (savedOnL2 != null && Boolean.TRUE.equals(savedOnL2) && TextToSpeech.voiceInterruptor == false) {

			TextToSpeech.playVoice("Game loaded!");

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
			TextToSpeech.voiceInterruptor = true;
			savedOnL2 = false;
			return;
		}

		TextToSpeech.voiceInterruptor = false;

		if (savedOnL3 != null && Boolean.TRUE.equals(savedOnL3) && TextToSpeech.voiceInterruptor == false) {

			TextToSpeech.playVoice("Game loaded!");

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
			TextToSpeech.voiceInterruptor = true;
			savedOnL3 = false;
			return;
		}

		TextToSpeech.voiceInterruptor = false;

		if (savedOnL4 != null && Boolean.TRUE.equals(savedOnL4) && TextToSpeech.voiceInterruptor == false) {

			TextToSpeech.playVoice("Game loaded!");

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
			TextToSpeech.voiceInterruptor = true;
			savedOnL4 = false;
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