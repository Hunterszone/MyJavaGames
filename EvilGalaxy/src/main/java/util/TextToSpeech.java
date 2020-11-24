package util;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech {

	public static boolean finMusicIsPlayed;
	public static boolean voiceInterruptor = false;
	static boolean voiceStopped;
	static Voice voice;

	public static void initVoice(String message) {
		VoiceManager vm = VoiceManager.getInstance();
		voice = vm.getVoice(Constants.VOICENAME);
		if (voice != null) {
			voice.allocate();
			voice.speak(message);
		}

		if (voiceStopped == false) {
			voiceStopped = true;
		}
	}

}
