package resources;

public enum Sounds {
	
	COLLECTED {
		@Override
		public String getSound() {
			return "res/sounds/collect.wav";
		}
	},
	BOOM {
		@Override
		public String getSound() {
			return "res/sounds/explosion.wav";
		}
	},
	GAME_OVER {
		@Override
		public String getSound() {
			return "res/sounds/failure.wav";
		}
	},
	VICTORY {
		@Override
		public String getSound() {
			return "res/sounds/victory.wav";
		}
	},
	BG_MUSIC {
		@Override
		public String getSound() {
			return "res/sounds/bgmusic.wav";
		}
	};
	
	public abstract String getSound();
}
