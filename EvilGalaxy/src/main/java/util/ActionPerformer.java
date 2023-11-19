package util;

import java.util.Objects;

import javax.swing.Timer;

public class ActionPerformer {
	
	public static void setAnimator(Thread animator) {
		Constants.animator = animator;
	}

	public static Thread getAnimator() {
		return Constants.animator;
	}

	public static Timer getTimerMedium() {
		return Constants.timerMedium;
	}

	public static void setTimerMedium(Timer timerMedium) {
		Constants.timerMedium = Objects.requireNonNull(timerMedium);
	}

	public static Timer getTimerHard() {
		return Constants.timerHard;
	}

	public static void setTimerHard(Timer timerHard) {
		Constants.timerHard = Objects.requireNonNull(timerHard);
	}

	public static Timer getTimerEasy() {
		return Constants.timerEasy;
	}

	public static void setTimerEasy(Timer timerEasy) {
		Constants.timerEasy = Objects.requireNonNull(timerEasy);
	}

	public static boolean isIngame() {
		return Constants.isIngame;
	}

	public static void setIngame(boolean isIngame) {
		Constants.isIngame = isIngame;
	}
	
	public static String[] getCityAndCountry() {
		return LocationJsonExtractor.extractCityAndCountry();
	}

}
