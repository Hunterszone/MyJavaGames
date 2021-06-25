package util;

import enums.SoundEffects;
import sound_engine.PlayWave2nd;

public abstract class LoadSounds {
	
	private LoadSounds() {}
	
	public static final PlayWave2nd levelUp = new PlayWave2nd(SoundEffects.LEVEL_UP.getSound());
	public static final PlayWave2nd die = new PlayWave2nd(SoundEffects.DIE.getSound());
	public static final PlayWave2nd bite = new PlayWave2nd(SoundEffects.BITE.getSound());
	public static final PlayWave2nd restart = new PlayWave2nd(SoundEffects.RESTART.getSound());
	public static final PlayWave2nd bgMusic = new PlayWave2nd(SoundEffects.BG_MUSIC.getSound());
}