package util;

import enums.SoundEffects;
import sound_engine.PlayWave2nd;

public abstract class LoadSounds {
	public static PlayWave2nd bgMusic = new PlayWave2nd(SoundEffects.BGMUSIC.getSound());
	public static PlayWave2nd menuMusic = new PlayWave2nd(SoundEffects.MENUMUSIC.getSound());
	public static PlayWave2nd roar = new PlayWave2nd(SoundEffects.TAUNT.getSound());
	public static PlayWave2nd gameLost = new PlayWave2nd(SoundEffects.FIN.getSound());
	public static PlayWave2nd gameWon = new PlayWave2nd(SoundEffects.HIGHSC.getSound());
	public static PlayWave2nd gotHealthPack = new PlayWave2nd(SoundEffects.MAGIC.getSound());
	public static PlayWave2nd gotGoldBar = new PlayWave2nd(SoundEffects.COLLECT.getSound());
	public static PlayWave2nd fuse = new PlayWave2nd(SoundEffects.FUSE.getSound());
}