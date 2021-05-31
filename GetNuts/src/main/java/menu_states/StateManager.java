package menu_states;

public class StateManager{
	public enum STATES{
		GAME,
		CONTROLS,
		SETTINGS,
		EXTRAS,
		MANUAL,
		PAUSE,
		MENU
	}
	
	private STATES State = STATES.MENU;
	
	public STATES getState() {
		return State;
	}
	
	public void setState(STATES state) {
		State = state;
	}
	
}