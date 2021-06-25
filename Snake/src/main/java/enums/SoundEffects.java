package enums;

public enum SoundEffects {

	BITE {
		@Override
		public String getSound() {
			return "sounds/bite.wav";
		}
	},
	LEVEL_UP {
		@Override
		public String getSound() {
			return "sounds/levelup.wav";
		}
	},
	DIE {
		@Override
		public String getSound() {
			return "sounds/die.wav";
		}
	},
	RESTART {
		@Override
		public String getSound() {
			return "sounds/highsc.wav";
		}
	}, 
	BG_MUSIC {
		@Override
		public String getSound() {
			return "sounds/bgmusic.wav";
		}
	};

	public abstract String getSound();
}