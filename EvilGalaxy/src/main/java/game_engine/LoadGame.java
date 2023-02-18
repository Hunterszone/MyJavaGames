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

import enemy.Alien;
import enemy.Bunker;
import enemy.Dragon;
import enemy.EvilHead;
import enemy.PlayerShip;
import items.Gold;
import items.HealthPack;
import main.Main;
import marytts.exceptions.MaryConfigurationException;
import menu_engine.DisplayCanvas;
import menu_engine.MouseInputHandler;
import util.Flags;
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
	
	public void openFileChooser() {
		final JFileChooser chooser = new JFileChooser(new File("./saves"));
		final FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
		final int returnVal = chooser.showOpenDialog(null);

		chooser.setFileFilter(filter);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				loadGameDataFromFile(chooser.getSelectedFile());
			} catch (final ClassNotFoundException cnf) {
				System.out.println(cnf.getMessage());
			} catch (final IOException ioe) {
				System.out.println(ioe.getMessage());
			}
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
		}
	}

	private void loadGameDataFromFile(File loadfile) throws ClassNotFoundException, IOException {

		LoadSounds.MENU_MUSIC.stop();
		LoadSounds.BG_MUSIC.stop();

		FileInputStream fileStream = null;
		ObjectInputStream objectStream = null;

		try {
			fileStream = new FileInputStream(loadfile);
			objectStream = new ObjectInputStream(fileStream);
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
		} catch (final Exception e) {
			e.getMessage();
		} finally {
			fileStream.close();
			objectStream.close();
		}

		Flags.isMenuOn = false;
		if (MouseInputHandler.main == null) {
			DisplayCanvas.frame.remove(DisplayCanvas.canvas);
			DisplayCanvas.frame.dispose();
			EventQueue.invokeLater(() -> {
				try {
					MouseInputHandler.main = new Main();
					initSavedAssets();
				} catch (IOException e1) {
					e1.getMessage();
				} catch (final MaryConfigurationException e) {
					e.getMessage();
				}
				MouseInputHandler.main.setVisible(true);
			});
		}
		Flags.isMenuOn = true;
	}

	private void initSavedAssets() throws MaryConfigurationException {

		InitObjects.setIngame(false);

		savedOnLevelOne();

		savedOnLevelTwo();

		savedOnLevelThree();

		savedOnLevelFour();

		EvilHead.evilHead = new EvilHead(500, 500);
		EvilHead.evilHead.isVisible();
		EvilHead.evilHead.AIOnEasy();

		Gold.goldstack.add(new Gold(150, 150));
		HealthPack.healthpacks.add(new HealthPack(400, 400));

	}

	private void savedOnLevelFour() {
		TextToSpeech.voiceInterruptor = false;

		if (savedOnL4 != null && Boolean.TRUE.equals(savedOnL4) && 
				TextToSpeech.voiceInterruptor == false) {

			onLoadPerDifficulty();

			Alien.aliens.clear();
			Dragon.dragons.clear();
			UpdateObjects.setLifeBunker(50);
			TextToSpeech.voiceInterruptor = true;
			savedOnL4 = false;
		
			return;
		}
	}

	private void savedOnLevelThree() {
		TextToSpeech.voiceInterruptor = false;

		if (savedOnL3 != null && Boolean.TRUE.equals(savedOnL3) && 
				TextToSpeech.voiceInterruptor == false) {

			onLoadPerDifficulty();

			Alien.aliens.clear();
			Dragon.dragons.clear();
			TextToSpeech.voiceInterruptor = true;
			savedOnL3 = false;
			
			return;
		}
	}

	private void savedOnLevelTwo() {
		TextToSpeech.voiceInterruptor = false;

		if (savedOnL2 != null && Boolean.TRUE.equals(savedOnL2) && 
				TextToSpeech.voiceInterruptor == false) {

			onLoadPerDifficulty();

			Alien.aliens.clear();
			Dragon.dragons = new ArrayList<>();
			for (int i = 0; i < 30 - UpdateObjects.getDragonKilled(); i++) {
				final Dragon born = new Dragon((int) Math.ceil(Math.random() * 4700),
						(int) Math.ceil(Math.random() * 800));
				Dragon.dragons.add(born);
			}

			System.out.println("Score after loading will be added to " + Dragon.class.getName());
			TextToSpeech.voiceInterruptor = true;
			savedOnL2 = false;
			
			return;
		}
	}

	private void savedOnLevelOne() {
		TextToSpeech.voiceInterruptor = false;

		if (savedOnL1 != null && Boolean.TRUE.equals(savedOnL1) && 
				TextToSpeech.voiceInterruptor == false) {

			onLoadPerDifficulty();

			Alien.aliens = new ArrayList<>();
			for (int i = 0; i < 40 - UpdateObjects.getAlienKilled(); i++) {
				final Alien born = new Alien((int) Math.ceil(Math.random() * 7000),
						(int) Math.ceil(Math.random() * 800));
				Alien.aliens.add(born);
			}

			System.out.println("Score after loading will be added to " + Alien.class.getName());
			TextToSpeech.voiceInterruptor = true;
			savedOnL1 = false;
			
			return;
		}
	}
	
	private void onLoadPerDifficulty() {
		TextToSpeech.playVoice("Game loaded!");

		if (Flags.isEPressed == true) {
			Flags.isMPressed = false;
			Flags.isHPressed = false;
			Difficulty.getEasyDiff();
			InitObjects.getTimerEasy().stop();
			LoadSounds.BG_MUSIC.stop();
			LoadSounds.TAUNT.stop();
		} else if (Flags.isMPressed == true) {
			Flags.isEPressed = false;
			Flags.isHPressed = false;
			Difficulty.getMediumDiff();
			InitObjects.getTimerMedium().stop();
			LoadSounds.BG_MUSIC.stop();
			LoadSounds.TAUNT.stop();
		} else if (Flags.isHPressed == true) {
			Flags.isEPressed = false;
			Flags.isMPressed = false;
			Difficulty.getHardDiff();
			InitObjects.getTimerHard().stop();
			LoadSounds.BG_MUSIC.stop();
			LoadSounds.TAUNT.stop();
		} else {
			Difficulty.restart();
		}
	}
}