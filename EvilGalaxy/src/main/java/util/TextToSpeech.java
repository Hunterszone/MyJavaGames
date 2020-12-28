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

	public static void initVoice(String message) {

		try {
			marytts = new LocalMaryInterface();
		} catch (MaryConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		final Set<String> voices = marytts.getAvailableVoices();

		System.out.println("Available voices: " + voices);

		if (voices != null && !voices.isEmpty()) {
			marytts.setVoice(voices.iterator().next());
		}

		if (message.isEmpty()) {
			return;
		}

		try {
			AudioInputStream audio = marytts.generateAudio(message);
			final AudioPlayer player = new AudioPlayer(audio);
			player.start();
		} catch (SynthesisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
