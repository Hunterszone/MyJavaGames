package menu_engine;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import menu_states.ControlsState;
import menu_states.ExtrasState;
import menu_states.ManualState;
import menu_states.MenuState;
import menu_states.SettingsState;
import menu_states.StateManager;
import util.Constants;
import util.TextToSpeech;

public class CanvasMenu implements Runnable {

	public static DisplayCanvas display;
	private BufferStrategy bs;
	private Graphics g;
	private Thread thread;
	private boolean isRunning;
	public static boolean isPause;
	public static ColorSwitcher color;
	public static ColorSwitcher color2;
	public static StateManager State;

	private MenuState mainMenu;
	private ControlsState controlsMenu;
	private SettingsState settingsMenu;
	private ExtrasState extrasMenu;
	private ManualState manualMenu;

	public CanvasMenu() {}

	private void init() {

		Constants.LOAD_ASSETS.init(); // initialize game assets
		TextToSpeech.initVoice(); // initialize supported voices
		
		display = new DisplayCanvas();
		display.getCanvas().addMouseListener(new MouseInputHandler());

		State = new StateManager();
		this.mainMenu = new MenuState();
		this.controlsMenu = new ControlsState();
		this.settingsMenu = new SettingsState();
		this.manualMenu = new ManualState();
		this.extrasMenu = new ExtrasState();
		
		color = new ColorSwitcher();
		color2 = new ColorSwitcher();
		color2.nextColor(color.getColor());
	}

	private void render() {
		
		this.bs = display.getCanvas().getBufferStrategy();

		if (this.bs == null) {
			display.getCanvas().createBufferStrategy(2);
			return;
		}
		
		this.g = this.bs.getDrawGraphics();
		this.g.clearRect(0, 0, DisplayCanvas.WIDTH, DisplayCanvas.HEIGHT);

        //Start Drawing
	    this.g.drawImage(Constants.LOAD_ASSETS.blackBG, 0,0, 1200, 800, null);

		if (State.getState() == StateManager.STATES.MENU) {
			this.mainMenu.render(this.g);
		} else if(State.getState() == StateManager.STATES.CONTROLS) {
            this.controlsMenu.render(g);
        } else if(State.getState() == StateManager.STATES.MANUAL) {
            this.manualMenu.render(g);
        }
        else if(State.getState() == StateManager.STATES.SETTINGS) {
            this.settingsMenu.render(g, color, color2);
        }
        else if(State.getState() == StateManager.STATES.EXTRAS) {
            this.extrasMenu.render(g);
        }

		// Stop Drawing
		this.g.dispose();
		if(this.bs != null) {			
			this.bs.show();
		}
	}

	@Override
	public void run() {
		this.init();

		while (isRunning && display.getCanvas().isDisplayable()) {
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
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

}
