package menu_states;

public class StateManager {
	public enum STATES {
		GAME,
		CONTROLS,
		SETTINGS,
		EXTRAS,
		MANUAL,
		PAUSE,
		MENU
	}

	public STATES getState() {
		return state;
	}

	public void setState(STATES state) {
		this.state = state;
	}

	private STATES state = STATES.MENU;

}
