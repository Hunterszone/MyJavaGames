package game_engine;

public enum SoundEffects {
	BGMUSIC {
		@Override
		public String getSound() {
			return "res/sounds/bgmusic.wav";
		}
	},
	COLLECT {
		@Override
		public String getSound() {
			return "res/sounds/collect.wav";
		}
	},
	DENIED {
		@Override
		public String getSound() {
			return "res/sounds/denied.wav";
		}
	},
	DING {
		@Override
		public String getSound() {
			return "res/sounds/ding.wav";
		}
	},
	EXPLOSION {
		@Override
		public String getSound() {
			return "res/sounds/explosion.wav";
		}
	},
	FOOTSTEPS {
		@Override
		public String getSound() {
			return "res/sounds/footsteps.wav";
		}
	},
	HIGHSC {
		@Override
		public String getSound() {
			return "res/sounds/highsc.wav";
		}
	},
	HUH {
		@Override
		public String getSound() {
			return "res/sounds/huh.wav";
		}
	},
	MAGIC {
		@Override
		public String getSound() {
			return "res/sounds/magic.wav";
		}
	},
	QUACK {
		@Override
		public String getSound() {
			return "res/sounds/quack.wav";
		}
	};
	public abstract String getSound();
}