package menu_engine;

import menu_engine.Display;
import menu_states.*;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class CanvasMenu implements Runnable {

	private Display display;
	private BufferStrategy bs;
	private Graphics g;
	private Thread thread;
	private boolean isRunning;
	public static boolean isPause;

	private MenuState mainMenu;
	private SettingsState settingsMenu;
	
	public static StateManager State;

	public CanvasMenu() {}

	private void init() {

		Constants.LOAD_ASSETS.init();

		this.display = new Display();
		this.display.getCanvas().addMouseListener(new MouseInputHandler());

		State = new StateManager();
		this.mainMenu = new MenuState();
		this.settingsMenu = new SettingsState();
	}

	private void render() {
		this.bs = this.display.getCanvas().getBufferStrategy();

		if (this.bs == null) {
			this.display.getCanvas().createBufferStrategy(2);
			return;
		}
		this.g = this.bs.getDrawGraphics();
		this.g.clearRect(0, 0, Display.WIDTH, Display.HEIGHT);

        //Start Drawing
	    this.g.drawImage(Constants.LOAD_ASSETS.blackBG, 0,0, 1200, 800, null);

		if (State.getState() == StateManager.STATES.MENU) {
			this.mainMenu.render(this.g);
		} else if(State.getState() == StateManager.STATES.SETTINGS) {
            this.settingsMenu.render(g);
        }

		// Stop Drawing
		this.g.dispose();
		this.bs.show();
	}

	@Override
	public void run() {
		this.init();

		while (isRunning) {
			this.render();
		}

		this.stop();
	}

	public synchronized void start() {
		this.thread = new Thread(this);

		this.isRunning = true;
		this.thread.start();
	}

	public synchronized void stop() {
		try {
			this.isRunning = false;
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
