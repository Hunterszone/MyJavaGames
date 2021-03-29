package util;

import enums.SoundEffects;
import sound_engine.PlayWave2nd;

public abstract class LoadSounds {
	public static final PlayWave2nd BG_MUSIC = new PlayWave2nd(SoundEffects.BGMUSIC.getSound());
	public static final PlayWave2nd MENU_MUSIC = new PlayWave2nd(SoundEffects.MENUMUSIC.getSound());
	public static final PlayWave2nd TAUNT = new PlayWave2nd(SoundEffects.TAUNT.getSound());
	public static final PlayWave2nd FIN = new PlayWave2nd(SoundEffects.FIN.getSound());
	public static final PlayWave2nd HIGHSC = new PlayWave2nd(SoundEffects.HIGHSC.getSound());
	public static final PlayWave2nd GOT_HP = new PlayWave2nd(SoundEffects.MAGIC.getSound());
	public static final PlayWave2nd GOT_GOLD = new PlayWave2nd(SoundEffects.COLLECT.getSound());
	public static final PlayWave2nd HIT = new PlayWave2nd(SoundEffects.FUSE.getSound());
	public static final PlayWave2nd DENIED = new PlayWave2nd(SoundEffects.DENIED.getSound());
}