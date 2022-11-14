package util;

import enums.SoundEffect;
import sound_engine.PlayWave2nd;

public abstract class LoadSounds {
	public static final PlayWave2nd BG_MUSIC = new PlayWave2nd(SoundEffect.BGMUSIC.getSound());
	public static final PlayWave2nd MENU_MUSIC = new PlayWave2nd(SoundEffect.MENUMUSIC.getSound());
	public static final PlayWave2nd TAUNT = new PlayWave2nd(SoundEffect.TAUNT.getSound());
	public static final PlayWave2nd FIN = new PlayWave2nd(SoundEffect.FIN.getSound());
	public static final PlayWave2nd HIGHSC = new PlayWave2nd(SoundEffect.HIGHSC.getSound());
	public static final PlayWave2nd GOT_HP = new PlayWave2nd(SoundEffect.MAGIC.getSound());
	public static final PlayWave2nd GOT_GOLD = new PlayWave2nd(SoundEffect.COLLECT.getSound());
	public static final PlayWave2nd HIT = new PlayWave2nd(SoundEffect.FUSE.getSound());
	public static final PlayWave2nd DENIED = new PlayWave2nd(SoundEffect.DENIED.getSound());
	public static final PlayWave2nd HOVER = new PlayWave2nd(SoundEffect.HOVER.getSound());
}