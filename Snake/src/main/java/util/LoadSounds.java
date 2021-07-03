package util;

import java.util.Arrays;
import java.util.List;

import enums.SoundEffects;
import sound_engine.PlayWave2nd;

public abstract class LoadSounds {
	
	private LoadSounds() {}
	
	public static final PlayWave2nd LEVEL_UP = new PlayWave2nd(SoundEffects.LEVEL_UP.getSound());
	public static final PlayWave2nd GAME_OVER = new PlayWave2nd(SoundEffects.DIE.getSound());
	public static final PlayWave2nd BITE = new PlayWave2nd(SoundEffects.BITE.getSound());
	public static final PlayWave2nd RESTART = new PlayWave2nd(SoundEffects.RESTART.getSound());
	public static final PlayWave2nd BG_SOUND = new PlayWave2nd(SoundEffects.BG_MUSIC.getSound());
	
	public static final List<String> allSoundFiles = Arrays.asList(SoundEffects.LEVEL_UP.getSound(), SoundEffects.DIE.getSound(), 
			SoundEffects.BITE.getSound(), SoundEffects.RESTART.getSound(), SoundEffects.BG_MUSIC.getSound());
}