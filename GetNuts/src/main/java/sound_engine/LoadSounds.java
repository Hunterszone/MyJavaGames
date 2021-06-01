package sound_engine;

import enums.SoundEffects;

public abstract class LoadSounds {

	public static final PlayWave2nd BG_MUSIC = new PlayWave2nd(SoundEffects.BACKMUSIC.getSound());
	public static final PlayWave2nd NEGATIVE = new PlayWave2nd(SoundEffects.DENIED.getSound());
	public static final PlayWave2nd BOING = new PlayWave2nd(SoundEffects.BOING.getSound());
	public static final PlayWave2nd MOVE = new PlayWave2nd(SoundEffects.MOVE.getSound());
	public static final PlayWave2nd DEAD = new PlayWave2nd(SoundEffects.DEAD.getSound());
	public static final PlayWave2nd HIGHSC = new PlayWave2nd(SoundEffects.HIGHSC.getSound());
	public static final PlayWave2nd CLICK = new PlayWave2nd(SoundEffects.CLICK.getSound());
}
