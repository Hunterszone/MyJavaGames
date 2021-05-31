package menu_engine;

import menu_states.ControlsState;
import menu_states.MenuState;

public class CanvasMenu implements Runnable {
	public static DisplayCanvas display;
	//check the menu_states package to make sure images are drawn to spec
	//public static StateManager State;
	
	private Thread thread;
	
	private Boolean isRunning;
	
	private MenuState mainMenu;
	
	public CanvasMenu() {}
	
	private void init() {
		//initialize sounds?
		
		display = new DisplayCanvas();
		//necessary for mouse display so be sure to make a mouseInputHandler class
		//display.getCanvas().addMouseListener(new MouseInputHandler());
		
		//State = new StateManager();
		this.mainMenu = new MenuState();
		this.controlsMenu = new ControlsState();
		this.settingsMenu = new SettingsState();
		
	}
	
	@Override
	public void run() {
		this.init();
		
		while (isRunning && display.getCanvas().isDisplayable()) {
			//render is another menu_States function
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
		try{
			this.isRunning = false;
			this.thread.join();
		} catch(final InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}