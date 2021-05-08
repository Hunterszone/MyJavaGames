package util;

import java.util.Set;

import javax.sound.sampled.AudioInputStream;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.AudioPlayer;

public class TextToSpeech {

	public static boolean finMusicIsPlayed;
	public static boolean voiceInterruptor = false;
	private static MaryInterface marytts = null;
	static Set<String> voices = null;

	public static void initVoice() {

		try {
			marytts = new LocalMaryInterface();
		} catch (final MaryConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (marytts != null) {
			voices = marytts.getAvailableVoices();
		}

		System.out.println("Available voices: " + voices);

		if (voices != null && !voices.isEmpty()) {
			for (final String voice : voices) {
				if (voice != null && !voice.isEmpty()) {
					marytts.setVoice(voice);
//					marytts.setAudioEffects("JetPilot");
				}
			}
		}
	}

	public static void playVoice(String message) {

		if (message == null || message.isEmpty()) {
			return;
		}
		
		try {
			final AudioInputStream audio = marytts.generateAudio(message);
			final AudioPlayer player = new AudioPlayer(audio);
			player.start();
		} catch (final SynthesisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
