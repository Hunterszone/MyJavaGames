package util;

import enums.SoundEffects;
import sound_engine.PlayWave2nd;

public abstract class LoadSounds {
	public static PlayWave2nd levelUp = new PlayWave2nd(SoundEffects.LEVEL_UP.getSound());
	public static PlayWave2nd die = new PlayWave2nd(SoundEffects.DIE.getSound());
	public static PlayWave2nd bite = new PlayWave2nd(SoundEffects.BITE.getSound());
	public static PlayWave2nd restart = new PlayWave2nd(SoundEffects.RESTART.getSound());
	public static PlayWave2nd bgMusic = new PlayWave2nd(SoundEffects.BG_MUSIC.getSound());
}