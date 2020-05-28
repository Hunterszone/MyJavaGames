package menu_states;

public class StateManager {
	public enum STATES {
		GAME,
		CONTROLS,
		SETTINGS,
		PAUSE,
		MENU
	}

	public STATES getState() {

		return State;
	}

	public void setState(STATES state) {

		State = state;
	}

	private STATES State = STATES.MENU;

}
