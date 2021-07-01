package enums;

public enum SoundEffects {

	CLICK {
		@Override
		public String getSound() {
			return "sounds/reload.wav";
		}
	},
	FORBIDDEN {
		@Override
		public String getSound() {
			return "sounds/forbidden.wav";
		}
	},
	HOVER {
		@Override
		public String getSound() {
			return "sounds/hover.wav";
		}
	},
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