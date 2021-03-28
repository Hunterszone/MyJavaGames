package sound_engine;

import enums.SoundEffects;

public abstract class LoadSounds {

	public static PlayWave2nd bgMusic = new PlayWave2nd("sounds/backmusic.wav");
	public static PlayWave2nd negative = new PlayWave2nd(SoundEffects.DENIED.getSound());

}
